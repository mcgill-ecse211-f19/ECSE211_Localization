package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;
import java.lang.Math;

/**
 * This class is used to navigate the robot to a given way point. Class includes methods that calculate distance as well
 * as angular displacement to a way-point.
 * 
 * @author Abhimukth Chaudhuri, Aly Elgharabawy
 *
 */

public class Navigation extends Thread {

  /**
   * This method causes the robot to travel to the absolute field location (x, y), specified in tile points. This method
   * polls the odometer for information
   * 
   * @param x Cartesian coordinate X for way-point specification
   * @param y Cartesian coordinate Y for way-point specification
   * @author Abhimukth Chaudhuri, Aly Elgharabawy
   */
  public static void travelTo(final double x, final double y, boolean risingEdge) {
    // set accelerations and speed to default values
    leftMotor.setAcceleration(ACCELERATION);
    rightMotor.setAcceleration(ACCELERATION);
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);

    // Get positional values and calculate differences from given co-ordinate
    double[] xyt = odometer.getXYT();
    double dx = (x * TILE_SIZE) - xyt[0];
    double dy = (y * TILE_SIZE) - xyt[1];
    double dtheta = (Math.toDegrees(Math.atan2(dx, dy)) - xyt[2]);

    // adjust dtheta (degrees to turn) so that it's always smaller than 360
    if (dtheta > 360 || dtheta < -360)
      dtheta = dtheta % 360;

    // Shortest turn calculations
    if (dtheta > 180)
      dtheta = -1 * (360 - dtheta);
    if (dtheta < -180)
      dtheta = (360 + dtheta);

    LCD.drawString("dT: " + dtheta, 0, 3);

    // Turn to correct angle
    turnTo(dtheta);

    // calculate distance to move and move said distance
    final double distance = Math.sqrt(dx * dx + dy * dy);
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);

    // Approximating distance from point (1,1) correction on the point after light localization is finished.
    if (risingEdge) {
      leftMotor.rotate((int) (-1 * distance * 180 / (WHEEL_RAD * Math.PI)), true);
      rightMotor.rotate((int) (-1 * distance * 180 / (WHEEL_RAD * Math.PI)));
    } else {
      leftMotor.rotate((int) (distance * 180 / (WHEEL_RAD * Math.PI)), true);
      rightMotor.rotate((int) (distance * 180 / (WHEEL_RAD * Math.PI)));
    }
  }

  /**
   * Method causes the robot to turn a minimal angle to the absolute heading theta/ to its target.
   * 
   * @param theta angle to rotate to head towards
   * @author Abhimukth Chaudhuri, Aly Elgharabawy
   */
  public static void turnTo(double theta) {
    leftMotor.rotate((int) (theta * CONSTANT / 2), true);
    rightMotor.rotate((int) (-1 * theta * CONSTANT / 2));
  }

  /**
   * Method makes the robot rotate around its center of rotation for the specified angle.
   * 
   * @param theta angle to rotate in degrees
   * @author Abhimukth Chaudhuri, Aly Elgharabawy
   */
  public static void turnToInstantReturn(double theta) {
    leftMotor.rotate((int) (theta * CONSTANT / 2), true);
    rightMotor.rotate((int) (-1 * theta * CONSTANT / 2), true);
  }

  /**
   * Method causes the robot to move forward for the provided distance value.
   * 
   * @param distance in cm
   * @author Abhimukth Chaudhuri, Aly Elgharabawy
   */
  public static void moveForward(double distance) {
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    leftMotor.rotate((int) (distance * 180 / (WHEEL_RAD * Math.PI)), true);
    rightMotor.rotate((int) (distance * 180 / (WHEEL_RAD * Math.PI)));
  }

}
