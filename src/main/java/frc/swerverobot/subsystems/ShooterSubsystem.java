// package frc.swerverobot.subsystems;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// //Robot Map
// import static frc.swerverobot.RobotMap.*;

// //Color Sensor
// import edu.wpi.first.wpilibj.util.Color;

// //Smart Dashboard
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// /*      ----Notes----
// - Motors
//     - Prototype used 2 launch motors and 1 feed motor
//     - Balls are Stored with feed Motor
// - Distance to target with machine vision (Rasberry pie with network cable?|TODO:Figure out network tables/Rasberry pie)
// - TODO: When Shooter is built calibrate the power needed for different distances & the time it takes for it to get the Launch Motors up to speed

// - Notes from Prototype Testing
//     - With Current Motors and angle of ~30 degrees power of 0.6T 0.6B works well to get it into the goal from distance
//     - From Close up adding backspin helps
//     - Assuming no added force from storage system (The 3rd motor at the end adds speed) 0.1 second wait time before wheels back up to speed 
// */

// /*      ----Electronics Needed----
// - Motors (3|Needs Testing)
//     - 2 for shooter
//     - 1 for feed
// - Motor Controllers (3|Needs Testing)
//     - 1 for each motor in the shooter
//     - Probably Spark or Spark Max motor controllers
// - Machine Vision
//     - Camera (1?)
//     - Rasberry Pie (1)
//         - Figure out network tables
//     - Needs to be able to Detect high goal & Distance to hub
//         - Detect distance to hub for shooting processs #2 and #3
//         - If distance to hub is not viable we need to find some way of reliably detecting distance to hub
//             - Things other than machine vision might need to be aimed upwards because the bottom section is not uniform maybe aim for middle of the lower hoop from edge of the field?
// - Color Sensor (1|Functional)
//     - Detect first ball color and count
// - Photo Eye (1|Functional)
//     - Detect if there is a second ball
//     - Detect if there is only a second ball and not a first one
// - PID Controller (Not-Needed)
//     - Keep flywheels at constant speed
//     - Might not be needed
// */

// /*      ----Driver Interaction----
// All controls should be on Noahs controller
// - shootingProcess1(X:Needs Testing) - Shoots balls this will use values from auto aim to shoot, The driver can also manually change these values with 
// - shootingProcess2(Not Needed) - Auto aims the robot but doesent shoot, the boolean is for aiming for the high or low goal (true = High || false = Low)
// - shootingProcess3(Right Trigger for High Goa0l:Needs Testing | Left Trigger for Low Goal:Needs Testing) - Auto aims the robot and shoots, the boolean is for aiming for the high or low goal (true = High || false = Low)
// - manualShooterDistanceIncrease(Y:Functional) - Increases the power to both shooter motors by 5%, doesent shoot balls
// - manualShooterDistanceDecrease(A:Functional) - Decrease the power to both shooter motors by 5%, doesent shoot balls
// - dropBall(B:Needs Testing) - Drops the first ball in the system
// */

// /*      ----Processes----
// - Shooting Process #1 (No Aim Assist & 2 Balls | Needs Testing)
//     - If Process isnt already running
//     - Driver Preses Shoot Button or Activated by code (Start Function)
//     - Spin bottom & top Motors to variables (This value can be changed by the driver manually or buy the aim program)
//     - Wait ~0.5 Seconds
//     - Activate Feed Motor to release first Ball (20% power worked in testing)
//     - Wait ~0.1 Seconds
//     - Stop Feed Motor
//     - Use Color Sensor to detect if there is still a ball in the first slot
//     - If there is a ball
//         - Wait for wheels to regain lost speed ~0.5s
//         - Turn Feed Motor to release second Ball
//         - Wait for ball to exit shooter ~0.1s
//     - Stop both intake motors
//     - End
    
