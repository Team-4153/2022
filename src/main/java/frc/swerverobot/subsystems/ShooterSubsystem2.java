package frc.swerverobot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Smart Dashboard (For Varaibles)
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Robot Map
import static frc.swerverobot.RobotMap.*;

//Motor Controlers
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class ShooterSubsystem2 extends SubsystemBase {
    //     ----Variable Initialization----
    private final Spark topMotor;
    private final Spark botMotor;

    //     ----Motor Declarations----                   [Fully Functional]
    public ShooterSubsystem2() {
        this.topMotor = new Spark(TopMotorPort); //Defines the top motor with the port from the RobotMap
        this.botMotor = new Spark(BottomMotorPort); //Defines the bottom motor with the port from the RobotMap
    }

    //     ----Distance Adjustments----                 [Needs Testing]
    public static double lerp(double from, double to, double progress) {
        return from + ((to - from) * progress);
    }
    public double[] SetMotorDistance(boolean highLow) {
        if (highLow) {
            double[][] MotorSpeedHighGoal = {
                //Cant make it into high goal from 100 distance
                {133,-0.65,0.7,-1},
                {163,-0.6,0.7,-1},
                {183,-0.67,0.77,-1},
                {215,-0.75,0.85,-1},
                {250,-1,1,-1}
            };
            return SetMotorDistanceCalc(MotorSpeedHighGoal);
        }
        else {
            double[][] MotorSpeedLowGoal = {
                {77,-0.4,0.5,-1},
                {117,-0.45,0.55,-1},
                {153,-0.5,0.6,-1},
                {172,-0.55,0.65,-1}
            };
            return SetMotorDistanceCalc(MotorSpeedLowGoal);
        }
    }
    public double[] SetMotorDistanceCalc(double[][] MotorSpeed){
        double distance = SmartDashboard.getNumber("ShooterTarget/TargetDistance", 0);

        //0 = Top Motor, 1 = Bottom Motor, 2 = Feed Motor
        double[] SpeedsToSet = {DEFAULT_TOP_MOTOR_SPEED,DEFAULT_BOTTOM_MOTOR_SPEED,DEFAULT_FEED_MOTOR_SPEED};

        double progress = 0.5;

        if (distance != 0) {
            int idx;
            for (idx = 0; idx < MotorSpeed.length; idx++) {
                if (distance < MotorSpeed[idx][0]) {
                    break;
                }
            }
            SmartDashboard.putNumber("idx", idx);

            if (idx == 0 || idx == MotorSpeed.length) {
                return null;
            }

            double[] lowDistance = MotorSpeed[idx - 1];
            double[] highDistance = MotorSpeed[idx];

            for (int i = 0; i < SpeedsToSet.length; i++) {
                //0 = Top Motor, 1 = Bottom Motor, 2 = Feed Motor
                //Accepts 0-1 for progress 0 is first 1 is second
                progress = (distance-lowDistance[0])/(highDistance[0]-lowDistance[0]);
                SpeedsToSet[i] = lerp(lowDistance[i+1], highDistance[i+1], progress);
            }
        }
        SmartDashboard.putNumber("Top Motor Calculated Speed", SpeedsToSet[0]);
        SmartDashboard.putNumber("Bottom Motor Calculated Speed", SpeedsToSet[1]);
        SmartDashboard.putNumber("Feed Motor Calculated Speed", SpeedsToSet[2]);
        return SpeedsToSet;
    }

    //      ----Shooter Motor Functions----             [SMS:Fully Functional, FMS: Fully Functional, PL: Fully Functional]
    public void setShootMotorSpeeds(double topSpeed, double botSpeed) {
        SmartDashboard.putBoolean("Shooting", topSpeed != 0);
        //Sets the speed of the top and bottom motors to the inputed speed variables
	    topMotor.set(motorPowerLimits(topSpeed)); //Sets the top motor speed to the topSpeed variable (Range: -1 to 1)
	    botMotor.set(motorPowerLimits(botSpeed)); //Sets the bottom motor speed to the botSpeed variable (Range: -1 to 1)
    }
    public void setFeedMotorSpeeds(double feedSpeed) {
        //Sets the feed motor speed to the inputed feedSpeed variable (Range: -1 to 1)
    	feedMotor.set(motorPowerLimits(feedSpeed)); //Sets the feed motor speed to the feedSpeed variable (Range: -1 to 1)
    }
    public double motorPowerLimits(double speed) {
        //Limits the motor speed to the range of -1 to 1
        if (speed < -1) {
            return -1;//If the speed is less than -1, return -1
        }
        else if (speed > 1) {
            return 1;//If the speed is greater than 1, return 1
        }
        else {
            return speed;//If the speed is between -1 and 1, return the speed
        }
    }

    //      ----Dashboard Functions----                 [DV:Fully Functional]
    public void pushDashboardVars() {
        //Pushes the variables to the SmartDashboard
        SmartDashboard.putNumber("Top Motor Speed", topMotor.get());
        SmartDashboard.putNumber("Bottom Motor Speed", botMotor.get());
        SmartDashboard.putNumber("Feed Motor Speed", feedMotor.get());
    }

    //      ----Init & Periodic Functions----           [Fully Functional]
    public void disableMotorSafeties() {
        feedMotor.setSafetyEnabled(false);
        topMotor.setSafetyEnabled(false);
        botMotor.setSafetyEnabled(false);
    }
    @Override
    public void periodic() {
        pushDashboardVars();
    }
}

