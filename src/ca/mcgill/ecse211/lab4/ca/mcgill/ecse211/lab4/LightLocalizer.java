package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;
import lejos.robotics.SampleProvider;

public class LightLocalizer {

  private static final int THRESHOLD = 35;
  private static SampleProvider color_sensor = colorSensor.getRedMode();
  private static float[] sensor_data = new float[color_sensor.sampleSize()]; // array of sensor readings
  private static int current_color_value = 0;

  

  /**
   * TODO: ADD DOCUMENTATION FOR BLACK LINE TRIGGER METHOD
   * 
   * @return true iff black line detected
   */
  public static boolean blackLineTrigger() {
    color_sensor.fetchSample(sensor_data, 0);
    current_color_value = (int) (sensor_data[0] * 100); // sensor data read in 2dp - convert to integer value to compare
    // System.out.println("Recorded Intensity: " + current_color_value);
    // when color intensity is below threshold
    if (current_color_value < THRESHOLD) {
      return true;
    } else {
      return false;
    }
  }
}
