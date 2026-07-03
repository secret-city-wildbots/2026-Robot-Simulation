package frc.robot.Actors.Subsystems.Intake;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final IntakeIO io;

    public Intake(IntakeIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        DogLog.log("Intake/GamePiecesCount", io.getGamePiecesCount());
    }

    public double getTemp() {
        return io.getTemperature();
    }

    public double getVel() {
        return io.getVelocity();
    }

    public void intake() {
        io.setRunning(true);
    }

    public void set(double percent) {
        io.setVelocity(percent);
    }

    public void setVelocity(double rps) {
        io.setVelocity(rps);
    }

    public void stop() {
        io.setRunning(false);
    }

    public boolean hasFuel() {
        return io.hasFuel();
    }

    public boolean obtainFuel() {
        return io.obtainFuel();
    }
}