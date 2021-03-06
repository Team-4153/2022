package frc.swerverobot.subsystems;

//Setup
import static frc.swerverobot.RobotMap.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Pneumatics
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//Sensors
// import edu.wpi.first.wpilibj.util.Color;


public class IntakeSubsystem extends SubsystemBase {
  public  boolean feedStatus = false;
  public boolean phe2 = false;
  public boolean ballColorRun = false;

  //Creates a new IntakeSubsystem
  public IntakeSubsystem() {}

  //Solenoid for intake extension and compression
  private DoubleSolenoid Intake_Sol = new DoubleSolenoid(PH_CAN_ID, PneumaticsModuleType.REVPH, INTAKE_SOLa, INTAKE_SOLb);
  
  //      ----Ball Count & Color Functions----        [BC:Fully Functional, B1C: Fully Functional]
  public int ballCount() {
    // Starts the count of balls at 0
    int ballCount = 0;
    boolean ballStuck = false;

    // Look for first ball with color
    if (photoEye2.get()) {

      // 1 Ball Found
      ballCount = 1;

    } 
    // Look for second ball with photo eye
    if (photoEye.get()) {
      // 2 Balls found
      ballCount = ballCount + 1;
    }
    SmartDashboard.putNumber("Ball Count", ballCount);
    SmartDashboard.putBoolean("Ball Stuck", ballStuck);

    // No else statment because value is initalized at 0
    return ballCount;
  }
  // public String ball1color() {
  //   String ball1Color = "none";
  //   if ((photoEye2.get() != phe2) && photoEye2.get()) {
  //     phe2 = true;
  //     ball1Color = colorSensor();
  //   }
  //   else if (!photoEye2.get()) {
  //     phe2 = photoEye2.get();
  //   }
  //   SmartDashboard.putString("Ball 1 Color", ball1Color);
  //   return ball1Color;
  // }
  // public String colorSensor() {
  //   String ballColor = "none";
  //   Color detectedColor = colorSensor.getColor();
  //   int proximity = colorSensor.getProximity();
  //   // Check ball color
  //   if (proximity > colorSensorDistance) { // Smaller values are closer and bigger is farther away
  //     if (detectedColor.red > detectedColor.blue)// The 1st ball is Red
  //     {
  //       ballColor = "Red";
  //     } 
  //     else { // The 1st ball is Blue 
  //       ballColor = "Blue";
  //     }
  //   }
  //   return ballColor;
  // }

  @Override
  public void periodic() 
  {
    //Stops when 2 balls in robot
    int ballcount = ballCount();
    if (ballcount == 2)
    {
      Intake_Motor.stopMotor();
    }
    
    // if (ballColorRun != photoEye2.get()) {
    //   ball1color();
    // }
    // ballColorRun = photoEye2.get();
}

  @Override
  public void simulationPeriodic() 
  {

  }

  public void Motor_init()
  {
    Intake_Motor.setSafetyEnabled(false);
    feedMotor.setSafetyEnabled(false);
  }

  public void Sol_init() 
  {
    Intake_Sol.set(DoubleSolenoid.Value.kForward);
  }

  public void Extend() 
  {
    int ballcount = ballCount();
    if (ballcount < 2) 
    {
      Intake_Sol.set(DoubleSolenoid.Value.kReverse);
      Intake_Motor.set(-0.5);
    }
  }

  public void setIntakeSol(boolean compress) {
    if(compress) {
      Intake_Sol.set(DoubleSolenoid.Value.kForward);
    }
    else {
      Intake_Sol.set(DoubleSolenoid.Value.kReverse);
    }
  }

  public void setIntakeMotor(double speed) {
    Intake_Motor.set(speed);
  }

  public void Compress() 
  {
    Intake_Sol.set(DoubleSolenoid.Value.kForward);
    Intake_Motor.set(0.0);  
  }
            
  public void init()
  {
    this.Sol_init();
    this.Motor_init();
  }
}
