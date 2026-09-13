package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.pedro.procedures.ForesightTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.Tests;
import org.firstinspires.ftc.teamcode.pedro.procedures.TwoWheelTuner;

public class Tuning {
    @Tuner
    public static Procedure mecanumTuner() { //Use to autotune on http://192.168.43.1:10158 --> Mecanum Procedures
        return new MecanumTuner();
    }
    @Tuner
    public static Procedure tests() { //Test Driving + Tuning everything else
        return new Tests(hardwareMap -> new Mecanum(hardwareMap, PedroConstants.driveConfig), (hardwareMap -> new PinpointLocalizer(hardwareMap, PedroConstants.localizerConfig)), () -> new Foresight(PedroConstants.foresightConfig));
    }
    @Tuner
    public static Procedure twoWheelTuner() {
        return new TwoWheelTuner();
    }

    @Tuner
    public static Procedure foresightTuner() {//Localizer constants will be created after localization is finalized. Tunes foresight configs
        return new ForesightTuner((hardwareMap) -> new PinpointLocalizer(hardwareMap, PedroConstants.localizerConfig), (hardwareMap) -> new Mecanum(hardwareMap, PedroConstants.driveConfig));
    }


}
