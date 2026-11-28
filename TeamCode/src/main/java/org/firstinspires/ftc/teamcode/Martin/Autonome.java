package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

// Autonome de base : une suite d'étapes, chacune passe à la suivante quand allerA() renvoie true.
// Repère de départ : le robot démarre en (0, 0) avec le cap 0.
@Autonomous(name = "Auto_base")
public class Autonome extends OpMode {

    private Batterie batterie;
    private Mecanum base;
    private Odometrie odo;
    private Deplacement deplacement;

    private int etape = 0;

    @Override
    public void init() {
        Hubs.activerBulkCaching(hardwareMap);

        batterie = new Batterie(hardwareMap);
        base = new Mecanum(hardwareMap, batterie);
        odo = new Odometrie(hardwareMap);
        deplacement = new Deplacement(odo, base);
    }

    @Override
    public void loop() {
        odo.update();

        switch (etape) {
            case 0:
                // Coordonnées d'exemple (mm, mm, rad) : à remplacer par ton parcours
                if (deplacement.allerA(600, 0, 0)) etape++;
                break;
            case 1:
                if (deplacement.allerA(600, 600, Math.toRadians(90))) etape++;
                break;
            default:
                base.drive(0, 0, 0);
                break;
        }

        telemetry.addData("Etape", etape);
        telemetry.addData("X_mm", odo.getX());
        telemetry.addData("Y_mm", odo.getY());
        telemetry.addData("Cap_deg", Math.toDegrees(odo.getCap()));
        telemetry.update();
    }

    @Override
    public void stop() {
        base.drive(0, 0, 0);
    }
}
