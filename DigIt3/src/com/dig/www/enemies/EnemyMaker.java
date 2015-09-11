package com.dig.www.enemies;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.dig.www.util.StageBuilder;
import com.dig.www.util.Statics;

public class EnemyMaker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = (StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/mapE.ser");
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(s));
			ArrayList<Enemy> enemies = new ArrayList<Enemy>();
			
			enemies.add(new Launch(6700, 1400, "images/enemies/turrets/0.png", 75, false));
			enemies.add(new Launch(200, 1400, "images/enemies/unique/machineLaunch.png", 20, false));
			enemies.add(new TrackingEnemy(100, 1000, "images/enemies/unique/chair.png", false));
			enemies.add(new WalkingEnemy(100, 1400, "images/enemies/unique/ghost.png", false));
			
			enemies.add(new StandEnemy(3800, 1500, "images/enemies/unique/fishbowl.png", true));
			enemies.add(new StandEnemy(3800, 1600, "images/enemies/unique/fishbowl.png", true));
			enemies.add(new StandEnemy(3800, 1700, "images/enemies/unique/fishbowl.png", true));
			enemies.add(new StandEnemy(3800, 1800, "images/enemies/unique/fishbowl.png", true));
			os.writeObject(enemies);
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
