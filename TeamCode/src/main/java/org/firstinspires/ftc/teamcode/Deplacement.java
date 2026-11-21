package org.firstinspires.ftc.teamcode.Martin;

// Déplacement autonome vers une cible (x mm, y mm, cap rad) par correcteur P.
public class Deplacement {

    private final Odometrie odo;
    private final Mecanum base;

    // Réglages (à ajuster au test)
    private static final double KP = 0.01;            // puissance par mm d'erreur
    private static final double K_CAP = 2.0;          // puissance par radian d'erreur
    private static final double ROT_MIN = 0.08;       // puissance mini de rotation (vaincre les frottements)
    private static final double PUISSANCE_MAX = 0.5;  // plafond translation et rotation (premiers tests)
    private static final double TOL_MM = 20;          // tolérance position
    private static final double TOL_CAP = Math.toRadians(2); // tolérance cap

    public Deplacement(Odometrie odo, Mecanum base) {
        this.odo = odo;
        this.base = base;
    }

    // À appeler à chaque tour de loop(). Renvoie true quand le robot est arrivé.
    public boolean allerA(double cibleX, double cibleY, double cibleCap) {
        double x = odo.getX();
        double y = odo.getY();
        double cap = odo.getCap();

        // 1. Erreurs dans le repère terrain
        double ex = cibleX - x;
        double ey = cibleY - y;
        double ecap = cibleCap - cap;

        // Ramener l'erreur de cap entre -PI et PI
        while (ecap > Math.PI) ecap -= 2 * Math.PI;
        while (ecap < -Math.PI) ecap += 2 * Math.PI;

        // 2. Arrivé ?
        double distance = Math.hypot(ex, ey);
        if (distance < TOL_MM && Math.abs(ecap) < TOL_CAP) {
            base.drive(0, 0, 0);
            return true;
        }

        // 3. Correcteur P
        double forward = KP * ex;
        double strafe = -KP * ey;   // signe à vérifier au test
        double rot = -K_CAP * ecap; // signe à vérifier au test

        // 4. Limiter la NORME du vecteur de translation (garde la direction vers la cible)
        double norme = Math.hypot(forward, strafe);
        if (norme > PUISSANCE_MAX) {
            forward *= PUISSANCE_MAX / norme;
            strafe *= PUISSANCE_MAX / norme;
        }

        // Rotation : plafond + puissance minimale tant que le cap n'est pas dans la tolérance
        rot = Math.max(-PUISSANCE_MAX, Math.min(PUISSANCE_MAX, rot));
        if (Math.abs(ecap) >= TOL_CAP && Math.abs(rot) < ROT_MIN) {
            rot = Math.copySign(ROT_MIN, rot);
        }

        // 5. Piloter (conversion terrain -> robot par driveField)
        base.driveField(forward, strafe, rot, cap);
        return false;
    }
}
