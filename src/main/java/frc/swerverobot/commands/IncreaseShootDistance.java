package frc.swerverobot.commands;

import frc.swerverobot.subsystems.ShooterSubsystem;

public class IncreaseShootDistance {
    public IncreaseShootDistance(ShooterSubsystem shooter) {
        shooter.manualShooterDistanceIncrease();
    }
}
