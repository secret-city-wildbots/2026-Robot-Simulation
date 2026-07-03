package frc.robot.Actors.Subsystems.Intake;

import static edu.wpi.first.units.Units.Meters;

import org.ironmaple.simulation.IntakeSimulation;
import org.ironmaple.simulation.drivesims.AbstractDriveTrainSimulation;
import static frc.robot.Constants.SimulationConstants.*;

public class IntakeIOSim implements IntakeIO {

    private final IntakeSimulation intakeSimulation;

    public IntakeIOSim(AbstractDriveTrainSimulation driveTrain) {
        this.intakeSimulation = IntakeSimulation.OverTheBumperIntake(
                "Fuel",
                driveTrain,
                Meters.of(intakeWidth),   // intake width (~24")
                Meters.of(intakeExtBeyondBumper),   // extension beyond bumpers
                IntakeSimulation.IntakeSide.FRONT,
                hopperSize);               // Hopper Size
    }

    @Override
    public void setRunning(boolean run) {
        if (run) {
            intakeSimulation.startIntake();
        } else {
            intakeSimulation.stopIntake();
        }
    }

    @Override
    public void setVelocity(double rps) {
        // In sim, treat any nonzero velocity as running the intake
        setRunning(Math.abs(rps) > 0.1);
    }

    @Override
    public double getVelocity() {
        // No real motor to read in sim; return nonzero when intake is active
        return intakeSimulation.getGamePiecesAmount() >= 0 ? 60.0 : 0.0;
    }

    @Override
    public double getTemperature() {
        return 0.0;
    }

    @Override
    public boolean hasFuel() {
        return intakeSimulation.getGamePiecesAmount() > 0;
    }

    @Override
    public boolean obtainFuel() {
        return intakeSimulation.obtainGamePieceFromIntake();
    }

    @Override
    public int getGamePiecesCount() {
        return intakeSimulation.getGamePiecesAmount();
    }
}
