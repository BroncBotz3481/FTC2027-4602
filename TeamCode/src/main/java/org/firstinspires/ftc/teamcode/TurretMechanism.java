package org.firstinspires.ftc.teamcode;

public class TurretMechanism {
    double kP = 0.0001;
    double error = 0;
    double lastError = 0;
    double goalX = 0;
    double angleTolerance = 1;
    double MAX_POWER = 0.6;
    private double power = 0;

    double kD = 0;
    double curTime = 0;
    double lastTime = 0;
    double[] stepSizes = {1.0, 0.1, 0.001, 0.0001};
    int stepIndex = 2;

    public void setKp(double newKp) {
        kP = newKp;
    }

    public double getKp(double newkP) {
        return kP;
    }

    public void setKD(double newKD) {
        kD = newKD;
    }

    public double getKD(double newKD) {
        return kD;
    }

}



