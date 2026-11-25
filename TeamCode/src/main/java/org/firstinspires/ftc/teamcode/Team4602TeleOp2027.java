
package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;


@TeleOp (name = "Team4602TeleOp2026", group = "4602")
public class Team4602TeleOp2027 extends LinearOpMode {


    Team4602HM2027 robot = new Team4602HM2027();

    double turretD = 0.00098;

    double turretP = 0.091;

    int turretTargetPosition = 0;

    double turretLastError = 0;

    double turretError = 0;

    int turretTolerance = 1;

    int regressionVelocity = 1020;

    ElapsedTime timer = new ElapsedTime();



    public void runOpMode() {

        robot.Map(hardwareMap);

        telemetry.update();


        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(49.5, 0, 0, 13.7);

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

        timer.reset();

        boolean isHub1 = true;

        boolean isRed = true;

        AutoAimNoOffsets autoAim = new AutoAimNoOffsets(robot);

        while (opModeIsActive()) {
            //robot.Turret.setPower(0.4);

            robot.pinpoint.update();

            double xPos = robot.pinpoint.getPosX(DistanceUnit.INCH);
            double yPos = robot.pinpoint.getPosY(DistanceUnit.INCH);

            telemetry.addData("Pos", "(" + xPos + ", " + yPos + ")");


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


            robot.DriveLeftFront.setPower(frontLeftPower);
            robot.DriveLeftBack.setPower(backLeftPower);
            robot.DriveRightFront.setPower(frontRightPower);
            robot.DriveRightBack.setPower(backRightPower);


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

            if(gamepad2.dpadRightWasPressed()){
                regressionVelocity +=10;
            }
            else if(gamepad2.dpadLeftWasPressed()){
                regressionVelocity-=10;
            }

            if (gamepad2.right_trigger > 0.3) {
                robot.leftShooter.setVelocity(regressionVelocity);
                robot.rightShooter.setVelocity(regressionVelocity);
            }
            else{
                robot.leftShooter.setVelocity(0);
                robot.rightShooter.setVelocity(0);
            }


            if(gamepad2.dpadDownWasPressed()){
                robot.pinpoint.setPosition(new Pose2D(DistanceUnit.INCH,8.375,8.25,AngleUnit.DEGREES ,0));
            }
            if(gamepad2.dpadUpWasPressed()){
                robot.pinpoint.setPosition(new Pose2D(DistanceUnit.INCH,8.375,135.5,AngleUnit.DEGREES ,180));
            }

            if(gamepad2.xWasPressed()){
                isHub1 = !isHub1;
            }
            if(gamepad2.yWasPressed()){
                isRed = !isRed;
            }

            turretTargetPosition = autoAim.TurretAim(xPos,yPos,isHub1,isRed);

            turretError = turretTargetPosition - robot.Turret.getCurrentPosition();

            setTurretPower(turretError, turretLastError);

            telemetry.addData("Target Position", "%d", autoAim.TurretAim(xPos,yPos,isHub1,isRed));

            telemetry.addData("Current Velocity", "%.2f", robot.leftShooter.getVelocity());
            telemetry.addData("TargetVelocity", "%d", regressionVelocity);
            telemetry.update();

            //robot.Turret.setTargetPosition(0);

            turretLastError = turretError;

            timer.reset();

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

    public void setTurretPower(double error, double lastError){
        if(error <= turretTolerance){
            robot.Turret.setPower(0);
        }else{
            double dt = timer.seconds();
            robot.Turret.setPower((turretP * error) + (turretD * ((error-lastError)/dt)));
        }
    }
}
