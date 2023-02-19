package org.firstinspires.ftc.teamcode.exampleOpModeWrapper;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.exampleOpModeWrapper.gamepadEX.GamepadEX;

import java.security.InvalidParameterException;
import java.util.List;

public class RobotHardware {

    /**
     * An enum us used to store a list of the subsystems, it works particularly well with the switch statements we check against for, and android studio will helpfully generate missing branches from said switch statements when new subsystems are added.
     */
    public enum Subsystems {
        DEMO_SUBSYSTEM1,
        DEMO_SUBSYSTEM2,
        GAMEPAD_EX1,
        GAMEPAD_EX2; //enhanced performance gamepad objects that fufill the role of a utility object that uses the power of the wrapper
    }

    public Subsystems[] enabledSubsystems;

    /**
     * It is important that the RobotHardware class is a singleton, to prevent issues that may arise from having multiple copies of the object.
     */
    private static RobotHardware robotHardwareInstance;


    /**
     * By passing the whole OpMode object we are able to pass through both the Telemetry and HardwareMap objects to our subsystems.
     */
    public OpMode opMode;

    /**
     * DemoSubsystem1 and 2 are identical, but show the beginnings of how subsystems have the potential for much more depth and complexity
     */
    private DemoSubsystem1 demoSubsystem1;
    private DemoSubsystem2 demoSubsystem2;
    private GamepadEX gamepadEX1;
    private GamepadEX gamepadEX2;

    private final List<LynxModule> allHubs;

    private boolean built;

