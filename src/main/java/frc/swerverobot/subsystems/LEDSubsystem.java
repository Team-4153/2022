// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swerverobot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.swerverobot.RobotMap.*;

public class LEDSubsystem extends SubsystemBase {
  private int lengthstrand1 = 25;
  private int lengthstrand2 = 53;
  private int lengthstrand3 = 70;

  private AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(lengthstrand1 + lengthstrand2 + lengthstrand3);
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

  boolean shooting = false;
  boolean winch = false;
  boolean climberExtended = false;
  boolean climberLockedR = false;
  boolean climberLockedL = false;
  double count = 0;
  boolean mode = false;

  boolean shooting2 = false;
  boolean winch2 = false;
  boolean climberLockedR2 = false;
  boolean climberLockedL2 = false;
  double count2 = 0;

  public int ledJump(int pos) {
    if (climberLockedR != climberLockedR2) {
      //Third Strand
      for (int i = 0; i < lengthstrand3; i++) {
        rightLED(lengthstrand1 + lengthstrand2 + i);
      }
      return lengthstrand1 + lengthstrand2;
    }
    else if (climberLockedL != climberLockedL2) {
      //First Strand
      for (int i = 0; i < lengthstrand1; i++) {
        leftLED(i);
      }
      return 0;
    }
    else if (count != count2 || shooting != shooting2) {
      //Second Strand
      for (int i = 0; i < lengthstrand2; i++) {
        shooterLED(lengthstrand1 + i);
      }
      return lengthstrand1;
    }
    else {
      //No changes to variables
      return pos+1;
    }
  }

  public void shooterLED(int pos) {
    if (shooting) {
      //If The Robot is Shooting
      m_ledBuffer.setRGB(pos, 0, 50, 0);//Green
    }
    else if (count == 2) {
      //If there are 2 Balls
      m_ledBuffer.setRGB(pos, 50, 50, 0);//Orange
    }
    else if (count == 1) {
      //If there is 1 Ball
      m_ledBuffer.setRGB(pos, 50, 0, 0);//Red 
    }
    else {
      //Idle
      m_ledBuffer.setRGB(pos, 0, 50, 50);//Teal
    }
  }

  public void rightLED(int pos) {
    if (climberLockedR) {
      //If Right Static Hook is Locked
      m_ledBuffer.setRGB(pos, 0, 75, 0);//Green
    }
    else if (winch) {
      //If Robot is Winching
      m_ledBuffer.setRGB(pos, 50, 50, 0);//Orange
    }
    else if (climberExtended) {
      //If Climber is Extended
      m_ledBuffer.setRGB(pos, 50, 0, 0);//Red
    }
    else if (mode) {
      m_ledBuffer.setRGB(pos, 0, 75, 0);//Green
    }
    else {
      //Idle
      m_ledBuffer.setRGB(pos, 0, 50, 50);//Teal
    }
  }

  public void leftLED(int pos) {
    if (climberLockedL) {
      //If Left Static Hook is Locked
      m_ledBuffer.setRGB(pos, 0, 150, 0);//Green
    }
    else if (winch) {
      //If Robot is Winching
      m_ledBuffer.setRGB(pos, 100, 100, 0);//Orange
    }
    else if (climberExtended) {
      //If Climber Is Extended
      m_ledBuffer.setRGB(pos, 100, 0, 0);//Red
    }
    else if (mode) {
      m_ledBuffer.setRGB(pos, 0, 75, 0);//Green
    }
    else {
      //Idle
      m_ledBuffer.setRGB(pos, 0, 100, 100);//Teal
    }
  }

  public void wormLED(int pos) {
    if (winch) {
      //If Robot is Winching
      m_ledBuffer.setRGB(pos, 0, 150, 0);//Green
    }
    else {
      //Idle
      m_ledBuffer.setRGB(pos, 60, 60, 60);//White
    }
  }

  public void colorPositionLED() {
    shooting = SmartDashboard.getBoolean("Shooting", false);
    winch = SmartDashboard.getBoolean("Climbing", false);
    climberExtended = SmartDashboard.getBoolean("Climb Arm Extention", false);
    climberLockedR = !StatHook1.get();
    climberLockedL = !StatHook2.get();
    // climberLocked = SmartDashboard.getBoolean("Climb Hook Locked", false);
    count = SmartDashboard.getNumber("Ball Count", 0);
    if (SmartDashboard.getString("Mode", "tele") == "auto") {
      mode = true;
    }
    else {
      mode = false;
    }

    //If CLimber is Locked The Worm is Green
    //If WInch is active background on Climber LED's is Orange
    //If Climber is extended and winch is not active Climber LED's are Red
    //IF there is 1 ball Shooter LED's are Red
    //If there are 2 Balls Shooter LED's are Orange
    //IF it is Shooting Shooter LED's are Green 

    if (running_LED <= lengthstrand1) {
      //Strand 1
      wormLED(running_LED);
    }
    else if (running_LED <= lengthstrand1 + lengthstrand2) {
      //Strand 2
      wormLED(running_LED);
    }
    else if (running_LED <= lengthstrand1 + lengthstrand2 + lengthstrand3) {
      //Strand 3
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
    else if (chasingLED <= lengthstrand1 + lengthstrand2 + lengthstrand3) {
      //Strand 3
      rightLED(chasingLED);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run


    //Set LED's
    //Worm
    colorPositionLED();

    //Change Positions
    running_LED = ledJump(running_LED);

    shooting2 = shooting;
    winch2 = winch;
    count2 = count;
    climberLockedL2 = climberLockedL;
    climberLockedR2 = climberLockedR;

    if (running_LED >= m_ledBuffer.getLength()) { //reset the position back to start
      running_LED = 0;
    }
    chasingLED = running_LED-length;
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
