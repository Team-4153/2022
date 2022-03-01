package frc.swerverobot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import static frc.swerverobot.RobotMap.*;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.util.Color;

public class ShooterSubsystem2 extends SubsystemBase {

    private final topMotor;
    private final botMotor;
    private final feedMotor;
    private int ballCount;

    public ShooterSubsystem2() {
	this.topMotor = new Spark(RobotMap.TopMotorPort);
	this.bottomMotor = new Spark(RobotMap.BottomMotorPart);
	this.feedMotor = new Spark(RobotMap.FeedMotorPort);
    }

    public void init() {
        feedMotor.setSafetyEnabled(false);
        topMotor.setSafetyEnabled(false);
        bottomMotor.setSafetyEnabled(false);
	ballCount = 0;
    }

    @Override
    public void periodic() {
	checkBalls();
    }

    public void checkBalls() {
	if (ball1Color() != "none" || photoEye.get()) {
	    ballCount = 1;

	    if (ball1Color() != "none" && photoEye.get()) {
		ballCount = 2;
	    }

	}
	else {
	    ballCount = 0;
	}

    }

    public String ball1color() {
        //Detected Color & Proximity from Color Sensor 1
        Color detectedColor = colorSensor.getColor();
        int proximity = colorSensor.getProximity();

        String ball1Color = "none";

        //Check ball color
        if (proximity > 100) {//125 is 1 inch and half a ball away from the color sensor
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

    public int getBallCount() {
	return ballCount;
    }

    public void setShootMotorSpeeds(double topSpeed, double botSpeed) {
	topMotor.set(topSpeed);
	botMotor.set(botSpeed);
    }

    public void setFeedMotorSpeeds(double feedSpeed) {
	feedMotor.set(feedSpeed);
    }

}
