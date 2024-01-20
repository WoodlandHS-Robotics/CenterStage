package org.firstinspires.ftc.teamcode.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

// todo modularize(?) things to stop crashing when stuff is unplugged
public class centerStageRobot extends baseRobot {
    private final Servo knockerServo;
    private final Servo PALServo;

    private final DcMotorEx grappleMotor;
    private final Servo grappleServo;

    private final OpenCvCamera frontCamera;
    private final OpenCvCamera sideCamera;
    private final CenterStageAprilTagModule mod;

    public centerStageRobot(LinearOpMode opmode) {
        super(opmode, 3.5, 13.75);
        this.knockerServo = setUpServo(constants.KNOCKER_SERVO_NAME);
        this.knockerServo.setDirection(Servo.Direction.FORWARD);
        this.grappleServo = setUpServo(constants.GRAPPLE_SERVO_NAME);
        this.PALServo = setUpServo(constants.PAL_SERVO_NAME);

        this.grappleMotor = createDefaultMotor("grappleMotor");
        this.grappleMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // todo: I think this camera can go up to 1920x1080 & 60fps. Not sure, test maybe?
        this.camera = setUpCamera(constants.CAMERA_NAME, constants.CAMERA_WIDTH, constants.CAMERA_HEIGHT);
        dashboard.startCameraStream(camera, 60);
    private DcMotorEx armMotor;
    private double holdMotorPower = 0.2;

    public centerStageRobot(LinearOpMode opmode) {
        super(opmode, 3.5, 13.75);

        //this.armMotor = createDefaultMotor("armMotor");
        this.frontCamera = setUpCamera(constants.FRONT_CAMERA_NAME, constants.FRONT_CAMERA_WIDTH, constants.FRONT_CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
        // todo find specs for this camera
        mod = new CenterStageAprilTagModule(opmode.hardwareMap.get(WebcamName.class, constants.SIDE_CAMERA_NAME), constants.SIDE_CAMERA_WIDTH, constants.SIDE_CAMERA_HEIGHT);
        sideCamera = mod.getCamera();

        //dashboard.startCameraStream(frontCamera, 60);
        dashboard.startCameraStream(sideCamera, 60);
    }

    public void driveStrafe(double inches, double power) {
        int ticks = (int) this.inchesToEncoder(inches);
        int[] target = new int[] {ticks, -ticks, -ticks, ticks};
        double[] powers = new double[] {power, -power, -power, power};

        driveEncoded(target, powers);
    }


    private Servo setUpServo(String servoName){
        Servo servo = opMode.hardwareMap.get(Servo.class, servoName);
        return servo;
    }

    public Servo getKnockerServo(){
        // servo specs: GoBilda 2000 Series Dual Mode Servo (25-3, Speed)
        // SKU: 2000-0025-0003
        return knockerServo;
    }

    public Servo getPALServo() {
        return PALServo;
    }

    public DcMotorEx getGrappleMotor(){
        return grappleMotor;
    }

    public Servo getGrappleServo(){ return grappleServo; }

    private OpenCvCamera setUpCamera(String cameraName, int cameraWidth, int cameraHeight, OpenCvCameraRotation orientation){
        WebcamName cameraNameThing = opMode.hardwareMap.get(WebcamName.class, cameraName);
        OpenCvCamera webcam = OpenCvCameraFactory.getInstance().createWebcam(cameraNameThing);
        // This sets up the camera n stuff. Basically just does settings
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.setViewportRenderer(OpenCvCamera.ViewportRenderer.NATIVE_VIEW);
                webcam.startStreaming(cameraWidth, cameraHeight, orientation);
            }

            @Override
            public void onError(int errorCode) {}
        });
        return webcam;
    }

    public OpenCvCamera getFrontCamera() {
        return frontCamera;
    }
    // servo specs: GoBilda 2000 Series Dual Mode Servo (25-3, Speed)
    // SKU: 2000-0025-0003
    public OpenCvCamera getSideCamera(){ return sideCamera; }
    public CenterStageAprilTagModule getMod(){ return mod; }
}
