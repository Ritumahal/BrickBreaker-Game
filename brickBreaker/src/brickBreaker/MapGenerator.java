package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	public int map[][];
	public int brickHeight;
	public int brickWidth;

	public MapGenerator(int row, int column) {
		// instantiating 2D Array
		map = new int[row][column];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = 1;
			}
		}
		brickWidth = 540 / column;
		brickHeight = 150 / row;
		
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] > 0) {// ek particular value of array map
					g.setColor(Color.white);
					g.fillRect(j * brickWidth+80, i * brickHeight+50, brickWidth, brickHeight);
					
					//generating border around every brick
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth+80, i * brickHeight+50, brickWidth, brickHeight);
				}
			}
		}
	}
	public void setBricksValue(int value,int row,int column){
		map[row][column]=value;
	}
}
