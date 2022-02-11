package frc.swerverobot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.swerverobot.RobotMap;

public class ClimberSubsystem extends SubsystemBase{
    public ClimberSubsystem(int motor, int solenoid_hook1, int solenoid_hook2, int solenoid_winch1, int solenoid_winch2, int aSwitch) {
        super();
        this.motor = new PWMSparkMax(motor);
        this.solenoid = new DoubleSolenoid(RobotMap.PH_CAN_ID, PneumaticsModuleType.REVPH, solenoid_hook1, solenoid_hook1);
        this.solenoid2 = new DoubleSolenoid(RobotMap.PH_CAN_ID, PneumaticsModuleType.REVPH, solenoid_winch1, solenoid_winch2);
        this.Switch = new DigitalInput(aSwitch);
    }

    private final PWMSparkMax motor;
    private final DoubleSolenoid solenoid;
    private final DoubleSolenoid solenoid2;
    private final DigitalInput Switch;
    private static boolean Step2;
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
    public void Lock2(){
        solenoid2.set(DoubleSolenoid.Value.kForward);
    }
    public void Unlock2() {
        solenoid2.set(DoubleSolenoid.Value.kReverse);
        }
    public boolean GetSwitch(){
        return Switch.get();
    }
    public static boolean isStep2() {
        return Step2;
    }
    public static void setStep2(){
        Step2 = !Step2;
    }
}
