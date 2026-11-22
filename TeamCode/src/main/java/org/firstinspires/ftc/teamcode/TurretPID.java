package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class TurretPID extends OpMode {
    Team4602HM2027 robot = new Team4602HM2027();
    public double highPosition = 128;
    public double lowPosition = 0;

    public double curTargetPosition = highPosition;

    double D = 0;
    double P = 0;

    ElapsedTime timer = new ElapsedTime();

    double lastError = 0;
    double[] stepSizes = {1, 0.1, 0.01, 0.001, 0.0001,0.00001,0.000001};
    int stepIndex = 1;
    @Override
    public void init() {
        robot.Map(hardwareMap);
        telemetry.addLine("Init Complete");
    }

    @Override
    public void start(){
        timer.reset();
    }

    @Override
    public void loop() {
        // WasPressed executes the code once and not again until you release and press again
        if (gamepad1.yWasPressed()) {
            if (curTargetPosition == highPosition) {
                curTargetPosition = lowPosition;
            } else {
                curTargetPosition = highPosition;
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

        double curPosition = robot.Turret.getCurrentPosition();
        double error = curTargetPosition - curPosition;
        double dt = timer.seconds();
        timer.reset();

        robot.Turret.setPower((P * error) + (D * ((error-lastError)/dt)));

        lastError = error;

        telemetry.addData("Target Position", curTargetPosition);
        telemetry.addData("Current Position", "%.2f", curPosition);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addLine("--------------------------");
        telemetry.addData("Tuning P", "%.4f (Dpad U/D)",P);
        telemetry.addData("Tuning D", "%.4f (Dpad L/R)",D);
        telemetry.addData("Step Size", "%.4f (B button) ", stepSizes[stepIndex]);
    }
}
