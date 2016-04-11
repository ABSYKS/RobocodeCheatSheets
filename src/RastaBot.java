import java.awt.Color;
import java.awt.geom.Point2D;

import robocode.*;
import robocode.util.*;
  
 public class RastaBot extends AdvancedRobot {



		 final static double BULLET_POWER=3;//Our bulletpower.
		 final static double BULLET_DAMAGE=BULLET_POWER*4;//Formula for bullet damage.
		 final static double BULLET_SPEED=20-3*BULLET_POWER;//Formula for bullet speed.

		 //Variables
		 static double oldEnemyHeading;
		 static double enemyEnergy;
		 private RobotStatus robotStatus;
		 private double distance;
		 private double dir=1;
		 private boolean hitWall = false;

		 public void run() {
			 while (true) {
				 setRobotColor();
				 turnRadarRight(360);
				 //moveFromWall();

			 }
		 }


		 public void onStatus(StatusEvent e) {
			 this.robotStatus = e.getStatus();
		 }



		 public void setRobotColor(){
			 setBodyColor(newColor());
			 setGunColor(newColor());
			 setRadarColor(newColor());
			 setBulletColor(newColor());
			 setScanColor(newColor());
		 }

		 public Color newColor() {

			 return new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random()));
		 }


		 public void onScannedRobot(ScannedRobotEvent e) {
			 if(e.getName().contains("tooAgile4U")) return;
			 double absBearing=e.getBearingRadians()+getHeadingRadians();


			 double turn=absBearing+Math.PI/2;

			 turn-=Math.max(0.5,(1/e.getDistance())*100)*dir;

			 setTurnRightRadians(Utils.normalRelativeAngle(turn-getHeadingRadians()));

			 if(enemyEnergy>(enemyEnergy=e.getEnergy())){

				 if(Math.random()>200/e.getDistance()){
					 dir=-dir;
				 }
			 }

			 setMaxVelocity(400/getTurnRemaining());

			 setAhead(100*dir);

			 double enemyHeading = e.getHeadingRadians();
			 double enemyHeadingChange = enemyHeading - oldEnemyHeading;
			 oldEnemyHeading = enemyHeading;

			 double deltaTime = 0;
			 double predictedX = getX()+e.getDistance()*Math.sin(absBearing);
			 double predictedY = getY()+e.getDistance()*Math.cos(absBearing);
			 while((++deltaTime) * BULLET_SPEED <  Point2D.Double.distance(getX(), getY(), predictedX, predictedY)){

				 predictedX += Math.sin(enemyHeading) * e.getVelocity();
				 predictedY += Math.cos(enemyHeading) * e.getVelocity();

				 enemyHeading += enemyHeadingChange;

				 predictedX=Math.max(Math.min(predictedX,getBattleFieldWidth()-18),18);
				 predictedY=Math.max(Math.min(predictedY,getBattleFieldHeight()-18),18);

			 }

			 double aim = Utils.normalAbsoluteAngle(Math.atan2(  predictedX - getX(), predictedY - getY()));

			 setTurnGunRightRadians(Utils.normalRelativeAngle(aim - getGunHeadingRadians()));
			 setFire(BULLET_POWER);






			 setTurnRadarRightRadians(Utils.normalRelativeAngle(absBearing-getRadarHeadingRadians())*2);
		 }
		 public void onBulletHit(BulletHitEvent e){
			 enemyEnergy-=BULLET_DAMAGE;
		 }
		 public void onHitWall(HitWallEvent e){
			 dir=-dir;
		 }
	 }
