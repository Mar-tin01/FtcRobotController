package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
 * COMMANDES
 * Init (robot IMMOBILE) : gamepad1 B = alliance rouge, gamepad1 X = alliance bleue
 *
 * Gamepad 1 (pilote)
 *   stick droit            : avancer / strafer
 *   stick gauche X         : tourner
 *   bumper gauche (maintenu) : visée automatique sur l'AprilTag (rotation auto)
 *   A (maintenu)           : mode précision (vitesse réduite)
 *   gâchette gauche        : intake avale
 *   gâchette droite        : intake rejette
 *   bumper droit           : servos d'intake
 *
 * Gamepad 2 (opérateur)
 *   A / B                  : shooter Nectar / Pollen
 *   stick gauche X         : tourelle Nectar
 *   stick droit X          : tourelle Pollen
 */
@TeleOp(name = "Teleop_complet")
public class TeleopPrincipal extends OpMode {

    private static final double VITESSE_PRECISION = 0.4;

    private Batterie batterie;
    private Mecanum base;
    private Odometrie odo;
    private Intake intake;
    private Shooter shooter;
    private TargetSelector selecteur;
    private Vision vision;

    private final FiltreJoystick fwFiltre = new FiltreJoystick();
    private final FiltreJoystick stfFiltre = new FiltreJoystick();
    private final FiltreJoystick rotFiltre = new FiltreJoystick();

    private boolean allianceRouge = true;

    @Override
    public void init() {
        Hubs.activerBulkCaching(hardwareMap);

        batterie = new Batterie(hardwareMap);
        base = new Mecanum(hardwareMap, batterie);
        odo = new Odometrie(hardwareMap);
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        selecteur = new TargetSelector(allianceRouge);
        vision = new Vision(hardwareMap, selecteur);
    }

    @Override
    public void init_loop() {
        if (gamepad1.b) allianceRouge = true;
        if (gamepad1.x) allianceRouge = false;
        selecteur.setAllianceRouge(allianceRouge);

        telemetry.addData("Alliance", allianceRouge ? "ROUGE (B)" : "BLEUE (X)");
        telemetry.update();
    }

    @Override
    public void loop() {
        // --- Base ---
        double fw = fwFiltre.traiter(gamepad1.right_stick_y);
        double stf = stfFiltre.traiter(gamepad1.right_stick_x);
        double rot = rotFiltre.traiter(gamepad1.left_stick_x);

        vision.update();
        boolean visee = gamepad1.left_bumper && vision.isTagDetecte();
        if (visee) {
            rot = vision.getRotationAuto();
        }

        base.setVitesseMax(gamepad1.a ? VITESSE_PRECISION : 1.0);
        base.drive(-fw, stf, rot);

        // --- Mécanismes ---
        intake.drive(gamepad1.left_trigger, gamepad1.right_trigger, gamepad1.right_bumper);
        shooter.drive(gamepad2.a, gamepad2.b);
        shooter.tourelle(gamepad2.left_stick_x, gamepad2.right_stick_x);

        // --- Odométrie ---
        odo.update();

        // --- Télémétrie ---
        telemetry.addData("Alliance", allianceRouge ? "ROUGE" : "BLEUE");
        telemetry.addData("Visee auto", visee);
        telemetry.addData("Shooter pret", shooter.pret());
        telemetry.addData("Batterie V", batterie.getVoltage());

        telemetry.addData("fw", fw);
        telemetry.addData("stf", stf);
        telemetry.addData("rot", rot);

        telemetry.addData("fl RPM", base.getRpmFL());
        telemetry.addData("fr RPM", base.getRpmFR());
        telemetry.addData("bl RPM", base.getRpmBL());
        telemetry.addData("br RPM", base.getRpmBR());
        telemetry.addData("intake RPM", intake.getRPM());
        telemetry.addData("shooter Nectar RPM", shooter.getRpmNectar());
        telemetry.addData("shooter Pollen RPM", shooter.getRpmPollen());

        telemetry.addData("X_mm", odo.getX());
        telemetry.addData("Y_mm", odo.getY());
        telemetry.addData("Cap_deg", Math.toDegrees(odo.getCap()));
        telemetry.addData("Xtick", odo.getTickX());
        telemetry.addData("Ytick", odo.getTickY());

        vision.afficher(telemetry);
        telemetry.update();
    }

    @Override
    public void stop() {
        vision.stop();
    }
}
