package frc.swerverobot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase{
    public ClimberSubsystem(int motor, int solenoid, int aSwitch) {
        super();
        this.motor = new CANSparkMax(motor, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.solenoid = new DoubleSolenoid(solenoid, PneumaticsModuleType.CTREPCM, 0, 1);
        this.Switch = new DigitalInput(aSwitch);
    }

    private final CANSparkMax motor;
    private final DoubleSolenoid solenoid;
    private final DigitalInput Switch;
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
