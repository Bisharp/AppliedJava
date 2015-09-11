package com.dig.www.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.dig.www.blocks.*;
import com.dig.www.enemies.Enemy;
import com.dig.www.start.Board;

public class StageBuilder {
	
	private static final int OFF = Statics.BOARD_WIDTH / 2 - 50;
	private static StageBuilder me;
	
	public static StageBuilder getInstance() {
		
		if (me == null)
			me = new StageBuilder();
		
		return me;
	}

	public ArrayList<Block> read(String loc, Board par) {

		ArrayList<Block> world = new ArrayList<Block>();

		int ln = 0;

		try {

			System.out.println("try");

			String tryLoc = StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + ".txt";

			File map = new File(tryLoc);

			if (map.exists()) {

				System.out.println("map exists");

				BufferedReader reader = new BufferedReader(new FileReader(tryLoc));
				String line;

				while ((line = reader.readLine()) != null) {
					// System.out.println(line);
					for (int i = 0; i < line.length(); i++) {
						switch (line.charAt(i)) {
						
						case 'O':
							par.setSpawnX(-Statics.BLOCK_HEIGHT * i + OFF);
							par.setSpawnY(-Statics.BLOCK_HEIGHT * ln + OFF);
						case '1':
							world.add(new Block(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, "images/dummy.png", par, Block.Blocks.GROUND));
							break;
						
						case 'W':
							world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, "images/dummy.png", par, Block.Blocks.WALL));
							break;

						case 'P':
							world.add(new Block(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, "images/dummy.png", par, Block.Blocks.PIT));
							break;
							
						case 'R':
							world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, "images/dummy.png", par, Block.Blocks.ROCK));
							break;
							
						case 'C':
							world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, "images/dummy.png", par, Block.Blocks.CARPET));
							break;
							
//						case 'E':
//							world.add(new EnemyBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, "images/dummy.png", par, line.charAt(i + 1)));
//							world.add(new Block(Statics.BLOCK_HEIGHT * (i + 1), Statics.BLOCK_HEIGHT * ln, "images/dummy.png", par, Block.Blocks.GROUND));
//							i++;
//							break;
							
						case '*':
							world.add(new Block(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, "images/dummy.png", par, Block.Blocks.CRYSTAL));
							break;
							
						case '>':
							world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, "images/dummy.png", par, Block.Blocks.SWITCH));
							break;
						}
					}
					ln++;
				}
				reader.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return world;
	}
	public ArrayList<Enemy> loadEn(String loc) {
		try {
			ObjectInputStream is=new ObjectInputStream(new FileInputStream(StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + "E.ser"));
			ArrayList<Enemy> objects=(ArrayList<Enemy>) is.readObject();
			
			return objects;
	
		} catch (Exception ex) {
		ex.printStackTrace();
		return new ArrayList<Enemy>();
			
			
			
		}

	}
}