package frc.swerverobot.commands;

import frc.swerverobot.subsystems.ShooterSubsystem;

public class DecreaseShootDistance {
    public DecreaseShootDistance(ShooterSubsystem shooter) {
        shooter.manualShooterDistanceDecrease();
    }
}
