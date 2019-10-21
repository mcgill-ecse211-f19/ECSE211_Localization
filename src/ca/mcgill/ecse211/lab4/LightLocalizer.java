package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;

public class LightLocalizer {

  /**
   * Threshold value to determine if a black line is detected or not
   */
  private static final int THRESHOLD = 35;

  // Variables and values to operate color sensor
  private static SampleProvider color_sensor = colorSensor.getRedMode();
  private static float[] sensor_data = new float[color_sensor.sampleSize()]; // array of sensor readings
  private static int current_color_value = 1000;

  // Array holds angle values
  private static double[] angles = new double[4];

  /**
   * Method performs the light localization routine. Robot rotates 360 degrees around center of rotation on point (1,1).
   * The angle values recorded when a black line is detected is used to correct the robots heading and position on the
   * point (1,1). The robot returns to a 0 degree stop at the end of the process.
   * 
   * @author Abhimukth Chaudhuri, Aly Elgharabawy
   * 
   */
  public static void lightLocalize() {

    // Set motion towards point (1,1)
    Navigation.turnTo(45);
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    leftMotor.forward();
    rightMotor.forward();

    while (true) {
      // Stop as soon as first black line is detected by the light sensor
      if (LightLocalizer.blackLineTrigger()) {
        Sound.beep();
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break;
      }
    }

    // Make robot move back such that the center of roation is somewhat close to the point (1,1)
    Navigation.moveForward(-OFFSET_FROM_WHEELBASE);

    // Perform full rotation to record angle values at each black line
    Navigation.turnToInstantReturn(360);

    int i = 0;
    while (true) {
      if (LightLocalizer.blackLineTrigger()) {
        LCD.drawString("IF BLOCK REACHED", 0, 6);
        angles[i] = Main.display.theta;
        Sound.beep();
        i++;
      }
      // Break after 4 angle values have been recorded
      if (i == 4)
        break;
    }

    // Calculations to know current robot location and corrections to make accordingly
    double thetaY = angles[3] - angles[1];
    double thetaX = angles[2] - angles[0];
    double x = -12 * Math.cos(Math.toRadians(thetaY / 2));
    double y = -12 * Math.cos(Math.toRadians(thetaX / 2));
    odometer.setXYT(x, y, odometer.getXYT()[2]);

    double deltaTheta = 90 - angles[3]  + thetaY / 2;
    odometer.setOffset(odometer.getOffset() + deltaTheta);

    // Perform distance from (1,1) correction based on the type of US Localizer mode selected
    if(odometer.getXYT()[1]+odometer.getXYT()[0] > 1)
    {
    if (Main.buttonChoice == Button.ID_RIGHT) {
      Navigation.travelTo(0, 0, true);
    } else {
      Navigation.travelTo(0, 0, false);
    }
    }
    

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Return to 0 degree y-axis
    Navigation.turnTo(-1 * Main.display.theta);
  }

  /**
   * The method fetches data recorded by the color sensor in RedMode and compares the most recent value to verify if the
   * robot has traveled over a black line. Method makes use of a fixed threshold value which may not be reliable in
   * certain conditions, however it has been tested and conditioned to minimize false negatives.
   * 
   * @return true iff black line is detected
   */
  public static boolean blackLineTrigger() {
    color_sensor.fetchSample(sensor_data, 0);
    current_color_value = (int) (sensor_data[0] * 100);

    // When recorded color intensity is below threshold
    if (current_color_value < THRESHOLD) {
      return true;
    } else {
      return false;
    }
  }
}
