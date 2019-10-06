package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;

public class UltrasonicLocalizer {
  public double alpha;
  public double beta;
  
  public void fallingEdge() {
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(-1*ROTATE_SPEED);
    leftMotor.forward();
    rightMotor.backward();
    while(true)
    {
      if(UP.getDistance() < D - K) {
        this.alpha =  odometer.getXYT()[2];
        leftMotor.stop();
        rightMotor.stop();
        break;
      }
      
    }
    leftMotor.setSpeed(-1*ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    while(true) {
      if(UP.getDistance() < D - K) {
        this.beta =  odometer.getXYT()[2];
        leftMotor.stop();
        rightMotor.stop();
        break;
      
    }
   }
    if(alpha>beta)
    odometer.setTheta((450-alpha-beta)/2);
    else
      odometer.setTheta((90-alpha-beta)/2);
  }
  
  public void risingEdge() {
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(-1*ROTATE_SPEED);
    
    
    
    
    
  }

}
