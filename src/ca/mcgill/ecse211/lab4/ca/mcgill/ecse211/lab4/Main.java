package ca.mcgill.ecse211.lab4;

import lejos.hardware.Button;
import lejos.hardware.Sound;

import static ca.mcgill.ecse211.lab4.Resources.*;
import ca.mcgill.ecse211.lab4.Display;
import ca.mcgill.ecse211.lab4.Odometer;

public class Main {
  public static void main(String[] args) {

    
  new Thread(odometer).start();
  
  new Thread(new Display()).start();
  
  new Thread(UP).start();
  
  Button.waitForAnyPress();

  
  if(UP.getDistance() > 1.414 * TILE_SIZE) {
    UL.fallingEdge();
    }
  else {
    UL.risingEdge();
  }
  
  
  }


} 
