package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.core.Auto;
import org.firstinspires.ftc.teamcode.core.Drive;

//@Disabled
@Autonomous(name = "AutoBlueRightDuck", group = "A")
public class AutoBlueRightDuck extends LinearOpMode {

  final Auto auto = new Auto();
  final Drive drive = new Drive();

  // @Override
  public void runOpMode() {
    drive.init(hardwareMap, telemetry);
    auto.init(hardwareMap, telemetry, drive, Auto.SIDE.BLUE);

    waitForStart();
    auto.recordTeamScorePos();

    // go forward
    drive.moveY = 0.5;
    drive.apply();
    sleep(240);

    // stop moving forward, move right,
    drive.moveY = 0;
    drive.moveX = 0.5;
    drive.apply();
    sleep(1400);

    // pull out the spinner, and spin the spinner, and stop moving right
    drive.spinnerJointPos = 0.7;
    drive.spinnerSpeed = 0.8;
    drive.moveX = 0;
    drive.apply();
    sleep(300);

    // stop moving and let the duck fall off
    drive.moveX = 0;
    drive.apply();
    sleep(2500);

    // pull back spinner, stop spinner and move right
    drive.spinnerJointPos = -0.2;
    drive.spinnerSpeed = 0.49;
    drive.moveX = 0.5;
    drive.apply();
    sleep(320);

    // stop moving right and move forward
    drive.moveX = 0;
    drive.moveY = 0.5;
    drive.apply();
    sleep(1650);

    // stop moving forward and move arm to correct height
    drive.moveY = 0;
    drive.apply();
    auto.moveArmToCorrectPosition();
    sleep(500);

    // and rotate left
    drive.rot = -0.5;
    drive.apply();
    sleep(670);

    // stop rotating and move forward
    drive.rot = 0;
    drive.moveY = 0.5;
    drive.apply();
    sleep(1200);

    // stop moving
    drive.moveY = 0;
    drive.apply();
    sleep(500);

    // let go
    drive.clawTargetState = 0;
    drive.apply();
    sleep(500);

    // move back
    drive.moveY = -0.5;
    drive.apply();
    sleep(1200);

    // stop moving back, move arm to default and turn left
    drive.moveY = 0;
    drive.rot = -0.5;
    drive.setState(Drive.ARM_STATE.DEFAULT);
    drive.apply();
    sleep(800);

    // stop rotating and move forwards
    drive.rot = 0;
    drive.moveY = 0.5;
    drive.apply();
    sleep(900);

    // stop moving forwards and move left
    drive.moveY = 0;
    drive.moveX = -0.5;
    drive.apply();
    sleep(700);

    // stop moving
    drive.moveX = 0;
    drive.apply();
    sleep(200);
  }
}
