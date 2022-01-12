package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//@Disabled
@Autonomous(name = "AutoBlueRightDuck", group = "A")
public class AutoBlueRightDuck extends LinearOpMode {

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
    sleep(120);

    // stop moving forward, move right,
    drive.moveY = 0;
    drive.moveX = 1;
    drive.apply();
    sleep(700);

    // pull out the spinner, and spin the spinner, and stop moving right
    drive.spinnerJointSpeed = 0.6f;
    drive.spinnerSpeed = 0.8f;
    drive.moveX = 0;
    drive.apply();
    sleep(300);

    // stop moving and let the duck fall off
    drive.moveX = 0;
    drive.apply();
    sleep(2500);

    // pull back spinner, stop spinner and move right
    drive.spinnerJointSpeed = -0.2f;
    drive.spinnerSpeed = 0.49f;
    drive.moveX = 1;
    drive.apply();
    sleep(100);

    // stop moving right and move forward
    drive.moveX = 0;
    drive.moveY = -1;
    drive.apply();
    sleep(900);

    // stop moving forward and move arm to correct height
    drive.moveY = 0;
    if (autonomousAPI.camResult == CameraPipeline.LOCATION.RIGHT) {
      drive.setState(DriverControlAPI.STATE.HIGH);
    } else if (autonomousAPI.camResult == CameraPipeline.LOCATION.MIDDLE) {
      drive.setState(DriverControlAPI.STATE.MIDDLE);
    } else {
      drive.setState(DriverControlAPI.STATE.LOW);
    }
    drive.apply();
    sleep(500);

    // and rotate left
    drive.rot = -1;
    drive.apply();
    sleep(380);

    // stop rotating and move forward
    drive.rot = 0;
    drive.moveY = -0.5f;
    drive.apply();
    sleep(1300);

    // open claw
    drive.moveY = 0;
    drive.clawTargetState = 0;
    drive.apply();
    sleep(200);

    // move back
    drive.moveY = 1;
    drive.apply();
    sleep(505);

    // stop moving back and move to the left
    drive.moveY = 0;
    drive.setState(DriverControlAPI.STATE.LOW);
    drive.moveX = -1;
    drive.apply();
    sleep(760);

    // turn a bit to the right
    drive.moveX = 0;
    drive.rot = 1;
    drive.apply();
    sleep(28);

    // stop moving
    drive.rot = 0;
    drive.apply();
    sleep(200);
  }
}
