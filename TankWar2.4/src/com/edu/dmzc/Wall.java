package com.edu.dmzc;
import java.awt.*;
/**
 * 
 * @author dmzc
 *在主界面创建一堵墙
 */
public class Wall {
	
	int x;
	int y;
	int height;
	int width;
	TankClient tc;
	Wall(int x,int y,int width,int height,TankClient tc){
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tc = tc;
	}
	/**
	 * 将墙画出来的方法
	 * @param g要对其实施绘画操作的对象
	 */
	public void draw(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		g.setColor(c);
	}
	/**
	 * 
	 * @return以墙的尺寸一个Rectangle对象
	 */
	public Rectangle getRectangle() {
		
		return new Rectangle(x, y, width, height);
	}
}
