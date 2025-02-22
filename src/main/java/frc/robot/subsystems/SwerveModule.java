package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.DriveConstants;
import frc.robot.util.DrivingMotor;
import frc.robot.util.TurningMotor;

/**
 * <h2> SwerverModule </h2>
 * The {@code SwerverModule} class focuses on controlling the individual hardware components within each swerve module. 
 * This class is used in {@code DriveSubsystem} to intelligently manage the robot's movement.
 * <hr>
 * @author Parker Huibregtse
 * @since v1.1.0
 * @see {@link edu.wpi.first.wpilibj2.command.SubsystemBase}
 * @see {@link frc.robot.subsystems.DriveSubsystem}
*/
public class SwerveModule extends SubsystemBase {

    // Driving Motors
    SparkMax drivingNeo;
    TalonFX drivingKrakenX60;
    TalonFX drivingKrakenX60FOC;
    TalonFX drivingFalcon;

    // Turning Motors
    SparkMax turningNeo550;
    TalonFX turningFalcon;

    // The offsets of the individual modules
    Rotation2d moduleAngularOffset;
    int drivingCANID;
    int turningCANID;

    DrivingMotor activeDrivingMotor = DriveConstants.DEFAULT_DRIVING_MOTOR;
    TurningMotor activeTurningMotor = DriveConstants.DEFAULT_TURNING_MOTOR;

    /**
     * Creates a swerve module
     * 
     * @param drivingCANID        The CANID for the driving motor
     * @param turningCANID        The CANID for the turning motor
     * @param moduleAngularOffset The offset of the module relative to the chassis
     */
    public SwerveModule(int drivingCANID, int turningCANID, Rotation2d moduleAngularOffset) {

        // Updating the module offset
        this.moduleAngularOffset = moduleAngularOffset;

        // Setting the CAN IDs
        this.drivingCANID = drivingCANID;
        this.turningCANID = turningCANID;

        activateDrivingMotor(activeDrivingMotor);
        activateTurningMotor(activeTurningMotor);

    }

     

    /**
     * gets the position of the active driving motor
     * @return the position of the active driving motor in meters
     */
    private double getDrivingPosition() {
        switch (activeDrivingMotor) {
            case NEO:
                return drivingNeo.getEncoder().getPosition();
            case KRAKEN_X60:
                return drivingKrakenX60.getPosition().getValueAsDouble();
            case KRAKEN_X60_FOC:
                return drivingKrakenX60FOC.getPosition().getValueAsDouble();
            case FALCON:
                return drivingFalcon.getPosition().getValueAsDouble();
            default:
                return 0.0;
        }
    }

    /**
     * gets the velocity of the active driving motor
     * @return the velocity of the active driving motor in meters per second
     */
    private double getDrivingVelocity() {
        switch (activeDrivingMotor) {
            case NEO:
                return drivingNeo.getEncoder().getVelocity();
            case KRAKEN_X60:
                return drivingKrakenX60.getVelocity().getValueAsDouble();
            case KRAKEN_X60_FOC:
                return drivingKrakenX60FOC.getVelocity().getValueAsDouble();
            case FALCON:
                return drivingFalcon.getVelocity().getValueAsDouble();
            default:
                return 0.0;
        }
    }

    /**
     * sets the velocity of the active motor
     * @param velocity the desired velocity, in meters per second
     */
    private void setDrivingVelocity(double velocity) {
        VelocityVoltage velocityRequest; // Used if TalonFX are being used
        switch (activeDrivingMotor) {
            case NEO:
                drivingNeo.getClosedLoopController().setReference(velocity, ControlType.kVelocity);
                break;
            case KRAKEN_X60:
                velocityRequest = new VelocityVoltage(velocity);
                if (drivingCANID == 2) {
                    System.out.println(velocity);
                   System.out.println(drivingKrakenX60.getVelocity().getValueAsDouble());
                }
                drivingKrakenX60.setControl(velocityRequest);
                break;
            case KRAKEN_X60_FOC:
                velocityRequest = new VelocityVoltage(velocity);
                velocityRequest.EnableFOC = true;
                drivingKrakenX60FOC.setControl(velocityRequest);
                break;
            case FALCON:
                velocityRequest = new VelocityVoltage(velocity);
                drivingFalcon.setControl(velocityRequest);
                break;
        }
    }

    /**
     * gets the rotation of the active turning motor, relative to the chassis
     * @return the rotation of the active turning motor, relative to the chassis
     */
    private Rotation2d getTurningAngle() {
        Rotation2d rawAngle;
        switch (activeTurningMotor) {
            case NEO_550:
                rawAngle = new Rotation2d(turningNeo550.getAbsoluteEncoder().getPosition());
                break;
            case FALCON:
                rawAngle = new Rotation2d(turningFalcon.getPosition().getValueAsDouble());
            default:
                rawAngle = new Rotation2d();
                break;
        }
        return rawAngle.minus(moduleAngularOffset);
    }

