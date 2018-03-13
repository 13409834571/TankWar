package com.edu.dmzc;
import java.awt.*;
/**
 * ��ӹ�����Ч����
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
	 * ��������õĹ��ӻ��߻��Ĺ���
	 * @param good ���ӵĺû�
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
	 * ���Ӷ��еĻ�ͼ����
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
	 * �жϹ����Ƿ���Ȼ����
	 * @return �������͵�ֵ
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * �趨���ӵ�����
	 * @param live ��������
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * �жϹ��ӵĺû�
	 * 
	 */
	public boolean isGood() {
		return good;
	}
	/**
	 * �趨���ӵĺû�
	 * @param good ���ӵĺû�
	 */
	public void setGood(boolean good) {
		this.good = good;
	}
	/**
	 * 
	 * @return ���ӵ�����Χ
	 */
	public Rectangle getRectangle() {

		return new Rectangle(x, y, width, height);
	}
}
