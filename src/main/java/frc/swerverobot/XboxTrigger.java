package frc.swerverobot;

import edu.wpi.first.wpilibj.buttons.Button;
 

public class XboxTrigger extends Button { //} {
    @Override
    public boolean get() {
      // This returns whether the trigger is active
      return RobotMap.Driver_controller.getLeftTriggerAxis().get() > 0.5;

    }
  }