    /**
     * sets the angle of the turning motor
     * @param angle the angle of the motor, relative to the robot base
     */
    private void setTurningAngle(Rotation2d angle) {
        Rotation2d rawAngle = angle.plus(moduleAngularOffset);
        switch (activeTurningMotor) {
            case NEO_550:
                turningNeo550.getClosedLoopController().setReference(rawAngle.getRadians(), ControlType.kPosition);
                break;
            case FALCON:
                final PositionVoltage positionRequest = new PositionVoltage(rawAngle.getRadians()*(2*Math.PI)).withSlot(0);
                if(turningCANID == 3) {
                    System.out.println(rawAngle.getRadians()*(2*Math.PI));
                    System.out.println(turningFalcon.getPosition().getValueAsDouble());
                }
                turningFalcon.setControl(positionRequest);
                break;
        }
    }

    /**
     * gets the module's position (relative to the field)
     * 
     * @return the position of the module
     */
    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
                getDrivingPosition(),
                getTurningAngle()
        );
    }

    /**
     * gets the module's state (relative to the field)
     * 
     * @return the state of the module
     */
    public SwerveModuleState getState() {
        return new SwerveModuleState(
                getDrivingVelocity(),
                getTurningAngle()
        );        
    }

    /**
     * Disable active driving motors to prevent them from controlling the drive
     */
    private void disableActiveDrivingMotor() {
        
        switch (activeDrivingMotor) {

            case NEO:
                drivingNeo.disable();
                break;
            case KRAKEN_X60:
                drivingKrakenX60.disable();
                break;
            case KRAKEN_X60_FOC:
                drivingKrakenX60FOC.disable();
                break;
            case FALCON:
                drivingFalcon.disable();
                break;

        }

    }

    /**
     * activates the driving motor
     * @param drivingMotor the motor type to activate
     */
    private void activateDrivingMotor(DrivingMotor drivingMotor) {

        //Setting up the motors
        switch (drivingMotor) {

            case NEO:
                drivingNeo = new SparkMax(drivingCANID,MotorType.kBrushless);
                drivingNeo.configure(Configs.Swerve.Driving.NEO_DRIVING_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                drivingNeo.getEncoder().setPosition(0.0);
                break;
            
            case KRAKEN_X60:
                drivingKrakenX60 = new TalonFX(drivingCANID);
                drivingKrakenX60.getConfigurator().apply(Configs.Swerve.Driving.KRAKEN_X60_CONFIGURATION);
                drivingKrakenX60.setPosition(0.0);
                break;

            case KRAKEN_X60_FOC:
                drivingKrakenX60FOC = new TalonFX(drivingCANID);
                drivingKrakenX60FOC.getConfigurator().apply(Configs.Swerve.Driving.KRAKEN_X60_FOC_CONFIGURATION);
                drivingKrakenX60FOC.setPosition(0.0);
                break;

            case FALCON:
                drivingFalcon = new TalonFX(drivingCANID);
                drivingFalcon.getConfigurator().apply(Configs.Swerve.Driving.FALCON_CONFIGURATION);
                drivingFalcon.setPosition(0.0);
                break;

        }

    }

    /**
     * sets the driving motor type
     * @param drivingMotor the type of driving motor
     */
    public void setDrivingMotor(DrivingMotor drivingMotor) {
        
        //Disabling the current active motor
        disableActiveDrivingMotor();

        activateDrivingMotor(drivingMotor);

        //Setting the current active motor to the new one
        activeDrivingMotor = drivingMotor;
    }

    /**
     * Disable active turning motors to prevent them from controlling the drive
     */
    private void disableActiveTurningMotor() {
        
        switch (activeTurningMotor) {

            case NEO_550:
                turningNeo550.disable();
                break;
            case FALCON:
                turningFalcon.disable();

        }

    }

    /**
     * activates the turning motor
     * @param turningMotor the turning motor type to activate
     */
    private void activateTurningMotor(TurningMotor turningMotor) {
        
        //Setting up the motors
        switch (turningMotor) {

            case NEO_550:
                turningNeo550 = new SparkMax(turningCANID,MotorType.kBrushless);
                turningNeo550.configure(Configs.Swerve.Turning.NEO_550_TURNING_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                break;
            case FALCON:
                turningFalcon = new TalonFX(turningCANID);
                turningFalcon.getConfigurator().apply(Configs.Swerve.Turning.FALCON_TURNING_CONFIG);
                turningFalcon.setPosition(0.0);
                break;

        }

    }

    /**
     * sets the turning motor type
     * @param turningMotor the type of turning motor
     */
    public void setTurningMotor(TurningMotor turningMotor) {
        
        //Disabling the current active motor
        disableActiveTurningMotor();

        activateTurningMotor(turningMotor);

        //Setting the current active motor to the new one
        activeTurningMotor = turningMotor;
    }

    /**
     * updates the PID controller to target a new state
     * 
     * @param state the new state to target
     */
    public void setState(SwerveModuleState state) {
        SwerveModuleState correctedDesiredState = new SwerveModuleState();
        correctedDesiredState.speedMetersPerSecond = state.speedMetersPerSecond;
        correctedDesiredState.angle = state.angle.times(1.0);

        // optimze the desired state so that the robot will never rotate more than PI/2
        // radians
        correctedDesiredState.optimize(getPosition().angle);

        // Don't change the orientation of the turning wheels if the speed is low
        if (Math.abs(correctedDesiredState.speedMetersPerSecond) < 1e-3) {
            correctedDesiredState.angle = getPosition().angle;
        }

        // Setting the velocities
        setDrivingVelocity(correctedDesiredState.speedMetersPerSecond);
        setTurningAngle(correctedDesiredState.angle);

    }

}