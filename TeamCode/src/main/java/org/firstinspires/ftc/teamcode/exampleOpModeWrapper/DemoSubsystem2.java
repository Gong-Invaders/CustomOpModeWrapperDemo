package org.firstinspires.ftc.teamcode.exampleOpModeWrapper;

import com.qualcomm.robotcore.hardware.DcMotor;

public class DemoSubsystem2 {
    private final RobotHardware r;

    private final DcMotor exampleMotor;

    /**
     * An accessible value for the encoder position.
     */
    public double examplePosition = 0;

    /**
     * The target encoder position for the control loop to seek
     */
    private double setPoint;

    /**
     * A utility class that is not attached to the RobotHardware class and structure
     */
    private final Log log;

    public DemoSubsystem2(RobotHardware r){
        this.r = r;
        exampleMotor = r.opMode.hardwareMap.get(DcMotor.class, "Spool2");
        log = new Log(RobotHardware.Subsystems.DEMO_SUBSYSTEM1.toString(), "encoder position", "set point");
    }

    /**
     * A simple method of setting power, in this case to only one motor but this subsystem class could easily do processing to handle multiple motors and more complex control.
     * @param power the target power, this does not need to be sanitised as the {@link DcMotor}.setPower() method can handle any input here.
     */
    public void setPower(double power){
        exampleMotor.setPower(power);
        r.opMode.telemetry.addData("motor power", power);
    }

    /**
     * This is a method that needs to be called once at the start of every updating loop which is well suited to being handled by our custom OpMode wrapper.
     */
    public void encoderRead(){
        examplePosition = exampleMotor.getCurrentPosition();
        log.logData(0, examplePosition);
    }

    public void encoderReset(){
        exampleMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        exampleMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * This is a method that needs to be called once at the end of every updating loop, which is well suited to being handled by our custom OpMode wrapper.
     */
    public void controlLoopUpdate(){
        double error = setPoint - examplePosition;
        double kP = 0.01;
        exampleMotor.setPower(error * kP);
        log.logData(1, setPoint);
    }

    /**
     * A method of updating the setPoint and applying any handling we want to it
     * @param setPoint the target position for the control loop to move to, sanitises to the desired ranges
     */
    public void setPoint(double setPoint){
        if (setPoint > 1000){
            setPoint = 1000;
        }
        if (setPoint < -10){
            setPoint = -10;
        }
        this.setPoint = setPoint;
    }

    /**
     * This is a method that needs to be called during the stop method of the OpMode, which is also good for being handled by our custom OpMode wrapper;
     */
    public void closeLogs(){
        r.opMode.telemetry.addData("closing logs for", this.getClass().toString()); //this is rather pointless as the message will never be seen, and serves rather to demonstrate the usage of telemetry through the OpMode Object.
        log.close();
    }
}
