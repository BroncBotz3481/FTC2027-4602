
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;


@TeleOp (name = "Team4602TeleOp2026", group = "4602")
public class Team4602TeleOp2027 extends LinearOpMode {


    Team4602HM2027 robot = new Team4602HM2027();

    public void runOpMode() {
        robot.Map(hardwareMap);

        telemetry.addData("Say", "TeleOp Starting");
        telemetry.update();

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(1.854, 0, 0, 13.923);

        robot.leftShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        robot.rightShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        robot.DriveRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveRightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.DriveLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveLeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.DriveRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveRightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.DriveLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveLeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            robot.IntakeArm.setPosition(1);
            telemetry.addData("Intake Arm Position", robot.IntakeArm.getPosition());

            boolean speedslow = gamepad1.right_bumper;
            double mag = speedslow ? 0.5 : 1.0;

            double y = gamepad1.left_stick_y; // Remember, this is reversed!
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            double turretY = gamepad2.left_stick_x;

            // Optional Deadzones
//            double y = (Math.abs(gamepad1.left_stick_y) > 0.1 ? gamepad1.left_stick_y : 0); // Remember, this is reversed!
//            double x = -(Math.abs(gamepad1.left_stick_x) > 0.1 ? gamepad1.left_stick_x : 0) * 1.1; // Counteract imperfect strafing
//            double rx = -gamepad1.right_stick_x;


            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.5);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            /*if (turretY > 0.1) {
                robot.Turret.setPower(Math.min(turretY, 0.4));
            }
            else if(turretY < -0.1){
                robot.Turret.setPower(Math.max(turretY,-0.4));
            }*/

            robot.DriveLeftFront.setPower(frontLeftPower * mag);
            robot.DriveLeftBack.setPower(backLeftPower * mag);
            robot.DriveRightFront.setPower(frontRightPower * mag);
            robot.DriveRightBack.setPower(backRightPower * mag);

            telemetry.addData("RightFront", robot.DriveRightFront.getCurrentPosition());
            telemetry.addData("RightBack", robot.DriveRightBack.getCurrentPosition());
            telemetry.addData("LeftFront", robot.DriveLeftFront.getCurrentPosition());
            telemetry.addData("LeftBack", robot.DriveLeftBack.getCurrentPosition());
            telemetry.update();

           /* if (gamepad1.dpad_up){
                moveForward(mag);
            } else if (gamepad1.dpad_down){
                moveBackward(mag);
            } else if (gamepad1.dpad_left){
                moveLeft(mag);
            } else if (gamepad1.dpad_right){
                moveRight(mag);
            }*/
//            boolean encoderWire = true;
            if (gamepad1.right_trigger > 0.2) {
                robot.IntakeTransfer.setPower(0.98);
            }
            else if(gamepad1.right_bumper){
                robot.IntakeTransfer.setPower(-0.98);
            }
            else{
                robot.IntakeTransfer.setPower(0);
            }

           /* if (gamepad1.a) {
                robot. IntakeTransfer.setPower(0.6);
                robot.leftShooter.setPower(-0.45);
                robot.rightShooter.setPower(0.45);
                sleep(200);

            }*/
            if (gamepad2.b) {
                robot.leftShooter.setVelocity(1100);
                robot.rightShooter.setVelocity(1100);
            } else if (gamepad2.x) {
                robot.leftShooter.setVelocity(1200);
                robot.rightShooter.setVelocity(1200);
            } else if (gamepad2.y) {
                robot.leftShooter.setVelocity(900);
                robot.rightShooter.setVelocity(900);
            } else if (gamepad2.a) {
                robot.leftShooter.setVelocity(1000);
                robot.rightShooter.setVelocity(1000);
            }

            else if(gamepad2.right_bumper){
                robot.leftShooter.setVelocity(+900);
                robot.rightShooter.setVelocity(-900);
            }
            else {
                robot.leftShooter.setPower(0);
                robot.rightShooter.setPower(0);
            }

            telemetry.addData("Current Velocity", "%.2f", robot.leftShooter.getVelocity());

        }
    }


    // class specific method that aren't in constants file go here
    public void exampleMethod(double power) {

        robot.DriveLeftFront.setPower(power);
        robot.DriveRightFront.setPower(-power);
        robot.DriveLeftBack.setPower(-power);
        robot.DriveRightBack.setPower(power);
    }

    public void moveLeft(double power) {
        robot.DriveLeftFront.setPower(power);
        robot.DriveRightFront.setPower(-power);
        robot.DriveLeftBack.setPower(-power);
        robot.DriveRightBack.setPower(power);
    }

    //
    public void moveRight(double power) {
        // Left Wheels                         //Right Wheels
        robot.DriveLeftFront.setPower(-power);
        robot.DriveRightFront.setPower(power);
        robot.DriveLeftBack.setPower(power);
        robot.DriveRightBack.setPower(-power);
    }

    public void moveForward(double power) {
        // Left Wheels                         //Right Wheels
        robot.DriveLeftFront.setPower(power);
        robot.DriveRightFront.setPower(power);
        robot.DriveLeftBack.setPower(power);
        robot.DriveRightBack.setPower(power);
    }

    public void moveBackward(double power) {
        // Left Wheels                         //Right Wheels
        robot.DriveLeftFront.setPower(-power);
        robot.DriveRightFront.setPower(-power);
        robot.DriveLeftBack.setPower(-power);
        robot.DriveRightBack.setPower(-power);
    }

    public void stopDriveTrainMotors() {
        // Left Wheels                         //Right Wheels
        robot.DriveLeftFront.setPower(0);
        robot.DriveRightFront.setPower(0);
        robot.DriveRightBack.setPower(0);
        robot.DriveLeftBack.setPower(0);

    }
}
