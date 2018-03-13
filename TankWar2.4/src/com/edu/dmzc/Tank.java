package com.edu.dmzc;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * 
 * @author dmzc
 *这将会构建出一个坦克对象
 */
public class Tank {
	int x, y;
	/**
	 * 坦克x方向移动的速度
	 */
	public static final int XSPEED = 5;
	/**
	 * 坦克y方向移动的速度
	 */
	public static final int YSPEED = 5;
	/**
	 * 坦克的宽度
	 */
	public static final int WIDTH = 30;
	/**
	 * 坦克的高度
	 */
	public static final int HEIGHT = 30;
	boolean bL = false, bU = false, bR = false, bD = false;
	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	private int oldX;
	private int oldY;
	TankClient tc = null;
	boolean good;
	boolean live = true;
	Random r = new Random();
	int step = r.nextInt(10) + 3;
	private int life = 100;
	BloodBar bb = new BloodBar();
	/**
	 * 
	 * @param x 坦克左上角点的x坐标
	 * @param y 坦克左上角点的y坐标
	 */
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
		oldX = x;
		oldY = y;
	}
	/**
	 * 
	 * @param x 坦克左上角点的x坐标
	 * @param y 坦克左上角点的y坐标
	 * @param tc 每一个坦克对象都持有一个tc对象
	 */
	public Tank(int x, int y, TankClient tc) {
		this(x, y);
		this.tc = tc;
	}

	/**
	 * 
	 * @param x 坦克左上角点的x坐标
	 * @param y 坦克左上角点的y坐标
	 * @param tc 每一个坦克对象都持有一个tc对象
	 *	@param good 用来区分坦克的阵营，true则我方阵营，反之
	 */
	public Tank(int x, int y, TankClient tc, boolean good) {
		this(x, y, tc);
		this.good = good;
	}
	/**
	 * 
	 * @param x  坦克左上角点的x坐标
	 * @param y 坦克左上角点的y坐标
	 * @param tc
	 * @param good
	 * @param dir 指定坦克一开始的运动方向
	 */
	public Tank(int x, int y, TankClient tc, boolean good, Direction dir) {
		this(x, y, tc, good);
		this.dir = dir;
	}
	/**
	 * @param g
	 * 对应着坦克绘图的基本操作
	 */
	public void draw(Graphics g) {
		Color c = g.getColor();
		if (!live) {
			if (!good) {
				tc.tanks.remove(this);
			}
			return;
		} else if (!good)
			g.setColor(Color.BLUE);
		else
			g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		if (good) {
			bb.draw(g);
		}
		/*
		 * bL = false; bU = false; bR = false; bD = false; 有点问题哟
		 */
		switch (ptDir) {// 此处
		case L:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT / 2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT / 2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH, y + Tank.HEIGHT);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH / 2, y + Tank.HEIGHT);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y + Tank.HEIGHT);
			break;
		}
		if (dir != Direction.STOP)
			ptDir = dir;
		move();
	}
	/**
	 * 为外界提供用以监听某些特定键的按下的接口
	 * @param e 键盘事件
	 */
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		// 此处switch语句没有break语句会出现问题
		switch (key) {
		case KeyEvent.VK_F2:
			if(this.isGood()&&!this.isLive()) {
				this.setLife(100);
				this.setLive(true);
			}
		case KeyEvent.VK_CONTROL:
			// tc.missiles.add(fire());不适合调试
			if (this.live)
				fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		case KeyEvent.VK_A:
			this.superFire();
		}
		locateDirection();
	}
	/**
	 * 释放时候的监听接口
	 * @param e 键盘事件
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		// 此处switch语句没有break语句会出现问题
		switch (key) {

		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();
	}

	void move() {
		oldX = x;
		oldY = y;
		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		if (good) {
			if (x < 0) {
				x = TankClient.GAME_WIDTH - WIDTH;
			}
			if (x > (TankClient.GAME_WIDTH - WIDTH)) {
				x = 0;
			} // 此处添加对y的处理会出现问题
			if (y < 30) {
				y = TankClient.GAME_HEIGHT - 30;
			}
			if (y > (TankClient.GAME_HEIGHT - 30)) {
				y = 30;
			}
		} else {
			if (x < 0) {
				x = 0;
			}
			if (x > (TankClient.GAME_WIDTH - WIDTH)) {
				x = TankClient.GAME_WIDTH - WIDTH;
			} // 此处添加对y的处理会出现问题
			if (y < 30) {
				y = 30;
			}
			if (y > (TankClient.GAME_HEIGHT - 30)) {
				y = TankClient.GAME_HEIGHT - 30;
			}

			Direction[] dirs = Direction.values();
			if (step == 0) {

				step = r.nextInt(10) + 3;
				int ni = r.nextInt(dirs.length);
				dir = dirs[ni];
			}
			step--;

			if (r.nextInt(40) > 25) {
				this.fire();
			}
		}

	}
	
	private void stay() {
		x = oldX;
		y = oldY;
	}

	void locateDirection() {
		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
	}

	void fire() {

		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, ptDir, tc, good);// 这里曾经没有传入tc参数，导致异常
		tc.missiles.add(m);
	}

	void fire(Direction dir) {
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, dir, tc, good);// 这里曾经没有传入tc参数，导致异常
		tc.missiles.add(m);
	}

	void superFire() {

		Direction[] dirs = Direction.values();

		for (int i = 0; i < 8; i++) {
			fire(dirs[i]);
		}
	}
	/**
	 * 
	 * 判断坦克是否处于存活状态
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * 设定坦克的生死
	 * @param live 布尔类型的值
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	
	Rectangle getRectangle() {

		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	/**
	 * 用来判断坦克所属阵营
	 * @return 布尔类型的值
	 */
	public boolean isGood() {

		return good;
	}
	/**
	 * 判定判定会不会和墙相撞
	 * @param w 墙
	 * @return布尔类型的值
	 */
	public boolean collideWall(Wall w) {
		if (live && this.getRectangle().intersects(w.getRectangle())) {
			this.stay();
			return true;
		}
		return false;
	}
	/**
	 * 判断坦克之间是否相撞
	 * @param tanks 一个装满坦克的列表
	 * @return布尔类型的值
	 */
	public boolean collideTank(ArrayList<Tank> tanks) {

		for (int i = 0; i < tanks.size(); i++) {

			Tank t = tanks.get(i);
			if (t != this) {
				if (this.live && t.isLive() && this.getRectangle().intersects(t.getRectangle())) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 获得坦克的生命值
	 */
	public int getLife() {
		return life;
	}
	/**
	 * 用来设定坦克的生命值
	 * @param life 坦克的生命值
	 */
	public void setLife(int life) {
		this.life = life;
	}
	/**
	 * 专门用来设定坦克血量的类
	 * @author dmzc
	 *
	 */
	private class BloodBar {

		public void draw(Graphics g) {

			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y - 10, WIDTH, 10);
			int w = life * WIDTH / 100;
			g.fillRect(x, y - 10, w, 10);
		}
	}
	/**
	 * 用来吃果子的方法
	 * @param f Fruit对象
	 */
	public void eat(Fruit f) {
		
		if(!live)return;
		if(this.life == 0)this.setLive(false);
		if(this.live&&f.isLive()&&(this.getRectangle().intersects(f.getRectangle()))&&this.isGood()) {
			
			if(f.isGood()) {
				
				this.setLife(this.getLife() + 10);
				f.setLive(false);
			}
			else {
				this.setLife(this.getLife() - 10);
				f.setLive(false);
			}
				
			}
	}
}
