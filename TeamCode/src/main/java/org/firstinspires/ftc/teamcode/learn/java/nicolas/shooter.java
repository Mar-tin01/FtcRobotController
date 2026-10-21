package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class shooter {

    private DcMotor shooterNectar;
    private DcMotor shooterPollen;
    private CRServo tourelleNectar;
    private CRServo tourellePollen;


    public void init(HardwareMap hdMap) {
        shooterNectar = hdMap.get(DcMotor.class, "shooterNectar");
        shooterPollen = hdMap.get(DcMotor.class,"shooterPollen");
        tourelleNectar = hdMap.get(CRServo.class,"tourelleNectar");
        tourellePollen = hdMap.get(CRServo.class,"tourellePollen");

    }

    public void drive( boolean powerShooterNectar, boolean powerShooterPollen, double powerServoNectar, double powerServoPollen) {

// moteur shooter 1 et 2
        if(powerShooterNectar == true){
            shooterNectar.setPower(1.0);

        }
        else {
            shooterNectar.setPower(0.0);
        }

        if(powerShooterPollen == true){
            shooterPollen.setPower(1.0);
        }
        else {
            shooterPollen.setPower(0.0);
        }
// servo tourelle
        tourelleNectar.setPower(powerServoNectar);

        tourellePollen.setPower(powerServoPollen);



    }





}
