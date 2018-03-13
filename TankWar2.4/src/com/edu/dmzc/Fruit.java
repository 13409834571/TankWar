package com.edu.dmzc;
import java.awt.*;
/**
 * 添加果子特效的类
 * @author dmzc
 *
 */
public class Fruit {

	int x;
	int y;
	int width;
	int height;
	int step = 0;
	boolean live = true;
	boolean good;
	int bPos[][] = { { 350, 300 }, { 360, 300 }, { 375, 275 }, { 400, 200 }, { 360, 270 }, { 365, 290 }, { 340, 280 } };
	int gPos[][] = { { 650, 300 }, { 660, 600 }, { 675, 575 }, { 500, 500 }, { 560, 570 }, { 665, 490 }, { 640, 580 } };
	/**
	 * 用来创造好的果子或者坏的果子
	 * @param good 果子的好坏
	 */
	public Fruit(boolean good) {
		if (good) {
			x = gPos[0][1];
			y = gPos[1][0];
		}
		else {
			x = bPos[0][1];
			y = bPos[1][0];
		}
		width = 15;
		height = 15;
		this.good = good;
	}
	/**
	 * 
	 * 果子独有的绘图方法
	 */
	public void draw(Graphics g) {

		if (!live)
			return;
		Color c = g.getColor();
		if (good)
			g.setColor(Color.green);
		else
			g.setColor(Color.GRAY);
		g.fillOval(x, y, width, height);
		g.setColor(c);
		move();
	}

	void move() {

		step++;
		if (step ==bPos.length) {
			step = 0;
		}
		if(good) {
			x = gPos[step][0];
			y = gPos[step][1];
		}
		else {
			x = bPos[step][0];
			y = bPos[step][1];
		}
	}
	/**
	 * 判断果子是否依然存在
	 * @return 布尔类型的值
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * 设定果子的生死
	 * @param live 布尔类型
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * 判断果子的好坏
	 * 
	 */
	public boolean isGood() {
		return good;
	}
	/**
	 * 设定果子的好坏
	 * @param good 果子的好坏
	 */
	public void setGood(boolean good) {
		this.good = good;
	}
	/**
	 * 
	 * @return 果子的区域范围
	 */
	public Rectangle getRectangle() {

		return new Rectangle(x, y, width, height);
	}
}
