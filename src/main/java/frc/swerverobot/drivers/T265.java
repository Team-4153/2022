package frc.swerverobot.drivers;

//import com.kauailabs.navx.frc.AHRS;
//import edu.wpi.first.wpilibj.SPI;
import org.frcteam2910.common.drivers.Gyroscope;
import org.frcteam2910.common.math.RigidTransform2;
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;

public final class T265  {
    private static final double M2I = 1/0.0254;
    // translation
    private NetworkTableEntry ntX;
 //   private NetworkTableEntry ntY;
    private NetworkTableEntry ntZ;

    // rotation
 //   private NetworkTableEntry ntPitch;
 //   private NetworkTableEntry ntRoll;
    private NetworkTableEntry ntYaw;

    // velocity
    private NetworkTableEntry ntVX;
 //   private NetworkTableEntry ntVY;
    private NetworkTableEntry ntVZ;

    // rotational velocity
 //   private NetworkTableEntry ntRVX;
    private NetworkTableEntry ntRVY;
 //   private NetworkTableEntry ntRVZ;

    // acceleration
    private NetworkTableEntry ntAX;
 //   private NetworkTableEntry ntAY;
    private NetworkTableEntry ntAZ;
    
    // rotational acceleration
 //   private NetworkTableEntry ntRAX;
    private NetworkTableEntry ntRAY;
 //   private NetworkTableEntry ntRAZ;

    // zero pose
    private double zeroX, /*zeroY,*/ zeroZ;
    private double /*zeroPitch, zeroRoll,*/ zeroYaw;

    public T265() {
        // T265.Z is robot.X, T265.X is robot.Y, T265.Pitch is robot.Pitch
        ShuffleboardTab tab = Shuffleboard.getTab("T265");

        ntX = tab.add("X", 0.0).getEntry();
//        ntY = tab.add("Y", 0.0).getEntry();
        ntZ = tab.add("Z", 0.0).getEntry();

//        ntPitch = tab.add("Pitch", 0.0).getEntry();
//        ntRoll = tab.add("Roll", 0.0).getEntry();
        ntYaw = tab.add("Yaw", 0.0).getEntry();

        ntVX = tab.add("VX", 0.0).getEntry();
//        ntVY = tab.add("VY", 0.0).getEntry();
        ntVZ = tab.add("VZ", 0.0).getEntry();

//        ntRVX = tab.add("RVX", 0.0).getEntry();
        ntRVY = tab.add("RVY", 0.0).getEntry();
//        ntRVZ = tab.add("RVZ", 0.0).getEntry();

        ntAX = tab.add("AX", 0.0).getEntry();
//        ntAY = tab.add("AY", 0.0).getEntry();
        ntAZ = tab.add("AZ", 0.0).getEntry();

//        ntRAX = tab.add("RAX", 0.0).getEntry();
        ntRAY = tab.add("RAY", 0.0).getEntry();
//        ntRAZ = tab.add("RAZ", 0.0).getEntry();
        reset();
    }

    public void reset() {
        zeroX = ntX.getDouble(0.0);
//        zeroY = ntY.getDouble(0.0);
        zeroZ = ntZ.getDouble(0.0);
//        zeroRoll = ntRoll.getDouble(0.0);
//        zeroPitch = ntPitch.getDouble(0.0);
        zeroYaw = ntYaw.getDouble(0.0);
    }

    public void setRotation(double set) {
        zeroYaw = set;
    }

    public Vector2 getTranslation() {
        return new Vector2(M2I*(ntZ.getDouble(0.0) - zeroZ), M2I*(ntX.getDouble(0.0) - zeroX));
    }

    public double getRotation() {
        return -ntYaw.getDouble(0.0) + zeroYaw;
    }

    public RigidTransform2 getPose() {
        return new RigidTransform2(getTranslation(), Rotation2.fromRadians(getRotation()));
    }

    public Vector2 getVelocity() {
        return new Vector2(M2I*ntVZ.getDouble(0.0), M2I*ntVX.getDouble(0.0));
    }

    public double getAngularVelocity() {
        return ntRVY.getDouble(0.0);
    }

    public Vector2 getAcceleration() {
        return new Vector2(M2I*ntAZ.getDouble(0.0), M2I*ntAX.getDouble(0.0));
    }

    public double getAngularAcceleration() {
        return ntRAY.getDouble(0.0);
    }
}
