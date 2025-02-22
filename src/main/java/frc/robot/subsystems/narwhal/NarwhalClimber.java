// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.narwhal;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.NarwhalConstants.NarwhalClimberConstants;

/**
 * <h2> NarwhalWrist </h2>
 * The {@code NarwhalWrist} class is a class that represents the Narwhal's wrist mechanism.
 * It allows for game elements to be rotated around the wrist's base, fueling complex movement and design.
 * <hr>
 * @author Patrick Wang
 * @since v1.2.0
 */
public class NarwhalClimber extends SubsystemBase {
    public NarwhalClimberState currentState;
    
    private final SparkMax climber;
    private final SparkMaxConfig climberConfig;
    private final SparkClosedLoopController climberPID;
    
    public NarwhalClimber() {
        // a whole lotta stuff for the spark max config
        // Motor configs
        climberConfig = new SparkMaxConfig();
        // General Configs
        climberConfig
            .smartCurrentLimit(40)
            .idleMode(IdleMode.kBrake)
            .inverted(false);
        climberConfig.encoder
            .positionConversionFactor(1.0/NarwhalClimberConstants.CLIMBER_ANGLE_TO_MOTOR_ANGLE);

        climberConfig.closedLoop
            .pidf(
                NarwhalClimberConstants.PID_P,
                NarwhalClimberConstants.PID_I,
                NarwhalClimberConstants.PID_D,
                NarwhalClimberConstants.PID_F
            )
            .outputRange(-1.0, 1.0);
        
        climber = new SparkMax(NarwhalClimberConstants.CLIMBER_CAN_ID, MotorType.kBrushless);
        climber.configure(climberConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        climberPID = climber.getClosedLoopController();
        currentState = NarwhalClimberState.STARTING;
    }

    /**
     * Function to move the climber piece to a target angle (relative to the world) & sets the current state to CUSTOM.
     * @param targetAngle Rotation2d target position
     */
    public void setCurrentMotorAngle(Rotation2d targetAngle){
        double targetAngleRotations = targetAngle.getRotations();
        climberPID.setReference(targetAngleRotations, ControlType.kPosition);
        currentState = NarwhalClimberState.CUSTOM;
    }

    /**
     * Set the wrist motor to the intake angle (defined in constants) & update status.
     */
    public void hold() {
        // Inline construction of command goes here.
        // Subsystem::RunOnce implicitly requires `this` subsystem.double targetAngleRotations = targetAngle.getRotations();
        climberPID.setReference(climber.getEncoder().getPosition(), ControlType.kPosition);
        currentState = NarwhalClimberState.HOLDING;
    }

    public void goToDeploy() {
        // Inline construction of command goes here.
        // Subsystem::RunOnce implicitly requires `this` subsystem.
        setCurrentMotorAngle(NarwhalClimberConstants.DEPLOYED_ANGLE);
        currentState = NarwhalClimberState.DEPLOYING; // must be after the set function because the set function will default to CUSTOM state
    }

    /**
     * Set the wrist motor to the outtake angle (defined in constants) & update status.
     */
    public void climb() {
        setCurrentMotorAngle(NarwhalClimberConstants.CLIMB_ANGLE);
        currentState = NarwhalClimberState.CLIMBING; // must be after the set function because the set function will default to CUSTOM state
    }

    public void runXBoxController(XboxController xBoxController){
        if(xBoxController.getPOV() == 0){
            goToDeploy();
        }
        if(xBoxController.getPOV() == 90){
            climb();
        }
    }
    
    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
