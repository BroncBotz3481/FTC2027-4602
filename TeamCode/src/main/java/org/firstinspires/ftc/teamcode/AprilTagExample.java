package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous
public class AprilTagExample extends OpMode {
    AprilTagTesting aprilTagTesting = new AprilTagTesting();

    @Override
    public void init(){
        aprilTagTesting.init(hardwareMap,telemetry);
    }

    @Override
    public void loop(){
        aprilTagTesting.update();
        AprilTagDetection id20 = aprilTagTesting.getTagBySpecificId(20);
        aprilTagTesting.displayDetectionTelemetry(id20);
    }

}
