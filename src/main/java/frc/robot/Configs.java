package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

import edu.wpi.first.wpilibj.motorcontrol.Talon;

import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Constants.DriveConstants;

public final class Configs {
    
    public static final class Swerve {

        public static final class Driving {

            // Driving Configs
            public static final SparkMaxConfig NEO_DRIVING_CONFIG = new SparkMaxConfig();
            public static final TalonFXConfiguration KRAKEN_X60_CONFIGURATION = new TalonFXConfiguration();
            public static final TalonFXConfiguration KRAKEN_X60_FOC_CONFIGURATION = new TalonFXConfiguration();
            public static final TalonFXConfiguration FALCON_CONFIGURATION = new TalonFXConfiguration();

            static {

                // --- NEO DRIVING CONFIG ---
    
                // Configure encoder settings
                NEO_DRIVING_CONFIG.encoder
                    .positionConversionFactor(DriveConstants.NeoDriving.ENCODER_POSITION_FACTOR)
                    .velocityConversionFactor(DriveConstants.NeoDriving.ENCODER_VELOCITY_FACTOR);
    
                // Configure PIDF control loop
                NEO_DRIVING_CONFIG.closedLoop
                    .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                    .pidf(DriveConstants.NeoDriving.KP, DriveConstants.NeoDriving.KI, 
                        DriveConstants.NeoDriving.KD, DriveConstants.NeoDriving.FF)
                    .outputRange(DriveConstants.NeoDriving.PID_MIN_OUTPUT, 
                                DriveConstants.NeoDriving.PID_MAX_OUTPUT);
    
                // Configure motor settings
                NEO_DRIVING_CONFIG
                    .idleMode(DriveConstants.NeoDriving.IDLE_MODE)
                    .smartCurrentLimit(DriveConstants.NeoDriving.CURRENT_LIMIT);

                // --- KRAKEN X60 DRIVING CONFIG ---

                KRAKEN_X60_CONFIGURATION
                    .Feedback.SensorToMechanismRatio = DriveConstants.KrakenX60Driving.SENSOR_TO_MECHANISM_RATIO;

                KRAKEN_X60_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLimitEnable = DriveConstants.KrakenX60Driving.CURRENT_LIMIT_ENABLED;
                KRAKEN_X60_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLimit = DriveConstants.KrakenX60Driving.CURRENT_LIMIT;
                KRAKEN_X60_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLowerLimit = DriveConstants.KrakenX60Driving.CURRENT_LOWER_LIMIT;
                KRAKEN_X60_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLowerTime = DriveConstants.KrakenX60Driving.CURRENT_LOWER_TIME;

                KRAKEN_X60_CONFIGURATION
                    .Slot0
                        .kP = DriveConstants.KrakenX60Driving.KP;
                KRAKEN_X60_CONFIGURATION
                    .Slot0
                        .kI = DriveConstants.KrakenX60Driving.KI;
                KRAKEN_X60_CONFIGURATION
                    .Slot0
                        .kD = DriveConstants.KrakenX60Driving.KD;
                KRAKEN_X60_CONFIGURATION
                    .Slot0
                        .kV = DriveConstants.KrakenX60Driving.KV;
                KRAKEN_X60_CONFIGURATION
                    .Slot0
                        .kA = DriveConstants.KrakenX60Driving.KA;

                KRAKEN_X60_CONFIGURATION
                    .MotorOutput
                        .NeutralMode = DriveConstants.KrakenX60Driving.NEUTRAL_MODE;

                // --- KRAKEN X60 FOC DRIVING CONFIG ---

                KRAKEN_X60_FOC_CONFIGURATION
                    .Feedback.SensorToMechanismRatio = DriveConstants.KrakenX60FOCDriving.SENSOR_TO_MECHANISM_RATIO;

                KRAKEN_X60_FOC_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLimitEnable = DriveConstants.KrakenX60FOCDriving.CURRENT_LIMIT_ENABLED;
                KRAKEN_X60_FOC_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLimit = DriveConstants.KrakenX60FOCDriving.CURRENT_LIMIT;
                KRAKEN_X60_FOC_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLowerLimit = DriveConstants.KrakenX60FOCDriving.CURRENT_LOWER_LIMIT;
                KRAKEN_X60_FOC_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLowerTime = DriveConstants.KrakenX60FOCDriving.CURRENT_LOWER_TIME;

                KRAKEN_X60_FOC_CONFIGURATION
                    .Slot0
                        .kP = DriveConstants.KrakenX60FOCDriving.KP;
                KRAKEN_X60_FOC_CONFIGURATION
                    .Slot0
                        .kI = DriveConstants.KrakenX60FOCDriving.KI;
                KRAKEN_X60_FOC_CONFIGURATION
                    .Slot0
                        .kD = DriveConstants.KrakenX60FOCDriving.KD;
                KRAKEN_X60_FOC_CONFIGURATION
                        .Slot0
                            .kV = DriveConstants.KrakenX60FOCDriving.KV;
                KRAKEN_X60_FOC_CONFIGURATION
                        .Slot0
                            .kA = DriveConstants.KrakenX60FOCDriving.KA;

                KRAKEN_X60_FOC_CONFIGURATION
                    .MotorOutput
                        .NeutralMode = DriveConstants.KrakenX60FOCDriving.NEUTRAL_MODE;

                // --- FALCON DRIVING CONFIG ---


                FALCON_CONFIGURATION
                    .Feedback.SensorToMechanismRatio = DriveConstants.FalconDriving.SENSOR_TO_MECHANISM_RATIO;
                FALCON_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLimitEnable = DriveConstants.FalconDriving.CURRENT_LIMIT_ENABLED;
                FALCON_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLimit = DriveConstants.FalconDriving.CURRENT_LIMIT;
                FALCON_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLowerLimit = DriveConstants.FalconDriving.CURRENT_LOWER_LIMIT;
                FALCON_CONFIGURATION
                    .CurrentLimits
                        .SupplyCurrentLowerTime = DriveConstants.FalconDriving.CURRENT_LOWER_TIME;

                FALCON_CONFIGURATION
                    .Slot0
                        .kP = DriveConstants.FalconDriving.KP;
                FALCON_CONFIGURATION
                    .Slot0
                        .kI = DriveConstants.FalconDriving.KI;
                FALCON_CONFIGURATION
                    .Slot0
                        .kD = DriveConstants.FalconDriving.KD;
                FALCON_CONFIGURATION
                    .Slot0
                        .kV = DriveConstants.FalconDriving.KV;
                FALCON_CONFIGURATION
                    .Slot0
                        .kA = DriveConstants.FalconDriving.KA;

            
            }

        }

