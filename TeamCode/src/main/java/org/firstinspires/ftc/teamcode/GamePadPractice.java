package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp

public class GamePadPractice extends OpMode {
     public void init() {

     }
     public void loop() {
         double gamepadLeftX = -gamepad1.left_stick_x;
         double gamepadLeftY = -gamepad1.left_stick_y;
         double gamepadRightY = -gamepad1.right_stick_y;
         double gamepadRightX = -gamepad1.right_stick_x;
         boolean gamepadB = gamepad1.b;
         double differenceXLeftAndXRight = gamepadLeftX - gamepadRightX;
         double SumTrigger = gamepad1.left_trigger + gamepad1.right_trigger;

         telemetry.addData("Left X", gamepadLeftX);
         telemetry.addData("Left Y", gamepadLeftX);
         telemetry.addData("a", gamepad1.a);
         telemetry.addData("Right Y", gamepadRightY);
         telemetry.addData("Right X", gamepadRightX);
         telemetry.addData("B", gamepadB );
         telemetry.addData("Difference", differenceXLeftAndXRight);
         telemetry.addData("Sum Trigger", SumTrigger);
     }


}
