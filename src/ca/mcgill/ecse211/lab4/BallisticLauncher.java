package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;

public class BallisticLauncher {

  private int motorLaunchingSpeed;
  
  private static BallisticLauncher bl; // Returned as singleton
  
  
  
  public double[] launchLocation(double x, double y) {
      y = (y-4.5) ;
      
      double[] location = new double[2];
      location[0] = x;
      location[1] = y;
      return location;
      
  }

  void launch(double distance) {
    // motorLaunchingSpeed = (int) (LAUNCH_COEFFICIENT * distance);
    motorLaunchingSpeed = 850;

    leftBallisticMotor.setSpeed(motorLaunchingSpeed);
    rightBallisticMotor.setSpeed(motorLaunchingSpeed);
    leftBallisticMotor.setAcceleration(ACCELERATION);
    rightBallisticMotor.setAcceleration(ACCELERATION);
    leftBallisticMotor.rotate(-LAUNCHING_ANGLE, true);
    rightBallisticMotor.rotate(-LAUNCHING_ANGLE, false);


  }
  
  /**
   * Returns the BallisticLauncher object. Use this method to obtain an instance of Launcher. Method used to make sure there
   * is just one instance of BallisticLauncher throughout the code
   * 
   * @return the BallisticLauncher Object
   */
  public synchronized static BallisticLauncher getBallisticLauncher() {
    if (bl == null) {
      bl = new BallisticLauncher();
    }
    return bl;
  }
}