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
    - Distnace Sensor (Shooting Process #2,3)
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
        - Driver Preses Shoot Button
        - Front 2 wheels spin up to speed (0.5s)
        - Release first ball
        - If there is a second ball
            - Wait for wheels to regain lost speed (0.5s)
            - Release Second Ball
        - End
        
    - Shooting Process #2 (Aim Assist & 2 Balls)
        - Driver Preses aim button for either high or low (Maybe a button combination for the low goal)
        - Machiene vision to find the reflective tape at the top of the hub
        - Robot rotates shooter to face hub (Either rotates entire robot or just the turret)
        - Uses Distance sensor to detect distance to hub and change verticle offset (If Verticle Availible if not adjust power as much as possible)
        - Driver should then press manual shoot button (Process #1)
        - End
        
    - Shooting Process #3 (Auto Aim/Auto Shoot)
        - Driver Preses Shoot Button for either high or low (Maybe a button combination for the low goal)
        - Start front 2 wheels spinning(Done at same time as aiming)
        - Machiene vision to find the reflective tape at the top of the hub
        - Robot rotates shooter to face hub (Either rotates entire robot or just the turret)
        - Uses Distance sensor to detect distance to hub and change verticle offset (If Verticle Availible if not adjust power as much as possible)
        - Release first ball
        - If there is a second ball
            - Wait for wheels to regain lost speed (0.5s)
            - Release Second Ball
        - End

*/

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{
    
    public void shootingProcess1() {
        //No Aim Assist

        //Start shooter Wheels Spinning
        //Wait 0.5 Seconds for weels to gain speed
        //Release First Ball
        //Detect if there is a second ball
        if (balls > 1) {
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
    public int ballCount() {
        private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);//TODO: Fix when know sensor port for color sensor
        Color detectedColor = m_colorSensor.getColor();//Detected Color from first color sensor

        int ballCount = 0;//Starts the count of balls at 0

        if (detectedColor.red > 1/*Red Min Value*/ && detectedColor.red < 5/*Red Max Value*/ || detectedColor.blue > 1/*Blue Min Value*/ && detectedColor.red < 5/*Blue Max Value*/) {
            //1 Ball Found Look for next one
            ballCount = 1;
            if (detectedColor.red > 1/*Red Min Value*/ && detectedColor.red < 5/*Red Max Value*/ || detectedColor.blue > 1/*Blue Min Value*/ && detectedColor.red < 5/*Blue Max Value*/) {
                //1 Ball Found Look for next one
                ballCount = 2;
            } 
            return ballCount;
        }
        else {
            //No Balls in storage
            return 0;
        }
        return ballCount;
    }
    public int ballcolor() {
        int ballCount = 0;//Starts the count of balls at 0
        
        return ballCount;
    }
    public int updateBallData() {
        private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);//TODO: Fix when know sensor port for color sensor

        String ball1Color;

        Color detectedColor = m_colorSensor.getColor();//Detected Color from first color sensor

        if (detectedColor.red > 1/*Replace with Red Min Value*/ || detectedColor.red < 5/*Replace with Red Max Value*/) {
            //1 Red Ball Found Look for next one
            ball1Color = "Red";
        } 
        else if (detectedColor.blue > 1/*Replace with Blue Min Value*/ || detectedColor.red < 5/*Replace with Blue Max Value*/) {
            //1 Blue Ball Found Look for next one
            ball1Color = "Blue";
            updateBallData2(ball1Color);
        }
        else {
            //No Balls in storage
            return;
        }
    }
    public string updateBallData2(String ball1Color) {
        private final ColorSensorV3 m_colorSensor2 = new ColorSensorV3(i2cPort);//TODO: Fix when know sensor port for color sensor

        String ball2Color;

        Color detectedColor2 = m_colorSensor2.getColor();//Detected Color from second color sensor

        if (detectedColor2.red > 1/*Replace with Red Min Value*/ || detectedColor2.red < 5/*Replace with Red Max Value*/) {
            //2nd Red Ball Found
            ball2Color = "Red";
        } 
        else if (detectedColor2.blue > 1/*Replace with Blue Min Value*/ || detectedColor2.red < 5/*Replace with Blue Max Value*/) {
            //2nd Blue Ball Found
            ball2Color = "Blue";
            return ball1Color;
        }
        else {
            //No Second Ball Found
            return ball1Color;
        }
    }
}
