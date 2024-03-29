package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;

/**
 * Samples the US sensor and invokes the selected controller on each cycle.
 * 
 * Control of the ultrasonic localizer is applied periodically by the UltrasonicPoller thread. The while
 * loop at the bottom executes in a loop. Assuming that the us.fetchSample, and cont.processUSData
 * methods operate in about 20ms, and that the thread sleeps for 25 ms at the end of each loop, then
 * one cycle through the loop is approximately 45 ms. This corresponds to a sampling rate of 1/45ms
 * or about 22.2 Hz.
 */
public class UltrasonicPoller implements Runnable {

  private float[] usData;
  int distance;

  public UltrasonicPoller() {
    usData = new float[US_SENSOR.sampleSize()];
  }

  /*
   * Sensors now return floats using a uniform protocol. Need to convert US result to an integer
   * [0,255] (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  public void run() {

    while (true) {
      US_SENSOR.getDistanceMode().fetchSample(usData, 0); // acquire distance data in meters
      distance = (int) (usData[0] * 100.0);
//      if(Main.selectedController.avoiding == false)// extract from buffer, convert to cm, cast to int

      try {
        Thread.sleep(25);
      } catch (Exception e) {
      } // Poor man's timed sampling
    }
  }
  public int getDistance() {
    return distance;
  }

}
