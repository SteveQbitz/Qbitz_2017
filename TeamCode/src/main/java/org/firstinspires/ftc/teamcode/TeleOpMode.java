package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by Rawley on 11/30/2017.
 */
@TeleOp(name="TeleOpMode", group="LinearOpMode")
public class TeleOpMode extends LinearOpMode {
    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;
    private DcMotor glyphRotator = null;
    private DcMotor linearSlide = null;
    private DcMotor slideForward = null;

    private Servo gripper1 = null;
    private Servo gripper2 = null;
    private Servo pushDownPlate1 = null;
    private Servo pushDownPlate2 = null;

    private double clawOffset1 = 0;
    private double clawOffset2 = 0;
    private final double CLAW_SPEED = 0.02;
    private final double MID_SERVO = 0.5;
    private int oneEighty = 1120 / 2;


    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontLeft.setDirection(DcMotor.Direction.FORWARD);

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backLeft.setDirection(DcMotor.Direction.FORWARD);

        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backRight.setDirection(DcMotor.Direction.REVERSE);

        glyphRotator = hardwareMap.get(DcMotor.class, "glyphRotator");
        glyphRotator.setDirection(DcMotor.Direction.FORWARD);

        linearSlide = hardwareMap.get(DcMotor.class, "linearSlide");
        linearSlide.setDirection(DcMotor.Direction.FORWARD);

        gripper1 = hardwareMap.get(Servo.class, "gripper1");
        gripper2 = hardwareMap.get(Servo.class, "gripper2");
        pushDownPlate1 = hardwareMap.get(Servo.class, "plate1");
        pushDownPlate2 = hardwareMap.get(Servo.class, "plate2");


        slideForward = hardwareMap.get(DcMotor.class, "slideForward");

        waitForStart();

        while (opModeIsActive()) {


            if( gamepad2.a =true){

                slideForward.setPower(.5);
            }
           fullCircleStrafe();
           glyphGripper1();
           glyphGripper2();
           moveLinearSlide();

            pushThePlatformDown();
            slideForward();


            telemetry.addData("Gripper1 Position", gripper1.getPosition());
            telemetry.addData("Gripper2 Position", gripper2.getPosition());
            telemetry.addData("bal1", pushDownPlate1.getPosition());
            telemetry.addData("bal2", pushDownPlate2.getPosition());
            telemetry.update();

        }
    }


    private void fullCircleStrafe() {
        double radius = Math.hypot(-gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
        double rotation = gamepad1.right_stick_x / 2;

        final double frontLeftPower = radius * Math.cos(robotAngle) - rotation;
        final double frontRightPower = radius * Math.sin(robotAngle) + rotation;
        final double backLeftPower = radius * Math.sin(robotAngle) - rotation;
        final double backRightPower = radius * Math.cos(robotAngle) + rotation;

        frontLeft.setPower(frontLeftPower * 0.75);
        frontRight.setPower(frontRightPower * 0.75);
        backLeft.setPower(backLeftPower * 0.75);
        backRight.setPower(backRightPower * 0.75);

        telemetry.addData("FL_Power", frontLeftPower);
        telemetry.addData("FR_Power", frontRightPower);
        telemetry.addData("BL_Power", backLeftPower);
        telemetry.addData("BR_Power", backRightPower);
    }

    private void slideForward() {

    }

    private void glyphGripper1() {


        if (gamepad2.right_bumper) {
            clawOffset1 += CLAW_SPEED;
        }
        if (gamepad2.left_bumper) {
            clawOffset1 -= CLAW_SPEED;
        }
        clawOffset1 = Range.clip(clawOffset1, -1, 1);
        gripper1.setPosition(MID_SERVO + clawOffset1);


    }


    private void glyphGripper2() {
        if (gamepad2.right_trigger > 0.3)
            clawOffset2 += CLAW_SPEED;
        if (gamepad2.left_trigger > 0.3) {
            clawOffset2 -= CLAW_SPEED;
        }
        clawOffset2 = Range.clip(clawOffset2, -1, 1);
        gripper2.setPosition(MID_SERVO + clawOffset2);


    }


    private void rotateGripper180() {
        glyphRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if (gamepad2.a) {
            glyphRotator.setTargetPosition(oneEighty);
            glyphRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            glyphRotator.setPower(0.3);

            glyphRotator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            glyphRotator.setPower(0);

        }
    }


    private void moveLinearSlide() {
        if (gamepad1.right_trigger > 0.3) {
            linearSlide.setPower(0.75);
        }
        if (gamepad1.left_trigger > 0.3) {
            linearSlide.setPower(-0.75);
        }
        linearSlide.setPower(0);

    }

    private void pushThePlatformDown() {
        if (gamepad1.a) {
            pushDownPlate1.setPosition(1);
            pushDownPlate2.setPosition(1);

        }
        if (gamepad1.b) {
            pushDownPlate1.setPosition(pushDownPlate1.getPosition() - 0.3);
            pushDownPlate1.setPosition(pushDownPlate1.getPosition() - 0.3);
            pushDownPlate1.setPosition(pushDownPlate1.getPosition() - 0.3);
            pushDownPlate2.setPosition(pushDownPlate1.getPosition() + 0.3);
            pushDownPlate2.setPosition(pushDownPlate1.getPosition() + 0.3);
            pushDownPlate2.setPosition(pushDownPlate1.getPosition() + 0.3);
        }
    }
}
