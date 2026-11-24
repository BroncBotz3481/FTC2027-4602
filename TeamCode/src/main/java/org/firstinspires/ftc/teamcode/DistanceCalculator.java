package org.firstinspires.ftc.teamcode;

public class DistanceCalculator {
    public final int hub1RedXPos = 58;
    public final int hub1RedYPos = 61;
    public final int hub2RedXPos = 58;
    public final int hub2RedYPos = 82;

    public final  int hub1BlueXPos = 84;
    public final int hub1BlueYPos = 61;

    public final int hub2BlueXPos = 84;
    public final int hub2BlueYPos = 82;
    public int velocityCalc(double xPos, double yPos, boolean isRed, boolean hub1){
        int hubPositionX;
        int hubPositionY;
        int distance_velocity;
        if (isRed && hub1) {
             hubPositionX = hub1RedXPos;
             hubPositionY = hub1RedYPos;
        } else if (isRed && !hub1) {
             hubPositionX = hub2RedXPos;
             hubPositionY = hub2RedYPos;
        } else if (!isRed && hub1) {
             hubPositionX = hub1BlueXPos;
             hubPositionY = hub1BlueYPos;
        } else {
             hubPositionX = hub2BlueXPos;
             hubPositionY = hub2BlueYPos;
        }
        double robotDistanceCalc = Math.sqrt((hubPositionX - xPos) * (hubPositionX - xPos) + (hubPositionY - yPos) * (hubPositionY - yPos));
        if (robotDistanceCalc <= 40) {
            distance_velocity = 1000;
        }
        else if (robotDistanceCalc <= 60) {
            distance_velocity = 1150;
        }
        else if (robotDistanceCalc <= 75) {
            distance_velocity = 1350;
        }
        else {
            distance_velocity = 1350; // Just to be safe if it's a little above 75
        }
        return distance_velocity;
    }
}
