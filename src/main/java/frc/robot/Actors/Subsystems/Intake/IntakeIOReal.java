package frc.robot.Actors.Subsystems.Intake;

import frc.robot.Actors.Motor;
import frc.robot.Utils.MotorType;
import frc.robot.Utils.RotationDir;
import frc.robot.Constants.IntakeConstants;

public class IntakeIOReal implements IntakeIO {

    private final Motor motor;

    public IntakeIOReal() {
        this.motor = new Motor(IntakeConstants.intakeMotorID, MotorType.TFX, "rio");
        this.motor.motorConfig.direction = RotationDir.Clockwise;
        this.motor.motorConfig.brake = false;
        this.motor.applyConfig();
        this.motor.slot0TFX.kV = 0.012;
        this.motor.pid(0.05, 0.0, 0.0);
    }

    @Override
    public void setRunning(boolean run) {
        if (run) {
            motor.vel(60.0);
        } else {
            motor.vel(0.0);
        }
    }

    @Override
    public void setVelocity(double rps) {
        motor.vel(rps);
    }

    @Override
    public double getVelocity() {
        return motor.vel();
    }

    @Override
    public double getTemperature() {
        return motor.getTemp();
    }

    @Override
    public boolean hasFuel() {
        // On real robot, this would check a beam break sensor
        // TODO: wire up beam break sensor on port IndexerConstants.bpsBeamBreakPort
        return false;
    }

    @Override
    public boolean obtainFuel() {
        // On real robot, fuel transfer is handled by the indexer
        return false;
    }

    @Override
    public int getGamePiecesCount() {
        // On real robot, this would track via beam break sensor counts
        return 0;
    }
}
