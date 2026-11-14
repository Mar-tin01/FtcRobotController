package org.firstinspires.ftc.teamcode.Martin;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class intake {

    private DcMotor intakeMotor;

    public intake(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
    }

    public void drive(double gauche, double droite) {
        if (gauche > 0.1) {
            intakeMotor.setPower(1);
        } else if (droite > 0.1) {
            intakeMotor.setPower(-1);
        } else {
            intakeMotor.setPower(0);
        }
    }

    public double getRPM() {
        return ((DcMotorEx) intakeMotor).getVelocity(AngleUnit.DEGREES) / 6;
    }
}