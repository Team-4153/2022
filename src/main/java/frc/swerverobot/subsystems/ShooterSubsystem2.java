package frc.swerverobot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Smart Dashboard (For Varaibles)
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Robot Map
import static frc.swerverobot.RobotMap.*;

//Motor Controlers
import edu.wpi.first.wpilibj.motorcontrol.Spark;

// Color Sensor
import edu.wpi.first.wpilibj.util.Color;

public class ShooterSubsystem2 extends SubsystemBase {
    //     ----Variable Initialization----
    private final Spark topMotor;
    private final Spark botMotor;
    private final Spark feedMotor;
    private int ballCount = 0;
    private boolean ballStuck = false;

    //     ----Motor Declarations----                   [Fully Functional]
    public ShooterSubsystem2() {
        this.topMotor = new Spark(TopMotorPort); //Defines the top motor with the port from the RobotMap
        this.botMotor = new Spark(BottomMotorPort); //Defines the bottom motor with the port from the RobotMap
        this.feedMotor = new Spark(FeedMotorPort); //Defines the feed motor with the port from the RobotMap
    }

    //      ----Shooter Motor Functions----             [SMS:Fully Functional, FMS: Fully Functional, PL: Fully Functional]
    public void setShootMotorSpeeds(double topSpeed, double botSpeed) {
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

    //      ----Ball Count & Color Functions----        [BC:Fully Functional, B1C: Fully Functional]
    public int ballCount() {
        //Resets the ball count to 0
        ballCount = 0;

        //Look for first ball with color
        if (ball1color() != "none") {
            ballStuck = false;
            
            //1 Ball Found
            ballCount = 1;

            //Look for second ball with photo eye
            if (photoEye.get()) {
                //2 Balls found
                ballCount = 2;
            }
        }
        else {
            //Check if there is a ball in second position but not first
            if (photoEye.get()) {
                //A ball is in the second position but not the first
                ballCount = 1;
                ballStuck = true;
            }
        }

        //No else statment because value is initalized at 0
        SmartDashboard.putNumber("Ball Count", ballCount);
        SmartDashboard.putBoolean("Ball Stuck", ballStuck);
        return ballCount;
    }
    public String ball1color() {
        //Detected Color & Proximity from Color Sensor 1
        Color detectedColor = colorSensor.getColor();
        int proximity = colorSensor.getProximity();

        String ball1Color = "none";

        //Check ball color
        if (proximity > 100) {//Smaller values are closer and bigger is farther away
            if (detectedColor.red > detectedColor.blue) {
                //The 1st ball is Red
                //set variable to be returned to Red
                ball1Color = "Red";
            }
            else {
                //The 1st ball is Blue
                //set variable to be returned to Blue
                ball1Color = "Blue";
            }
        }
        
        //No else statment because value is initalized at "none"
        SmartDashboard.putString("Ball 1 Color", ball1Color);
        return ball1Color;
    }

    //      ----Dashboard Functions----                 [DV:Fully Functional]
    public void pushDashboardVars() {
        //Pushes the variables to the SmartDashboard
        SmartDashboard.putNumber("Top Motor Speed", topMotor.get());
        SmartDashboard.putNumber("Bottom Motor Speed", botMotor.get());
        SmartDashboard.putNumber("Feed Motor Speed", feedMotor.get());
        SmartDashboard.putNumber("Ball Count", ballCount());
        SmartDashboard.putBoolean("Ball Stuck", ballStuck);
    }

    //      ----Init & Periodic Functions----           [Fully Functional]
    public void init() {
        feedMotor.setSafetyEnabled(false);
        topMotor.setSafetyEnabled(false);
        botMotor.setSafetyEnabled(false);
    }
    @Override
    public void periodic() {
	    ballCount();
        pushDashboardVars();
    }
}
