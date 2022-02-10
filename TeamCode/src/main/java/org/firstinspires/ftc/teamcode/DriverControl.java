package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.core.Drive;

@TeleOp(name = "DriverControl", group = "Linear Opmode")
// @Disabled
public class DriverControl extends LinearOpMode {

  final Drive drive = new Drive();

  private final ElapsedTime runtime = new ElapsedTime();

  private Gamepad player1 = gamepad1;
  private Gamepad player2 = gamepad2;

  // Constants
  public final float MOVEMENT_PRECISION = 2f;

  public final int EXTENDER_MIN_POS = 0;
  public final int EXTENDER_MAX_POS = EXTENDER_MIN_POS + 1666;

  public final int EXTENDER_INPUT_SPEED = 20;
  public final float WRIST_INPUT_SPEED = 0.01f;
  public final float WRIST_MIN_ANGLE = 0.2f;
  public final float WRIST_MAX_ANGLE = 0.63f;

  public final float TURN_COEFFICIENT = 0.8f;

  public enum DRIVE_MODE {
    NORMAL,
    GOD
  }

  public int godPlayer;

  // mutables
  DRIVE_MODE driveMode = DRIVE_MODE.NORMAL;

  // @Override
  public void runOpMode() {
    drive.init(hardwareMap, telemetry);

    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();

    runtime.reset();

    while (opModeIsActive()) {
      // Power modes for slow and fast robot speeds
      drive.movementPower = 0.75f - (player1.left_trigger * 0.5f);

      // God mode toggler
      if (gamepad1.back) {
        if (driveMode == DRIVE_MODE.NORMAL) {
          player1 = gamepad1;
          player2 = gamepad1;
          driveMode = DRIVE_MODE.GOD;
          godPlayer = 1;
        } else {
          player1 = gamepad1;
          player2 = gamepad2;
          driveMode = DRIVE_MODE.NORMAL;
          godPlayer = 0;
        }
        while (gamepad1.back) sleep(1);
      } else if (gamepad2.back) {
        if (driveMode == DRIVE_MODE.NORMAL) {
          player1 = gamepad2;
          player2 = gamepad2;
          driveMode = DRIVE_MODE.GOD;
          godPlayer = 2;
        } else {
          player1 = gamepad1;
          player2 = gamepad2;
          driveMode = DRIVE_MODE.NORMAL;
          godPlayer = 0;
        }
        while (gamepad2.back) sleep(1);
      }

      // Tweak spinner joint speed
      drive.spinnerJointPos = player1.right_trigger;

      // Dampen controls to give more precision at lower power levels
      double dampedLeftJoystickX =
        Math.signum(player1.left_stick_x) *
        Math.pow(player1.left_stick_x, MOVEMENT_PRECISION);
      double dampedLeftJoystickY =
        Math.signum(player1.left_stick_y) *
        Math.pow(player1.left_stick_y, MOVEMENT_PRECISION);
      double dampedRightJoystickX =
        Math.signum(player1.right_stick_x) *
        Math.pow(player1.right_stick_x, MOVEMENT_PRECISION);
      double dampedRightJoystickY = // unused for now
        Math.signum(player1.right_stick_y) *
        Math.pow(player1.right_stick_y, MOVEMENT_PRECISION);

      drive.moveX = (float) dampedLeftJoystickX;
      drive.moveY = (float) -dampedLeftJoystickY;
      drive.rot = (float) dampedRightJoystickX * TURN_COEFFICIENT;

      // Tweak extender joint target
      drive.extenderTargetPos =
        Math.min(
          drive.extenderTargetPos +
          Math.round(EXTENDER_INPUT_SPEED * player2.right_trigger),
          EXTENDER_MAX_POS
        );
      drive.extenderTargetPos =
        Math.max(
          drive.extenderTargetPos -
          Math.round(EXTENDER_INPUT_SPEED * player2.left_trigger),
          EXTENDER_MIN_POS
        );

      // Tweak wrist joint target
      if (player2.dpad_up) drive.wristTargetAngle =
        Math.min(drive.wristTargetAngle + WRIST_INPUT_SPEED, 1);
      if (player2.dpad_down) drive.wristTargetAngle =
        Math.min(
          Math.max(drive.wristTargetAngle - WRIST_INPUT_SPEED, WRIST_MIN_ANGLE),
          WRIST_MAX_ANGLE
        );

      // Apply states
      if (!player2.start) {
        if (player2.a) {
          drive.setState(Drive.ARM_STATE.BOTTOM);
        } else if (player2.x) {
          drive.setState(Drive.ARM_STATE.MIDDLE);
        } else if (player2.y) {
          drive.setState(Drive.ARM_STATE.TOP);
        } else if (player2.b) {
          drive.setState(Drive.ARM_STATE.GROUND);
        }
      }

      drive.clawTargetState = player2.right_bumper ? 0 : 1;
      if (player1.right_bumper) drive.spinnerSpeed = 1;
      if (player1.left_bumper) drive.spinnerSpeed = 0;
      if (!player1.right_bumper && !player1.left_bumper) drive.spinnerSpeed =
        0.49f;

      drive.apply();
      drive.compensateForVoltage();

      // Update telemetry
      telemetry.addData("Status", "Run Time: " + runtime.toString());
      telemetry.addData("Drive mode", driveMode);
      telemetry.addData("Battery voltage", drive.getBatteryVoltage());
      telemetry.addData("God player", godPlayer == 0 ? "none" : godPlayer);
      telemetry.addData("----------", "----------");

      drive.logTargetsAndCurrents();

      telemetry.update();
    }
  }
}
