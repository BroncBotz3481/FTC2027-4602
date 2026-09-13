package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;

import com.qualcomm.robotcore.hardware.HardwareMap;

// the following constants are simply examples of possible constants and might not be used! this is is simply a proof of concept
public class Constants {

    public class AutoTimeBasedConstants {
        public static final String AutoName = "Team4602AutoTimeBased";
        public static final String group = "4602";
    }

    public class TeleopConstants {
        public static final double highPower = 0.8; // rough example 1
//        public static final highPower = 0.4; // rough example 2
    }

    public class movementMethods {
        Team4602HM2025 robot = new Team4602HM2025();
        HardwareMap hwMap = null;
        hwMap = hardwareMap;
        public void moveLeft(double power) {
            robot.DriveLeftFront.setPower(power);
            robot.DriveRightFront.setPower(-power);
            robot.DriveLeftBack.setPower(-power);
            robot.DriveRightBack.setPower(power);
        }
//
        public void moveRight(double power) {
            // Left Wheels                         //Right Wheels
            robot.DriveLeftFront.setPower(-power);
            robot.DriveRightFront.setPower(power);
            robot.DriveLeftBack.setPower(power);
            robot.DriveRightBack.setPower(-power);
        }

        public void moveForward(double power) {
            // Left Wheels                         //Right Wheels
            robot.DriveLeftFront.setPower(power);
            robot.DriveRightFront.setPower(power);
            robot.DriveLeftBack.setPower(power);
            robot.DriveRightBack.setPower(power);
        }

        public void moveBackward(double power) {
            // Left Wheels                         //Right Wheels
            robot.DriveLeftFront.setPower(-power);
            robot.DriveRightFront.setPower(-power);
            robot.DriveLeftBack.setPower(-power);
            robot.DriveRightBack.setPower(-power);
        }

        public void stopDriveTrainMotors() {
            // Left Wheels                         //Right Wheels
            robot.DriveLeftFront.setPower(0);
            robot.DriveRightFront.setPower(0);
            robot.DriveRightBack.setPower(0);
            robot.DriveLeftBack.setPower(0);

        }

    }
}
