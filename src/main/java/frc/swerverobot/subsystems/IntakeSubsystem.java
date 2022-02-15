package frc.swerverobot.subsystems;

//
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.swerverobot.RobotMap.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Pneumatics
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

//Motors
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

//Sensors
import edu.wpi.first.wpilibj.util.Color;



public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {}

  private DoubleSolenoid Intake_Sol = new DoubleSolenoid(PH_CAN_ID, PneumaticsModuleType.REVPH, INTAKE_SOLa, INTAKE_SOLb);
  private PWMVictorSPX Intake_Motor = new PWMVictorSPX(Intake_Motor_PWM);

  boolean ballStuck = false; //Is true if there is a ball in the second position(Photoeye) but not the first(Color Sensor)

  //      ----Ball Count & Color Functions----
  public int ballCount() {
    //Starts the count of balls at 0
    int ballCount = 0;

    //Look for first ball with color
    if (ball1color() != "none") {
        ballStuck = false;
        
        //1 Ball Found
        ballCount = 1;

        //Look for second ball with photo eye
        if (photoEye.get() == true) {
            //2 Balls found
            ballCount = 2;
        }
    }
    else {
        //Check if there is a ball in second position but not first
        if (photoEye.get() == true) {
            //A ball is in the second position but not the first
            ballCount = 1;
            ballStuck = true;
        }
    }

    //No else statment because value is initalized at 0
    SmartDashboard.putNumber("Ball Count", ballCount);
    SmartDashboard.putBoolean("Ball Stuck", ballStuck);
    return ballCount;
}
  public String ball1color() {
    //Detected Color & Proximity from Color Sensor 1
    Color detectedColor = colorSensor.getColor();
    int proximity = colorSensor.getProximity();

    String ball1Color = "none";

    //Check ball color
    if (proximity > 125) {//125 is 1 inch and half a ball away from the color sensor
        if (detectedColor.red > detectedColor.blue) {
            //The 1st ball is Red
            //set variable to be returned to Red
            ball1Color = "Red";
        }
        else {
            //The 1st ball is Blue
            //set variable to be returned to Blue
            ball1Color = "Blue";
        }
    }
    
    //No else statment because value is initalized at "none"
    SmartDashboard.putString("Ball 1 Color", ball1Color);
    return ball1Color;
}


  @Override
  public void periodic() 
  {

  }

  @Override
  public void simulationPeriodic() 
  {

  }

  public void Motor_init ()
  {
    Intake_Motor.setSafetyEnabled(false);
  }


  public void Sol_init () 
  {
    Intake_Sol.set(DoubleSolenoid.Value.kReverse);
  }

  public void Extend () 
  {
    int ballcount = ballCount();
    if (ballcount == 0) {
      Intake_Sol.set(DoubleSolenoid.Value.kForward);
      Intake_Motor.set(0.5);
    }
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
