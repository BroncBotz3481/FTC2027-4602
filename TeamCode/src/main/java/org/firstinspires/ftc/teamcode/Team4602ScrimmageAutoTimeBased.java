package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name="Team4602ScrimmageAutoTimeBased", group="4602")
public class Team4602ScrimmageAutoTimeBased extends LinearOpMode {

    Team4602HM2027 robot = new Team4602HM2027();
    int regressionVelocity = 1020;
    ElapsedTime Time = new ElapsedTime();


    @Override
    public void runOpMode() {
        robot.Map(hardwareMap);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(0.4, 0, 0, 12.5);
        robot.leftShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        robot.rightShooter.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        sleep(3000);
        waitForStart();
        robot.leftShooter.setVelocity(regressionVelocity);
        robot.rightShooter.setVelocity(regressionVelocity);
        sleep(3000);
        robot.gate.setPosition(0.2);
        sleep(500);
        robot.IntakeTransfer.setPower(0.98);
        //moveForward(0.5,1000); // This powers all the motors to move the robot forward for 1000 milseconds
    }

    public void moveForward (double power, int time){
        robot.DriveRightFront.setPower(power);
        robot.DriveLeftFront.setPower(power);
        robot.DriveRightBack.setPower(power);
        robot.DriveLeftBack.setPower(power);
        sleep(time);
        robot.DriveRightFront.setPower(0);
        robot.DriveLeftFront.setPower(0);
        robot.DriveRightBack.setPower(0);
        robot.DriveLeftBack.setPower(0);
    }


}