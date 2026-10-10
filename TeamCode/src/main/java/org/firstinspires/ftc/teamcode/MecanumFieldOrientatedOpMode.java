package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Mecanisms.MecanumDrive;

@TeleOp(name = "Mecanum Field Oriented")// nom dans driver station
public class MecanumFieldOrientatedOpMode extends OpMode {

    MecanumDrive drive = new MecanumDrive();

    double forward , strafe , rotate ;

    double currentForward = 0;
    double currentStrafe = 0;
    double currentRotate = 0;

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        drive.init(hardwareMap);
        drive.resetYaw();
        timer.reset();
    }

    @Override
    public void loop() {
        forward = -gamepad1.left_stick_y;
        strafe = -gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        double speedMultiplier = gamepad1.left_bumper ? 0.30 : 1.0;

        double dt = timer.seconds();
        timer.reset();

        currentForward = ramp(
                currentForward,
                forward * speedMultiplier,
                5.0 * dt
        );

        currentStrafe = ramp(
                currentStrafe,
                strafe * speedMultiplier,
                5.0 * dt
        );

        currentRotate = ramp(
                currentRotate,
                rotate * speedMultiplier,
                8.0 * dt
        );
        drive.driveFieldRelative(
                currentForward,
                currentStrafe,
                currentRotate
        );

        if (gamepad1.y) { //test pour réinitialiser le field oriented puis supprimer le commentaire
            drive.resetYaw();
        }
            telemetry.addData("Forward Target", forward * speedMultiplier);
            telemetry.addData("Forward Current", currentForward);

            telemetry.addData("Strafe Target", strafe * speedMultiplier);
            telemetry.addData("Strafe Current", currentStrafe);

            telemetry.addData("Rotate Target", rotate * speedMultiplier);
            telemetry.addData("Rotate Current", currentRotate);

            telemetry.addData("Yaw", drive.getYaw());
            telemetry.addData("Speed", speedMultiplier);

            telemetry.update();
    }
    private double ramp(double current, double target, double maxChange) {

        double difference = target - current;

        if (Math.abs(difference) <= maxChange) {
            return target;
        }

        return current + Math.signum(difference) * maxChange;
    }
        }


