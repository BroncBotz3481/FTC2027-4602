//package org.firstinspires.ftc.teamcode;
//
//import static com.pedropathing.api.Paths.*;
//import com.pedropathing.api.Paths;
//
//import com.pedropathing.api.PoseFactory;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.math.Pose;
//import com.pedropathing.paths.Path;
//import com.pedropathing.ivy.Command;
//import com.pedropathing.ivy.Scheduler;
//import static com.pedropathing.ivy.Scheduler.schedule;
//import static com.pedropathing.ivy.commands.Commands.*;
//import static com.pedropathing.ivy.groups.Groups.sequential;
//import static com.pedropathing.ivy.pedro.PedroCommands.follow;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//@Autonomous(name = "Team4602AutoRightRedDoubleShoot", group = "Autonomous")
//public class Team4602AutoRightRedDoubleShoot extends LinearOpMode {
//
//    private Follower follower;
//
//    private final PoseFactory poseFactory = PoseFactory.degrees();
//
//    private final Pose start = poseFactory.of(56, 8, 90);
//    private final Pose park = poseFactory.of(11.3713, 46.5982, 180);
//    private final Pose parkControl1 = poseFactory.of(41.7083, 34.7045, 0);
//    private final Pose point2 = poseFactory.of(25.8928, 128.654, 180);
//    private final Pose point2Control1 = poseFactory.of(36.6691, 114.0822, 0);
//    private final Pose point3 = poseFactory.of(8.3279, 99.0991, 180);
//    private final Pose point3Control1 = poseFactory.of(45.0107, 92.4403, 0);
//
//    // Autonomous routine
//    public Command autoRoutine() {
//        return sequential(
//                follow(follower, park()),
//                follow(follower, path2()),
//                follow(follower, path3())
//        );
//    }
//
//    @Override
//    public void runOpMode() {
//        Scheduler.reset();
//        follower = Constants.create(hardwareMap);
//        follower.setPose(start);
//        follower.update();
//
//        waitForStart();
//        schedule(autoRoutine());
//
//        while (opModeIsActive()) {
//            follower.update();
//            Scheduler.execute();
//
//            telemetry.addData("x", follower.pose().x());
//            telemetry.addData("y", follower.pose().y());
//            telemetry.addData("heading", follower.pose().heading());
//
//            if (follower.currentPath() != null) {
//                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
//                telemetry.addData("Path number", follower.pathIndex());
//            }
//
//            telemetry.update();
//        }
//    }
//
//    public Path park() {
//        return Paths.curve(start, parkControl1, park).linear(start, park);
//    }
//
//    public Path path2() {
//        return Paths.curve(park, point2Control1, point2).constant(point2);
//    }
//
//    public Path path3() {
//        return Paths.curve(point2, point3Control1, point3).constant(point3);
//    }
//}
//
