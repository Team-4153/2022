package frc.swerverobot.subsystems;

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

 - Processes
    - Shooting Process #1 (No Aim Assist & 2 Balls)
        - Driver Preses Shoot Button
        - Front 2 wheels spin up to speed (0.45s)
        - Release first ball
        - Wait for wheels to regain lost speed (0.5s)
        - Release Second Ball
        - End
        
    - Shooting Process #2 (Assist for top hoop & 2 Balls)
        - Driver Preses aim button for either high or low (Maybe a button combination for the low goal)
        - Machiene vision to find the reflective tape at the top of the hub
        - Robot rotates shooter to face hub (Either rotates entire robot or just the turret)
        - Uses Distance sensor to detect distance to hub and change verticle offset accordingly
        - Driver should then press manual shoot button (Process #1)
        - End
        
    - Shooting Process #3 (Auto Aim/Auto Shoot & 2 Balls)
        - Driver Preses Shoot Button for either high or low (Maybe a button combination for the low goal)
        - Start front 2 wheels spinning(Done at same time as aiming)
        - Machiene vision to find the reflective tape at the top of the hub
        - Robot rotates shooter to face hub (Either rotates entire robot or just the turret)
        - Uses Distance sensor to detect distance to hub and change verticle offset accordingly
        - Release first ball
        - Wait for wheels to regain lost speed (0.5s)
        - Release Second Ball
        - End

 - Sensors Needed
    - Distnace Sensor (Shooting Process #2,3)
    - Machine Vision
    - PID Controller (To keep flywheels at constant speed, maybe)

*/

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{
    
}
