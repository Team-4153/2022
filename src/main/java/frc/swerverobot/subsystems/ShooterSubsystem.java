package frc.swerverobot.subsystems;

//Color Sensor
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;

/*
Notes

 - Motors
    - Prototype used 2 on the front and one in the back to hold the ball
 - How are balls stored?
 - Distance to target/machine vision?
 - Autoaim?

 - Notes from Prototype Testing
    - With Current Motors and angle of ~30 degrees power of 0.6T0.6B works well to get it into the goal from distance
    - From Close up adding backspin helps
    - Assuming no added force from storage system (The 3rd motor at the end adds speed) 0.5 second wait time before wheels back up to speed

 - Sensors/Controllers Needed
    - Motors
        - 3 are planned 
            - 2 for shooter
            - 1 for storage
    - 3 Motor Controllers
    - Distnace Sensor (Shooting Process #2,3) (Ultrasonic maybe?)
        - Detect distance to hub for shooting processs #2 and #3
        - Might need to be aimed upwards because the bottom section is not uniform maybe aim for middle of the lower hoop from edge of the field?
    - Machine Vision
        - Detect high goal for auto aim
    - Color Sensor (2)
        - Detect ball color and count
    - PID Controller
        - Keep flywheels at constant speed
        - Might not be needed


 - Processes
    - Shooting Process #1 (No Aim Assist & 2 Balls)
        - Driver Preses Shoot Button or Activated by code (Start Function)
        - Front 2 wheels spin up to speed
        - Wait ~0.5 Seconds
        - Turn Storage Motor to release first Ball
        - Wait ~0.1 Seconds
        - Stop Storage Motor
        - Use Color Sensor to detect if there is still a ball in the first slot
        - If there is a ball
            - Wait for wheels to regain lost speed ~0.5s
            - Turn Storage Motor to release second Ball (~0.1s)
            - Wait for ball to exit shooter ~0.1s
        - Stop both intake motors
        - End
        
    - Shooting Process #2 (Aim Assist & 2 Balls)
        - Driver should position robot roughly aming at the hub before running program
        - Driver Preses Aim Button or Activated by code high or low (Maybe a button combination for the low goal) (Start Function)
        - Machiene vision to find the reflective tape on high goal
        - Robot rotates shooter to face hub
        - When facing hub
        - Use Distance sensor to detect distance to hub 
        - If high goal
            - Ajust power of top and bottom motor to get required distance (Using high goal Calibration)
            - If distance is not possible
                - Notify Driver (Maybe a beep or just something on the HUD)
        - else
            - Ajust power of top and bottom motor to get required distance (Using low goal Calibration)
            - If distance is not possible
                - Notify Driver (Maybe a beep or just something on the HUD)
        - End
        
    - Shooting Process #3 (Auto Aim/Auto Shoot)
        - Driver should position robot roughly aming at the hub before running program
        - Driver Preses Auto Shoot Button for either high or low (Maybe a button combination for the low goal)
        - Run Shooting Process #1
        - When #1 finished
        - Run Shooting Process #2 (Input high low value from driver to process 2)
        - End

*/

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{

    //Shooting Processes
    public void shootingProcess1() {
        //No Aim Assist

        //Start shooter Wheels Spinning
        //Wait 0.5 Seconds for weels to gain speed
        //Release First Ball
        //Detect if there is a second ball
        if (ballCount() < 1) {
            //Wait 0.5 Seconds
            //Release Second Ball
        }
        //Stop Motors
    }
    public void shootingProcess2(Boolean highLow) {
        //Aim Assist (No Shoot)
        
        //Machine vision to find reflective tape on the top of the hub
        //Rotate to face hub (Either Rotate whole Robot or just turret)
        if (highLow) {
            //High Goal = true

            //Use distance sensor to get distance to hub (Robot should already be facing hub)
            //Ajust vertical angle for high goal (If Availible if not adjust power as much as possible)
            //Notify Driver of the turret will not be able to get enough power to get it in
        }
        else {
            //Low Goal = false

            //Use distance sensor to get distance to hub (Robot should already be facing hub)
            //Ajust vertical angle for low goal (If Availible if not adjust power as much as possible)
            //Notify Driver of the turret will not be able to get enough power to get it in
        }
    }
    public void shootingProcess3(Boolean highLow) {
        //Auto Aim/Auto Shoot

        //Run ShootingProcess2 with highlow boolean to aim the robot
        shootingProcess2(highLow);

        //Run ShootingProcess1 to shoot all the balls in the robot
        shootingProcess1();
    }

    //Distance sensor
    public int distanceFront() {
        //Get distance infront of robot
        int distance = 5;
        return distance;
    }

    //Color Sensor Functions
    public static final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);//TODO: Fix when know sensor port for color sensor
    private final ColorSensorV3 colorSensor2 = new ColorSensorV3(i2cPort);//TODO: Fix when know sensor port for color sensor

    public int ballCount() {
        //Detected Color from Color Sensor 1
        Color detectedColor = colorSensor.getColor();//Detected Color from first color sensor

        //Detected Color from Color Sensor 2
        Color detectedColor2 = colorSensor2.getColor();//Detected Color from first color sensor

        int ballCount = 0;//Starts the count of balls at 0

        //Look for first ball
        if (detectedColor.red > 1/*Red Min Value*/ && detectedColor.red < 5/*Red Max Value*/ || detectedColor.blue > 1/*Blue Min Value*/ && detectedColor.blue < 5/*Blue Max Value*/) {
            //1 Ball Found
            ballCount = 1;

            //Look for second ball
            if (detectedColor2.red > 1/*Red Min Value*/ && detectedColor2.red < 5/*Red Max Value*/ || detectedColor2.blue > 1/*Blue Min Value*/ && detectedColor2.blue < 5/*Blue Max Value*/) {
                //2 Balls found
                ballCount = 2;
            } 
        }
        //No else statment because value is initalized at 0
        return ballCount;
    }
    public String ball1color() {
        String ballColor1 = "none";//Starts the first ball color at none
        
        //Detected Color from Color Sensor 1
        Color detectedColor = colorSensor.getColor();//Detected Color from first color sensor

        //Check ball color
        if (detectedColor.red > 1/*Red Min Value*/ && detectedColor.red < 5/*Red Max Value*/) {
            //Red Ball Found
            ballColor1 = "Red";
        }
        else if (detectedColor.blue > 1/*Blue Min Value*/ && detectedColor.blue < 5/*Blue Max Value*/) {
            //Red Ball Found
            ballColor1 = "Blue";
        }
        
        return ballColor1;
    }
    public String ball2color() {
        String ballColor2 = "none";//Starts the second ball color at none

        //Detected Color from Color Sensor 2
        Color detectedColor2 = colorSensor.getColor();//Detected Color from first color sensor

        //Check ball color
        if (detectedColor2.red > 1/*Red Min Value*/ && detectedColor2.red < 5/*Red Max Value*/) {
            //Red Ball Found
            ballColor2 = "Red";
        }
        else if (detectedColor2.blue > 1/*Blue Min Value*/ && detectedColor2.blue < 5/*Blue Max Value*/) {
            //Red Ball Found
            ballColor2 = "Blue";
        }
        
        return ballColor2;
    }
}
