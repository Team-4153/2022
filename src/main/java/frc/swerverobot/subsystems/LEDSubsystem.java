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
  private int lengthstrand1 = 80;
  private int lengthstrand2 = 34;
  private int lengthstrand3 = 80;

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
  boolean climberLocked = false;

  boolean shooting2 = false;
  boolean winch2 = false;
  boolean climberExtended2 = false;
  boolean climberLocked2 = false;
  double count = 0;

  public void colorPositionLED() {
    shooting = SmartDashboard.getBoolean("Shooting", false);
    winch = SmartDashboard.getBoolean("Climbing", false);
    climberExtended = SmartDashboard.getBoolean("Climb Arm Extention", false);
    climberLocked = SmartDashboard.getBoolean("Climb Hook Locked", false);
    count = SmartDashboard.getNumber("Ball Count", 0);

    if (shooting != shooting2) {
      running_LED = lengthstrand1;
    }
    else if (winch != winch2) {
      running_LED = lengthstrand1;
    }
    else if (climberExtended != climberExtended2) {
      running_LED = 1;
    }
    else if (climberLocked != climberLocked2) {
      running_LED = lengthstrand1;
    }

    if (running_LED <= lengthstrand1) {
      //Strand 1
      if (climberLocked) {
        m_ledBuffer.setRGB(running_LED, 100, 0, 0);//Red
      }
      else {
        m_ledBuffer.setRGB(running_LED, 30, 30, 30);//White
      }
    }
    else if (running_LED <= lengthstrand1 + lengthstrand2) {
      //Strand 2
      m_ledBuffer.setRGB(running_LED, 50, 50, 50);//White
    }
    else if (running_LED <= lengthstrand1 + lengthstrand2 + lengthstrand3) {
      //Strand 3
      if (climberLocked) {
        m_ledBuffer.setRGB(running_LED, 100, 0, 0);//Red
      }
      else {
        m_ledBuffer.setRGB(running_LED, 30, 30, 30);//White
      }
    }


    if (chasingLED <= lengthstrand1) {
      //Strand 1
      if (winch) {
        //If Climber Is winching
        m_ledBuffer.setRGB(chasingLED, 50, 50, 0);//Orange
      }
      else if (climberExtended) {
        //If Climber Is Extended
        m_ledBuffer.setRGB(chasingLED, 50, 0, 0);//Red
      }
      else {
        //Else
        m_ledBuffer.setRGB(chasingLED, 0, 50, 50);//Teal
      }
    }
    else if (chasingLED <= lengthstrand1 + lengthstrand2) {
      //Strand 2
      if (shooting) {
        //If The Robot is Shooting
        m_ledBuffer.setRGB(chasingLED, 0, 50, 0);//Green
      }
      else if (count == 2) {
        //If there are 2 Balls
        m_ledBuffer.setRGB(chasingLED, 50, 50, 0);//Orange
      }
      else if (count == 1) {
        //If there is 1 Ball
        m_ledBuffer.setRGB(chasingLED, 50, 0, 0);//Red 
      }
      else {
        m_ledBuffer.setRGB(chasingLED, 0, 50, 50);//Teal
      }
    }
    else if (chasingLED <= lengthstrand1 + lengthstrand2 + lengthstrand3) {
      //Strand 3
      if (winch) {
        //If Climber Is winching
        m_ledBuffer.setRGB(chasingLED, 50, 50, 0);//Orange
      }
      else if (climberExtended) {
        m_ledBuffer.setRGB(chasingLED, 50, 0, 0);//Red
      }
      else {
        m_ledBuffer.setRGB(chasingLED, 0, 50, 50);//Teal
      }
    }
    
    shooting = shooting2;
    winch = winch2;
    climberExtended = climberExtended2;
    climberLocked = climberLocked2;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run


    //Set LED's
    //Worm
    colorPositionLED();

    //Change Positions
    running_LED++;
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
