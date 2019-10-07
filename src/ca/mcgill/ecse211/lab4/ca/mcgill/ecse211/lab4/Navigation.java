package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;
import java.lang.Math;
/**
 * This class is used to navigate the robot to a given way point.
 * Class includes methods that calculate distance as well as angular displacement
 * to next way-point.
 * 
 * @author Abhimukth Chaudhuri, Aly Elgharabawy
 *
 */

public class Navigation extends Thread{

  /**
   * Method takes an array of coordinate points and calls travel method for each way point
   * @param waypoints array of coordinates for a map
   * @throws InterruptedException
   * @author Abhimukth Chaudhuri, Aly Elgharabawy
   */
  public static void travelMap(double[][] waypoints){
    for(double[] coordinate: waypoints) {
      Navigation.travelTo(coordinate[0], coordinate[1]);
    }
  }
  
  /**
   * This method causes the robot to travel to the absolute field location (x, y), 
   * specified in tile points. This method polls the odometer for information
   * @param x Cartesian coordinate X for way-point specification
   * @param y Cartesian coordinate Y for way-point specification
   * @author Abhimukth Chaudhuri, Aly Elgharabawy
   */
  public static void travelTo(final double x, final double y) {
    //set accelerations and speed to default values
    leftMotor.setAcceleration(ACCELERATION);
    rightMotor.setAcceleration(ACCELERATION);
    leftMotor.setSpeed(FORWARD_SPEED);
    rightMotor.setSpeed(FORWARD_SPEED);


    //Get positional values and calculate differences from given co-ordinate
    double[] xyt = odometer.getXYT();
    double dx = (x*TILE_SIZE)-xyt[0];
    double dy = (y*TILE_SIZE)-xyt[1];
    double dtheta = (Math.toDegrees(Math.atan2(dx,dy))-xyt[2]);

    //adjust dtheta (degrees to turn) so that it's always smaller than 360
    if(dtheta > 360 || dtheta < -360)
      dtheta = dtheta % 360;

    //Shortest turn calculations
    if(dtheta >180)
      dtheta =-1*(360-dtheta);
    if(dtheta < -180)
      dtheta = (360+dtheta);

    LCD.drawString("dT: " + dtheta, 0, 3);
    //Turn to correct angle
    turnTo(dtheta);

    //calculate distance to move and move said distance
    final double distance = Math.sqrt( dx*dx + dy*dy);
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    leftMotor.rotate((int)(distance*180/(WHEEL_RAD*Math.PI)), true);
    rightMotor.rotate((int)(distance*180/(WHEEL_RAD*Math.PI)));
  }

  /**
   * This method causes the robot to turn a minimal angle to the absolute heading theta/ to its target. 
   * @param theta angle to rotate to head towards next way-point
   * @author Abhimukth Chaudhuri, Aly Elgharabawy
   */
  public static  void turnTo(double theta) {
    leftMotor.rotate((int) (theta*CONSTANT/2),true);
    rightMotor.rotate((int) (-1 * theta*CONSTANT/2));
  }
  
  public static  void turnToInstantReturn(double theta) {
    leftMotor.rotate((int) (theta*CONSTANT/2),true);
    rightMotor.rotate((int) (-1 * theta*CONSTANT/2),true);
  }
  public static void moveForward(double distance) {
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    leftMotor.rotate((int)(distance*180/(WHEEL_RAD*Math.PI)), true);
    rightMotor.rotate((int)(distance*180/(WHEEL_RAD*Math.PI)));
    
    
  }

  /**
   * Flag to notify if robot is navigating or turning to a way-point
   * @return true iff another thread has called travelTo() or turnTo() and method has yet to retun 
   * @author Abhimukth Chaudhuri, Aly Elgharabawy    
   */
  public boolean isNavigating() {
    return false;
  }

}