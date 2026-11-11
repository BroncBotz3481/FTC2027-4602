

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Autonomous(name = "PreloadShootParkAuto", group = "4602")
public class PreloadShootParkAuto extends LinearOpMode {

    public void runOpMode() {
        robot.Map(hardwareMap);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(1.854, 0, 0, 13.923);

        waitForStart();
            robot.leftShooter.setVelocity(-1300);
            robot.rightShooter.setVelocity(1300);
            moveForward(0.3,200);
            sleep(4000);
            robot.IntakeTransfer.setPower(0.6);
            sleep(8000);
            robot.leftShooter.setPower(0);
            robot.rightShooter.setPower(0);
            robot.leftShooter.setPower(0);
            robot.rightShooter.setVelocity(0);
            robot.IntakeTransfer.setVelocity(0);
            moveRight(0.3,3500);
            moveForward(0.3,5000);

    }
    Team4602HM2027 robot = new Team4602HM2027();
    void moveRight ( double power,long time){
        // Left Wheels                         //Right Wheels
        robot.DriveLeftFront.setPower(-power);
        robot.DriveRightFront.setPower(power);
        robot.DriveLeftBack.setPower(power);
        robot.DriveRightBack.setPower(-power);
        sleep(time);
        robot.DriveLeftFront.setPower(0);
        robot.DriveRightFront.setPower(0);
        robot.DriveLeftBack.setPower(0);
        robot.DriveRightBack.setPower(0);
    }

    void moveLeft(double power, long time) {
        robot.DriveLeftFront.setPower(power);
        robot.DriveRightFront.setPower(-power);
        robot.DriveLeftBack.setPower(-power);
        robot.DriveRightBack.setPower(power);
        sleep(time);
        robot.DriveLeftFront.setPower(0);
        robot.DriveRightFront.setPower(0);
        robot.DriveLeftBack.setPower(0);
        robot.DriveRightBack.setPower(0);

    }

    void moveForward ( double power, long time){
        // Left Wheels                         //Right Wheels
        robot.DriveLeftFront.setPower(power);
        robot.DriveRightFront.setPower(power);
        robot.DriveLeftBack.setPower(power);
        robot.DriveRightBack.setPower(power);
        sleep(time);
        robot.DriveLeftFront.setPower(0);
        robot.DriveRightFront.setPower(0);
        robot.DriveLeftBack.setPower(0);
        robot.DriveRightBack.setPower(0);

    }

    void moveBackward ( double power,long time){
        // Left Wheels                         //Right Wheels
        robot.DriveLeftFront.setPower(-power);
        robot.DriveRightFront.setPower(-power);
        robot.DriveLeftBack.setPower(-power);
        robot.DriveRightBack.setPower(-power);
        sleep(time);
        robot.DriveLeftFront.setPower(0);
        robot.DriveRightFront.setPower(0);
        robot.DriveLeftBack.setPower(0);
        robot.DriveRightBack.setPower(0);
    }

    void stopDriveTrainMotors () {
        // Left Wheels                         //Right Wheels
        robot.DriveLeftFront.setPower(0);
        robot.DriveRightFront.setPower(0);
        robot.DriveRightBack.setPower(0);
        robot.DriveLeftBack.setPower(0);
    }
}


