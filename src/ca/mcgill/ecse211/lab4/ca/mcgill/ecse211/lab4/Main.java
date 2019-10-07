package ca.mcgill.ecse211.lab4;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import static ca.mcgill.ecse211.lab4.Resources.*;
import ca.mcgill.ecse211.lab4.Display;
import ca.mcgill.ecse211.lab4.Odometer;

public class Main {
  static Display display = new Display();

  public static void main(String[] args) {
    // Start threads
    new Thread(odometer).start();
    new Thread(display).start();
    new Thread(UP).start();

    // Button choice prior to light localization
    int buttonChoice = Button.waitForAnyPress();

    // Falling edge for left button
    if (buttonChoice == Button.ID_LEFT) {
      if (UP.getDistance() > 1.414 * TILE_SIZE) {
        UL.fallingEdge();
      }
    }
    // Rising edge for right button
    else if (buttonChoice == Button.ID_RIGHT) {
      UL.risingEdge();
    }

    // ButtonChoice for lightLocalizer
    Button.waitForAnyPress();

    Navigation.turnTo(45);
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    leftMotor.forward();
    rightMotor.forward();
    while(true) {
      if(LightLocalizer.blackLineTrigger()) {
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break; 
      }



    }
    Navigation.moveForward(-12);   




    Button.waitForAnyPress();
    System.exit(0);

  }



}
