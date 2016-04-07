# RobocodeCheatSheets
List of Re-Usable Robocode Methods

package sample;
import robocode.*;
import java.awt.Color;
import java.lang.Object;
import robocode.Event;
import robocode.*; 
import robocode.ScannedRobotEvent;

public class robo extends AdvancedRobot
{

  private byte moveDirection = 1;
  private RobotStatus robotStatus;
  
	public void run() {

		setColors(Color.black,Color.gray,Color.red); // body,gun,radar

		setAdjustRadarForRobotTurn(true);
		while (true) {
		turnRadarRight(360);
	}
}

	public int randomizer(int MIN, int MAX)
 {
	int range = (MAX - MIN) + 1;
	int total = (int)(Math.random() * range) + MIN;
	return total;
}


    public void onStatus(StatusEvent e) {
        this.robotStatus = e.getStatus();
    }  


	public void onScannedRobot(ScannedRobotEvent e) {
	
		double distance = e.getDistance();
		/*
		double angleToEnemy = e.getBearing();
 		// Angle to the scanned robot
        double angle = Math.toRadians((robotStatus.getHeading() + angleToEnemy) % 360);

        // Coordinates of the robot
        double enemyX = (robotStatus.getX() + Math.sin(angle) * e.getDistance());
        double enemyY = (robotStatus.getY() + Math.cos(angle) * e.getDistance());
		*/
		if (distance <= 450){
		 	setTurnRadarRight(getHeading() - getRadarHeading() + e.getBearing());
			turnRight(e.getBearing());
			ahead(0.5 * distance);
			setFire(Math.min(400 / e.getDistance(), 3));
			}
			
else {
	turnRadarRight(360);
	setTurnRight(e.getBearing() + 90);

	// strafe by changing direction every 20 ticks
	if (getTime() % 20 == 0) {
		moveDirection *= -1;
		setAhead(150 * moveDirection);
		turnRight(e.getBearing());
		fire(1);
}
	}
	}

	public void onHitByBullet(HitByBulletEvent e) {
		setTurnRight(e.getBearing() + 90);

	// strafe by changing direction every 20 ticks
	if (getTime() % 20 == 0) {
		moveDirection *= -1;
		setAhead(150 * moveDirection);
	}
	}
	
	public void onHitWall(HitWallEvent e) {
		back(10);
		turnLeft(e.getBearing());
		ahead(10);
	}	
}
