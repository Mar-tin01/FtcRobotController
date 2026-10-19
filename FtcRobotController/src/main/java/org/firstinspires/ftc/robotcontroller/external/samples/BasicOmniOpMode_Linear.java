/* Copyright (c) 2021 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * OpMode TeleOp pour une base roulante holonome à 4 moteurs (Mecanum ou X-Drive).
 *
 * 1) Axial:    avancer / reculer                 Joystick gauche avant/arrière
 * 2) Lateral:  déplacement latéral (strafe)      Joystick gauche gauche/droite
 * 3) Yaw:      rotation                          Joystick droit gauche/droite
 *
 * Si le robot recule quand on pousse le joystick gauche vers l'avant,
 * inverser la direction des 4 moteurs (FORWARD <-> REVERSE) dans le code ci-dessous.
 */

@TeleOp(name="Basic: Omni Linear OpMode", group="Linear OpMode")
public class BasicOmniOpMode_Linear extends LinearOpMode {

    // Déclaration des membres de l'OpMode pour chacun des 4 moteurs.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;

    @Override
    public void runOpMode() {

        // Les noms ci-dessous doivent correspondre exactement à la configuration du robot
        // sur le Driver Station / Robot Controller.
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");

        // Tester les directions des moteurs : si une roue tourne à l'envers quand on pousse
        // le joystick gauche vers l'avant, inverser sa direction (FORWARD <-> REVERSE).
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Attendre le début du match (le pilote appuie sur START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // Boucle principale jusqu'à la fin du match (le pilote appuie sur STOP)
        while (opModeIsActive()) {
            double max;

            // Mode POV : joystick gauche pour avancer/strafer, joystick droit pour tourner.
            double axial = -gamepad1.left_stick_y;  // pousser vers l'avant donne une valeur négative
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;

            // Combinaison des 3 axes pour obtenir la puissance de chaque roue.
            double frontLeftPower = axial + lateral + yaw;
            double frontRightPower = axial - lateral - yaw;
            double backLeftPower = axial - lateral + yaw;
            double backRightPower = axial + lateral - yaw;

            // Normalisation : aucune roue ne doit dépasser 100 % de puissance.
            max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            max = Math.max(max, Math.abs(backLeftPower));
            max = Math.max(max, Math.abs(backRightPower));

            if (max > 1.0) {
                frontLeftPower /= max;
                frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }

            // CODE DE TEST (à décommenter uniquement pour vérifier les moteurs un par un) :
            // chaque bouton doit faire tourner le moteur correspondant vers l'AVANT.
            /*
            frontLeftPower  = gamepad1.x ? 1.0 : 0.0;  // X
            backLeftPower   = gamepad1.a ? 1.0 : 0.0;  // A
            frontRightPower = gamepad1.y ? 1.0 : 0.0;  // Y
            backRightPower  = gamepad1.b ? 1.0 : 0.0;  // B
            */

            // Envoi de la puissance aux roues
            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);

            // Affichage du temps de jeu et de la puissance des roues.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
            telemetry.update();
        }
    }
}