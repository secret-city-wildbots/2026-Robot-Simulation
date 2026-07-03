package frc.robot.Actors.Subsystems.Shooter;

public interface ShooterIO {
    /** Set flywheel target velocity in RPS. */
    void setFlywheelRPS(double rps);

    /** Get current flywheel velocity in RPS. */
    double getFlywheelRPS();

    /** Stop flywheels. */
    void stopFlywheel();

    /** Set hood angle in degrees. */
    void setHoodAngle(double degrees);

    /** Get current hood angle in degrees. */
    double getHoodAngle();

    /** Set hood brake mode. */
    void setHoodBrake(boolean brake);

    /**
     * Launch a fuel projectile (sim only).
     * Real implementation is a no-op since firing is mechanical.
     */
    void launchFuel();
}
