// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swerverobot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.swerverobot.RobotMap;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.swerverobot.RobotMap.*;

public class LEDSubsystem extends SubsystemBase {
  private int lengthstrand1 = 30;//Left Climber
  private int lengthstrandlefty = 42;//Length of the left Y
  private int lengthstrand2 = 34;//Shooter
  private int lengthstrandrighty = 42;//Length of the left Y
  private int lengthstrand3 = 84;//Right CLimber

  //Change Values in the RobotMap.java file
  private boolean rightypluggedin = RobotMap.RightYLEDS;
  private boolean leftypluggedin = RobotMap.LeftYLEDS;

  private AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(ledCount());
  private AddressableLED m_led = new AddressableLED(LEDPWMPort);

  private int running_LED = 1;
  private int chasingLED = 0;

  private int length = 10;

//SmartDashboard.getNumber("TargetOff", 0)
  /*
   * LED Strip Lengths
   * Climber: 28in/103 LED's
   * Shooter: 11in/40 LED's
   * 
   * Running LED sets Worm Color
   * Chasing LED sets background
   *
   * Climber LED Indicators
   * Extended: Background Red
   * Locked: Backgound Green
   * Wich Active: Moving Orange Light
   */
  public LEDSubsystem() {}

  boolean shooting = false;         //When the robot is shooting (false = not shooting, true = shooting)
  boolean winch = false;            //When the winch is active (false = not active, true = active)
  boolean intake = false;           //When the intake is extended (false = retracted, true = extended)
  boolean climberExtended = false;  //When the climber is extended (false = retracted, true = extended)
  boolean climberLockedR = false;   //When the right static hook is locked (false = Unlocked, true = Locked)
  boolean climberLockedL = false;   //When the left static hook is locked (false = Unlocked, true = Locked)
  boolean allianceColor = false;    //What Alliance color the robot is on (false = blue, true = red)
  double count = 0;                 //How many balls are in the robot (0,1,2)
  String mode = "null";             //What mode the robot is in (null, auto-high, auto-low, tele)
  int distance = 0;                 //How far the robot is from the target (132-250)

  boolean shooting2 = false;        //The second variable is to check for changes since the led's were last changed
  boolean winch2 = false;           //The second variable is to check for changes since the led's were last changed
  boolean intake2 = false;          //The second variable is to check for changes since the led's were last changed
  boolean climberLockedR2 = false;  //The second variable is to check for changes since the led's were last changed
  boolean climberLockedL2 = false;  //The second variable is to check for changes since the led's were last changed
  double count2 = 0;                //The second variable is to check for changes since the led's were last changed

  public int loopLEDPos(int pos) {
    //Tries to move the led's to be within the bounds of the array
    if (pos > m_ledBuffer.getLength()) {
      //When Position is above the length of the LED strip, return the position from 0
      return trimLEDPos(pos - m_ledBuffer.getLength());
    }
    else if (pos < 0) {
      //When Position is bellow the length of the LED strip, return the position from the end
      return trimLEDPos(pos + m_ledBuffer.getLength());
    }
    return pos;
  }
  public int trimLEDPos(int pos) {
    //Limits the LED's to the bounds of the array.
    if (pos > m_ledBuffer.getLength() || pos < 0) {
      //When Position is outside of the array set it to 1
      return 1;
    }
    return pos;
  }

  public int ledCount() {
    if (leftypluggedin && rightypluggedin) {
      //Both Y's plugged in
      return lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrandrighty + lengthstrand3;
    }
    else if (rightypluggedin) {
      //Only Right Y Plugged in
      return lengthstrand1 + lengthstrandrighty + lengthstrand2 + lengthstrand3;
    }
    else if (leftypluggedin) {
      //Only Left Y Plugged in
      return lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrand3;
    }
    else {
      //No Y's plugged in
      return lengthstrand1 + lengthstrand2 + lengthstrand3;
    }
  }

