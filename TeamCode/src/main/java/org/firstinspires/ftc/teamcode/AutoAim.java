package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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
    public final  int hub1RedXPos = 60;
    public final int hub1RedYPos = 72;

    public final int hub2RedXPos = 96;
    public final int hub2RedYPos = 72;

    public final  int hub1BlueXPos = 60;
    public final int hub1BlueYPos = 72;

    public final int hub2BlueXPos = 96;
    public final int hub2BlueYPos = 72;

    public final boolean isHub1 = true;

    //--------- PID Tuning ---------

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

        xPos = (currentXInTicks / TICKS_PER_REV) * POD_CIRCUMFERENCE;
        yPos = (currentYInTicks / TICKS_PER_REV) * POD_CIRCUMFERENCE;
    }

    public int findTurretAngle(double posX , double posY){
        double distanceToHub = Math.hypot(Math.abs(hub1XPos - xPos), Math.abs(hub1YPos - yPos));
        double angleToHub = Math.toDegrees(Math.atan2(hub1YPos - yPos, hub1XPos - xPos));





        return
    }

    public void updateTurretPosition(int targetTicks) {
        robot.Turret.setTargetPosition(targetTicks);
        robot.Turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
