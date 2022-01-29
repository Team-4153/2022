package frc.swerverobot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.frcteam2910.common.robot.UpdateManager;

//Motors - Motor/Controller Type Not Decided Yet
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

//Solenoids
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/*      ----Electronics Needed----
- Motors (2)
- Solenoids (2)
- Pnuematics Control Module (1)
*/

/*      ----Driver Interaction----
- Button to extend/retract(toggle)

*/
/*      ----Processes----
- IntakeMove
    - extend/rectract intake
- IntakeMotor
    - activates intake motors
- IntakeFull
    -Turns off intake motors when two balls are in the intake
*/
public class IntakeSubsystem extends SubsystemBase implements UpdateManager.Updatable {
    public boolean intakeout = false;
    public void IntakeMove() {
        //pushes out intake when button pressed
       if(IntakeButton=Pressed&& !intakeout){//fix when buttons known
           //Solenoid out
           intakeout = true;
       }
       //pulls in intake when button pressed 
       if(IntakeButton=Pressed&& intakeout){//fix when buttons known
            //Solenoid in
            intakeout = false;
       }
    }
    
    public void IntakeMotor(){
        if(intakeout=true){
            //set motor speed 0.8(80%)
        }
        if(intakeout=false){
            //set motor speed 0(0%)
        }
    }

    public void IntakeFull(){
        if(ballCount()=2){
            //set motor speed 0(0%)
            //Solenoid in
            intakeout = false;
        }
    }

    public void update(double timestamp, double dt) {

    }

}
