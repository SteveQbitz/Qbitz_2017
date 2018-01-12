package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="ColorSensorTest", group="LinearOpMode")
public class ColorSensorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Percepts percepts = Percepts.getInstance();
        percepts.initialize(hardwareMap);
        waitForStart();
        while(opModeIsActive()){

            telemetry.addData("Hue", percepts.getHue());
            telemetry.addData("Alpha", percepts.getAlpha());
            telemetry.addData("Red", percepts.getRed());
            telemetry.update();
        }
    }
}