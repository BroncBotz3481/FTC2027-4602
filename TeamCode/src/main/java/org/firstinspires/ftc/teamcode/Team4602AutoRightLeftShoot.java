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
//@Autonomous(name = "Team4602AutoRightLeftShoot", group = "Autonomous")
//public class Team4602AutoRightLeftShoot extends LinearOpMode {
//
//    private Follower follower;
//
//    private final PoseFactory poseFactory = PoseFactory.degrees();
//
//    private final Pose start = poseFactory.of(63.6646, 133.5812, 270);
//    private final Pose park = poseFactory.of(63.2546, 132.6774, 270);
//    private final Pose point2 = poseFactory.of(47.5561, 115.395, 270);
//    private final Pose point2Control1 = poseFactory.of(56.5894, 107.7743, 0);
//    private final Pose point3 = poseFactory.of(46.9456, 130.5927, 90);
//    private final Pose point4 = poseFactory.of(7.64, 115.3127, 90);
//    private final Pose point4Control1 = poseFactory.of(33.2411, 111.8232, 0);
//
//    // Autonomous routine
//    public Command autoRoutine() {
//        return sequential(
//                follow(follower, park()),
//                follow(follower, path2()),
//                follow(follower, path3()),
//                follow(follower, path4())
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
//        return Paths.line(start, park).linear(start, park);
//    }
//
//    public Path path2() {
//        return Paths.curve(park, point2Control1, point2).constant(point2);
//    }
//
//    public Path path3() {
//        return Paths.line(point2, point3).constant(point3);
//    }
//
//    public Path path4() {
//        return Paths.curve(point3, point4Control1, point4).constant(point4);
//    }
//}
//
