package com.dig.www.enemies;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.dig.www.util.StageBuilder;
import com.dig.www.util.Statics;

public class EnemyMaker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = (StageBuilder.class.getProtectionDomain().getCodeSource()
				.getLocation().getFile() + "maps/mapE.ser");
		try {
			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream(s));
			ArrayList<Enemy> enemies = new ArrayList<Enemy>();

			// L,6700, 1400, images/enemies/turrets/0.png, f, 75
			// L,200, 1400, images/enemies/unique/machineLaunch.png, f, 20
			// T,100, 1000, images/enemies/unique/chair.png, f
			// W,100, 1400, images/enemies/unique/ghost.png, f
			// S,3800, 1500, images/enemies/unique/fishbowl.png, t
			// S,3800, 1600, images/enemies/unique/fishbowl.png, t
			// S,3800, 1700, images/enemies/unique/fishbowl.png, t
			// S,3800, 1800, images/enemies/unique/fishbowl.png, t
			os.writeObject(enemies);
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
