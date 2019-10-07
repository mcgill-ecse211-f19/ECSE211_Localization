package ca.mcgill.ecse211.lab4;

import com.sun.org.apache.xerces.internal.util.Status;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

/**
 * This class is used to define static resources in one place for easy access and to avoid 
 * cluttering the rest of the codebase. All resources can be imported at once like this:
 * 
 * <p>{@code import static ca.mcgill.ecse211.lab3.Resources.*;}
 */
public class Resources {

  /**
   * The wheel radius in centimeters.
   */
  public static final double WHEEL_RAD = 2.130; //Maybe change to 2.20, but this value given is more or less accurate

  /**
   * The robot width in centimeters.
   */
  public static final double TRACK = 15.178;

  /**
   * The speed at which the robot moves forward in degrees per second.
   */
  public static final int FORWARD_SPEED = 175;

  /**
   * The speed at which the robot rotates in degrees per second.
   */
  public static final int ROTATE_SPEED = 100;

  /**
   * The motor acceleration in degrees per second squared.
   */
  public static final int ACCELERATION = 2000;

  /**
   * Timeout period in milliseconds.
   */
  public static final int TIMEOUT_PERIOD = 1000;

  /**
   * The tile size in centimeters.
   */
  public static final double TILE_SIZE = 30.48;

  /**
   * The left motor.
   */
  public static final EV3LargeRegulatedMotor leftMotor =
      new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));

  /**
   * The right motor.
   */
  public static final EV3LargeRegulatedMotor rightMotor =
      new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
  /**
   * The Ultrasonic sensor
   */
  public static final EV3UltrasonicSensor US_SENSOR = 
      new EV3UltrasonicSensor(LocalEV3.get().getPort("S4"));
  /**
   * The localizer
   */
  public static final UltrasonicLocalizer UL = new UltrasonicLocalizer();
  /**
   * The poller
   */
  public static final UltrasonicPoller UP = new UltrasonicPoller();
  /**
   * The light sensor 
   */
  public static final EV3ColorSensor colorSensor = 
      new EV3ColorSensor(LocalEV3.get().getPort("S1"));
  /**
   * The LCD.
   */
  public static final TextLCD LCD = LocalEV3.get().getTextLCD();
  
  /**
   * proportionality constant representing ratio of wheel turn degrees to robot turn degrees
   * 1:6.95
   */
  public static final double CONSTANT = 7.09;
  
  public static final int D = 40;
  
  public static final int K = 1;

  /**
   * The odometer.
   */
  public static Odometer odometer = Odometer.getOdometer();

}