// - Shooting Process #2 (Aim Assist & 2 Balls | Needs Testing)
//     - If Process isnt already running
//     - Driver should position robot roughly aming at the hub before running program
//     - Driver Preses Aim Button or Activated by code high or low (Maybe a button combination for the low goal) (Start Function)
//     - Machiene vision to find the reflective tape on high goal
//     - Rotate Robot to face hub
//     - Wait Until facing hub
//     - Use Distance sensor or Machine Vision to detect distance to hub 
//     - If high goal
//         - Ajust power of top and bottom motor to get required distance (Using high goal Calibration)
//         - If distance is not possible
//             - Notify Driver (Maybe a beep or just something on the HUD)
//     - else
//         - Ajust power of top and bottom motor to get required distance (Using low goal Calibration)
//         - If distance is not possible
//             - Notify Driver (Maybe a beep or just something on the HUD)
//     - End
    
// - Shooting Process #3 (Auto Aim/Auto Shoot | Needs Testing)
//     - If Process isnt already running
//     - Driver should position robot roughly aming at the hub before running program
//     - Driver Preses Auto Shoot Button for either high or low (Maybe a button combination for the low goal)
//     - Run Shooting Process #2 (Input High Low Goal)
//     - Wait until #2 finished
//     - Run Shooting Process #1 
//     - End
// - Drop Balls
//     - If Process isnt already running
//     - Driver presses button
//     - Gently spin shoot motors
//     - Push forward first ball
//     - Wait 0.2 Seconds
//     - Stop All Motors
//     - End
// */

// public class ShooterSubsystem extends SubsystemBase{
//     float bottomMotorPower = 0.3f;  //Start the Bottom motor at low power
//     float topMotorPower = 0.3f;     //Start the Top motor at low power
//     boolean ballStuck = false;      //Is true if there is a ball in the second position(Photoeye) but not the first(Color Sensor)

//     //Boolean Values for if a function is in progress
//     public Boolean s1 = false; //Shooting Process 1
//     public Boolean s2 = false; //Shooting Process 2
//     public Boolean s3 = false; //Shooting Process 3
//     public Boolean db = false; //Drop Ball Function

//     //      ----Independent Wait Function----               [Broken]
//     public void Wait(float Seconds) {
//         //Currently Broken
//         float initTime = System.currentTimeMillis() / 1000f;
//         while (System.currentTimeMillis() / 1000f < initTime + Seconds) {
//             // return.wait(Seconds);
//         }
//     }

//     //      ----Shooting Functions----                      [SP1:Needs Testing, SP2:Placeholder, SP3:Needs Testing, DB: Needs Testing]
//     public void shootingProcess1a() {
//         // if (!s2) {//If the function isnt already running or If the number of balls is 0 stop the function
//             // s1 = true;//Mark the function as already running to prevent multiple instances of a function running at once
//             //Set Top Motor to topMotorPower (This is from autoaim or the manual adjustment functions)
//             topShooterMotor.set(topMotorPower);
//             //Set Bottom Motor to bottomMotorPower (This is from autoaim or the manual adjustment functions)
//             bottomShooterMotor.set(bottomMotorPower);
//         // }
//         // return;
//     }
//     //Wait 0.2 Seconds for front wheels to get up to speed
//     public void shootingProcess1b() {
//         if (ballCount() < 0) {
//             //Set Feed Motor to 0.2 (Releases a ball into shooter)
//             feedMotor.set(-0.2);
//         }
//     }
//     //Wait 0.1 Seconds for ball to leave shooter
//     public void shootingProcess1c() {
//         //Set Feed Motor to 0 (Stops any more balls from entering the shooter until front wheels are at speed)
//         feedMotor.stopMotor();
//     }
//     //Wait 0.1 Seconds for ball to leave shooter
//     public void shootingProcess1e() {
//         //Stop All Motors
//         feedMotor.stopMotor();
//         topShooterMotor.stopMotor();
//         bottomShooterMotor.stopMotor();
//         // s1 = false;//Mark the function as finished this is to prevent multiple instances of a function running at once
//     }
    
