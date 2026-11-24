package org.firstinspires.ftc.teamcode;

public class findShooterVelocity {
    public final int hub1RedXPos = 58;
    public final int hub1RedYPos = 61;
    public final int hub2RedXPos = 58;
    public final int hub2RedYPos = 82;

    public final  int hub1BlueXPos = 84;
    public final int hub1BlueYPos = 61;

    public final int hub2BlueXPos = 84;
    public final int hub2BlueYPos = 82;
    public double velocityCalc(double xPos, double yPos, boolean isRed, boolean hub1){
        int hubPositionX;
        int hubPositionY;
        double distance_velocity;
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
        double distance = Math.sqrt((hubPositionX - xPos) * (hubPositionX - xPos) + (hubPositionY - yPos) * (hubPositionY - yPos));

        distance_velocity = Math.pow(distance,3) + Math.pow(distance,2) + distance + 900;
        return distance_velocity;
    }
}
