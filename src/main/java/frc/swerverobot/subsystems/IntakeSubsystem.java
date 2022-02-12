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
import org.frcteam2910.common.robot.input.Axis;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;
import frc.swerverobot.XboxTrigger;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {}

  //public final Compressor phCompressor = new Compressor(1, PneumaticsModuleType.REVPH);
  private DoubleSolenoid exampleSolenoidPH = new DoubleSolenoid(PH_CAN_ID, PneumaticsModuleType.REVPH, INTAKE_SOLa, INTAKE_SOLb);
  private PWMVictorSPX victor = new PWMVictorSPX(Intake_Motor_PWM);

  private boolean triggerDone = false;

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // if (Intake_Extension.get() > 0.5)
    //   {
    //     if (!triggerDone)
    //     {
    //       this.Sol_toggle();
    //       triggerDone = true;
    //     }
    //   }
    //   else
    //   {
    //     triggerDone = false;
    //   }
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
    victor.set(.2);
  }
  public void Motor_Stop ()
  {
    victor.set(0);
  }

    public void Extension () {
    // This method turns the whenPressed into a toggle command
    if (exampleSolenoidPH.get() == DoubleSolenoid.Value.kReverse)
    {
      exampleSolenoidPH.set(DoubleSolenoid.Value.kForward);
    } 
    else 
    {
      exampleSolenoidPH.set(DoubleSolenoid.Value.kReverse);
    }
  }

    public void Retract () {
      // This method turns the whenPressed into a toggle command
      if (exampleSolenoidPH.get() == DoubleSolenoid.Value.kReverse)
      {
        exampleSolenoidPH.set(DoubleSolenoid.Value.kForward);
      } 
      else 
      {
        exampleSolenoidPH.set(DoubleSolenoid.Value.kReverse);
      }  
      }

    public void Sol_init () {
    // This method sets the solonoid to a position on bootup
    exampleSolenoidPH.set(DoubleSolenoid.Value.kReverse);
    }

    public void Button_Binding (){
          Intake_Extension.whenPressed
          (
          //[Intake Subystem] Start Motor
            () ->  this.Extension()
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
