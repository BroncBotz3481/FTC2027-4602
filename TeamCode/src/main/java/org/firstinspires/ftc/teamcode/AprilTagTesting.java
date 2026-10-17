package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AprilTagTesting {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    private List<AprilTagDetection> detectedTags = new ArrayList<>();

    private Telemetry telemetry;

    public void init(HardwareMap hardwareMap, Telemetry telemetry) {

        this.telemetry = telemetry;

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam"));
        builder.setCameraResolution(new Size(640, 480));
        // can add multiple processors if needed
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();

    }

    public void update() {
        detectedTags = aprilTagProcessor.getDetections();
    }

    @SuppressWarnings("unused")
    public List<AprilTagDetection> getDetectedTags() {
        return detectedTags;
    }

    public void displayDetectionTelemetry(AprilTagDetection detectedId) {
        if (detectedId == null) {
            return;
        }
        if (detectedId instanceof AprilTagSingleDetection) {
            AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detectedId;
            if (singleDet.metadata != null) {
                telemetry.addLine(String.format(Locale.US, "\n==== (ID %d) %s", singleDet.id, singleDet.metadata.name));
                if (singleDet.ftcPose != null) {
                    telemetry.addLine(String.format(Locale.US, "XYZ %6.1f %6.1f %6.1f  (inch)", singleDet.ftcPose.x, singleDet.ftcPose.y, singleDet.ftcPose.z));
                    telemetry.addLine(String.format(Locale.US, "PRY %6.1f %6.1f %6.1f  (deg)", singleDet.ftcPose.pitch, singleDet.ftcPose.roll, singleDet.ftcPose.yaw));
                    telemetry.addLine(String.format(Locale.US, "RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", singleDet.ftcPose.range, singleDet.ftcPose.bearing, singleDet.ftcPose.elevation));
                }
            } else {
                telemetry.addLine(String.format(Locale.US, "\n==== (ID %d) Unknown", singleDet.id));
                if (singleDet.center != null) {
                    telemetry.addLine(String.format(Locale.US, "Center %6.0f %6.0f   (pixels)", singleDet.center.x, singleDet.center.y));
                }
            }
        }
    }

    public AprilTagDetection getTagBySpecificId(int id) {
        for (AprilTagDetection detection : detectedTags) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;
                if (singleDet.id == id) {
                    return singleDet;
                }
            }
        }
        return null;
    }

    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
