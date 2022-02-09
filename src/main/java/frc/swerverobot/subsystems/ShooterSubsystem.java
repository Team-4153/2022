package frc.swerverobot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

//Robot Map
import static frc.swerverobot.RobotMap.*;

//Motors - Motor/Controller (Spark Motor Controllers)
import edu.wpi.first.wpilibj.motorcontrol.Spark;

//Color Sensor
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;

/*      ----Notes----
- Motors
    - Prototype used 2 on the front and one in the back to hold the ball
- How are balls stored?
    - Held away from shooter by a motor behind shooter
- Distance to target/machine vision?
- Autoaim?

- Notes from Prototype Testing
- With Current Motors and angle of ~30 degrees power of 0.6T0.6B works well to get it into the goal from distance
- From Close up adding backspin helps
- Assuming no added force from storage system (The 3rd motor at the end adds speed) 0.5 second wait time before wheels back up to speed
*/



/*      ----Electronics Needed----
- Motors (3)
    - 2 for shooter
    - 1 for storage
- Motor Controllers (3)
    - 1 for each motor in the shooter
- Machine Vision
    - Camera 
    - Rasberry Pie
    - Detect high goal for auto aim
    - Distance Maybe?
        - If we can get reliable distance from Machine Vision relace distance sensor with Machine Vision.
- Distnace Sensor (Shooting Process #2,3) (Ultrasonic maybe?) (Might be replaced with Machine Vision)
    - Detect distance to hub for shooting processs #2 and #3
    - Might need to be aimed upwards because the bottom section is not uniform maybe aim for middle of the lower hoop from edge of the field?
- Color Sensor (1)
    - Detect ball color and count
- Photo Eye
    - Detect if there is a second ball
- PID Controller (Maybe)
    - Keep flywheels at constant speed
    - Might not be needed
*/



/*      ----Driver Interaction----
- shootingProcess1(Right Trigger) - Shoots balls this will use values from auto aim to shoot, The driver can also manually change these values with 
- shootingProcess2(X for High Goal | A for Low Goal) - Auto aims the robot but doesent shoot, the boolean is for aiming for the high or low goal (true = High || false = Low)
- shootingProcess3(Not A Button Yet) - Auto aims the robot and shoots, the boolean is for aiming for the high or low goal (true = High || false = Low)
- manualShooterDistanceIncrease(D-Pad Up) - Increases the power to both shooter motors by 5%, doesent shoot balls
- manualShooterDistanceDecrease(D-Pad Down) - Decrease the power to both shooter motors by 5%, doesent shoot balls

- TODO:Add Shooting Process 3 to Keybindings
*/



/*      ----Processes----
- Shooting Process #1 (No Aim Assist & 2 Balls)
    - Driver Preses Shoot Button or Activated by code (Start Function)
    - Spin bottom & top Motors to variables (This value can be changed by the driver manually or buy the aim program)
    - Wait ~0.5 Seconds
    - Activate Storage Motor to release first Ball (20% power worked in testing)
    - Wait ~0.1 Seconds
    - Stop Storage Motor
    - Use Color Sensor to detect if there is still a ball in the first slot
    - If there is a ball
        - Wait for wheels to regain lost speed ~0.5s
        - Turn Storage Motor to release second Ball
        - Wait for ball to exit shooter ~0.1s
    - Stop both intake motors
    - End
    
- Shooting Process #2 (Aim Assist & 2 Balls)
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
    
- Shooting Process #3 (Auto Aim/Auto Shoot)
    - Driver should position robot roughly aming at the hub before running program
    - Driver Preses Auto Shoot Button for either high or low (Maybe a button combination for the low goal)
    - Run Shooting Process #2 (Input High Low Goal)
    - Wait until #2 finished
    - Run Shooting Process #1 
    - End
*/

public class ShooterSubsystem extends SubsystemBase{
    float bottomMotorPower = 0;
    float topMotorPower = 0;

    //Shooter Motors
    Spark topShooterMotor = new Spark(TopMotorPort);// Value from Robot Map
    Spark bottomShooterMotor = new Spark(BottomMotorPort);  // The Number is the RIO PWM port
    Spark storageMotor = new Spark(StorageMotorPort);  // The Number is the RIO PWM port