        public static final class Turning {

            // Turning Configs
            public static final SparkMaxConfig NEO_550_TURNING_CONFIG = new SparkMaxConfig();
            public static final TalonFXConfiguration FALCON_TURNING_CONFIG = new TalonFXConfiguration();

            static {
                
                // --- NEO 550 TURNING CONFIG ---
                    
                // Configure absolute encoder settings
                NEO_550_TURNING_CONFIG.absoluteEncoder
                    .positionConversionFactor(DriveConstants.Neo550Turning.ENCODER_POSITION_FACTOR)
                    .velocityConversionFactor(DriveConstants.Neo550Turning.ENCODER_VELOCITY_FACTOR)
                    .inverted(DriveConstants.Neo550Turning.ENCODER_INVERTED);

                // Configure PID control loop
                NEO_550_TURNING_CONFIG.closedLoop
                    .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                    .pid(DriveConstants.Neo550Turning.KP, DriveConstants.Neo550Turning.KI, DriveConstants.Neo550Turning.KD)
                    .outputRange(DriveConstants.Neo550Turning.PID_MIN_OUTPUT, DriveConstants.Neo550Turning.PID_MAX_OUTPUT)
                    .positionWrappingEnabled(true)
                    .positionWrappingInputRange(DriveConstants.Neo550Turning.POSITION_PID_MIN_INPUT, 
                                                DriveConstants.Neo550Turning.POSITION_PID_MAX_INPUT);

                // Configure motor settings
                NEO_550_TURNING_CONFIG
                    .idleMode(DriveConstants.Neo550Turning.IDLE_MODE)
                    .smartCurrentLimit(DriveConstants.Neo550Turning.CURRENT_LIMIT);
                
                FALCON_TURNING_CONFIG.ClosedLoopGeneral.ContinuousWrap = DriveConstants.FalconTurning.CONTINUOUS_WRAP;
                FALCON_TURNING_CONFIG.Feedback.SensorToMechanismRatio = DriveConstants.FalconTurning.SENSOR_TO_MECHANISM_RATIO;

                FALCON_TURNING_CONFIG
                    .CurrentLimits
                        .SupplyCurrentLimitEnable = DriveConstants.FalconTurning.CURRENT_LIMIT_ENABLED;
                FALCON_TURNING_CONFIG
                    .CurrentLimits
                        .SupplyCurrentLimit = DriveConstants.FalconTurning.CURRENT_LIMIT;
                FALCON_TURNING_CONFIG
                    .CurrentLimits
                        .SupplyCurrentLowerLimit = DriveConstants.FalconTurning.CURRENT_LOWER_LIMIT;
                FALCON_TURNING_CONFIG
                    .CurrentLimits
                        .SupplyCurrentLowerTime = DriveConstants.FalconTurning.CURRENT_LOWER_TIME;

                FALCON_TURNING_CONFIG
                    .Slot0
                        .kP = DriveConstants.FalconTurning.KP;
                FALCON_TURNING_CONFIG
                    .Slot0
                        .kI = DriveConstants.FalconTurning.KI;
                FALCON_TURNING_CONFIG
                    .Slot0
                        .kD = DriveConstants.FalconTurning.KD;
                FALCON_TURNING_CONFIG
                    .Slot0
                        .kV = DriveConstants.FalconTurning.KV;
                FALCON_TURNING_CONFIG
                    .Slot0
                        .kA = DriveConstants.FalconTurning.KA;

            }

        }


    }

    public static final class Squid {

    }

    public static final class Narwhal {

    }

}
