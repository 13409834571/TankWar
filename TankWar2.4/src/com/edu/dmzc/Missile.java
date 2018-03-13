package com.edu.dmzc;
import java.awt.*;
import java.util.*;
/**
 * 管理子弹的类
 * @author dmzc
 *
 */
public class Missile {
	/**
	 * 子弹x方向移动的速度
	 */
	public static final int XSPEED = 30;
	/**
	 * 子弹y方向移动的速度
	 */
	public static final int YSPEED = 30;
	/**
	 * 子弹的宽度
	 */
	public static final int WIDTH = 10;
	/**
	 * 子弹的高度
	 */
	public static final int HEIGHT = 10;
	int x,y;
	Tank.Direction dir = Tank.Direction.STOP;
	private TankClient tc = null;
	private boolean live = true;
	private boolean good;
	/**
	 * 
	 * @param x 子彈左上角x
	 * @param y 子彈左上角y
	 * @param dir 子弹的方向
	 */
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		
	}
	/**
	 * 
	 * @param x 子弹左上角x
	 * @param y 子弹左上角y
	 * @param dir 子弹方向
	 * @param tc tc的引用
	 */
	public Missile(int x,int y,Tank.Direction dir,TankClient tc) {
		
		this(x,y,dir);
		this.tc = tc;
	}
	/**
	 * 
	 * @param x 子弹左上角x
	 * @param y 子弹左上角y
	 * @param dir 子弹方向
	 * @param tc tc的引用
	 * @param good 判断子弹的阵营
	 */
	public Missile(int x,int y,Tank.Direction dir,TankClient tc,boolean good) {
		
		this(x, y, dir, tc);
		this.good = good;
	}
	/**
	 * 判断坦克是否活着
	 * @return布尔类型的值
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * g 
	 * 子弹的专有绘图方法
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
	 * 攻击坦克
	 * @param t要攻击的坦克
	 * @return布尔类型值
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
	 * @param tanks 装满坦克的列表
	 * @return布尔类型的值
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
	 * 判断是否会与墙相撞
	 * @param w 墙
	 * @return布尔类型的值
	 */
	public boolean hitWall(Wall w) {
		
	if(live&&this.getRetangle().intersects(w.getRectangle())) {
		
			this.live = false;
			return true;
		}
		return false;
	}
}
