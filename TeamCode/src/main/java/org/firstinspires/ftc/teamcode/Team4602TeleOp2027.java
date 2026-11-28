
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

    double turretD = 0.00073;

    double turretP = 0.091;

    int turretTargetPosition = 0;

    double turretLastError = 0;

    double turretError = 0;

    int turretTolerance = 2;

    double shooterkS = 0.0865;

    double shooterkV = 0.000314;
    double shooterP = 0.00218;

    int regressionVelocity = 1020;

    boolean manualMode = false;

    ElapsedTime timer = new ElapsedTime();



    public void runOpMode() {

        robot.Map(hardwareMap);

        telemetry.update();

        robot.DriveRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveRightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.DriveLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveLeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.DriveRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveRightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.DriveLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveLeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.Turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        waitForStart();


        timer.reset();
        robot.pinpoint.setPosition(new Pose2D(DistanceUnit.INCH,8.375,8.25,AngleUnit.DEGREES,-90));

        boolean isHub1 = true;

        boolean isRed = true;

        AutoAimNoOffsets autoAim = new AutoAimNoOffsets(robot);

        while (opModeIsActive()) {
            //robot.Turret.setPower(0.4);

            robot.pinpoint.update();

            double xPos = robot.pinpoint.getPosX(DistanceUnit.INCH);
            double yPos = robot.pinpoint.getPosY(DistanceUnit.INCH);
            double heading = robot.pinpoint.getHeading(AngleUnit.DEGREES);

            telemetry.addData("Pos", "(" + xPos + ", " + yPos + "," + heading + ")");
            telemetry.addData("Heading", "%.1f", heading);


            double y = gamepad1.left_stick_y; // Remember, this is reversed!
            double x = -gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            double turretY = gamepad2.left_stick_x;


            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.5);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;


            robot.DriveLeftFront.setPower(frontLeftPower);
            robot.DriveLeftBack.setPower(backLeftPower);
            robot.DriveRightFront.setPower(frontRightPower);
            robot.DriveRightBack.setPower(backRightPower);

            if (gamepad2.left_trigger > 0.2) {
                robot.gate.setPosition(0.2);
                robot.IntakeTransfer.setPower(0.98);
            } else {
                robot.gate.setPosition(0);
                if (gamepad1.right_trigger > 0.2) {
                    robot.IntakeTransfer.setPower(0.98);
                } else if (gamepad1.right_bumper) {
                    robot.IntakeTransfer.setPower(-0.98);
                } else {
                    robot.IntakeTransfer.setPower(0);
                }
            }

            if(gamepad2.dpadRightWasPressed()){
                regressionVelocity +=10;
            }
            else if(gamepad2.dpadLeftWasPressed()){
                regressionVelocity-=10;
            }

            if (gamepad2.right_trigger > 0.3) {
                setShooterPower(regressionVelocity);
            }
            else{
                robot.leftShooter.setPower(0);
                robot.rightShooter.setPower(0);
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

            if(gamepad1.a){
                manualMode = !manualMode;
            }

            turretTargetPosition = autoAim.TurretAim(xPos,yPos,isHub1,isRed);

            turretError = turretTargetPosition - robot.Turret.getCurrentPosition();

            if(!manualMode) {
                setTurretPower(turretError, turretLastError);
            }

            telemetry.addData("Current Velocity", "%.2f", robot.leftShooter.getVelocity());
            telemetry.addData("TargetVelocity", "%d", regressionVelocity);

            //robot.Turret.setTargetPosition(0);

            turretLastError = turretError;

            timer.reset();
            telemetry.addData("Target Position", "%d", autoAim.TurretAim(xPos,yPos,isHub1,isRed));
            telemetry.addData("Current Position", "%d", robot.Turret.getCurrentPosition());
            telemetry.update();

        }

    }

    public void setTurretPower(double error, double lastError){
        if(Math.abs(error) <= turretTolerance){
            robot.Turret.setPower(0);
        }else{
            double dt = timer.seconds();
            robot.Turret.setPower((turretP * error) + (turretD * ((error-lastError)/dt)));
        }
    }

    public void setShooterPower(double goalVelocity){
        double shooterError = goalVelocity - robot.leftShooter.getVelocity();
        robot.leftShooter.setPower((shooterkV * goalVelocity) + (shooterP * shooterError) + shooterkS);
        robot.rightShooter.setPower((shooterkV * goalVelocity) + (shooterP * shooterError) + shooterkS);

    }
}
