package frc.swerverobot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Robot Map
import static frc.swerverobot.RobotMap.*;

//Motors - Motor/Controller (Spark Motor Controllers)
import edu.wpi.first.wpilibj.motorcontrol.Spark;

//Color Sensor
import edu.wpi.first.wpilibj.util.Color;

//Smart Dashboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    - 1 for feed
- Motor Controllers (3)
    - 1 for each motor in the shooter
- Machine Vision
    - Camera (1?)
    - Rasberry Pie (1)
    - Used to detect high goal and rotate robot towrds hub 
    - Distance?
        - If we can get reliable distance from Machine Vision relace distance sensor with Machine Vision.
- Distnace Sensor (Shooting Process #2,3) (Ultrasonic maybe?) (Might be replaced with Machine Vision)
    - Detect distance to hub for shooting processs #2 and #3
    - Might need to be aimed upwards because the bottom section is not uniform maybe aim for middle of the lower hoop from edge of the field?
- Color Sensor (1)
    - Detect ball color and count
- Photo Eye (1)
    - Detect if there is a second ball
- PID Controller (Maybe)
    - Keep flywheels at constant speed
    - Might not be needed
*/


/*      ----Driver Interaction----
All controls should be on Noahs controller
- shootingProcess1(X) - Shoots balls this will use values from auto aim to shoot, The driver can also manually change these values with 
- shootingProcess2(Not Needed) - Auto aims the robot but doesent shoot, the boolean is for aiming for the high or low goal (true = High || false = Low)
- shootingProcess3(Right Trigger for High Goal | Left Trigger for Low Goal) - Auto aims the robot and shoots, the boolean is for aiming for the high or low goal (true = High || false = Low)
- manualShooterDistanceIncrease(Y) - Increases the power to both shooter motors by 5%, doesent shoot balls
- manualShooterDistanceDecrease(A) - Decrease the power to both shooter motors by 5%, doesent shoot balls
- dropBall(B) - Drops the first ball in the system
*/


/*      ----Processes----
- Shooting Process #1 (No Aim Assist & 2 Balls)
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
    float bottomMotorPower = 0.3f; //Start the Bottom motor at low power
    float topMotorPower = 0.3f; //Start the Top motor at low power
    boolean ballStuck = false; //Is true if there is a ball in the second position(Photoeye) but not the first(Color Sensor)

    //Shooter Motors
    Spark topShooterMotor = new Spark(TopMotorPort); //The Number is the RIO PWM port from the RobotMap.java
    Spark bottomShooterMotor = new Spark(BottomMotorPort); //The Number is the RIO PWM port from the RobotMap.java
    Spark feedMotor = new Spark(FeedMotorPort); //The Number is the RIO PWM port from the RobotMap.java

    //      ----Wait Function----
    public void Wait(float Seconds) {
        float initTime = System.currentTimeMillis() / 1000f;
        while (System.currentTimeMillis() / 1000f < initTime + Seconds) {
            // return.wait(Seconds);
        }
    }

    //      ----Shooting Functions----
    public Boolean shootingProcess1() {
        //Get Number of balls at the start of shooting and saves it to a variable for use later
        int ballcount = ballCount();

        if (ballcount == 0) {
            //If the number of balls is 0 stop the function
            return false;
        }

        //Set Top Motor to topMotorPower (This is from autoaim or the manual adjustment functions)
        topShooterMotor.set(topMotorPower);
        //Set Bottom Motor to bottomMotorPower (This is from autoaim or the manual adjustment functions)
        bottomShooterMotor.set(bottomMotorPower);

        
        //Wait 0.2 Seconds for front wheels to get up to speed
        float initTime = System.currentTimeMillis() / 1000f;
        while (System.currentTimeMillis() / 1000f < initTime + 0.2f) {

            //Set Feed Motor to 0.2 (Releases first ball into shooter)
            feedMotor.set(0.2);

            //Wait 0.1 Seconds for ball to leave shooter
            float initTime2 = System.currentTimeMillis() / 1000f;
            while (System.currentTimeMillis() / 1000f < initTime2 + 0.1f) {

                //Set Feed Motor to 0 (Stops any more balls from entering the shooter until front wheels are at speed)
                feedMotor.stopMotor();

                //If there were 2 balls at the start
                if (ballcount < 1) {

                    //Wait 0.2 Seconds for front wheels to get up to speed
                    float initTime3 = System.currentTimeMillis() / 1000f;
                    while (System.currentTimeMillis() / 1000f < initTime3 + 0.2f) {
                        //Set Feed Motor to 0.25 (Releases second ball into shooter)
                        feedMotor.set(0.25);
                    }
                }

                //Wait 0.1 Seconds for ball to leave shooter
                float initTime4 = System.currentTimeMillis() / 1000f;
                while (System.currentTimeMillis() / 1000f < initTime4 + 0.1f) {
                    //Stop All Motors
                    feedMotor.stopMotor();
                    topShooterMotor.stopMotor();
                    bottomShooterMotor.stopMotor();
                    return true;
                }
            }
        }
        return false;
    }
    public Boolean shootingProcess2(Boolean highLow) {
        //Aim Assist (No Shoot)
        
        //TODO: When Shooter is Built calibrate if statments for distances
        
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
    public void dropBall() {
        //Drops the first ball in the system
        //Set Top Motor to 0.175 (Barely enough to move it)
        topShooterMotor.set(0.175);
        //Set Bottom Motor to 0.175 (Barely enough to move it)
        bottomShooterMotor.set(0.175);
        //Set Bottom Motor to 0.2
        feedMotor.set(0.2);

        float initTime = System.currentTimeMillis() / 1000f;
        while (System.currentTimeMillis() / 1000f < initTime + 0.2f) {//Wait 0.1 Seconds for ball to leave shooter
            //Stop all the motors
            feedMotor.stopMotor();
            topShooterMotor.stopMotor();
            bottomShooterMotor.stopMotor();
        }
    }

    //      ----Manual Adjustments Functions----
    public void changeShooterDistance(float changeTop,float changeBottom) {
        //Top Motor
        topMotorPower = topMotorPower + changeTop;
        if (topMotorPower > 1) {
            topMotorPower = 1f;
        }
        else if (topMotorPower < 0) {
            topMotorPower = 0f;
        }
        //Bottom Motor
        bottomMotorPower = bottomMotorPower + changeBottom;
        if (bottomMotorPower > 1) {
            bottomMotorPower = 1f;
        }
        else if (bottomMotorPower < 0) {
            bottomMotorPower = 0f;
        }
        SmartDashboard.putNumber("Bottom Motor Saved Power", bottomMotorPower); //Updates "Bottom Motor Saved Power"(int)
        SmartDashboard.putNumber("Top Motor Saved Power", topMotorPower); //Updates "Bottom Motor Saved Power"(int)
    }

    //      ----Distance Sensor Functions----
    public int distanceFront() {
        //Get distance infront of robot
        int distance = 5;//Placeholder Value TODO: Figure out how to get distance value from rasberrypie(Something about a network cable)
        SmartDashboard.putNumber("Distance to Hub", distance);
        return distance;
    }

    //      ----Ball Count & Color Functions----
    public int ballCount() {
        //Starts the count of balls at 0
        int ballCount = 0;

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
        if (proximity > 125) {//125 is 1 inch and half a ball away from the color sensor
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

    //      ----Variable Update Function----
    public void updateHudVariablesShooter() {    
        //Updates all the Variables that are sent to the drivers station for the shooter subsystem
        ballCount(); //Updates "Ball Count"(int), "Ball Stuck"(bool), "Ball 1 Color"(string)
        distanceFront(); //Updates "Distance to Hub"(int)
        SmartDashboard.putNumber("Bottom Motor Saved Power", bottomMotorPower); //Updates "Bottom Motor Saved Power"(int)
        SmartDashboard.putNumber("Top Motor Saved Power", topMotorPower); //Updates "Bottom Motor Saved Power"(int)
    }

    //      ----Controlls [Right Trigger Auto Aim & Shoot High | Left Trigger|Auto Aim & Shoot Low]----
    @Override
    public void periodic() {
        if (AimShootHigh.get() > 0.5) {
            //Auto Aim & Shoot into the High Goal
            shootingProcess3(true);
        }
        if (AimShootLow.get() > 0.5) {
            //Auto Aim & Shoot into the Low Goal
            shootingProcess3(false);
        }
        updateHudVariablesShooter();//Updates the variables being sent to the drivers station
    }
    public void ControllerButtonInit (){
        //When Buttons are Pressed
        Shoot.whenPressed(
            //[Shooter Subsystem] High Goal Auto Aim & Shoot
            () -> this.shootingProcess1()
        );
        ManualShootIncrease.whenPressed(
            //[Shooter Subsystem] Manually Increase Shooter Distance by 5%
            () -> this.changeShooterDistance(0.025f,0.025f)
        );
        ManualShootDecrease.whenPressed(
            //[Shooter Subsystem] Manually Decrease Shooter Distance by 5%
            () -> this.changeShooterDistance(-0.025f,-0.025f)
        );
        EjectBall.whenPressed(
            //[Shooter Subsystem] Drops the first ball in storage
            () -> this.dropBall()
        );
        //When Buttons are Held
        ManualShootIncrease.whileActiveContinuous(
            //[Shooter Subsystem] Manually Increase Shooter Distance by 1% when held
            () -> this.changeShooterDistance(0.02f,0.02f)
        );
        ManualShootDecrease.whileActiveContinuous(
            //[Shooter Subsystem] Manually Decrease Shooter Distance by 5%
            () -> this.changeShooterDistance(-0.02f,-0.02f)
        );
    }
}
