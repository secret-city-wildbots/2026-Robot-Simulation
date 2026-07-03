package frc.robot.Actors.Subsystems.Intake;

public interface IntakeIO {
    /** Spin the intake rollers (true = collecting, false = stopped). */
    void setRunning(boolean run);

    /** Set intake roller velocity directly in RPS. */
    void setVelocity(double rps);

    /** Current roller velocity in RPS. */
    double getVelocity();

    /** Motor temperature in degrees C. */
    double getTemperature();

    /** True if a game piece is held in the intake. */
    boolean hasFuel();

    /** Remove a game piece from the intake. Returns true if one was removed. */
    boolean obtainFuel();

    /** Number of game pieces currently held. */
    int getGamePiecesCount();
}
