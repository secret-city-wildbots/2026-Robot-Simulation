package frc.robot.Actors.Subsystems.Shooter;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import edu.wpi.first.math.MathUtil;
import frc.robot.Actors.Motor;
import frc.robot.Utils.MotorType;
import frc.robot.Utils.RotationDir;
import frc.robot.Constants.ShooterConstants;

public class ShooterIOReal implements ShooterIO {

    private final Motor leadMotor;
    private final Motor followMotor;
    private final Motor hoodMotor;

    public ShooterIOReal() {
        this.leadMotor = new Motor(ShooterConstants.leadMotorID, MotorType.TFX, "rio", true);
        this.followMotor = new Motor(ShooterConstants.followMotorID, MotorType.TFX, "rio", true);
        this.hoodMotor = new Motor(ShooterConstants.hoodMotorID, MotorType.TFX);

        // Configure lead motor
        this.leadMotor.motorConfig.direction = RotationDir.CounterClockwise;
        this.leadMotor.motorConfig.peakReverseDC = -1.0;
        this.leadMotor.motorConfig.brake = false;
        this.leadMotor.applyConfig();
        this.leadMotor.slot0TFX.kV = 0.011;
        this.leadMotor.pid(0.04, 0.0, 0.0);
        this.leadMotor.motorTFX.getVelocity().setUpdateFrequency(1000.0);

        // Follow motor opposes lead
        this.followMotor.motorTFX.setControl(
                new Follower(ShooterConstants.leadMotorID, MotorAlignmentValue.Opposed));

        // Hood motor
        this.hoodMotor.motorTFX.setPosition(0.02604); // 5 degrees
        this.hoodMotor.motorConfig.direction = RotationDir.Clockwise;
        this.hoodMotor.applyConfig();
        this.hoodMotor.slot0TFX.kG = 0.025;
        this.hoodMotor.slot0TFX.GravityType = GravityTypeValue.Elevator_Static;
        this.hoodMotor.pid(0.35, 0.0, 0.012);
    }

    @Override
    public void setFlywheelRPS(double rps) {
        leadMotor.vel(rps);
    }

    @Override
    public double getFlywheelRPS() {
        return leadMotor.vel();
    }

    @Override
    public void stopFlywheel() {
        leadMotor.vel(0.0);
    }

    @Override
    public void setHoodAngle(double degrees) {
        degrees = MathUtil.clamp(degrees, ShooterConstants.minDegree, ShooterConstants.maxDegree);
        double motorRotations = (degrees - ShooterConstants.minDegree) * ShooterConstants.hoodGearRatio / 360.0;
        hoodMotor.pos(motorRotations);
    }

    @Override
    public double getHoodAngle() {
        return hoodMotor.pos() * 360.0 / ShooterConstants.hoodGearRatio + ShooterConstants.minDegree;
    }

    @Override
    public void setHoodBrake(boolean brake) {
        hoodMotor.motorConfig.brake = brake;
        hoodMotor.applyConfig();
    }

    @Override
    public void launchFuel() {
        // On real robot, launching is mechanical — flywheel + indexer push the ball out
    }
}
