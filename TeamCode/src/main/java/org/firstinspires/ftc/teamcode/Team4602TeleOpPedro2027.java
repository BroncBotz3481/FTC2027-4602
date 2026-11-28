package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Team4602TeleOpPedro2027")
public class Team4602TeleOpPedro2027 extends OpMode {

    private Follower follower;
    Team4602HM2027 robot = new Team4602HM2027();
    private AutoAimNoOffsets autoAim;
    double turretD = 0.00098;
    double turretP = 0.091;
    int turretTargetPosition = 0;
    double turretLastError = 0;
    double turretError = 0;
    int turretTolerance = 2;

    int regressionVelocity = 1020;

    boolean manualMode = true;
    boolean isHub1 = true;
    boolean isRed = true;

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        robot.Map(hardwareMap);
        follower = Constants.create(hardwareMap);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(0.4, 0, 0, 12.5);
        robot.leftShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        robot.rightShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        autoAim = new AutoAimNoOffsets(robot);
    }

    @Override
    public void start(){
        if (OpModeStorage.autonomousEndPose != null) {
            follower.setPose(OpModeStorage.autonomousEndPose);
        }
        follower.update();
        //pinpoint.update
        //No need for pinpoint.update because follower gets that data
        timer.reset();
    }

    @Override
    public void loop() {
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x,
                follower.pose().heading()
        );

        follower.manual(powers);

        if(gamepad2.dpadDownWasPressed()){
            follower.setPose(new Pose(8.375, 8.25, 0));
            //robot.pinpoint.setPosition(new Pose2D(DistanceUnit.INCH,8.375,8.25, AngleUnit.DEGREES ,0));
        }
        if(gamepad2.dpadUpWasPressed()){
            follower.setPose(new Pose(8.375,135.5,Math.toRadians(180)));
            //robot.pinpoint.setPosition(new Pose2D(DistanceUnit.INCH,8.375,135.5,AngleUnit.DEGREES ,180));
        }

//        if (gamepad1.aWasPressed()) {
//            Pose cornerPose = new Pose(10.5, 10.5, Math.toRadians(90));
//            // On the fly Pose creation, we dont recommend this for Autonomous. Only accepts radians for heading
//            follower.setPose(cornerPose); // overrides our pose
//        } //Pick a button

        follower.update();
        Pose robotPose = follower.pose(); // returns a Pose object
        double xPos = follower.pose().x();
        double yPos = follower.pose().y();

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
            robot.leftShooter.setVelocity(regressionVelocity);
            robot.rightShooter.setVelocity(regressionVelocity);
        }
        else{
            robot.leftShooter.setVelocity(0);
            robot.rightShooter.setVelocity(0);
        }


        if(gamepad2.xWasPressed()){
            isHub1 = !isHub1;
        }
        if(gamepad2.yWasPressed()){
            isRed = !isRed;
        }

        if(gamepad1.aWasPressed()){
            manualMode = !manualMode;
        }

        turretTargetPosition = autoAim.TurretAim(xPos,yPos,isHub1,isRed);

        turretError = turretTargetPosition - robot.Turret.getCurrentPosition();

        if(!manualMode) {
            setTurretPower(turretError, turretLastError);
        }

        turretLastError = turretError;

        timer.reset();

        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));

        // Math.toDegrees() is a built-in java method
    }

    // in your autonomous

    @Override
    public void stop() {
        OpModeStorage.autonomousEndPose = follower.pose(); //saves your position in that file
    }

    public void setTurretPower(double error, double lastError){
        if(Math.abs(error) <= turretTolerance){
            robot.Turret.setPower(0);
        }else{
            double dt = timer.seconds();
            if (dt > 0) {
                robot.Turret.setPower((turretP * error) + (turretD * ((error - lastError) / dt)));
            }
        }
    }
}