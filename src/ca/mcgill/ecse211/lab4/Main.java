package ca.mcgill.ecse211.lab4;

import lejos.hardware.Button;
import static ca.mcgill.ecse211.lab4.Resources.*;
import ca.mcgill.ecse211.lab4.Display;


public class Main {
  public static Display display = new Display();
  public static int buttonChoice = 0;

  public static void main(String[] args) {
    // Start threads
    new Thread(odometer).start();
    new Thread(UP).start();

    // Button choice prior to light localization
    buttonChoice = chooseUSLocalization();
    new Thread(display).start();

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

    // Light localization routine
    LightLocalizer.lightLocalize();



    double distance = 40;
    
    Navigation.travelTo(1.0, 1.0,false);
    Navigation.turnTo(0);
    ballisticLauncher.launch(distance);
    System.exit(0);

  }

  /**
   * Asks the user for the type of US localization routine to run
   * 
   * @return the user choice
   */
  private static int chooseUSLocalization() {
    int buttonChoice;
    Display.showText("< Left | Right >", "  Fall |  Ris  ", " -ing  | -ing  ", "  edge |  edge ", "       |       ");
    do {
      buttonChoice = Button.waitForAnyPress();
    } while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);
    return buttonChoice;
  }


}
