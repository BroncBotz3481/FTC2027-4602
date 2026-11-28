package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.pedro.Constants;


@Autonomous(name = "ScrimmageAuto", group = "Autonomous")
public class ScrimmageAuto extends LinearOpMode {

    private Follower follower;

    Team4602HM2027 robot = new Team4602HM2027();

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(58.8346, 8.0022, 270);
    private final Pose park = poseFactory.of(6.6119, 100.6387, 270);
    private final Pose parkControl1 = poseFactory.of(31.8932, 13.6566, 0);
    int regressionVelocity = 1020;
    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, park())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        robot.Map(hardwareMap);
        robot.leftShooter.setVelocity(regressionVelocity);
        robot.rightShooter.setVelocity(regressionVelocity);
        sleep(5000);
        robot.gate.setPosition(0.2);
        robot.IntakeTransfer.setPower(0.98);
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path park() {
        return Paths.curve(start, parkControl1, park).linear(start, park);
    }
}

