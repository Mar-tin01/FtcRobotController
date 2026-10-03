package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Pince {
                //  Membres  //
    private Servo servo;
    private String etat = "None";
    private static final double ouvert = 0.7;
    private static final double ferme = 0.2;

    public Pince(HardwareMap nom){
        servo = nom.get(Servo.class, "Servo"); // Associe à la variable Servo le port déféni dans la configuration

    }

    public void ouvrir(){
        servo.setPosition(ouvert);
    }

    public void ferme(){
        servo.setPosition(ferme);
    }

}
