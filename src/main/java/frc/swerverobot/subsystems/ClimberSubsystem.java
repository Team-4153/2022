package frc.swerverobot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase{
    private CANSparkMax motor = new CANSparkMax(9, CANSparkMaxLowLevel.MotorType.kBrushless);
    private DoubleSolenoid solenoid = new DoubleSolenoid(0, PneumaticsModuleType.CTREPCM, 0,1);
    private DigitalInput Switch = new DigitalInput(0);
    public void RunMotor(){
        motor.set(1);
    }
    public void StopMotor(){
        motor.set(0);
    }
    public void Lock(){
        solenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void Unlock(){
        solenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public boolean GetSwitch(){
        return Switch.get();
    }
}
