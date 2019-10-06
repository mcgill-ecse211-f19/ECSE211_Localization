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
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break;
      }
      
    }
    leftMotor.setSpeed(-1*ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    leftMotor.rotate(-70,true);
    rightMotor.rotate(70);
    leftMotor.backward();
    rightMotor.forward();
    while(true) {
      if(UP.getDistance() < D - K) {
        this.beta =  odometer.getXYT()[2];
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break;
      
    }
   }
    if(alpha<beta)
    odometer.setOffset(225-(alpha+beta)/2);
    else
      odometer.setOffset(45-(alpha+beta)/2);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(-1*ROTATE_SPEED);
  Navigation.turnTo(360-Main.display.theta);
  
  }
  
  public void risingEdge() {
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(-1*ROTATE_SPEED);
    leftMotor.forward();
    rightMotor.backward();
    while(true)
    {
      if(UP.getDistance() > D + K) {
        this.alpha =  odometer.getXYT()[2];
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break;
      }
      
    }
    leftMotor.setSpeed(-1*ROTATE_SPEED);
    rightMotor.setSpeed(ROTATE_SPEED);
    leftMotor.rotate(-70,true);
    rightMotor.rotate(70);
    leftMotor.backward();
    rightMotor.forward();
    while(true) {
      if(UP.getDistance() > D + K) {
        this.beta =  odometer.getXYT()[2];
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        break;
      
    }
   }
    if(alpha<beta)
    odometer.setOffset(225-(alpha+beta)/2);
    else
      odometer.setOffset(45-(alpha+beta)/2);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    leftMotor.setSpeed(ROTATE_SPEED);
    rightMotor.setSpeed(-1*ROTATE_SPEED);
  Navigation.turnTo(360-Main.display.theta);
    
    
    
    
  }

}
