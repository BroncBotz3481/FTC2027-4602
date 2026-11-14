package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "AutoAimTestIterative", group = "4602")
public class AutoAim extends OpMode {

    // Add boundaries for how far the turret can go
    Team4602HM2027 robot = new Team4602HM2027();
    final int TICKS_PER_REV = 2000;
    final double POD_DIAMETER = 1.8898;
    final double POD_CIRCUMFERENCE = POD_DIAMETER * Math.PI;
    final double maxPower = 0.4;
    final double numTicsTurret = 383.6;

    public double xPos = 0;
    public double yPos = 0;

    // NEED TO FIND THESE VALUES
    public final double xOffset = 7;
    public final double yOffset = 4;
    public final  int hub1RedXPos = 58;
    public final int hub1RedYPos = 61;

    public final int hub2RedXPos = 58;
    public final int hub2RedYPos = 82;

    public final  int hub1BlueXPos = 84;
    public final int hub1BlueYPos = 61;

    public final int hub2BlueXPos = 84;
    public final int hub2BlueYPos = 82;

    // NEED TO CHANGE THESE NUMBERS LATER!!!
    public final int minTurn = -100;
    public final int maxTurn = 100;

    public final boolean isHub1 = true;
    boolean isRed = true;

    @Override
    public void init() {
        robot.Map(hardwareMap);

    }

    @Override
    public void loop() {
        updatePosition();

        int targetTicks = findTurretAngle(xPos,yPos);

        updateTurretPosition(targetTicks);
    }

    public void updatePosition() {
        double currentXInTicks = robot.DriveLeftFront.getCurrentPosition();
        double currentYInTicks = robot.DriveRightBack.getCurrentPosition();

        xPos = ((currentXInTicks / TICKS_PER_REV) * POD_CIRCUMFERENCE) + xOffset;
        yPos = ((currentYInTicks / TICKS_PER_REV) * POD_CIRCUMFERENCE) + yOffset;
    }

    public int findTurretAngle(double posX , double posY){
        double angleToHub = 0;

        if(isHub1) {
            angleToHub = Math.toDegrees(Math.atan2(hub1RedYPos - yPos, hub1RedXPos - xPos));
        }
        else{
            angleToHub = Math.toDegrees(Math.atan2(hub2RedYPos - yPos, hub2RedXPos - xPos));
        }

        double relativeAngle = (angleToHub - getHeading());

        changeAngle(relativeAngle);

        if(relativeAngle > minTurn && relativeAngle < maxTurn){
            return (int) Math.round(relativeAngle);
        }
        else{
            telemetry.addLine("OUT OF RANGE");
            return 0;
        }
    }

    public void updateTurretPosition(int targetTicks) {
        robot.Turret.setTargetPosition(targetTicks);
    }

    int angleToTicks(double angle) {
        return (int) Math.round(angle * numTicsTurret / 360);
    }

    public double getHeading(){
        return robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }


    //this makes it so the angle is always between 0 - +- 180 degrees so it goes the shortest path
    public double changeAngle(double relativeAngle){
        if(relativeAngle > 180){
            relativeAngle = relativeAngle - 360;
        }
        else if(relativeAngle < -180){
            relativeAngle = relativeAngle + 360;
        }
        return relativeAngle;
    }
}
