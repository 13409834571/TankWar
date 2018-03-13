package com.edu.dmzc;
import java.awt.*;
import java.util.*;
/**
 * �����ӵ�����
 * @author dmzc
 *
 */
public class Missile {
	/**
	 * �ӵ�x�����ƶ����ٶ�
	 */
	public static final int XSPEED = 30;
	/**
	 * �ӵ�y�����ƶ����ٶ�
	 */
	public static final int YSPEED = 30;
	/**
	 * �ӵ��Ŀ��
	 */
	public static final int WIDTH = 10;
	/**
	 * �ӵ��ĸ߶�
	 */
	public static final int HEIGHT = 10;
	int x,y;
	Tank.Direction dir = Tank.Direction.STOP;
	private TankClient tc = null;
	private boolean live = true;
	private boolean good;
	/**
	 * 
	 * @param x �ӏ����Ͻ�x
	 * @param y �ӏ����Ͻ�y
	 * @param dir �ӵ��ķ���
	 */
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		
	}
	/**
	 * 
	 * @param x �ӵ����Ͻ�x
	 * @param y �ӵ����Ͻ�y
	 * @param dir �ӵ�����
	 * @param tc tc������
	 */
	public Missile(int x,int y,Tank.Direction dir,TankClient tc) {
		
		this(x,y,dir);
		this.tc = tc;
	}
	/**
	 * 
	 * @param x �ӵ����Ͻ�x
	 * @param y �ӵ����Ͻ�y
	 * @param dir �ӵ�����
	 * @param tc tc������
	 * @param good �ж��ӵ�����Ӫ
	 */
	public Missile(int x,int y,Tank.Direction dir,TankClient tc,boolean good) {
		
		this(x, y, dir, tc);
		this.good = good;
	}
	/**
	 * �ж�̹���Ƿ����
	 * @return�������͵�ֵ
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * g 
	 * �ӵ���ר�л�ͼ����
	 */
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.black);
		if(good)
		g.fillOval(x, y, 3*WIDTH, 3*HEIGHT);
		else g.fillOval(x, y,WIDTH, HEIGHT);
		g.setColor(c);
		move();
	} 
	
	private void move() {
		
		switch(dir) {
		case L :
			x -= XSPEED;
			break;
		case LU :
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U :
			y -= YSPEED;
			break;
		case RU :
			x += XSPEED;
			y -= YSPEED;
			break;
		case R :
			x += XSPEED;
			break;
		case RD :
			x += XSPEED;
			y += YSPEED;
			break;
		case D :
			y += YSPEED;
			break;
		case LD :
			x -= XSPEED;
			y += YSPEED;
			break;
			
	}
		if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			live = false;
		}
	}
	/**
	 * ����̹��
	 * @param tҪ������̹��
	 * @return��������ֵ
	 */
	public boolean hitTank(Tank t) {
	
			if(this.getRetangle().intersects(t.getRectangle()) &&t.isLive()&&(this.good!=t.isGood())) { 
				
				if(t.isGood()) {
					t.setLife(t.getLife() - 10);
					if(t.getLife() <= 0) t.setLive(false);
				} else {
					t.setLive(false);
					Explode e = new Explode(x,y,tc);
					tc.explodes.add(e);
				}
				this.live = false;
				
				return true;
			}
			return false;
	}
	/**
	 * 
	 * @param tanks װ��̹�˵��б�
	 * @return�������͵�ֵ
	 */
	public boolean hitTanks(ArrayList<Tank> tanks) {
		
		for(int i =0;i < tanks.size();i++) {
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	Rectangle getRetangle() {
		
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	/**
	 * �ж��Ƿ����ǽ��ײ
	 * @param w ǽ
	 * @return�������͵�ֵ
	 */
	public boolean hitWall(Wall w) {
		
	if(live&&this.getRetangle().intersects(w.getRectangle())) {
		
			this.live = false;
			return true;
		}
		return false;
	}
}