  public int ledFillWhenChanged(int pos) {
    if (leftypluggedin && rightypluggedin) {
      //Both Y's plugged in
      if (climberLockedR != climberLockedR2) {
        //Third Strand & Right Y
        for (int i = 0; i < lengthstrand3 + lengthstrandrighty; i++) {
          rightLED(lengthstrand1 + lengthstrandlefty + lengthstrand2 + i);
        }
      }
      else if (climberLockedL != climberLockedL2) {
        //First Strand & Left Y
        for (int i = 0; i < lengthstrand1 + lengthstrandlefty; i++) {
          leftLED(i);
        }
      }
      else if (intake != intake2 || winch != winch2) {
        //First Strand
        for (int i = 0; i < lengthstrand1; i++) {
          leftLED(i);
        }
        //Third Strand
        for (int i = 0; i < lengthstrand3; i++) {
          rightLED(lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrandrighty + i);
        }
      }
      else if (count != count2 || shooting != shooting2) {
        //Second Strand
        for (int i = 0; i < lengthstrand2; i++) {
          shooterLED(lengthstrand1 + lengthstrandlefty + i + 1);
        }
      }
    }
    else if (leftypluggedin) {
      //Only Left Y Plugged in
      if (climberLockedR != climberLockedR2) {
        //Third Strand
        for (int i = 0; i < lengthstrand3; i++) {
          rightLED(lengthstrand1 + lengthstrandlefty + lengthstrand2 + i);
        }
      }
      else if (climberLockedL != climberLockedL2) {
        //First Strand & Left Y
        for (int i = 0; i < lengthstrand1 + lengthstrandlefty; i++) {
          leftLED(i);
        }
      }
      else if (intake != intake2 || winch != winch2) {
        //First Strand
        for (int i = 0; i < lengthstrand1 + lengthstrandlefty; i++) {
          leftLED(i);
        }
        //Third Strand
        for (int i = 0; i < lengthstrand3; i++) {
          rightLED(lengthstrand1 + lengthstrandlefty + lengthstrand2 + i);
        }
      }
      else if (count != count2 || shooting != shooting2) {
        //Second Strand
        for (int i = 0; i < lengthstrand2; i++) {
          shooterLED(lengthstrand1 + lengthstrandlefty + i);
        }
      }
    }
    else if (rightypluggedin) {
      //Only Right Y Plugged in
      if (climberLockedR != climberLockedR2) {
        //Third Strand & Right Y
        for (int i = 0; i < lengthstrand3 + lengthstrandrighty; i++) {
          rightLED(lengthstrand1 + lengthstrand2 + i);
        }
      }
      else if (climberLockedL != climberLockedL2) {
        //First Strand
        for (int i = 0; i < lengthstrand1; i++) {
          leftLED(i);
        }
      }
      else if (intake != intake2 || winch != winch2) {
        //First Strand
        for (int i = 0; i < lengthstrand1; i++) {
          leftLED(i);
        }
        //Third Strand
        for (int i = 0; i < lengthstrand3; i++) {
          rightLED(lengthstrand1 + lengthstrand2 + lengthstrandrighty + i);
        }
      }
      else if (count != count2 || shooting != shooting2) {
        //Second Strand
        for (int i = 0; i < lengthstrand2; i++) {
          shooterLED(lengthstrand1 + i + 1);
        }
      }
    }
    else {
      if (climberLockedR != climberLockedR2) {
        //Third Strand
        for (int i = 0; i < lengthstrand3; i++) {
          rightLED(lengthstrand1 + lengthstrand2 + i);
        }
      }
      else if (climberLockedL != climberLockedL2) {
        //First Strand
        for (int i = 0; i < lengthstrand1; i++) {
          leftLED(i);
        }
      }
      else if (intake != intake2 || winch != winch2) {
        //First Strand
        for (int i = 0; i < lengthstrand1; i++) {
          leftLED(i);
        }
        //Third Strand
        for (int i = 0; i < lengthstrand3; i++) {
          rightLED(lengthstrand1 + lengthstrand2 + i);
        }
      }
      else if (count != count2 || shooting != shooting2) {
        //Second Strand
        for (int i = 0; i < lengthstrand2; i++) {
          shooterLED(lengthstrand1 + i);
        }
      }
    }
    return pos+1;
  }

