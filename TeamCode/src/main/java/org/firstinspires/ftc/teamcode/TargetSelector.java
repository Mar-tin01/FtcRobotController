package org.firstinspires.ftc.teamcode.Martin;

import com.qualcomm.hardware.limelightvision.LLResultTypes;

import java.util.List;

// Détermine si un AprilTag correspond à l'alliance du robot
public class TargetSelector {

    private boolean allianceRouge;

    public TargetSelector(boolean allianceRouge) {
        this.allianceRouge = allianceRouge;
    }

    public void setAllianceRouge(boolean allianceRouge) {
        this.allianceRouge = allianceRouge;
    }

    public boolean isAllianceRouge() {
        return allianceRouge;
    }

    // Vérifier si un AprilTag correspond à notre alliance
    // /!\ Plages d'IDs reprises du code de Nicolas : à vérifier dans le manuel de jeu
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
            if (isCorrectTag(tag.getFiducialId())) {
                double ecart = Math.abs(tag.getTargetXDegrees());
                if (ecart < meilleurEcart) {
                    meilleurEcart = ecart;
                    meilleurTag = tag;
                }
            }
        }

        return meilleurTag;
    }
}
