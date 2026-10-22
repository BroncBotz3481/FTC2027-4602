package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class Team4602HM2027 {
    public DcMotorEx DriveRightBack = null;
    public DcMotorEx DriveLeftBack = null;
    public DcMotorEx DriveLeftFront = null;
    public DcMotorEx DriveRightFront = null;
    public WebcamName camera = null;
    public DcMotorEx Turret = null;
    public DcMotorEx frontShooter = null;
    public DcMotorEx backShooter = null;






    HardwareMap hwMap = null;
    public void Map(HardwareMap hardwareMap)
    {
        hwMap = hardwareMap;
        // plug x pod into this wheel
        DriveLeftFront = hwMap.get(DcMotorEx.class,"DriveLeftFront");
        //plug y pod into this wheel
        DriveRightFront = hwMap.get(DcMotorEx.class,"DriveRightFront");

        DriveLeftBack = hwMap.get(DcMotorEx.class,"DriveLeftBack");
        DriveRightBack = hwMap.get(DcMotorEx.class,"DriveRightBack");
        Turret = hwMap.get(DcMotorEx.class, "Turret");
        camera = hwMap.get(WebcamName.class, "Webcam");
        //   Lights = hwMap.get(RevBlinkinLedDriver.class,"Lights");
        frontShooter = hwMap.get(DcMotorEx.class, "frontShooter");
        backShooter = hwMap.get(DcMotorEx.class, "backShooter");

        DriveLeftFront.setDirection(DcMotorEx.Direction.FORWARD);
        DriveLeftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        DriveLeftFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        /* If you are using encoders, set to RUN_USING_ENCODER.
         If you put RUN_USING_ENCODERS and there is no encoder value getting
         recieved then the motor power is set to 1.0 even if you put a different
         value */
        DriveLeftFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        DriveRightFront.setDirection(DcMotorEx.Direction.REVERSE);
        DriveRightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        DriveRightFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        DriveRightFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        DriveLeftBack.setDirection(DcMotorEx.Direction.FORWARD);
        DriveLeftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        DriveLeftBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        DriveLeftBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        DriveRightBack.setDirection(DcMotorEx.Direction.REVERSE);
        DriveRightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        DriveRightBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        DriveRightBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        Turret.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        Turret.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Turret.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        frontShooter.setDirection(DcMotorEx.Direction.FORWARD);
        frontShooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontShooter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontShooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        backShooter.setDirection(DcMotorEx.Direction.REVERSE);
        backShooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backShooter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backShooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

    }
}
 