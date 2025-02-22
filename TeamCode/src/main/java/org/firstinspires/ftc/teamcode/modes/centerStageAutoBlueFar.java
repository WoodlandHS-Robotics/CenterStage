package org.firstinspires.ftc.teamcode.modes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.centerStageRobot;
import org.firstinspires.ftc.teamcode.robot.constants;

@Autonomous(name="EncoderAutonomousBlueFar", group="CenterStage")
public class centerStageAutoBlueFar extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        centerStageRobot robot = new centerStageRobot(this);
        waitForStart();

        if (opModeIsActive()) {
            //gets to team prop location area
            robot.driveInches(24, .5);
            sleep(1000);
            //fill in area with code for detecting prop and putting pixel
            robot.driveInches(constants.AUTO_PUSH_PIX_FORWARD_DIST_INCHES, .3);

            // sleep to avoid hitting our teammate
            sleep(constants.FAR_WAIT_TEAMMATE_MILLISECONDS);

            //go to back board
            robot.driveInches(-25, .5);
            robot.turnDegrees(-90, .3);
            robot.driveInches(85, .5);
        }
    }
}
