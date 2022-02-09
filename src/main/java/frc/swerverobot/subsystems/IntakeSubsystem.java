// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.swerverobot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.swerverobot.RobotMap;
// Pneumatics
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

//Motors
//import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

//Robot Map
import static frc.swerverobot.RobotMap.*;


public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {}

  //public final Compressor phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);
  private DoubleSolenoid exampleSolenoidPH = new DoubleSolenoid(1, PneumaticsModuleType.REVPH, RobotMap.INTAKE_SOLa, RobotMap.INTAKE_SOLb);
  private PWMVictorSPX victor = new PWMVictorSPX(Intake_Motor_PWM);


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  public void Motor_init ()
  {
    victor.setSafetyEnabled(false);
  }
  public void Motor_Start ()
  {
    if (exampleSolenoidPH.get() == DoubleSolenoid.Value.kForward)
    {
    victor.set(.2);
    }
    else
    {
    victor.set(0);
    }

  }
  public void Motor_Stop ()
  {
    victor.set(0);
  }

    public void Sol_toggle () {
    // This method turns the whenPressed into a toggle command
    if (exampleSolenoidPH.get() == DoubleSolenoid.Value.kReverse)
    {
      exampleSolenoidPH.set(DoubleSolenoid.Value.kForward);
    } else {
      exampleSolenoidPH.set(DoubleSolenoid.Value.kReverse);
    }
      
      
  }

    public void Sol_init () {
    // This method sets the solonoid to a position on bootup
    exampleSolenoidPH.set(DoubleSolenoid.Value.kReverse);
    }

public void Button_Binding (){
    Intake_Extension.whenPressed(
        () -> this.Sol_toggle()
);
    Intake_Roller.whileHeld(
        //[Intake Subystem] Start Motor
        () ->  this.Motor_Start()
);
    Intake_Roller.whenReleased(
        //[Intake Subystem] Stop Motor
        () ->  this.Motor_Stop()
);
}

public void init(){
    this.Sol_init();
    this.Motor_init();
    this.Button_Binding();
}
}