  public void shooterLED(int pos) {
    pos = loopLEDPos(pos);
    if (shooting) {
      //If The Robot is Shooting
      m_ledBuffer.setRGB(pos, 0, 50, 0);//Green
    }
    else if (count == 2) {
      //else if there are 2 Balls
      m_ledBuffer.setRGB(pos, 50, 50, 0);//Orange
    }
    else if (count == 1) {
      //esle if there is 1 Ball
      m_ledBuffer.setRGB(pos, 50, 0, 0);//Red 
    }
    else {
      //Idle
      m_ledBuffer.setRGB(pos, 0, 50, 50);//Teal
    }
  }
  public void climberLEDS(int pos) {
    if (winch) {
      //else if Robot is Winching
      m_ledBuffer.setRGB(pos, 90, 90, 0);//Orange
    }
    else if (climberExtended) {
      //else if Climber Is Extended
      m_ledBuffer.setRGB(pos, 90, 0, 0);//Red
    }
    else if (intake) {
      //else if Robot is Running Intake
      m_ledBuffer.setRGB(pos, 115, 65, 0);//Red-Orange
    }
    else if (distance > 150 && distance < 200) {
      //else if robot is in the ideal shooting zone
      m_ledBuffer.setRGB(pos, 0, 137, 100);//Dark Green
    }
    else if (mode == "auto-high") {
      //else if Robot is in a High Goal Auto
      m_ledBuffer.setRGB(pos, 133, 40, 158);//Purple
    }
    else if (mode == "auto-low") {
      //else if Robot is in a Low Goal Auto
      m_ledBuffer.setRGB(pos, 0, 46, 143);//Dark-Blue
    }
    else if (mode == "tele") {
      //else if Robot is in Tele
      m_ledBuffer.setRGB(pos, 0, 156, 150);//Teal
    }
    else {
      //Idle
      m_ledBuffer.setRGB(pos, 0, 56, 50);//Teal
    }
  }
  public void yLEDS(int pos) {
    if (allianceColor) {
      //if the robot is on the Red alliance
      m_ledBuffer.setRGB(pos, 175, 0, 0);//Red
    }
    else {
      //else if the robot is on the Blue alliance
      m_ledBuffer.setRGB(pos, 0, 156, 150);//Blue
    }
  }
  
  public void rightLED(int pos) {
    pos = loopLEDPos(pos);
    if (climberLockedR) {
      //If Right Static Hook is Locked
      m_ledBuffer.setRGB(pos, 0, 65, 0);//Green
    }
    else {
      //The rest of the LED's are the same for left and right strands
      climberLEDS(pos);
    }
  }
  public void leftLED(int pos) {
    pos = loopLEDPos(pos);
    //Low Density Strand so Increased Brightness
    if (climberLockedL) {
      //If Left Static Hook is Locked
      m_ledBuffer.setRGB(pos, 0, 150, 0);//Green
    }
    else {
      //The rest of the LED's are the same for left and right strands
      climberLEDS(pos);
    }
  }

  public void leftyLED(int pos) {
    pos = loopLEDPos(pos);
    if (climberLockedL) {
      //If Left Static Hook is Locked
      m_ledBuffer.setRGB(pos, 0, 150, 0);//Green
    }
    else {
      //The rest of the LED's are the same for left and right strands
      yLEDS(pos);
    }
  }
  public void rightyLED(int pos) {
    pos = loopLEDPos(pos);
    if (climberLockedR) {
      //If Right Static Hook is Locked
      m_ledBuffer.setRGB(pos, 0, 150, 0);//Green
    }
    else {
      //The rest of the LED's are the same for left and right strands
      yLEDS(pos);
    }
  }

  public void wormLED(int pos) {
    pos = loopLEDPos(pos);
    if (winch) {
      //If Robot is Winching
      m_ledBuffer.setRGB(pos, 0, 150, 0);//Green
    }
    else if (shooting) {
      //else if robot is shooting
      m_ledBuffer.setRGB(pos, 100, 100, 0);//Orange
    }
    else {
      //Idle
      m_ledBuffer.setRGB(pos, 60, 60, 60);//White
    }
  }

  /*
  Moving Worm
    If winch is active color is green
    else if  shooter is active color is Orange
    defaults to white

  Climber Sides
    If Static Hooks are in locked position the Relative side turns green
    else if robot is winching color is Orange
    else if climber is extended color is red
    else if the intake is running color is red orange
    else if robot is in auto color is green
    defaults to team color
  */