//     public Boolean shootingProcess2(Boolean highLow) {
//         //TODO: Find hub relative to robot and rotate robot accordingly
//         //Aim Assist (No Shoot)
//         // if (!s2) {//If the function isnt already running
//             // s2 = true;//Mark the function as already running to prevent multiple instances of a function running at once
            
//             //TODO: When Shooter is Built calibrate if statments for distances
//             //Machine vision to find reflective tape on high goal

//             //Rotate Robot to face hub
//             int rotationToHub = rotHub();
//             if (rotationToHub > 0) {
//                 //Turn Left
//                 //TODO: Figure out how to turn robot a certan number of degrees or radians to the right or left
//             }
//             else if (rotationToHub < 0) {
//                 //Turn Right
//                 //TODO: Figure out how to turn robot a certan number of degrees or radians to the right or left
//             }
            
//             //Wait Until Facing Hub
//             if (highLow) {
//                 //true = High Goal

//                 //Set bottomMotorPower & topMotorPower variables to needed to get into high goal
//                 if (distanceFront() > 3) {
//                     bottomMotorPower = 0.3f;
//                     topMotorPower = 0.3f;
//                     // s2 = false;//Mark the function as finished this is to prevent multiple instances of a function running at once
//                     return true;//Return true to signal program completion
//                 }
//                 else if (distanceFront() > 5) {
//                     bottomMotorPower = 0.5f;
//                     topMotorPower = 0.5f;
//                     // s2 = false;//Mark the function as finished this is to prevent multiple instances of a function running at once
//                     return true;//Return true to signal program completion
//                 }
//                 else if (distanceFront() > 10) {
//                     //Out of Bounds
//                     //Set Motors to max just incase driver needs to shoot
//                     bottomMotorPower = 1f;
//                     topMotorPower = 1f;
//                     // s2 = false;//Mark the function as finished this is to prevent multiple instances of a function running at once
//                     return false;//Return true to signal program completion
//                 }
//             }
//             else {
//                 //false = Low Goal

//                 //Set bottomMotorPower & topMotorPower variables to needed to get into low goal
//                 if (distanceFront() > 3) {
//                     bottomMotorPower = 0.2f;
//                     topMotorPower = 0.2f;
//                     // s2 = false;//Mark the function as finished this is to prevent multiple instances of a function running at once
//                     return true;//Return true to signal program completion
//                 }
//                 else if (distanceFront() > 5) {
//                     bottomMotorPower = 0.3f;
//                     topMotorPower = 0.3f;
//                     // s2 = false;//Mark the function as finished this is to prevent multiple instances of a function running at once
//                     return true;//Return true to signal program completion
//                 }
//                 else if (distanceFront() > 10) {
//                     //Out of Bounds
//                     //Set Motors to max just incase driver needs to shoot
//                     bottomMotorPower = 1f;
//                     topMotorPower = 1f;
//                     // s2 = false;//Mark the function as finished this is to prevent multiple instances of a function running at once
//                     return false;//Return false to notify driver of error
//                 } 
//             }
//         // }
//         return false;
//     }
//     public Boolean shootingProcess3(Boolean highLow) {
//         // if (!s3) {//If the function isnt already running
//             // s3 = true;//Mark the function as already running to prevent multiple instances of a function running at once
//             //Run ShootingProcess2 with highlow boolean to aim the robot
//             if (shootingProcess2(highLow) != false) {
//                 // s2 = true;//Mark shooting process 2 as in pogress untill shooting process 3 is done
//                 //Run ShootingProcess1 to shoot all the balls in the robot after done aiming
//                 //TODO: Figure out how to run a command from a subsystem
//                 // s2 = false;//Mark shooting process 2 as in pogress untill shooting process 3 is done
//                 // s3 = false;//Mark the function as finished this is to prevent multiple instances of a function running at once
//                 return true;
//             }
//             // s3 = false;//Mark the function as finished this is to prevent multiple instances of a function running at once
//             return false;
//         // }
//         // return false;
//     }
//     public void dropBall() {
//         //TODO: Test timing
//         // if (!db) {//If the function isnt already running
//             // db = true;//Mark the function as already running to prevent multiple instances of a function running at once
//             //Drops the first ball in the system
//             //Set Top Motor to 0.175 (Barely enough to move it)
//             topShooterMotor.set(0.175);
//             //Set Bottom Motor to 0.175 (Barely enough to move it)
//             bottomShooterMotor.set(0.175);
//             //Set Bottom Motor to 0.2
//             feedMotor.set(-0.2);
//         // }
//     }
//     public void dropBalla() {
//         //Stop all the motors
//         feedMotor.stopMotor();
//         topShooterMotor.stopMotor();
//         bottomShooterMotor.stopMotor();
//         // db = false;//Mark the function as finished this is to prevent multiple instances of a function running at once
//     }

