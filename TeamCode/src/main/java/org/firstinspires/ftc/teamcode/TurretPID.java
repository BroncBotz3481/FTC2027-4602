package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class TurretPID extends OpMode {
    Team4602HM2027 robot = new Team4602HM2027();
    public double highVelocity = 1450;
    public double lowVelocity = 1000;

    public double curPosition = highVelocity;

    double D = 0;
    double P = 0;
    double[] stepSizes = {10, 1, 0.1, 0.01, 0.001, 0.0001};
    int stepIndex = 1;


    @Override
    public void init() {
        robot.Map(hardwareMap);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, D,0);
        robot.Turret.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        telemetry.addLine("Init Complete");
    }

    @Override
    public void loop() {
        // WasPressed executes the code once and not again until you release and press again
        if (gamepad1.yWasPressed()) {
            if (curPosition == highVelocity) {
                curPosition = lowVelocity;
            } else {
                curPosition = highVelocity;
            }
        }
        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if (gamepad1.dpadLeftWasPressed()) {
            D -= stepSizes[stepIndex];
        }
        if (gamepad1.dpadRightWasPressed()) {
            D += stepSizes[stepIndex];
        }
        if (gamepad1.dpadDownWasPressed()) {
            P -= stepSizes[stepIndex];
        }
        if (gamepad1.dpadUpWasPressed()) {
            P += stepSizes[stepIndex];
        }
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, D, 0);
        robot.leftShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        robot.rightShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        //robot.Turret.setVelocity(curPosition);


        double curVelocity = robot.Turret.getVelocity();
        double error = curPosition - curVelocity;

        telemetry.addData("Target Velocity", curPosition);
        telemetry.addData("Current Velocity", "%.2f", curVelocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addLine("--------------------------");
        telemetry.addData("Tuning P", "%.4f (Dpad U/D)",P);
        telemetry.addData("Tuning F", "%.4f (Dpad L/R)",D);
        telemetry.addData("Step Size", "%.4f (B button) ", stepSizes[stepIndex]);


    }
}
