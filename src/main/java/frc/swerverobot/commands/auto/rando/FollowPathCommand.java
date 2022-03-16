package frc.swerverobot.commands.auto.rando;
/**
 *  Broken, working on PathCommand for now
 */

// package frc.swerverobot.commands;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.swerverobot.subsystems.*;
// import org.frcteam2910.common.control.Path;
// import org.frcteam2910.common.control.SimplePathBuilder;
// import org.frcteam2910.common.control.Trajectory;
// import org.frcteam2910.common.math.RigidTransform2;
// import org.frcteam2910.common.math.Rotation2;
// import org.frcteam2910.common.math.Vector2;

// public class FollowPathCommand extends CommandBase{
//     private final DrivetrainSubsystem drivetrain;

//     private boolean shouldRegeneratePaths = true;

//     public FollowPathCommand(DrivetrainSubsystem drivetrain) {
//         this.drivetrain = drivetrain;

//         addRequirements(drivetrain);
//     }

//     @Override
//     public void initialize() {
//         shouldRegeneratePaths = true;
//     }

//     @Override
//     public void execute() {
//         if (!shouldRegeneratePaths){
//             return;
//         }

//         Vector2 startingPosition = drivetrain.getPose().translation;
//         Rotation2 startingRotation = drivetrain.getPose().rotation;
// /*        Vector2 predictedPosition = vision.getPredictedPostition();
//         // We don't want to generate a path at is way too large due to bad vision data, so truncate it to a maximum distance
//         if (predictedPosition.length > 240.0) {
//             predictedPosition = predictedPosition.normal().scale(240.0);
//         }

//         if (predictedPosition.x < 40.0) {
//             shouldRegeneratePaths = false;
//         }

//         Vector2 goal = new Vector2(20.0, 0.0);
//         switch (loadingStationSide.get()) {
//             case LEFT:
//                 goal = new Vector2(20.0, 22.0);
//                 break;
//             case RIGHT:
//                 goal = new Vector2(20.0, -22.0);
//                 break;
//         }
// */
//         Path path = new SimplePathBuilder(startingPosition, startingRotation)
//                 .lineTo(new Vector2(startingPosition.x, startingPosition.y + 1.0))
//                 .build();

//         double startingVelocity = drivetrain.getVelocity().length;
//         Trajectory.State lastState = drivetrain.getFollower().getLastState();
//         if (lastState != null) {
//             startingVelocity = lastState.getVelocity();
//         }

//         Trajectory trajectory = new Trajectory(path, DrivetrainSubsystem.TRAJECTORY_CONSTRAINTS, 1.0);
// //        drivetrain.resetPose(new RigidTransform2(startingPosition, drivetrain.getPose().rotation));
//         drivetrain.getFollower().follow(trajectory);
//     }

//     @Override
//     public void end(boolean interrupted) {
//         drivetrain.getFollower().cancel();
//         drivetrain.drive(Vector2.ZERO, 0.0, false);
//     }
// }
