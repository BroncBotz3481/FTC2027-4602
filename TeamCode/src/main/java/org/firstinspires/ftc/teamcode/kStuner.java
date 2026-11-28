package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class kStuner extends OpMode {
    Team4602HM2027 robot = new Team4602HM2027();
    public double highVelocity = 1100;
    public double lowVelocity = 900;
    public double curTargetVelocity = highVelocity;

    double kS = 0.0865;

    double kV = 0.000312;
    double P = 0.00215;
    double[] stepSizes = {10, 1, 0.1, 0.01, 0.001, 0.0001,0.00001,0.000001};
    int stepIndex = 1;


    @Override
    public void init() {
        robot.Map(hardwareMap);
        telemetry.addLine("Init Complete");
    }

    @Override
    public void loop() {
        // WasPressed executes the code once and not again until you release and press again
        if (gamepad1.yWasPressed()) {
            if (curTargetVelocity == highVelocity) {
                curTargetVelocity = lowVelocity;
            } else {
                curTargetVelocity = highVelocity;
            }
        }
        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if (gamepad1.dpadLeftWasPressed()) {
            P -= stepSizes[stepIndex];
        }
        if (gamepad1.dpadRightWasPressed()) {
            P += stepSizes[stepIndex];
        }
        /*if (gamepad1.dpadDownWasPressed()) {
            kS -= stepSizes[stepIndex];
        }
        if (gamepad1.dpadUpWasPressed()) {
            kS += stepSizes[stepIndex];
        }*/

        double curVelocity = robot.leftShooter.getVelocity();
        double error = curTargetVelocity - curVelocity;

        robot.leftShooter.setPower((kV * curTargetVelocity) + (P * error) + kS);
        robot.rightShooter.setPower((kV * curTargetVelocity) + (P * error) + kS);

        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", "%.2f", curVelocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addLine("--------------------------");
        telemetry.addData("Tuning kS", "%.6f (Dpad U/D)",kS);
        telemetry.addData("Tuning kV", "%.6f (Dpad U/D)",kV);
        telemetry.addData("Tuning P", "%.6f (Dpad L/R)",P);
        telemetry.addData("Step Size", "%.6f (B button) ", stepSizes[stepIndex]);
    }
}
