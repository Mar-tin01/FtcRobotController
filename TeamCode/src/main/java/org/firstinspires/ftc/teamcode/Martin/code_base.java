package org.firstinspires.ftc.teamcode.Martin;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp (name = "base")
public class code_base extends OpMode {
    private mechanum base;

    private Odometrie odo;

    private gamepad fwFiltre = new gamepad();
    private gamepad stfFiltre = new gamepad();
    private gamepad rotFiltre = new gamepad();

    double Xoffset = 0;
    double Yoffset = 0;




    @Override
    public void init(){
        base = new mechanum(hardwareMap);
        odo = new Odometrie(hardwareMap, Xoffset, Yoffset);
    }

    @Override
    public void loop() {
        double fw = fwFiltre.traiter(gamepad1.right_stick_y);
        double stf = stfFiltre.traiter(gamepad1.right_stick_x);
        double rot = rotFiltre.traiter(gamepad1.left_stick_x);

        base.drive(-fw, stf, rot);

        telemetry.addData("fw", fw);
        telemetry.addData("stf", stf);
        telemetry.addData("rot", rot);

        telemetry.addData("fl", ((DcMotorEx) base.frontLeft).getVelocity());
        telemetry.addData("fr", ((DcMotorEx) base.frontRight).getVelocity());
        telemetry.addData("bl", ((DcMotorEx) base.backLeft).getVelocity());
        telemetry.addData("br", ((DcMotorEx) base.backRight).getVelocity());


        //  Odometrie

        odo.update();
        double odoX = odo.getX();
        double odoY = odo.getY();

        telemetry.addData("X_mm", odoX);
        telemetry.addData("Y_mm", odoY);

        telemetry.update();
    }
}
