package org.firstinspires.ftc.teamcode.exampleOpModeWrapper;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.exampleOpModeWrapper.gamepadEX.GamepadEX;

@TeleOp(name = "CustomOpModeWrapper Demonstration", group = "demonstration")
public class DemoOpMode extends CustomOpModeWrapper{

	DemoSubsystem1 demoSubsystem1;
	DemoSubsystem2 demoSubsystem2;
	GamepadEX gamepadEX1;
	GamepadEX gamepadEX2;
	/**
	 * We override this method when we extend from CustomOpModeWrapper, it get passed into the correct space in the equivalent method of OpMode
	 */
	@Override
	public void superInit() {
		//There is no need to create another reference to the RobotHardware singleton class, as one is available through CustomOpModeWrapper
		r.initSubsystems(
			RobotHardware.Subsystems.DEMO_SUBSYSTEM1,
			RobotHardware.Subsystems.DEMO_SUBSYSTEM2
		);

		demoSubsystem1 = r.getSubsystem(RobotHardware.Subsystems.DEMO_SUBSYSTEM1);
		demoSubsystem2 = r.getSubsystem(RobotHardware.Subsystems.DEMO_SUBSYSTEM2);
		gamepadEX1 = r.getSubsystem(RobotHardware.Subsystems.GAMEPAD_EX1);
		gamepadEX2 = r.getSubsystem(RobotHardware.Subsystems.GAMEPAD_EX2);
		//We also might want to begin with setting target positions for servos or anything else that might move to a starting position when we init the robot.
	}

	/**
	 * We override this method when we extend from CustomOpModeWrapper, it get passed into the correct space in the equivalent method of OpMode
	 */
	@Override
	public void superInit_Loop() {
		//this method is useful for things like vision, but we do not have anything to run here for the moment.
	}

	/**
	 * We override this method when we extend from CustomOpModeWrapper, it get passed into the correct space in the equivalent method of OpMode
	 */
	@Override
	public void superStart() {
		/*
		This is where control methods should be at the start of teleop, e.g. moving a part of the robot outside of the starting configuration bounds,
		turning off a camera stream, or initialising any control variables that weren't done in init().
		In a linear autonomous, the control code goes here too.
		*/
		demoSubsystem1.encoderReset();
		demoSubsystem2.encoderReset();
		demoSubsystem1.setPoint(0);
		demoSubsystem2.setPoint(0);
	}

	/**
	 * We override this method when we extend from CustomOpModeWrapper, it get passed into the correct space in the equivalent method of OpMode
	 */
	@Override
	public void superLoop() {
		/*
		This is where the majority of teleop code would go, or autonomous code if it was being written in an asynchronous fashion using road runner or otherwise
		Note that the update loop methods do not need to be called.
		*/
		if(gamepad1.a){
			demoSubsystem1.setPoint(850);
		}
		else{
			demoSubsystem1.setPoint(0);
		}

		if(gamepad1.b){
			demoSubsystem2.setPoint(850);
		}
		else{
			demoSubsystem2.setPoint(0);
		}

		//Please note that no code that uses the GamepadEX objects has been written as it is not a major focus of this project to demonstrate it
		//The resulting code is both clean and easy to understand, hiding away and complicated control loops and the like.
	}

	/**
	 * We override this method when we extend from CustomOpModeWrapper, it get passed into the correct space in the equivalent method of OpMode
	 */
	@Override
	public void superStop() {
		//no need to put anything here either, it is bad practice to put anything that moves the robot here, as the robot must stop at the end of the match when the stop button is pressed.
	}
}
