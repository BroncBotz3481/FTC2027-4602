package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("DriveLeftFront");
        c.frontRightName.set("DriveRightFront");
        c.backLeftName.set("DriveLeftBack");
        c.backRightName.set("DriveRightBack");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        c.xPodOffset.set(6.060932189460814);
        c.yPodOffset.set(-0.3171014785766602);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.5044083507278121);
                Controller secondaryTranslationalForward = Controller.proportional(0.18636540755456654);
                Controller primaryTranslationalLateral = Controller.proportional(1.1617649303526814);
                Controller secondaryTranslationalLateral = Controller.proportional(0.429241098834651);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.015379983558595463));
                c.brake.set(Controller.proportionalFeedforward(0.013072986024806143));

                c.headingFeedback.set(Controller.proportional(5.944320986811944));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04830253621640971, 0.010617577427131018));

                c.linearBrakeCoefficients.set(Matrix.diag(0.12183218257158432, 0.07126314102798707));
                c.quadraticBrakeCoefficients.set(Matrix.diag(9.888105998368188E-4, 0.0016703366249623173));

                c.maxAchievableForwardVelocity.set(68.06092378019312);
                c.maxAchievableStrafeVelocity.set(50.669322306191916);
                c.naturalForwardDeceleration.set(48.78277846765327);
                c.naturalStrafeDeceleration.set(77.0792145978429);
            }
    );

    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}