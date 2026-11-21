package org.firstinspires.ftc.teamcode.learn.java.nicolas;

import java.util.List;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

// Déterminer si un AprilTag correspond à l'alliance du robot
public class TargetSelector {

    private final boolean allianceRouge;

    public TargetSelector(boolean allianceRouge) {
        this.allianceRouge = allianceRouge;
    }

    // Vérifier si un AprilTag correspond à notre alliance
    public boolean isCorrectTag(int id) {
        if (allianceRouge) {
            return id >= 30 && id <= 37;
        } else {
            return id >= 38 && id <= 45;
        }
    }

    // Choisir parmi les tags de notre alliance celui le plus proche du centre
    public LLResultTypes.FiducialResult choisirMeilleurTag(List<LLResultTypes.FiducialResult> tags) {

        LLResultTypes.FiducialResult meilleurTag = null;
        double meilleurEcart = Double.MAX_VALUE;

        for (LLResultTypes.FiducialResult tag : tags) {
            int id = tag.getFiducialId();

            // Vérifier si le tag correspond à notre alliance
            if (isCorrectTag(id)) {

                double txTag = tag.getTargetXDegrees();
                double ecart = Math.abs(txTag);

                // Garder le tag le plus proche du centre
                if (ecart < meilleurEcart) {
                    meilleurEcart = ecart;
                    meilleurTag = tag;
                }
            }
        }

        return meilleurTag;
    }
}