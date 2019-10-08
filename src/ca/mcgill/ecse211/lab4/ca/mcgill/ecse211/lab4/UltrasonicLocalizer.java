package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;

/**
 * This class contains methods for the two ultrasonic localization routines: the falling edge and rising edge
 * localization routines.
 * 
 * @author Abhimukth Chaudhuri, Aly Elgharabawy
 *
 */
public class UltrasonicLocalizer {

  /**
   * angle at which back wall is detected
   */
  public double alpha;
  /**
   * angle at which left wall is detected
   */
  public double beta;

  /**
   * Method performs the falling edge localization. Robot always completes an clockwise rotation around its center of
   * rotation to record the value for alpha. Then it rotates in the anti-clockwise direction to record value for beta.
   * Using the values recorded, the robot will then appropriately orient itself accordingly along the 0 degree y-axis.
   * 
   * @author Abhimukth Chaudhuri, Aly Elgharabawy
   */
  public void fallingEdge() {

    // clockwise rotation to record value for alpha
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(-1 * ROTATE_SPEED);
    leftMotor.forward();
    rightMotor.backward();
    while (true) {
      if (UP.getDistance() < COMMON_D - FALLINGEDGE_K) {
        this.alpha = odometer.getXYT()[2];
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break;
      }
    }

    // anti-clockwise rotation to record beta value
    leftMotor.setSpeed(-1 * ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    leftMotor.rotate(-70, true);
    rightMotor.rotate(70);
    leftMotor.backward();
    rightMotor.forward();
    while (true) {
      if (UP.getDistance() < COMMON_D - FALLINGEDGE_K) {
        this.beta = odometer.getXYT()[2];
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break;
      }
    }

    if (alpha < beta)
      odometer.setOffset(225 - (alpha + beta) / 2);
    else
      odometer.setOffset(45 - (alpha + beta) / 2);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Approximately orienting against the 0 degree y-axis.
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(-1 * ROTATE_SPEED);
    Navigation.turnTo(360 - Main.display.theta);

  }

  /**
   * Method performs the rising edge localization. Robot always completes an clockwise rotation around its center of
   * rotation to record the value for alpha. Then it rotates in the anti-clockwise direction to record value for beta.
   * Using the values recorded, the robot will then appropriately orient itself accordingly along the 0 degree y-axis.
   * 
   * @author Abhimukth Chaudhuri, Aly Elgharabawy
   * 
   */
  public void risingEdge() {

    // clockwise rotation to record alpha value
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(-1 * ROTATE_SPEED);
    leftMotor.forward();
    rightMotor.backward();
    while (true) {
      if (UP.getDistance() > COMMON_D + RISINGEDGE_K) {
        this.alpha = odometer.getXYT()[2];
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break;
      }

    }

    // anti-clockwise rotation to record beta value
    leftMotor.setSpeed(-1 * ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    leftMotor.rotate(-70, true);
    rightMotor.rotate(70);
    leftMotor.backward();
    rightMotor.forward();
    while (true) {
      if (UP.getDistance() > COMMON_D + RISINGEDGE_K) {
        this.beta = odometer.getXYT()[2];
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break;

      }
    }

    if (alpha > beta)
      odometer.setOffset(225 - (alpha + beta) / 2);
    else
      odometer.setOffset(45 - (alpha + beta) / 2);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Approximately orienting against the 0 degree y-axis
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(-1 * ROTATE_SPEED);
    Navigation.turnTo(360 - Main.display.theta);
  }

}
