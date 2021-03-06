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
  private int lengthstrand1 = 33;//Left Climber
  private int lengthstrandlefty = 77;//Length of the left Y
  private int lengthstrand2 = 34;//Shooter
  private int lengthstrandrighty = 77;//Length of the left Y
  private int lengthstrand3 = 84;//Right CLimber
  private int brightness = 1;//1-100 (1 is Birght 100 is Dim)
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
  boolean fancyLEDS = false;    //Can be activated anytime for style
  boolean climberExtended = false;  //When the climber is extended (false = retracted, true = extended)
  boolean climberLockedR = false;   //When the right static hook is locked (false = Unlocked, true = Locked)
  boolean climberLockedL = false;   //When the left static hook is locked (false = Unlocked, true = Locked)
  boolean allianceColor = false;    //What Alliance color the robot is on (false = blue, true = red)
  double count = 0;                 //How many balls are in the robot (0,1,2)
  String mode = "null";             //What mode the robot is in (null, auto-high, auto-low, tele)
  double distance = 0;                 //How far the robot is from the target (132-250)
  boolean goldenZone = false;
  int tick = 1;
  int LEDspeed = RobotMap.LEDSpeed;
  boolean LEDmove = RobotMap.FancyLEDMove;

  boolean shooting2 = false;        //The second variable is to check for changes since the led's were last changed
  boolean winch2 = false;           //The second variable is to check for changes since the led's were last changed
  boolean intake2 = false;          //The second variable is to check for changes since the led's were last changed
  boolean goldenZone2 = false;
  boolean climberLockedR2 = false;  //The second variable is to check for changes since the led's were last changed
  boolean climberLockedL2 = false;  //The second variable is to check for changes since the led's were last changed
  double count2 = 0;                //The second variable is to check for changes since the led's were last changed

  public int loopPos(int pos) {
    if (pos > m_ledBuffer.getLength()) { //reset the position back to start
      return pos - m_ledBuffer.getLength();
    }
    else if (pos < 0) {
      return pos + m_ledBuffer.getLength();
    }
    return pos;
  }
  public int trimPos(int pos) {
    if (pos > m_ledBuffer.getLength()) { //reset the position back to start
      return 1;
    }
    else if (pos < 0) {
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

  private void rainbow(int startPos, int Length, double rainbowOffset, double hueModdifier) {
    // if (startPos + Length < m_ledBuffer.getLength()) {
      for (var i = startPos; i < startPos + Length; i++) {
        final int hue = (int) (((((rainbowOffset + i) * 180) / Length) + hueModdifier) % 180);
        m_ledBuffer.setHSV(i, (hue), 255, 128);
      }
    // }
    // else {
    //   startPos = startPos-1;
    //   rainbow(startPos, Length, rainbowOffset, 0);
    // }
  }

  public int ledFillWhenChanged(int pos) {
    if (leftypluggedin && rightypluggedin) {
      //Both Y's plugged in
      if (goldenZone != goldenZone2) {
        for (int i = 0; i < lengthstrand3 + lengthstrandrighty; i++) {
          posledFunctions(lengthstrand1 + lengthstrandlefty + lengthstrand2 + i);
        }
        for (int i = 0; i < lengthstrand1 + lengthstrandlefty; i++) {
          posledFunctions(i);
        }
      }
      else if (climberLockedR != climberLockedR2) {
        //Third Strand & Right Y
        for (int i = 0; i < lengthstrand3 + lengthstrandrighty; i++) {
          posledFunctions(lengthstrand1 + lengthstrandlefty + lengthstrand2 + i);
        }
      }
      else if (climberLockedL != climberLockedL2) {
        //First Strand & Left Y
        for (int i = 0; i < lengthstrand1 + lengthstrandlefty; i++) {
          posledFunctions(i);
        }
      }
      else if (intake != intake2 || winch != winch2) {
        //First Strand
        for (int i = 0; i < lengthstrand1; i++) {
          posledFunctions(i);
        }
        //Third Strand
        for (int i = 0; i < lengthstrand3; i++) {
          posledFunctions(lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrandrighty + i);
        }
      }
      else if (count != count2 || shooting != shooting2) {
        //Second Strand
        for (int i = 1; i < lengthstrand2; i++) {
          posledFunctions(lengthstrand1 + lengthstrandlefty + i);
        }
      }
    }
    else if (leftypluggedin) {
      //Only Left Y Plugged in
      if (goldenZone != goldenZone2) {
        for (int i = 0; i < lengthstrand3; i++) {
          posledFunctions(lengthstrand1 + lengthstrandlefty + lengthstrand2 + i);
        }
        for (int i = 0; i < lengthstrand1 + lengthstrandlefty; i++) {
          posledFunctions(i);
        }
      }
      else if (climberLockedR != climberLockedR2) {
        //Third Strand & Right Y
        for (int i = 0; i < lengthstrand3; i++) {
          posledFunctions(lengthstrand1 + lengthstrandlefty + lengthstrand2 + i);
        }
      }
      else if (climberLockedL != climberLockedL2) {
        //First Strand & Left Y
        for (int i = 0; i < lengthstrand1 + lengthstrandlefty; i++) {
          posledFunctions(i);
        }
      }
      else if (intake != intake2 || winch != winch2) {
        //First Strand
        for (int i = 0; i < lengthstrand1; i++) {
          posledFunctions(i);
        }
        //Third Strand
        for (int i = 0; i < lengthstrand3; i++) {
          posledFunctions(lengthstrand1 + lengthstrandlefty + lengthstrand2 + i);
        }
      }
      else if (count != count2 || shooting != shooting2) {
        //Second Strand
        for (int i = 1; i < lengthstrand2; i++) {
          posledFunctions(lengthstrand1 + lengthstrandlefty + i);
        }
      }
    }
    else if (rightypluggedin) {
      //Only Right Y Plugged in
      if (goldenZone != goldenZone2) {
        for (int i = 0; i < lengthstrand3 + lengthstrandrighty; i++) {
          posledFunctions(lengthstrand1 + lengthstrand2 + i);
        }
        for (int i = 0; i < lengthstrand1 ; i++) {
          posledFunctions(i);
        }
      }
      else if (climberLockedR != climberLockedR2) {
        //Third Strand & Right Y
        for (int i = 0; i < lengthstrand3 + lengthstrandrighty; i++) {
          posledFunctions(lengthstrand1 + lengthstrand2 + i);
        }
      }
      else if (climberLockedL != climberLockedL2) {
        //First Strand & Left Y
        for (int i = 0; i < lengthstrand1; i++) {
          posledFunctions(i);
        }
      }
      else if (intake != intake2 || winch != winch2) {
        //First Strand
        for (int i = 0; i < lengthstrand1; i++) {
          posledFunctions(i);
        }
        //Third Strand
        for (int i = 0; i < lengthstrand3; i++) {
          posledFunctions(lengthstrand1 + lengthstrand2 + lengthstrandrighty + i);
        }
      }
      else if (count != count2 || shooting != shooting2) {
        //Second Strand
        for (int i = 1; i < lengthstrand2; i++) {
          posledFunctions(lengthstrand1 + i);
        }
      }
    }
    else {
      //No LEDS Plugged In
      if (goldenZone != goldenZone2) {
        for (int i = 0; i < lengthstrand3; i++) {
          posledFunctions(lengthstrand1 + lengthstrand2 + i);
        }
        for (int i = 0; i < lengthstrand1; i++) {
          posledFunctions(i);
        }
      }
      else if (climberLockedR != climberLockedR2) {
        //Third Strand & Right Y
        for (int i = 0; i < lengthstrand3; i++) {
          posledFunctions(lengthstrand1 + lengthstrand2 + i);
        }
      }
      else if (climberLockedL != climberLockedL2) {
        //First Strand & Left Y
        for (int i = 0; i < lengthstrand1; i++) {
          posledFunctions(i);
        }
      }
      else if (intake != intake2 || winch != winch2) {
        //First Strand
        for (int i = 0; i < lengthstrand1; i++) {
          posledFunctions(i);
        }
        //Third Strand
        for (int i = 0; i < lengthstrand3; i++) {
          posledFunctions(lengthstrand1 + lengthstrand2 + i);
        }
      }
      else if (count != count2 || shooting != shooting2) {
        //Second Strand
        for (int i = 1; i < lengthstrand2; i++) {
          posledFunctions(lengthstrand1 + i);
        }
      }
    }
    return pos+1;
  }

  public void posledFunctions(int pos) {
    if (rightypluggedin && leftypluggedin) {
      //If both Y's are plugged in
      if (pos < lengthstrand1) {
        //Third Strand
        leftLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrandlefty) {
        //Left Y
        leftyLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrandlefty + lengthstrand2) {
        //Second Strand
        shooterLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrandrighty) {
        //Right Y
        rightyLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrandrighty + lengthstrand3) {
        //Third Strand
        rightLED(pos);
      }
    } 
    else if (leftypluggedin) {
      //If the left Y is plugged in
      if (pos < lengthstrand1) {
        //First Strand
        leftLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrandlefty) {
        //Left Y
        leftyLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrandlefty + lengthstrand2) {
        //Second Strand
        shooterLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrand3) {
        //Third Strand
        rightLED(pos);
      }
    }
    else if (rightypluggedin) {
      //If the right Y is plugged in
      if (pos < lengthstrand1) {
        //First Strand
        leftLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrand2) {
        //Second Strand
        shooterLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrand2 + lengthstrandrighty) {
        //Right Y
        rightyLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrand2 + lengthstrandrighty + lengthstrand3) {
        //Third Strand
        rightLED(pos);
      }
    }
    else {
      //If none of the Y's are plugged in 
      if (pos < lengthstrand1) {
        //First Strand
        leftLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrand2) {
        //Second Strand
        shooterLED(pos);
      }
      else if (pos < lengthstrand1 + lengthstrand2 + lengthstrand3) {
        //Third Strand
        rightLED(pos);
      }
    }
  }
 
  public void shooterLED(int pos) {
    if (shooting) {
      //If The Robot is Shooting
      m_ledBuffer.setRGB(pos, 0, 255/brightness, 0);//Green
    }
    else if (count == 2) {
      //else if there are 2 Balls
      m_ledBuffer.setRGB(pos, 255/brightness, 255/brightness, 0);//Orange
    }
    else if (count == 1) {
      //else if there is 1 Ball
      m_ledBuffer.setRGB(pos, 255/brightness, 0, 0);//Red 
    }
    else if (goldenZone) {
      //else if Robot is in Golden Zone
      m_ledBuffer.setRGB(pos, 123/brightness, 30/brightness, 148/brightness);//Purple
    }
    else {
      //Idle
      m_ledBuffer.setRGB(pos, 0, 255/brightness, 255/brightness);//Teal
    }
  }
  public void climberLEDS(int pos) {
    if (winch) {
      //else if Robot is Winching
      m_ledBuffer.setRGB(pos, 255/brightness, 255/brightness, 0);//Orange
    }
    else if (climberExtended) {
      //else if Climber Is Extended
      m_ledBuffer.setRGB(pos, 255/brightness, 0, 0);//Red
    }
    else if (intake) {
      //else if Robot is Running Intake
      m_ledBuffer.setRGB(pos, 255/brightness, 127/brightness, 0);//Red-Orange
    }
    else if (goldenZone) {
      //else if Robot is outside of Auto-Aim shooting zone
      m_ledBuffer.setRGB(pos, 123/brightness, 30/brightness, 148/brightness);//Purple
    }
    else {
      //Idle
      m_ledBuffer.setRGB(pos, 0, 255/brightness, 255/brightness);//Teal
    }
  }
  public void rightLED(int pos) {
    if (climberLockedR) {
      //If Right Static Hook is Locked
      m_ledBuffer.setRGB(pos, 0, 255/brightness, 0);//Green
    }
    else {
      //Add the LEDS that arent different for each side
      climberLEDS(pos);
    }
  }
  public void leftLED(int pos) {
    //Low Density Strand so Increased Brightness
    if (climberLockedL) {
      //If Left Static Hook is Locked
      m_ledBuffer.setRGB(pos, 0, 255/brightness, 0);//Green
    }
    else {
      //Add the LEDS that arent different for each side
      climberLEDS(pos);
    }
  }

  public void bothLEDYs(int pos) {
    if (goldenZone) {
      m_ledBuffer.setRGB(pos, 123/brightness, 30/brightness, 148/brightness);//Purple
    }
    else if (allianceColor) {
      m_ledBuffer.setRGB(pos, 255/brightness, 0, 0);//Red
    }
    else {
      m_ledBuffer.setRGB(pos, 0, 0, 255/brightness);//Blue
    }
  }
  
  public void leftyLED(int pos) {
    //True is red and False is blue
    if (climberLockedL) {
      m_ledBuffer.setRGB(pos, 0, 255/brightness, 0);//Green
    }
    else {
      bothLEDYs(pos);
    }
  }
  public void rightyLED(int pos) {
    //True is red and False is blue
    if (climberLockedR) {
      m_ledBuffer.setRGB(pos, 0, 255/brightness, 0);//Green
    }
    else {
      bothLEDYs(pos);
    }
  }

  public void wormLED(int pos) {
    if (winch) {
      //If Robot is Winching
      m_ledBuffer.setRGB(pos, 0, 255/brightness, 0);//Green
    }
    else if (shooting) {
      //else if robot is shooting
      m_ledBuffer.setRGB(pos, 255/brightness, 255/brightness, 0);//Orange
    }
    else {
      //Idle
      m_ledBuffer.setRGB(pos, 255/brightness, 255/brightness, 255/brightness);//White
    }
  }

  public void toggleFancy() {
    boolean oldfancyLEDS = fancyLEDS;
    fancyLEDS = !oldfancyLEDS;
  }
  public void setHighAuto() {
    mode = "auto-high";
  }
  public void setLowAuto() {
    mode = "auto-low";
  }
  public void setTele() {
    mode = "tele";
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
    allianceColor = SmartDashboard.getNumber("IntakeBall/BallColor", 1) == 1;//Ball Color (1=red|2=blue)
    distance = SmartDashboard.getNumber("ShooterTarget/TargetDistance", 1);
    goldenZone = (distance < RobotMap.AutoAimMaxDistance && distance > RobotMap.AutoAimMinDistance);
    fancyLEDS = SmartDashboard.getBoolean("fancyLEDS", false);

    if (leftypluggedin && rightypluggedin) {
      //Both LEDs are plugged in
      if (running_LED <= lengthstrand1 + lengthstrandlefty && running_LED > lengthstrand1) {
        //The Left Y
        // if (mode == "auto-high") {
        //   //if Auto-High Goal then Set Left Y to Rainbow
        //   rainbow(lengthstrand1 + 1, lengthstrandlefty, 11);//Left Y
        // }
        // else if (mode == "auto-low") {
        //   //if Auto-High Goal then Set Left Y to Rainbow
        //   rainbow(lengthstrand1 + 1, lengthstrandlefty, 11);//Left Y
        // }
        // else {
          leftyLED(running_LED);
        // }
      }
      else if (running_LED <= lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrandrighty && running_LED > lengthstrand1 + lengthstrandlefty + lengthstrand2) {
        //The Right Y
        // if (mode == "auto-high") {
        //   //if Auto-High Goal then Set Right Y to Rainbow
        //   rainbow(lengthstrand1 + lengthstrandlefty + lengthstrand2 + 1, lengthstrandrighty,19);//Right Y
        // }
        // else if (mode == "auto-low") {
        //   //if Auto-High Goal then Set Right Y to Rainbow
        //   rainbow(lengthstrand1 + lengthstrandlefty + lengthstrand2 + 1, lengthstrandrighty,19);//Right Y
        // }
        // else {
          rightyLED(running_LED);
        // }
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
        rightyLED(chasingLED);
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

  public void fancyDisplay(double hueModdifier) {
    if (leftypluggedin && rightypluggedin) {
      //Both Y's Plugged In
      rainbow(0, lengthstrand1, 0, hueModdifier);//First Strand
      rainbow(lengthstrand1 + 1, lengthstrandlefty, 11, hueModdifier);//Left Y
      
      rainbow(lengthstrand1 + lengthstrandlefty + 1, lengthstrand2, -5, hueModdifier);//Second Strand
      rainbow(lengthstrand1 + lengthstrandlefty + (lengthstrand2/2) + 1, lengthstrand2/2, -5, -hueModdifier);//Second Strand
  
      rainbow(lengthstrand1 + lengthstrandlefty + lengthstrand2 + 1, lengthstrandrighty, 19, -hueModdifier);//Right Y
      rainbow(lengthstrand1 + lengthstrandlefty + lengthstrand2 + lengthstrandrighty, lengthstrand3, 19, -hueModdifier);//Third Strand
    }
    else if (rightypluggedin) {
      //Right Y Plugged In
      rainbow(0, lengthstrand1, 0, hueModdifier);//First Strand
      
      rainbow(lengthstrand1 + 1, lengthstrand2, -5, hueModdifier);//Second Strand
      rainbow(lengthstrand1 + (lengthstrand2/2) + 1, lengthstrand2/2, -5, -hueModdifier);//Second Strand
  
      rainbow(lengthstrand1 + lengthstrand2 + 1, lengthstrandrighty, 19, -hueModdifier);//Right Y
      rainbow(lengthstrand1 + lengthstrand2 + lengthstrandrighty, lengthstrand3, 19, -hueModdifier);//Third Strand
    }
    else if (leftypluggedin) {
      //Left Y Plugged In
      rainbow(0, lengthstrand1, 0, hueModdifier);//First Strand
      rainbow(lengthstrand1 + 1, lengthstrandlefty, 11, hueModdifier);//Left Y
      
      rainbow(lengthstrand1 + lengthstrandlefty + 1, lengthstrand2, -5, hueModdifier);//Second Strand
      rainbow(lengthstrand1 + lengthstrandlefty + (lengthstrand2/2) + 1, lengthstrand2/2, -5, -hueModdifier);//Second Strand
  
      rainbow(lengthstrand1 + lengthstrandlefty + lengthstrand2, lengthstrand3, 19, -hueModdifier);//Third Strand
    }
    else {
      //No Y's Plugged In
      rainbow(0, lengthstrand1, 0, hueModdifier);//First Strand
      rainbow(lengthstrand1 + 1, lengthstrandlefty, 11, hueModdifier);//Left Y
      
      rainbow(lengthstrand1 + lengthstrandlefty + 1, lengthstrand2, -5, hueModdifier);//Second Strand
      rainbow(lengthstrand1 + lengthstrandlefty + (lengthstrand2/2) + 1, lengthstrand2/2, -5, -hueModdifier);//Second Strand
  
      rainbow(lengthstrand1 + lengthstrandlefty + lengthstrand2, lengthstrand3, 19, -hueModdifier);//Third Strand
    }
  }

  public void moveRainbow() {
    if (fancyLEDS && FancyLEDMove) {
      if (tick > 360) {
        tick = 0;
      }
      tick = tick + (1 * LEDspeed);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //Set LED's
    if (fancyLEDS) {
      fancyDisplay(tick);
      moveRainbow();
    }
    else {
      colorPositionLED();
      //Change Positions
      running_LED = ledFillWhenChanged(running_LED);
      climberLockedR2 = climberLockedR;
      climberLockedL2 = climberLockedL;
      intake2 = intake;
      winch2 = winch;
      count2 = count;
      shooting2 = shooting;
      goldenZone2 = goldenZone;

      if (running_LED >= m_ledBuffer.getLength()) { //reset the position back to start
        running_LED = 0;
      }
      chasingLED = running_LED-length;
      if (chasingLED < 0) {
        chasingLED = m_ledBuffer.getLength()+chasingLED;
      }
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