    //      ----Shooting Functions----
    public Boolean shootingProcess1() {
        //Get Number of balls at the start of shooting and saves it to a variable
        int ballcount = ballCount();

        if (ballcount == 0) {
            //If there are not balls in the shooter stop the program.
            return false;
        }

        //Set Top Motor to topMotorPower (This is from manual adjustment or the autoaim program)
        topShooterMotor.set(topMotorPower);
        //Set Bottom Motor to bottomMotorPower (This is from manual adjustment or the autoaim program)
        bottomShooterMotor.set(bottomMotorPower);

        float initTime = System.currentTimeMillis() / 1000f;
        while (System.currentTimeMillis() / 1000f < initTime + 0.2f) {//Wait 0.2 Seconds for front wheels to get up to speed
            //Set Storage Motor to 0.2(20%)(Releases first ball into shooter)
            storageMotor.set(0.2);

            float initTime2 = System.currentTimeMillis() / 1000f;
            while (System.currentTimeMillis() / 1000f < initTime2 + 0.1f) {//Wait 0.1 Seconds for ball to leave shooter
                //Set Storage Motor to 0 (Stops any more balls from entering the shooter until front wheels are at speed)
                storageMotor.stopMotor();

                //If there were 2 balls at the start
                if (ballcount < 1) {
                    float initTime3 = System.currentTimeMillis() / 1000f;
                    while (System.currentTimeMillis() / 1000f < initTime3 + 0.2f) {//Wait 0.2 Seconds for front wheels to get up to speed
                        //Set Storage Motor to 0.2(20%)(Releases first ball into shooter)
                        storageMotor.set(0.2);
                    }
                    new WaitCommand(0.1);//Wait 0.1 Seconds for ball to exit shooter
                }
                float initTime4 = System.currentTimeMillis() / 1000f;
                while (System.currentTimeMillis() / 1000f < initTime4 + 0.1f) {//Wait 0.1 Seconds for ball to leave shooter
                    //Stop All Motors
                    storageMotor.stopMotor();
                    topShooterMotor.stopMotor();
                    bottomShooterMotor.stopMotor();
                    return true;
                }
            }
        }
        return true;
    }
    public Boolean shootingProcess2(Boolean highLow) {
        //Aim Assist (No Shoot)
        
        //TODO: When Shooter Built Get Values for Distances
        
        //Machine vision to find reflective tape on high goal
        //Rotate Robot to face hub
        //Wait Until Facing Hub
        if (highLow) {
            //true = High Goal

            //Set bottomMotorPower & topMotorPower variables to needed to get into high goal
            if (distanceFront() > 3) {
                bottomMotorPower = 0.3f;
                topMotorPower = 0.3f;
                //Return true to signal program completion
                return true;
            }
            else if (distanceFront() > 5) {
                bottomMotorPower = 0.5f;
                topMotorPower = 0.5f;
                //Return true to signal program completion
                return true;
            }
            else if (distanceFront() > 10) {
                //Out of Bounds
                //Set Motors to max just incase driver needs to shoot
                bottomMotorPower = 1f;
                topMotorPower = 1f;
                //Return false to notify driver of error
                return false;
            } 
        }
        else {
            //false = Low Goal

            //Set bottomMotorPower & topMotorPower variables to needed to get into low goal
            if (distanceFront() > 3) {
                bottomMotorPower = 0.2f;
                topMotorPower = 0.2f;
                //Return true to signal program completion
                return true;
            }
            else if (distanceFront() > 5) {
                bottomMotorPower = 0.3f;
                topMotorPower = 0.3f;
                //Return true to signal program completion
                return true;
            }
            else if (distanceFront() > 10) {
                //Out of Bounds
                //Set Motors to max just incase driver needs to shoot
                bottomMotorPower = 1f;
                topMotorPower = 1f;
                //Return false to notify driver of error
                return false;
            } 
        }
        return false;
    }
    public Boolean shootingProcess3(Boolean highLow) {
        //Run ShootingProcess2 with highlow boolean to aim the robot
        if (shootingProcess2(highLow) != false) {
            //Run ShootingProcess1 to shoot all the balls in the robot after done aiming
            shootingProcess1();
            return true;
        }
        else {
            return false;
        }
    }

    //      ----Manual Adjustments Functions----
    public void manualShooterDistanceIncrease() {
        //Top Motor
        if (topMotorPower < 1) {
            topMotorPower = 1f;
        }
        else {
            topMotorPower = 0.05f + topMotorPower;
        }
        //Bottom Motor
        if (bottomMotorPower < 1) {
            bottomMotorPower = 1f;
        }
        else {
            bottomMotorPower = 0.05f + bottomMotorPower;
        }
    }
    public void manualShooterDistanceDecrease() {
        //Top Motor
        if (topMotorPower > 0) {
            topMotorPower = 0f;
        }
        else {
            topMotorPower = -0.1f + topMotorPower;
        }
        //Bottom Motor
        if (bottomMotorPower > 0) {
            bottomMotorPower = 0f;
        }
        else {
            bottomMotorPower = -0.1f + bottomMotorPower;
        }
    }


    //      ----Distance sensor----
    public int distanceFront() {
        //Get distance infront of robot
        int distance = 5;//Placeholder Value TODO: Replace with working distance sensor
        return distance;
    }

    //      ----Color Sensor Functions----
    public final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

    public int ballCount() {
        //Detected Color from Color Sensor 1
        Color detectedColor = colorSensor.getColor();//Detected Color from first color sensor

        //Detected true/false from photoeye
        Boolean photoeye = true; //Placeholder Value TODO:Figure out how photo eyes work

        int ballCount = 0;//Starts the count of balls at 0

        //Look for first ball with color sensor
        if (detectedColor.red > detectedColor.blue && detectedColor.red > detectedColor.green || detectedColor.blue > detectedColor.red && detectedColor.blue > detectedColor.green) {
            //1 Ball Found
            ballCount = 1;

            //Look for second ball with photo eye
            if (photoeye) {
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
        if (detectedColor.red > detectedColor.blue && detectedColor.red > detectedColor.green) {
            //Red Ball Found
            ballColor1 = "Red";
        }
        else if (detectedColor.blue > detectedColor.red && detectedColor.blue > detectedColor.green) {
            //Red Ball Found
            ballColor1 = "Blue";
        }
        
        return ballColor1;
    }
}