    /**
     * The private constructor allows us to prevent several RobotHardware objects from being made.
     * @param opMode the OpMode object to use and store.
     */
    private RobotHardware(OpMode opMode){
        this.opMode = opMode;

        //enabling manual bulk encoder reads
        this.allHubs = opMode.hardwareMap.getAll(LynxModule.class);
        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    /**
     * A method to allow the user to access the subsystems of this class, it requires a little more code, and in exchange allows for a much easier time of finding issues related to subsystems not being initialised.
     * @param subsystem the desired subsystem object's enum equivalent.
     * @return the desired subsystem, else throws an exception to assist in debugging.
     */
    public <T> T getSubsystem(Subsystems subsystem){
        Class<? extends T> desiredClass;
        T destiredSubsystem = null;

        switch (subsystem){
            case DEMO_SUBSYSTEM1:
                desiredClass = (Class<? extends T>) DemoSubsystem1.class;
                destiredSubsystem = desiredClass.cast(demoSubsystem1);
                break;
            case DEMO_SUBSYSTEM2:
                desiredClass = (Class<? extends T>) DemoSubsystem2.class;
                destiredSubsystem = desiredClass.cast(demoSubsystem2);
                break;
            case GAMEPAD_EX1:
                desiredClass = (Class<? extends T>) GamepadEX.class;
                destiredSubsystem = desiredClass.cast(gamepadEX1);
                break;
            case GAMEPAD_EX2:
                desiredClass = (Class<? extends T>) GamepadEX.class;
                destiredSubsystem = desiredClass.cast(gamepadEX2);
                break;
        }

        if(destiredSubsystem != null){
            return destiredSubsystem;
        }

        throw new IllegalArgumentException("Desired subsystem " + subsystem.toString() + " has not been initialised");
    }

    /**
     * The method of accessing the singleton RobotHardware.
     * @param opMode the OpMode object to use if there is no pre-existing RobotHardware object
     * @return the instance of RobotHardware is returned regardless.
     */
    public static RobotHardware getInstance(OpMode opMode) {
        if (robotHardwareInstance == null) {
            robotHardwareInstance = new RobotHardware(opMode);
        }
        return robotHardwareInstance;
    }

    /**
     * This method must be called in the init phase, and should only be called once, it will initialise all the subsystem objects
     * @param desiredSubsystems a list of desired subsystems, only those that are not included by default (in this example {@link GamepadEX} is included by default) need to be listed.
     */
    public void initSubsystems(Subsystems... desiredSubsystems){

        if(built){
            throw new InvalidParameterException("Attempt to initialise additional systems intercepted.\nSystems initialisation already built.");
        }

        int includedSystemsNumber = 2; //the number of default included systems
        enabledSubsystems = new Subsystems[desiredSubsystems.length + includedSystemsNumber]; //We now know the length of this array, as we have a total number of arguments

        enabledSubsystems[0] = Subsystems.GAMEPAD_EX1;
        enabledSubsystems[1] = Subsystems.GAMEPAD_EX2;
        // If you want more default systems: simply copy the above line, increment the included systems number,
        // and increment the array position, then change the member of Subsystems to the target included subsystem.

        System.arraycopy(desiredSubsystems, 0, enabledSubsystems, includedSystemsNumber, desiredSubsystems.length);

        for (Subsystems subsystem : enabledSubsystems) {
            switch (subsystem){
                case DEMO_SUBSYSTEM1:
                    demoSubsystem1 = new DemoSubsystem1(this); //the this keyword allows you to pass in ~this~ RobotHardware instance!
                    break;
                case DEMO_SUBSYSTEM2:
                    demoSubsystem2 = new DemoSubsystem2(this);
                    break;
                case GAMEPAD_EX1:
                    gamepadEX1 = new GamepadEX(opMode.gamepad1);
                    break;
                case GAMEPAD_EX2:
                    gamepadEX2 = new GamepadEX(opMode.gamepad2);
                    break;
            }
        }

        built = true;
    }

    /**
     * A method that gets run at the start of the loop() method of an iterative OpMode, by our custom wrapper, it checks against the enabled subsystems and runs the appropriate method for each.
     */
    public void systemsStartLoopUpdate() {
        //this automatically included update loop enables an easy and hassle free method of using manual bulk encoder reads
        for (LynxModule module : allHubs) {
            module.clearBulkCache();
        }

        for (Subsystems subsystem : enabledSubsystems) {
            switch (subsystem){
                case DEMO_SUBSYSTEM1:
                    demoSubsystem1.encoderRead();
                    break;
                case DEMO_SUBSYSTEM2:
                    demoSubsystem2.encoderRead();
                    break;
                case GAMEPAD_EX1:
                    gamepadEX1.startLoopUpdate();
                    break;
                case GAMEPAD_EX2:
                    gamepadEX2.startLoopUpdate();
                    break;
            }
        }
    }

    /**
     * A method that gets run at the end of the loop() method of an iterative OpMode, by our custom wrapper, it checks against the enabled subsystems and runs the appropriate method for each.
     */
    public void systemsEndLoopUpdate(){
        for (Subsystems subsystem : enabledSubsystems) {
            switch (subsystem){
                case DEMO_SUBSYSTEM1:
                    demoSubsystem1.controlLoopUpdate();
                    break;
                case DEMO_SUBSYSTEM2:
                    demoSubsystem2.controlLoopUpdate();
                    break;
                case GAMEPAD_EX1:
                    gamepadEX1.endLoopUpdate();
                    break;
                case GAMEPAD_EX2:
                    gamepadEX2.endLoopUpdate();
                    break;
            }
        }
    }

    /**
     * A method that gets run at the end of the OpMode, using the stop() method of an iterative OpMode, by our custom wrapper, it checks against the enabled subsystems and runs the appropriate method for each.
     */
    public void closeLogs(){
        for (Subsystems subsystem : enabledSubsystems) {
            switch (subsystem){
                case DEMO_SUBSYSTEM1:
                    demoSubsystem1.closeLogs();
                    break;
                case DEMO_SUBSYSTEM2:
                    demoSubsystem2.closeLogs();
                    break;
                case GAMEPAD_EX1:
                case GAMEPAD_EX2:
                    break;
            }
        }
    }

    public void lockDown(){
        built = false;
        robotHardwareInstance = null;
    }
}
