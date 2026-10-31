package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "AutoAimTestIterative", group = "4602")
public class AutoAim extends OpMode {

    // Add boundaries for how far the turret can go
    Team4602HM2027 robot = new Team4602HM2027();
    final int TICKS_PER_REV = 2000;
    final double POD_DIAMETER = 1.8898;
    final double POD_CIRCUMFERENCE = POD_DIAMETER * Math.PI;
    final double maxPower = 0.4;
    final double numTics = 383.6;

    public double xPos = 0;
    public double yPos = 0;
    public final  int hub1XPos = 60;
    public final int hub1YPos = 72;

    public final int hub2XPos = 96;
    public final int hub2YPos = 72;

    //--------- PID Tuning ---------

    double kP = 0.0002;
    double error = 0;
    double lastError = 0;
    double goalX = 0;
    double angleTolerance = 1;
    double MAX_POWER = 0.6;
    private double power = 0;

    double kD = 0;
    double curTime = 0;
    double lastTime = 0;
    double [] stepSizes = {1.0,0.1,0.001,0.0001};
    int stepIndex = 2;


    @Override
    public void init() {
        robot.Map(hardwareMap);
    }

    public void start(){
        resetRuntime();
        curTime = getRuntime();
    }

    @Override
    public void loop() {
        updatePosition();

        double distanceToHub = Math.hypot(Math.abs(hub1XPos - xPos), Math.abs(hub1YPos - yPos));
        double angleToHub = Math.toDegrees(Math.atan2(hub1YPos - yPos, hub1XPos - xPos));

        int targetTicks = (int) Math.round(angleToHub * (numTics / 360));

        updateTurretPosition(targetTicks);

        telemetry.addData("Current X (in)", "%.2f", xPos);
        telemetry.addData("Current Y (in)", "%.2f", yPos);
        telemetry.addData("Distance to Hub", "%.2f in", distanceToHub);
        telemetry.addData("Angle to Hub", "%.2f deg", angleToHub);
    }

    public void updatePosition() {
        double currentXInTicks = robot.DriveLeftFront.getCurrentPosition();
        double currentYInTicks = robot.DriveRightBack.getCurrentPosition();

        xPos = (currentXInTicks / TICKS_PER_REV) * POD_CIRCUMFERENCE;
        yPos = (currentYInTicks / TICKS_PER_REV) * POD_CIRCUMFERENCE;
    }

    public void updateTurretPosition(int targetTicks) {
        robot.Turret.setTargetPosition(targetTicks);
    }
}
