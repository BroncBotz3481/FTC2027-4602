package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;
@TeleOp(name = "Team4602TeleOpPedro2027", group = "4602")
public class Team4602TeleOpPedro2027 extends OpMode {
    private Follower follower;
    private Team4602HM2027 robot = new Team4602HM2027();
    private AutoAimNoOffsets autoAim;

    // Turret PD & Shooter variables
    private double turretD = 0.00098;
    private double turretP = 0.091;
    private int turretTargetPosition = 0;
    private double turretLastError = 0;
    private double turretError = 0;
    private int turretTolerance = 1;
    private int regressionVelocity = 1020;

    // Alliance & Target state toggles
    private boolean isHub1 = true;
    private boolean isRed = true;

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        // 2. Configure Shooter PIDF
        robot.Map(hardwareMap);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(49.5, 0, 0, 13.7);
        robot.leftShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        robot.rightShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        // 3. Initialize AutoAim
        autoAim = new AutoAimNoOffsets(robot);

    }
    @Override
    public void loop() {
        DrivePowers powers = ManualDrive.fieldCentric(
                gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x,
                follower.pose().heading()
        );
        follower.manual(powers); //disable if adding line right below
        //ManualDrive.driveOrHold(follower, powers); //Adjusts when getting pushed
        if (gamepad1.a) {
            Pose cornerPose = new Pose(10.5, 10.5, Math.toRadians(90));
            // On the fly Pose creation, we don't recommend this
            // for Autonomous. Only accepts radians for heading
            follower.setPose(cornerPose); // overrides our pose
        }
        if (gamepad2.dpadDownWasPressed()) {
            follower.setPose(new Pose(8.375, 8.25, Math.toRadians(0)));
        }
        if (gamepad2.dpadUpWasPressed()) {
            follower.setPose(new Pose(8.375, 135.5, Math.toRadians(180)));
        }//Reset position
        follower.update();
        Pose robotPose = follower.pose();
        double xPos = robotPose.x();
        double yPos = robotPose.y();// returns a Pose object

        if (gamepad1.right_trigger > 0.2) {
            robot.IntakeTransfer.setPower(0.98);
        } else if (gamepad1.right_bumper) {
            robot.IntakeTransfer.setPower(-0.98);
        } else {
            robot.IntakeTransfer.setPower(0);
        }

        // --- 3. SHOOTER VELOCITY & FLYWHEELS (Gamepad 2) ---
        if (gamepad2.dpadRightWasPressed()) {
            regressionVelocity += 10;
        } else if (gamepad2.dpadLeftWasPressed()) {
            regressionVelocity -= 10;
        }
        if (gamepad2.right_trigger > 0.3) {
            robot.leftShooter.setVelocity(regressionVelocity);
            robot.rightShooter.setVelocity(regressionVelocity);
        } else {
            robot.leftShooter.setVelocity(0);
            robot.rightShooter.setVelocity(0);
        }

        // --- 4. TARGET & ALLIANCE TOGGLES (Gamepad 2) ---
        if (gamepad2.xWasPressed()) {
            isHub1 = !isHub1;
        }
        if (gamepad2.yWasPressed()) {
            isRed = !isRed;
        }

        turretTargetPosition = autoAim.TurretAim(xPos, yPos, isHub1, isRed);
        turretError = turretTargetPosition - robot.Turret.getCurrentPosition();

        setTurretPower(turretError, turretLastError);

        turretLastError = turretError;
        timer.reset();

        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));
        telemetry.addData("Turret Target", "%d", turretTargetPosition);
        telemetry.addData("Shooter Current Vel", "%.2f", robot.leftShooter.getVelocity());
        telemetry.addData("Shooter Target Vel", "%d", regressionVelocity);
    }

    @Override
    public void start(){
        follower.setPose(OpModeStorage.autonomousEndPose);
        follower.update();
        timer.reset();
    }
    public void setTurretPower(double error, double lastError) {
        double dt = timer.seconds();
        if (dt > 0) {
            robot.Turret.setPower((turretP * error) + (turretD * ((error - lastError) / dt)));
        }
    }
}
