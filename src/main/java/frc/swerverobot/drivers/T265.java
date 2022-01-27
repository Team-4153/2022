package frc.swerverobot.drivers;

//import com.kauailabs.navx.frc.AHRS;
//import edu.wpi.first.wpilibj.SPI;
import org.frcteam2910.common.drivers.Gyroscope;
import org.frcteam2910.common.math.Rotation2;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;

public final class T265 extends Gyroscope {
//    private final AHRS navX;
    private double pitchZero;
    private NetworkTableEntry t265Pitch;

    public T265() {
//        this(port, (byte) 200);
        ShuffleboardTab t265Tab = Shuffleboard.getTab("T265");
        t265Pitch = t265Tab.add("Pitch", 0.0).getEntry();
    }

/*    public NavX(SPI.Port port, byte updateRate) {
        navX = new AHRS(port, updateRate);
    }
*/
    @Override
    public void calibrate() {
        pitchZero = t265Pitch.getDouble(1.0);
    }

    @Override
    public Rotation2 getUnadjustedAngle() {
        return Rotation2.fromRadians(t265Pitch.getDouble(1.0));
    }

    @Override
    public double getUnadjustedRate() {
        return Double.NaN;
        // return Math.toRadians(navX.getRate());
    }

    public double getAxis(Axis axis) {
        switch (axis) {
            case PITCH:
                return Math.toRadians(t265Pitch.getDouble(1.0));
            case ROLL:
                return Math.toRadians(t265Pitch.getDouble(1.0));
            case YAW:
                return Math.toRadians(0);
            default:
                return 0.0;
        }
    }

    public enum Axis {
        PITCH,
        ROLL,
        YAW
    }
}
