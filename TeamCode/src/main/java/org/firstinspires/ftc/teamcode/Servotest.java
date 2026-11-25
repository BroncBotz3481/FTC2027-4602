package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Servotest", group= "Opmode")

public class Servotest extends OpMode {

    Team4602HM2027 robot = new Team4602HM2027();

    @Override
    public void init(){
        robot.Map(hardwareMap);

    }



    @Override
    public void loop(){

        if(gamepad1.a){
            robot.gate.setPosition(0);
        }
        else if(gamepad1.b){
            robot.gate.setPosition(0.2);
        }
        else if(gamepad1.y){
            robot.gate.setPosition(0.4);
        }
        else if(gamepad1.x){
            robot.gate.setPosition(0.6);
        }
        else if(gamepad1.dpad_up){
            robot.gate.setPosition(0.8);
        }
        else if(gamepad1.right_trigger > 0.3){
            robot.gate.setPosition(1);
        }




    }

}
