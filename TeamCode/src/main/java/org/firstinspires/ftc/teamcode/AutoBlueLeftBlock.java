package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//@Disabled
@Autonomous(name = "AutoBlueLeftBlock", group = "A")
public class AutoBlueLeftBlock extends LinearOpMode {

  AutonomousAPI autonomousAPI;
  DriverControlAPI drive;

  // @Override
  public void runOpMode() {
    autonomousAPI = new AutonomousAPI();
    drive = new DriverControlAPI();

    drive.init(hardwareMap);
    drive.compensateForVoltage();

    waitForStart();
    autonomousAPI.init(hardwareMap);

    // go forward
    drive.moveY = -1;
    drive.apply();
    sleep(150);

    // stop moving forward and move right
    drive.moveY = 0;
    drive.moveX = 1;
    drive.apply();
    sleep(380);

    // stop moving right and move arm to position
    if (autonomousAPI.camResult == CameraPipeline.LOCATION.RIGHT) {
      drive.setState(DriverControlAPI.STATE.HIGH);
    } else if (autonomousAPI.camResult == CameraPipeline.LOCATION.MIDDLE) {
      drive.setState(DriverControlAPI.STATE.MIDDLE);
    } else {
      drive.setState(DriverControlAPI.STATE.LOW);
    }
    sleep(500);

    // go forward
    drive.moveX = 0;
    drive.moveY = -0.5f;
    drive.apply();
    sleep(520);

    // stop moving
    drive.moveY = 0;
    drive.apply();
    sleep(200);

    // drop block
    drive.clawTargetState = 0;
    drive.apply();
    sleep(200);

    // go back
    drive.moveY = 1;
    drive.apply();
    sleep(140);

    // stop moving, turn left, set to low state, and mvoe the wrist up
    drive.moveY = 0;
    drive.rot = -0.5f;
    drive.setState(DriverControlAPI.STATE.LOW);
    drive.wristTargetAngle = 1;
    sleep(800);

    // stop turning
    drive.rot = 0;
    drive.apply();
    sleep(200);

    // move forward
    drive.moveY = -1;
    drive.apply();
    sleep(1500);

    // stop moving
    drive.moveY = 0;
    drive.apply();
    sleep(200);

    //move to the right
    drive.moveX = 1;
    drive.apply();
    sleep(430);

    //stop moving right
    drive.moveX = 0;
    drive.apply();
    sleep(200);

    //forward a bit
    drive.moveY = -1;
    drive.apply();
    sleep(400);

    // stop moving
    drive.moveY = 0;
    drive.apply();
    sleep(200);
  }
}