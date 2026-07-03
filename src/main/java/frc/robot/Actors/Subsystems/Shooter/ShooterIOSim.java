package frc.robot.Actors.Subsystems.Shooter;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import dev.doglog.DogLog;
import java.util.function.Supplier;
import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.ironmaple.simulation.seasonspecific.rebuilt2026.RebuiltFuelOnFly;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.TurretConstants;

public class ShooterIOSim implements ShooterIO {

    private final SwerveDriveSimulation driveSim;
    private final Supplier<Rotation2d> turretAngleSupplier;

    private double flywheelRPS = 0;
    private double hoodAngleDeg = ShooterConstants.minDegree;

    public ShooterIOSim(SwerveDriveSimulation driveSim, Supplier<Rotation2d> turretAngleSupplier) {
        this.driveSim = driveSim;
        this.turretAngleSupplier = turretAngleSupplier;
    }

    @Override
    public void setFlywheelRPS(double rps) {
        this.flywheelRPS = rps;
    }

    @Override
    public double getFlywheelRPS() {
        return flywheelRPS;
    }

    @Override
    public void stopFlywheel() {
        this.flywheelRPS = 0;
    }

    @Override
    public void setHoodAngle(double degrees) {
        this.hoodAngleDeg = degrees;
    }

    @Override
    public double getHoodAngle() {
        return hoodAngleDeg;
    }

    @Override
    public void setHoodBrake(boolean brake) {
        // No-op in sim
    }

    @Override
    public void launchFuel() {
        if (Math.abs(flywheelRPS) < 1.0) return;

        Pose2d robotPose = driveSim.getSimulatedDriveTrainPose();
        ChassisSpeeds fieldSpeeds = driveSim.getDriveTrainSimulatedChassisSpeedsFieldRelative();
        Rotation2d turretAngle = turretAngleSupplier.get();

        // Shooter faces robot rotation + turret rotation
        Rotation2d shooterFacing = robotPose.getRotation().plus(turretAngle);

        // Convert flywheel RPS to launch speed (m/s)
        // wheelRadius * 2π * RPS = surface speed
        double launchSpeedMPS = ShooterConstants.wheelRadius_m * 2.0 * Math.PI * Math.abs(flywheelRPS);

        RebuiltFuelOnFly fuel = new RebuiltFuelOnFly(
                robotPose.getTranslation(),
                TurretConstants.turretPos,
                fieldSpeeds,
                shooterFacing,
                Meters.of(0.45),
                MetersPerSecond.of(launchSpeedMPS),
                Degrees.of(hoodAngleDeg));

        fuel.withProjectileTrajectoryDisplayCallBack(
                (poses) -> DogLog.log("Shooter/SuccessfulShot", poses.toArray(Pose3d[]::new)),
                (poses) -> DogLog.log("Shooter/MissedShot", poses.toArray(Pose3d[]::new)));

        SimulatedArena.getInstance().addGamePieceProjectile(fuel);
    }
}
