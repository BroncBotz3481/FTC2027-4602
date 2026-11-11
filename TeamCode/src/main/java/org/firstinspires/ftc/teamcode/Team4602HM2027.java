package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class Team4602HM2027 {
    public DcMotorEx DriveRightBack = null;
    public DcMotorEx DriveLeftBack = null;
    public DcMotorEx DriveLeftFront = null;
    public DcMotorEx DriveRightFront = null;
    public WebcamName camera = null;
    public DcMotorEx Turret = null;
    public DcMotorEx rightShooter = null;
    public DcMotorEx leftShooter = null;
    public AnalogInput DistanceSensor = null;
    public DcMotorEx IntakeTransfer = null;
    public Servo IntakeArm = null;




    HardwareMap hwMap = null;
    public void Map(HardwareMap hardwareMap)
    {
        hwMap = hardwareMap;
        //plug x pod into this wheel
        DriveLeftFront = hwMap.get(DcMotorEx.class,"DriveLeftFront");
        //plug y pod into this wheel
        DriveRightFront = hwMap.get(DcMotorEx.class,"DriveRightFront");

        DriveLeftBack = hwMap.get(DcMotorEx.class,"DriveLeftBack");
        DriveRightBack = hwMap.get(DcMotorEx.class,"DriveRightBack");
        Turret = hwMap.get(DcMotorEx.class, "Turret");
        //camera = hwMap.get(WebcamName.class, "Webcam");
        //Lights = hwMap.get(RevBlinkinLedDriver.class,"Lights");*/
        rightShooter = hwMap.get(DcMotorEx.class, "rightShooter");
        leftShooter = hwMap.get(DcMotorEx.class, "leftShooter");
        //DistanceSensor = hardwareMap.get(AnalogInput.class, "DistanceSensor");
        IntakeTransfer = hwMap.get(DcMotorEx.class, "IntakeTransfer");
        IntakeArm = hwMap.get(Servo.class, "IntakeArm");

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

        rightShooter.setDirection(DcMotorEx.Direction.REVERSE);
        rightShooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightShooter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightShooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        leftShooter.setDirection(DcMotorEx.Direction.FORWARD);
        leftShooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftShooter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftShooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        IntakeTransfer.setDirection(DcMotorEx.Direction.REVERSE);
        IntakeTransfer.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


    }
}
 