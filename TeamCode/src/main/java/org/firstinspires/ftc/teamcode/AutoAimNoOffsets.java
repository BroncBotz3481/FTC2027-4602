package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class AutoAimNoOffsets {
    Team4602HM2027 robot = new Team4602HM2027();
    final int TICKS_PER_REV = 2000;
    final double POD_DIAMETER = 1.8898;
    final double POD_CIRCUMFERENCE = POD_DIAMETER * Math.PI;
    final double maxPower = 0.4;
    final double numTicsTurret = 383.6;
    public final  int hub1RedXPos = 58;
    public final int hub1RedYPos = 61;

    public final int hub2RedXPos = 58;
    public final int hub2RedYPos = 82;

    public final  int hub1BlueXPos = 84;
    public final int hub1BlueYPos = 61;

    public final int hub2BlueXPos = 84;
    public final int hub2BlueYPos = 82;


    // NEED TO CHANGE THESE NUMBERS LATER!!!
    public final int minTurn = -110;
    public final int maxTurn = 110;

    public AutoAimNoOffsets(Team4602HM2027 robot){
        this.robot = robot;
    }

    public int TurretAim(double xPos, double yPos, boolean isHub1, boolean isRed) {

        int targetTicks = findTurretTicks(xPos,yPos,isHub1,isRed);

        return targetTicks;
    }

    public int findTurretTicks(double posX , double posY, boolean isHub1, boolean isRed){
        double angleToHub = 0;

        posY = posY + 3;

        if(isRed && isHub1) {
            angleToHub = Math.toDegrees(Math.atan2(hub1RedYPos - posY, hub1RedXPos - posX));
        }else if(isRed && !isHub1){
            angleToHub = Math.toDegrees(Math.atan2(hub2RedYPos - posY, hub2RedXPos - posX));
        }else if(!isRed && isHub1) {
            angleToHub = Math.toDegrees(Math.atan2(hub1BlueYPos - posY, hub1BlueXPos - posX));
        } else{
            angleToHub = Math.toDegrees(Math.atan2(hub2BlueYPos - posY, hub2BlueXPos - posX));
        }

        double relativeAngle = (angleToHub - getHeading()) + 180;

        relativeAngle = changeAngle(relativeAngle);

        //relativeAngle = -relativeAngle;

        if(relativeAngle > minTurn && relativeAngle < maxTurn){
            return angleToTicks(relativeAngle);
        }
        else{
            return robot.Turret.getCurrentPosition();
        }
    }
    int angleToTicks(double angle) {
        return (int) Math.round(angle * numTicsTurret / 360);
    }

    public double getHeading() {
        return robot.pinpoint.getHeading(AngleUnit.DEGREES);
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
