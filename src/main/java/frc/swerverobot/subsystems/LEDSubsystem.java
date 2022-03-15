// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swerverobot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import static frc.swerverobot.RobotMap.*;
import edu.wpi.first.wpilibj.util.Color;

public class LEDSubsystem extends SubsystemBase {
  private int lengthstrand1 = 96;
  private int lengthstrand2 = 42;
  private int lengthstrand3 = 99;

  private AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(lengthstrand1 + lengthstrand2 + lengthstrand3);
  private AddressableLED m_led = new AddressableLED(LEDPWMPort);

  private int running_LED = 1;
  private int chasingLED = 0;

  private int length = 8; // if you change length, the strands get messed up

  // private double winch = SmartDashboard.getNumber("winchsol", 0);
  // private double climberLocked = SmartDashboard.getNumber("climbrelocked", 0);
  // private double shooting = SmartDashboard.getNumber("shootingmotor", 0);
  // private double climberExtended = SmartDashboard.getNumber("climbersol", 0);

  private boolean winch = false;
  private boolean climberLocked = false;
  private boolean shooting = false;
  private boolean climberExtended = false;

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

  boolean phe2 = false;

  //      ----Ball Count & Color Functions----        [BC:Fully Functional, B1C: Fully Functional]
  public int ballCount() {
    // Starts the count of balls at 0
    int ballCount = 0;

    // Look for first ball with color
    if (ball1color() != "none") {

      // 1 Ball Found
      ballCount = 1;

      // Look for second ball with photo eye
      if (photoEye.get()) {
        // 2 Balls found
        ballCount = 2;
      }
    } 
    else {
      // Check if there is a ball in second position but not first
      if (photoEye.get()) {
        // A ball is in the second position but not the first
        ballCount = 1;
      }
    }

    // No else statment because value is initalized at 0
    return ballCount;
  }
  public String ball1color() {
    String ball1Color = "none";
    if (photoEye2.get() && !phe2) {
      phe2 = true;
      ball1Color = colorSensor();
    }
    else if (!photoEye2.get()) {
      phe2 = false;
    }
    return ball1Color;
  }
  public String colorSensor() {
    String ballColor = "none";
    Color detectedColor = colorSensor.getColor();
    int proximity = colorSensor.getProximity();
    // Check ball color
    if (proximity > colorSensorDistance) { // Smaller values are closer and bigger is farther away
      if (detectedColor.red > detectedColor.blue)// The 1st ball is Red
      {
        ballColor = "Red";
      } 
      else { // The 1st ball is Blue 
        ballColor = "Blue";
      }
    }
    return ballColor;
  }

  public void colorPositionLED() {
    if (running_LED <= lengthstrand1) {
      if (winch) {
        m_ledBuffer.setRGB(running_LED, 55, 10, 0);
      }
      else {
        m_ledBuffer.setRGB(running_LED, 10, 10, 10);
      }
    }
    else if (running_LED <= lengthstrand1 + lengthstrand2) {
      m_ledBuffer.setRGB(running_LED, 10, 10, 10);
    }
    else if (running_LED <= lengthstrand1 + lengthstrand2 + lengthstrand3) {
      if (running_LED <= lengthstrand1) {
        if (winch) {
          m_ledBuffer.setRGB(running_LED, 55, 10, 0);
        }
        else {
          m_ledBuffer.setRGB(running_LED, 10, 10, 10);
        }
      }
    }


    if (chasingLED <= lengthstrand1) {
      if (climberLocked) {
        m_ledBuffer.setRGB(chasingLED, 0, 50, 0);
      }
      else if (climberExtended) {
        m_ledBuffer.setRGB(chasingLED, 50, 0, 0);
      }
      else {
        m_ledBuffer.setRGB(chasingLED, 0, 3, 3);
      }
    }
    else if (running_LED <= lengthstrand1 + lengthstrand2) {
      if (ballCount() == 2) {
        m_ledBuffer.setRGB(chasingLED, 63, 43, 0);
      }
      else if (ballCount() == 1) {
        m_ledBuffer.setRGB(chasingLED, 50, 0, 0);
      }
      else if (shooting) {
        m_ledBuffer.setRGB(chasingLED, 0, 50, 0);
      }
      else {
        m_ledBuffer.setRGB(chasingLED, 0, 3, 3);
      }
    }
    else if (running_LED <= lengthstrand1 + lengthstrand2 + lengthstrand3) {
      if (climberLocked) {
        m_ledBuffer.setRGB(chasingLED, 0, 50, 0);
      }
      else if (climberExtended) {
        m_ledBuffer.setRGB(chasingLED, 50, 0, 0);
      }
      else {
        m_ledBuffer.setRGB(chasingLED, 0, 3, 3);
      }
    }
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
