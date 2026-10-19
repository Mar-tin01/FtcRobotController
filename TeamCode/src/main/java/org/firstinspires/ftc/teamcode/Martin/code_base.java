package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp (name = "base")
public class code_base extends OpMode {
    private mechanum base;


    @Override
    public void init(){
        base = new mechanum(hardwareMap);
    }

    @Override
    public void loop() {
        double fw = gamepad.traiter(gamepad1.right_stick_y);
        double stf = gamepad.traiter(gamepad1.right_stick_x);
        double rot = gamepad.traiter(gamepad1.left_stick_x);

        base.drive(-fw, stf, rot);

        telemetry.addData("fw", fw);
        telemetry.addData("stf", stf);
        telemetry.addData("rot", rot);

        telemetry.addData("fl", ((DcMotorEx) base.frontLeft).getVelocity());
        telemetry.addData("fr", ((DcMotorEx) base.frontRight).getVelocity());
        telemetry.addData("bl", ((DcMotorEx) base.backLeft).getVelocity());
        telemetry.addData("br", ((DcMotorEx) base.backRight).getVelocity());

        telemetry.addData("IMU", base.Getheading());

        telemetry.update();

    }
}