//     //      ----Manual Change Shoot Distance Function----   [Fully Functional]
//     public void changeShooterDistance(float changeTop,float changeBottom) {
//         //Top Motor
//         topMotorPower = topMotorPower + changeTop;
//         if (topMotorPower > 1) {
//             topMotorPower = 1f;
//         }
//         else if (topMotorPower < 0) {
//             topMotorPower = 0f;
//         }
//         //Bottom Motor
//         bottomMotorPower = bottomMotorPower + changeBottom;
//         if (bottomMotorPower > 1) {
//             bottomMotorPower = 1f;
//         }
//         else if (bottomMotorPower < 0) {
//             bottomMotorPower = 0f;
//         }
//         SmartDashboard.putNumber("Bottom Motor Saved Power", bottomMotorPower); //Updates "Bottom Motor Saved Power"(int)
//         SmartDashboard.putNumber("Top Motor Saved Power", topMotorPower); //Updates "Bottom Motor Saved Power"(int)
//     }

//     //      ----Machine Vision Functions----                [DF:Placeholder,RH:Placeholder]
//     public int distanceFront() {
//         //TODO: Figure out how to get distance value from rasberrypie (Uses Network Tables)
//         //Get distance infront of robot
//         int distance = 5;//Placeholder Value 
//         SmartDashboard.putNumber("Distance to Hub", distance);
//         return distance;
//     }
//     public int rotHub() {
//         //https://docs.wpilib.org/en/stable/docs/software/networktables/reading-array-values-published-by-networktables.html
//         //Above for when rasberry pi is publishing data to network table and is connected
//         int rotationToHub = 20; //TODO: Figure out how the Rasbery Pi mesures rotation to hub and adjust code accordingly (Value Currently Placeholder)
//         return rotationToHub;
//     }

//     //      ----Ball Count & Color Functions----            [BC:Fully Functional, B1C: Fully Functional]
//     public int ballCount() {
//         //Starts the count of balls at 0
//         int ballCount = 0;

//         //Look for first ball with color
//         if (ball1color() != "none") {
//             ballStuck = false;
            
//             //1 Ball Found
//             ballCount = 1;

//             //Look for second ball with photo eye
//             if (photoEye.get()) {
//                 //2 Balls found
//                 ballCount = 2;
//             }
//         }
//         else {
//             //Check if there is a ball in second position but not first
//             if (photoEye.get()) {
//                 //A ball is in the second position but not the first
//                 ballCount = 1;
//                 ballStuck = true;
//             }
//         }

//         //No else statment because value is initalized at 0
//         SmartDashboard.putNumber("Ball Count", ballCount);
//         SmartDashboard.putBoolean("Ball Stuck", ballStuck);
//         return ballCount;
//     }
//     public String ball1color() {
//         //Detected Color & Proximity from Color Sensor 1
//         Color detectedColor = colorSensor.getColor();
//         int proximity = colorSensor.getProximity();

//         String ball1Color = "none";

//         //Check ball color
//         if (proximity > 100) {//125 is 1 inch and half a ball away from the color sensor
//             if (detectedColor.red > detectedColor.blue) {
//                 //The 1st ball is Red
//                 //set variable to be returned to Red
//                 ball1Color = "Red";
//             }
//             else {
//                 //The 1st ball is Blue
//                 //set variable to be returned to Blue
//                 ball1Color = "Blue";
//             }
//         }
        
