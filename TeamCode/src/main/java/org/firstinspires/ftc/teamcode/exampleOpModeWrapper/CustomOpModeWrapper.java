package org.firstinspires.ftc.teamcode.exampleOpModeWrapper;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class CustomOpModeWrapper extends OpMode {
    /**
     * When we extend from CustomOpModeWrapper, we gain access to this method
     */
    public RobotHardware r;

    /**
     * We override this method when we extend from CustomOpModeWrapper, it get passed into the correct space in the equivalent method of OpMode
     */
    public abstract void superInit();
    /**
     * The OpMode method that we hide and wrap additional content around
     */
    @Override
    public void init() {
        r = RobotHardware.getInstance(this);
        superInit();
    }

    /**
     * We override this method when we extend from CustomOpModeWrapper, it get passed into the correct space in the equivalent method of OpMode
     */
    public abstract void superInit_Loop();

    /**
     * The OpMode method that we hide and wrap additional content around
     */
    @Override
    public void init_loop() {

        superInit_Loop();

    }

    /**
     * We override this method when we extend from CustomOpModeWrapper, it get passed into the correct space in the equivalent method of OpMode
     */
    public abstract void superStart();
    /**
     * The OpMode method that we hide and wrap additional content around
     */
    @Override
    public void start() {
        r.opMode.telemetry.clear();
        superStart();
    }

    /**
     * We override this method when we extend from CustomOpModeWrapper, it get passed into the correct space in the equivalent method of OpMode
     */
    public abstract void superLoop();
    /**
     * The OpMode method that we hide and wrap additional content around
     */
    @Override
    public void loop() {
        r.systemsStartLoopUpdate();
        superLoop();
        r.systemsEndLoopUpdate();
    }

    /**
     * We override this method when we extend from CustomOpModeWrapper, it get passed into the correct space in the equivalent method of OpMode
     */
    public abstract void superStop();
    /**
     * The OpMode method that we hide and wrap additional content around
     */
    @Override
    public void stop(){
        superStop();
        r.closeLogs();
        r.lockDown();
    }
}
