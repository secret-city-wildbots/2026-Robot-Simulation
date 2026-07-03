package frc.robot.Actors.Subsystems.Shooter;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

    private final ShooterIO io;

    public Shooter(ShooterIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        DogLog.log("Shooter/FlywheelRPS", io.getFlywheelRPS());
        DogLog.log("Shooter/HoodAngle", io.getHoodAngle());
    }

    public void setRPS(double rps) {
        io.setFlywheelRPS(rps);
    }

    public double getRPS() {
        return io.getFlywheelRPS();
    }

    public void stop() {
        io.stopFlywheel();
    }

    public void setHoodAngle(double degrees) {
        io.setHoodAngle(degrees);
    }

    public double getPos() {
        return io.getHoodAngle();
    }

    public void setBrake(boolean brake) {
        io.setHoodBrake(brake);
    }

    public void launchFuel() {
        io.launchFuel();
    }
}