//         //No else statment because value is initalized at "none"
//         SmartDashboard.putString("Ball 1 Color", ball1Color);
//         return ball1Color;
//     }

//     //      ----Variable Update Function----                [Fully Functional]
//     public void updateHudVariablesShooter() {    
//         //Updates all the Variables that are sent to the drivers station for the shooter subsystem
        
//         ballCount(); //Updates "Ball Count"(int), "Ball Stuck"(bool), "Ball 1 Color"(string)
//         distanceFront(); //Updates "Distance to Hub"(int)
//         SmartDashboard.putNumber("Bottom Motor Saved Power", bottomMotorPower); //Updates "Bottom Motor Saved Power"(int)
//         SmartDashboard.putNumber("Top Motor Saved Power", topMotorPower); //Updates "Bottom Motor Saved Power"(int)

//         //Debug Values Start (Theses can be removed after fixing the shooter if they aren't needed)

//         SmartDashboard.putNumber("Top Motor Set Power", topShooterMotor.get()); //Updates "Bottom Motor Saved Power"(int)
//         SmartDashboard.putNumber("Bottom Motor Set Power", bottomShooterMotor.get()); //Updates "Bottom Motor Saved Power"(int)
//         SmartDashboard.putNumber("Feed Motor Set Power", feedMotor.get()); //Updates "Bottom Motor Saved Power"(int)

//         SmartDashboard.putBoolean("Top Motor Saftey", topShooterMotor.isSafetyEnabled()); //Pushes Top Motor Saftey to the Smart Dashboard
//         SmartDashboard.putBoolean("Bottom Motor Saftey", bottomShooterMotor.isSafetyEnabled()); //Pushes Bottom Motor Saftey to the Smart Dashboard
//         SmartDashboard.putBoolean("Feed Motor Saftey", feedMotor.isSafetyEnabled()); //Pushes Feed Motor Saftey to the Smart Dashboard

//         //Debug Values End
//     }

//     //      ----Controls [Right Trigger Auto Aim & Shoot High | Left Trigger|Auto Aim & Shoot Low]----  [Fully Functional]
//     @Override
//     public void periodic() {
//         //When Triggers are Pressed
//         // if (AimShootHigh.get() > 0.5) {
//         //     //Auto Aim & Shoot into the High Goal
//         //     shootingProcess3(true);
//         // }
//         // if (AimShootLow.get() > 0.5) {
//         //     //Auto Aim & Shoot into the Low Goal
//         //     shootingProcess3(false);
//         // }
//         updateHudVariablesShooter();//Updates the variables being sent to the drivers station
//     }
//     public void ControllerButtonInit() {
//         //When Buttons are Pressed
//         ManualShootIncrease.whenPressed(
//             //Manually Increase Shooter Distance by 5%
//             () -> this.changeShooterDistance(0.025f,0.025f)
//         );
//         ManualShootDecrease.whenPressed(
//             //Manually Decrease Shooter Distance by 5%
//             () -> this.changeShooterDistance(-0.025f,-0.025f)
//         );
//         //When Buttons are Held
//         ManualShootIncrease.whileActiveContinuous(
//             //Manually Increase Shooter Distance by 1% while held
//             () -> this.changeShooterDistance(0.01f,0.01f)
//         );
//         ManualShootDecrease.whileActiveContinuous(
//             //Manually Decrease Shooter Distance by 1% while held
//             () -> this.changeShooterDistance(-0.01f,-0.01f)
//         );
//     }
//     public void init() {
//         feedMotor.setSafetyEnabled(false);
//         feedMotor.stopMotor();
//         topShooterMotor.setSafetyEnabled(false);
//         topShooterMotor.stopMotor();
//         bottomShooterMotor.setSafetyEnabled(false);
//         bottomShooterMotor.stopMotor();
//     }
// }
