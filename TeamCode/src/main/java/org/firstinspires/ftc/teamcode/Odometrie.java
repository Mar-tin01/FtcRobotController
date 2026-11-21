package org.firstinspires.ftc.teamcode.Martin;

import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.MM;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

// Odométrie calculée à la main à partir des ticks bruts du Pinpoint.
// Repère : X = avant du robot, Y = gauche, cap anti-horaire positif (radians).
public class Odometrie {

    // Offsets des pods (mm) par défaut
    public static final double X_OFFSET_MM = 9.1;
    public static final double Y_OFFSET_MM = -1.1;

    private final GoBildaPinpointDriver pinpoint;
    private final double xOffset;
    private final double yOffset;

    private double x;
    private double y;

    public final double circonference_mm = 2 * Math.PI * 16;
    public final double mm_par_tick = circonference_mm / 2000;

    private int tickXprec = 0;
    private int tickYprec = 0;
    private double capPrec = 0;

    private boolean premier = true;

    public Odometrie(HardwareMap hardwareMap) {
        this(hardwareMap, X_OFFSET_MM, Y_OFFSET_MM);
    }

    // Le robot doit rester immobile à l'init (calibration de l'IMU)
    public Odometrie(HardwareMap hardwareMap, double xOffset, double yOffset) {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        pinpoint.setOffsets(xOffset, yOffset, MM);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();

        pinpoint.update();
    }

    // Ajoute un déplacement robot (mm) en le faisant pivoter par le cap donné
    private void move(double xMm, double yMm, double capRad) {
        x += xMm * Math.cos(capRad) - yMm * Math.sin(capRad);
        y += xMm * Math.sin(capRad) + yMm * Math.cos(capRad);
    }

    public void update() {

        pinpoint.update();

        int tickX = pinpoint.getEncoderX();
        int tickY = pinpoint.getEncoderY();
        double cap = pinpoint.getHeading(AngleUnit.RADIANS);

        if (premier) {
            tickXprec = tickX;
            tickYprec = tickY;
            capPrec = cap;
            premier = false;
            return;
        }

        // Deltas depuis la dernière mise à jour
        double deltaX = tickX - tickXprec;
        double deltaY = tickY - tickYprec;
        double deltaCap = cap - capPrec;

        if (deltaCap > Math.PI) {
            deltaCap -= 2 * Math.PI;
        } else if (deltaCap < -Math.PI) {
            deltaCap += 2 * Math.PI;
        }

        // Cap moyen du pas : plus juste que le cap de fin quand le robot tourne vite
        double capMoyen = capPrec + deltaCap / 2;

        tickXprec = tickX;
        tickYprec = tickY;
        capPrec = cap;

        // Correction des offsets (les pods bougent aussi quand le robot tourne)
        // /!\ Signes à valider par le test des 360° sur place : X et Y doivent revenir ~0
        double arcX = xOffset * deltaCap;
        double arcY = yOffset * deltaCap;

        double xMm = deltaX * mm_par_tick - arcX;
        double yMm = deltaY * mm_par_tick + arcY;

        move(xMm, yMm, capMoyen);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getCap() { return capPrec; }

    public int getTickX() { return tickXprec; }
    public int getTickY() { return tickYprec; }
}
