package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="ColorSensorTest", group="LinearOpMode")
public class ColorSensorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Percepts percepts = Percepts.getInstance();
        percepts.initialize(hardwareMap);
        waitForStart();
        while(opModeIsActive()){
            Colors color = percepts.getColor();
            String txt =
                    color == Colors.Blue ? "Blue" :
                            color == Colors.Red ? "Red" : "Unknown";

            telemetry.addData("Color: ", txt);

            telemetry.update();
        }
    }
}