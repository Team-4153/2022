package frc.swerverobot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
// Pneumatics
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

//Motors
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

//Robot Map
import static frc.swerverobot.RobotMap.*;

//Controller
// import org.frcteam2910.common.robot.input.Axis;
// import org.frcteam2910.common.robot.input.Controller;
// import org.frcteam2910.common.robot.input.XboxController;
// import frc.swerverobot.XboxTrigger;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {}

  //public final Compressor phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);
  private DoubleSolenoid Intake_Sol = new DoubleSolenoid(PH_CAN_ID, PneumaticsModuleType.REVPH, INTAKE_SOLa, INTAKE_SOLb);
  private PWMVictorSPX Intake_Motor = new PWMVictorSPX(Intake_Motor_PWM);

  // private boolean triggerDone = false;

  @Override
  public void periodic() {

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void Motor_init ()
  {
    Intake_Motor.setSafetyEnabled(false);
  }

  // public void Motor_Start ()
  // {
  //   Intake_Motor.set(.5);
  // }

  // public void Motor_Stop ()
  // {
  //   Intake_Motor.set(0);
  // }

  public void Sol_init () 
  {
    // This method sets the solonoid to a position on bootup
    Intake_Sol.set(DoubleSolenoid.Value.kReverse);
  }

  public void Extend () 
  {
    Intake_Sol.set(DoubleSolenoid.Value.kForward);
    Intake_Motor.set(0.5);
  }

  public void Retract () 
  {
    Intake_Sol.set(DoubleSolenoid.Value.kReverse);
    Intake_Motor.set(0.0);  
  }

    

    public void Button_Binding (){
          Intake_Extension.whenPressed
          (
          //[Intake Subystem] Start Motor
            () ->  this.Extend()
          );
          Intake_Retract.whenPressed
          (
            //[Intake Subystem] Stop Motor
            () ->  this.Retract()
          );
        }
    
    
        
    public void init()
    {
        this.Sol_init();
        this.Motor_init();
        this.Button_Binding();
    }
}