/*      ----Notes----
    - Motors
        - Prototype used 2 launch motors and 1 feed motor
        - Balls are Stored with feed Motor
    - Distance to target with machine vision (Rasberry pie with network cable?

    - Notes from Prototype Testing
        - With Current Motors and angle of ~30 degrees power of 0.6T 0.6B works well to get it into the goal from distance
        - From Close up adding backspin helps
*/

/*      ----Electronics---- 
    - Motors (3|Functional)
        - 2 for shooter
        - 1 for feed
    - Motor Controllers (3|Functional)
        - 1 for each motor in the shooter
        - Spark Motro Controllers
    - Color Sensor (1|Functional)
        - Detect first ball color and count
    - Photo Eye (1|Functional)
        - Detect if there is a second ball
        - Detect if there is only a second ball and not a first one
    - Machine Vision
        - Camera (1?)
        - Rasberry Pie (1)
            - Figure out network tables
        - Needs to be able to Detect high goal & Distance to hub
            - Detect distance to hub for shooting processs #2 and #3
            - If distance to hub is not viable we need to find some way of reliably detecting distance to hub
                - Things other than machine vision might need to be aimed upwards because the bottom section is not uniform maybe aim for middle of the lower hoop from edge of the field?
    - PID Controller (Being Added?)
        - Keep flywheels at constant speed
        - Might not be needed
*/

/*      ----Driver Interaction----
    All controls should be on Noahs controller
    - shootingProcess1(X:Needs Testing) - Shoots balls this will use values from auto aim to shoot, The driver can also manually change these values with 
    - shootingProcess2(Not Needed) - Auto aims the robot but doesent shoot, the boolean is for aiming for the high or low goal (true = High || false = Low)
    - shootingProcess3(Right Trigger for High Goa0l:Needs Testing | Left Trigger for Low Goal:Needs Testing) - Auto aims the robot and shoots, the boolean is for aiming for the high or low goal (true = High || false = Low)
    - manualShooterDistanceIncrease(Y:Functional) - Increases the power to both shooter motors by 5%, doesent shoot balls
    - manualShooterDistanceDecrease(A:Functional) - Decrease the power to both shooter motors by 5%, doesent shoot balls
    - dropBall(B:Needs Testing) - Drops the first ball in the system
*/

/*      ----Processes (Outdated|Shooter Subsystem #1)----
    - Shooting Process #1 (No Aim Assist & 2 Balls | Needs Testing)
        - If Process isnt already running
        - Driver Preses Shoot Button or Activated by code (Start Function)
        - Spin bottom & top Motors to variables (This value can be changed by the driver manually or buy the aim program)
        - Wait ~0.5 Seconds
        - Activate Feed Motor to release first Ball (20% power worked in testing)
        - Wait ~0.1 Seconds
        - Stop Feed Motor
        - Use Color Sensor to detect if there is still a ball in the first slot
        - If there is a ball
            - Wait for wheels to regain lost speed ~0.5s
            - Turn Feed Motor to release second Ball
            - Wait for ball to exit shooter ~0.1s
        - Stop both intake motors
        - End
        
    - Shooting Process #2 (Aim Assist & 2 Balls | Needs Testing)
        - If Process isnt already running
        - Driver should position robot roughly aming at the hub before running program
        - Driver Preses Aim Button or Activated by code high or low (Maybe a button combination for the low goal) (Start Function)
        - Machiene vision to find the reflective tape on high goal
        - Rotate Robot to face hub
        - Wait Until facing hub
        - Use Distance sensor or Machine Vision to detect distance to hub 
        - If high goal
            - Ajust power of top and bottom motor to get required distance (Using high goal Calibration)
            - If distance is not possible
                - Notify Driver (Maybe a beep or just something on the HUD)
        - else
            - Ajust power of top and bottom motor to get required distance (Using low goal Calibration)
            - If distance is not possible
                - Notify Driver (Maybe a beep or just something on the HUD)
        - End
        
    - Shooting Process #3 (Auto Aim/Auto Shoot | Needs Testing)
        - If Process isnt already running
        - Driver should position robot roughly aming at the hub before running program
        - Driver Preses Auto Shoot Button for either high or low (Maybe a button combination for the low goal)
        - Run Shooting Process #2 (Input High Low Goal)
        - Wait until #2 finished
        - Run Shooting Process #1 
        - End
    - Drop Balls
        - If Process isnt already running
        - Driver presses button
        - Gently spin shoot motors
        - Push forward first ball
        - Wait 0.2 Seconds
        - Stop All Motors
        - End
*/
