package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutoAim {

    // we assume the robot stars at the corner on our side away from the park

    Team4602HM2027 robot = new Team4602HM2027();
    int numTicksPerPod = 2000;
    double podDiameter = 1.8898;

    double getXPosInTicks = robot.DriveLeftFront.getCurrentPosition();
    double getYPosInTicks = robot.DriveRightBack.getCurrentPosition();

    // we need to add the offsets I just put random numbers
    double xPodOffset = 6;
    double yPodOffset = 6;




    public double xPos =  (getXPosInTicks/numTicksPerPod) *  podDiameter * Math.PI;
    public double yPos =  (getYPosInTicks/numTicksPerPod) *  podDiameter * Math.PI;

    public int hub1XPos = 60;
    public int hub1YPos = 72;
    public int hub2XPos = 96;



}
