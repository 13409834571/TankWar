package com.edu.dmzc;
import java.awt.*;
/**
 * 
 * @author dmzc
 *�������洴��һ��ǽ
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
	 * ��ǽ�������ķ���
	 * @param gҪ����ʵʩ�滭�����Ķ���
	 */
	public void draw(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		g.setColor(c);
	}
	/**
	 * 
	 * @return��ǽ�ĳߴ�һ��Rectangle����
	 */
	public Rectangle getRectangle() {
		
		return new Rectangle(x, y, width, height);
	}
}