  public void colorPositionLED() {
    shooting = SmartDashboard.getBoolean("Shooting", false); // Shooting (true|false)
    winch = SmartDashboard.getBoolean("Winch", false); // Winch Active (true|false)
    climberExtended = !SmartDashboard.getBoolean("Climb Arm Extention", true);//Climber Arm Extention (true|false)
    climberLockedR = !StatHook1.get();//Right Static Arm (true|false)
    climberLockedL = !StatHook2.get();//Left Static Arm (true|false)
    intake = Intake_Motor.get() != 0;//Intake Motor (true|false)
    count = SmartDashboard.getNumber("Ball Count", 0);//Ball Count (0|1|2)
    mode = SmartDashboard.getString("Mode", "null");//Mode (auto-high|auto-low|tele)
    allianceColor = SmartDashboard.getNumber("IntakeBall/BallColor", 1) == 1;//Ball Color (1=red|2=blue)
    distance = (int) SmartDashboard.getNumber("ShooterTarget/TargetDistance", 0);//Distance (132-250)

    if (leftypluggedin && rightypluggedin) {
      //Both LEDs are plugged in
      if (running_LED <= lengthstrand1 + lengthstrandlefty && running_LED > lengthstrand1) {
        //The Left Y
        leftyLED(running_LED);
      }
      else if (running_LED <= lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrandrighty && running_LED > lengthstrand1 + lengthstrandlefty + lengthstrand2) {
        //The Right Y
        rightLED(running_LED);
      }
      else {
        //Everything Else
        wormLED(running_LED);
      }

      if (chasingLED <= lengthstrand1) {
        //Strand 1
        leftLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrandlefty) {
        //The Y
        leftyLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrandlefty + lengthstrand2) {
        //Strand 2
        shooterLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrandrighty) {
        //The Right Y
        leftyLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrandrighty + lengthstrand3) {
        //Strand 3
        rightLED(chasingLED);
      }
    }
    else if (leftypluggedin) {
      //Only Left LED is plugged in
      if (running_LED <= lengthstrand1 + lengthstrandlefty && running_LED > lengthstrand1) {
        //The Left Y
        leftyLED(running_LED);
      }
      else {
        //Strand 3
        wormLED(running_LED);
      }

      if (chasingLED <= lengthstrand1) {
        //Strand 1
        leftLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrandlefty) {
        //The Y
        leftyLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrandlefty + lengthstrand2) {
        //Strand 2
        shooterLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrand3) {
        //Strand 3
        rightLED(chasingLED);
      }
    }
    else if (rightypluggedin) {
      //Only Right LED is plugged in
      if (running_LED <= lengthstrand1 + lengthstrand2 + lengthstrandrighty && running_LED > lengthstrand1 + lengthstrand2) {
        //The Right Y
        rightLED(running_LED);
      }
      else {
        //Everything Else
        wormLED(running_LED);
      }

      if (chasingLED <= lengthstrand1) {
        //Strand 1
        leftLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrand2) {
        //Strand 2
        shooterLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrand2 + lengthstrandrighty) {
        //The Right Y
        leftyLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrand2 + lengthstrandrighty + lengthstrand3) {
        //Strand 3
        rightLED(chasingLED);
      }
    }
    else {
      //Neither Y's are plugged in
      wormLED(running_LED);

      if (chasingLED <= lengthstrand1) {
        //Strand 1
        leftLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrand2) {
        //Strand 2
        shooterLED(chasingLED);
      }
      else if (chasingLED <= lengthstrand1 + lengthstrand2 + lengthstrand3) {
        //Strand 3
        rightLED(chasingLED);
      }
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //Set LED's to the correct color
    colorPositionLED();

    climberLockedR2 = climberLockedR;
    climberLockedL2 = climberLockedL;
    intake2 = intake;
    winch2 = winch;
    count2 = count;
    shooting2 = shooting;

    //Update Running LED Position and trim position
    running_LED = loopLEDPos(ledFillWhenChanged(running_LED));
    //Move Running LED to start if beyond end of LED Strip
    if (running_LED >= m_ledBuffer.getLength()) { //reset the position back to start
      running_LED = 0;
    }
    //Update Chasing LED Position and trim position
    chasingLED = loopLEDPos(running_LED-length);
    if (chasingLED < 0) {
      chasingLED = m_ledBuffer.getLength()+chasingLED;
    }
    
    m_led.setData(m_ledBuffer);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void init() {
    m_led.setLength(m_ledBuffer.getLength());
    m_led.start();
  }

}
