/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

#include <unistd.h>
#include <iostream>
#include <librealsense2/rs.hpp>
#include "csrv.h"
#include <frc/shuffleboard/Shuffleboard.h>
#include <frc/shuffleboard/ShuffleboardTab.h>

#define POST_VELOCITIES 1
#define POST_ACCELERATIONS 1

int main(int argc, char* argv[]) {
	if (csrvInit(NULL, NULL) != 0)
		return -1;

	char fname[128];
	FILE *trajf = NULL;
	for(int i = 0; i < 1000; i++) {
		snprintf(fname, sizeof(fname), "/mnt/rw/trajectories/t%06d.txt", i);
		trajf = fopen(fname, "wx");
		if (trajf != NULL)
			break;
	}

	frc::ShuffleboardTab& tab = frc::Shuffleboard::GetTab("T265");
	nt::NetworkTableEntry xent = tab.Add("X", 0.0).GetEntry();
//	nt::NetworkTableEntry yent = tab.Add("Y", 0.0).GetEntry();
	nt::NetworkTableEntry zent = tab.Add("Z", 0.0).GetEntry();
//	nt::NetworkTableEntry pent = tab.Add("Pitch", 0.0).GetEntry();
//	nt::NetworkTableEntry rent = tab.Add("Roll", 0.0).GetEntry();
	nt::NetworkTableEntry went = tab.Add("Yaw", 0.0).GetEntry();
	nt::NetworkTableEntry tent = tab.Add("Time", 0.0).GetEntry();

#if POST_VELOCITIES
	nt::NetworkTableEntry vxent = tab.Add("VX", 0.0).GetEntry();
//	nt::NetworkTableEntry vyent = tab.Add("VY", 0.0).GetEntry();
	nt::NetworkTableEntry vzent = tab.Add("VZ", 0.0).GetEntry();
//	nt::NetworkTableEntry rvxent = tab.Add("RVX", 0.0).GetEntry();
	nt::NetworkTableEntry rvyent = tab.Add("RVY", 0.0).GetEntry();
//	nt::NetworkTableEntry rvzent = tab.Add("RVZ", 0.0).GetEntry();
#endif

#if POST_ACCELERATIONS
	nt::NetworkTableEntry axent = tab.Add("AX", 0.0).GetEntry();
//	nt::NetworkTableEntry ayent = tab.Add("AY", 0.0).GetEntry();
	nt::NetworkTableEntry azent = tab.Add("AZ", 0.0).GetEntry();
//	nt::NetworkTableEntry raxent = tab.Add("RAX", 0.0).GetEntry();
	nt::NetworkTableEntry rayent = tab.Add("RAY", 0.0).GetEntry();
//	nt::NetworkTableEntry razent = tab.Add("RAZ", 0.0).GetEntry();
#endif

	while (1) try {
		rs2::pipeline pipe;
		rs2::config conf;
		conf.enable_stream(RS2_STREAM_POSE, RS2_FORMAT_6DOF);
		rs2::pipeline_profile profile = pipe.start(conf);
		rs2::device dev = profile.get_device();
		auto sensor = dev.first<rs2::pose_sensor>();

		double tstart = 0.0;
		double lastprint;
		if (trajf != NULL)
			fprintf(trajf, "--------\n");

		while (1) {
			rs2::frameset frames = pipe.wait_for_frames(1000);
			auto frame = frames.first_or_default(RS2_STREAM_POSE);
			auto fp = frame.as<rs2::pose_frame>();
			auto pd = fp.get_pose_data();

			auto tstamp = fp.get_timestamp();
			if (tstart == 0.0) {
				tstart = tstamp;
//				lastprint = tstart;
			}

/*
			printf("%6.0f %6.3f %6.3f %6.3f %g %g %g %g %g %g %g %g %g %g\n",
				fp.get_timestamp() - tstart, pd.translation.x, pd.translation.y, pd.translation.z,
				pd.rotation.x, pd.rotation.y, pd.rotation.z, pd.rotation.w,
				pd.velocity.x, pd.velocity.y, pd.velocity.z,
				pd.acceleration.x, pd.acceleration.y, pd.acceleration.z);
*/

			auto q = pd.rotation;
			auto w = pd.rotation.w;
			auto x = -pd.rotation.z;
			auto y = pd.rotation.x;
			auto z = -pd.rotation.y;

			auto pitch =  -asin(2.0 * (x*z - w*y));// * 180.0 / M_PI;
			auto roll  =  atan2(2.0 * (w*x + y*z), w*w - x*x - y*y + z*z);// * 180.0 / M_PI;
			auto yaw   =  atan2(2.0 * (w*z + x*y), w*w + x*x - y*y - z*z);// * 180.0 / M_PI;

//			printf("yaw %f\n", yaw * 180 / M_PI);
			xent.SetDouble(pd.translation.x);
//			yent.SetDouble(pd.translation.y);
			zent.SetDouble(pd.translation.z);
//			pent.SetDouble(pitch);
//			rent.SetDouble(roll);
			went.SetDouble(yaw);
			tent.SetDouble((tstamp - tstart)/1000.0);

#if POST_VELOCITIES
			vxent.SetDouble(pd.velocity.x);
//			vyent.SetDouble(pd.velocity.y);
			vzent.SetDouble(pd.velocity.z);
//			rvxent.SetDouble(pd.angular_velocity.x);
			rvyent.SetDouble(pd.angular_velocity.y);
//			rvzent.SetDouble(pd.angular_velocity.z);
#endif

#if POST_ACCELERATIONS
			axent.SetDouble(pd.acceleration.x);
//			ayent.SetDouble(pd.acceleration.y);
			azent.SetDouble(pd.acceleration.z);
//			raxent.SetDouble(pd.angular_acceleration.x);
			rayent.SetDouble(pd.angular_acceleration.y);
//			razent.SetDouble(pd.angular_acceleration.z);
#endif

			// print once every hundred of a second
			if (lastprint + 100 > tstamp)
				continue;

			if (trajf != NULL) {
				fprintf(trajf, "%6.3f %5.3f %5.3f %4.1f\n", (tstamp - tstart) / 1000, x, z, yaw);
			}
				
			lastprint = tstamp;

#if 0
			if (mapsave) {
				std::unique_lock<std::mutex> lck (mapmtx);
				pipe.stop();

				printf("waiting for the map to be prepared...\n");
				usleep(6000000);
				auto lmap = sensor.export_localization_map();
				int fd = open("rsmap.bin", O_CREAT | O_RDWR, 0775);
				if (fd >= 0) {
					write(fd, lmap.data(), lmap.size());
					close(fd);
					fprintf(stderr, "rsmap saved: %d bytes\n", lmap.size());
				} else {
					perror("open");
				}
				mapsave = 0;
				return 0;
			}
#endif
		} 
	} catch (const rs2::error & e) {
		std::cerr << "RealSense error calling " << e.get_failed_function() << "(" << e.get_failed_args() << "):\n    " << e.what() << std::endl;

		// sleep for a second, and retry
		usleep(100);
	} catch (const std::exception& e) {
		// not sure if that can even happen, but anyway...
		// sleep for a second, and retry
		usleep(100);
	}
}
