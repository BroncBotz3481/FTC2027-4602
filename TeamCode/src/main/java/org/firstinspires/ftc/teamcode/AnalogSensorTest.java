package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

public class AnalogSensorTest {

    Team4602HM2027 robot = new Team4602HM2027();
    @TeleOp(name = "laserAnalogExample")

    public class laserAnalogExample extends LinearOpMode {



        // Sensor scale: 3.3V corresponds to 1000 mm
        private static final double MAX_VOLTS = 3.3;
        private static final double MAX_DISTANCE_MM = 1000.0;

        @Override
        public void runOpMode() {
            // Map the analog device from the hardware configuration


            // Wait for PLAY
            waitForStart();

            // Main loop
            while (opModeIsActive()) {
                // Read sensor voltage (0.0–3.3V)
                double volts = robot.DistanceSensor.getVoltage();

                // Convert voltage to distance in millimeters (linear mapping)
                double distanceMM = (volts / MAX_VOLTS) * MAX_DISTANCE_MM;

                // Telemetry
                telemetry.addData("Voltage (V)", "%.3f", volts);
                telemetry.addData("Distance (mm)", "%.1f", distanceMM);
                telemetry.update();
            }
        }
    }
}
