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
    
    double[] angles = new double[4];

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
        Sound.beep();
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break; 
      }



    }
    Navigation.moveForward(-12);
    
    Navigation.turnToInstantReturn(360);
    
    
    int i = 0;
    while(true) {
      if(LightLocalizer.blackLineTrigger()) {
        LCD.drawString("IF BLOCK REACHED", 0, 6);
        angles[i] = display.theta;
        Sound.beep();
        i++;
      }
      if(i == 4)
        break;
      
    }
    double thetaY = angles[1]-angles[3];
    double thetaX = angles[0]-angles[2];
    double x = -12 * Math.cos(Math.toRadians(thetaY/2));
    double y = -12 * Math.cos(Math.toRadians(thetaX/2));
    odometer.setXYT(x,y,odometer.getXYT()[2]);



    double deltaTheta = 90 - angles[3]+180+thetaY/2;
    odometer.setOffset(odometer.getOffset()+deltaTheta);

    Button.waitForAnyPress();
    System.exit(0);

  }



}
