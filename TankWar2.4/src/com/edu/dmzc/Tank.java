package com.edu.dmzc;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * 
 * @author dmzc
 *�⽫�ṹ����һ��̹�˶���
 */
public class Tank {
	int x, y;
	/**
	 * ̹��x�����ƶ����ٶ�
	 */
	public static final int XSPEED = 5;
	/**
	 * ̹��y�����ƶ����ٶ�
	 */
	public static final int YSPEED = 5;
	/**
	 * ̹�˵Ŀ��
	 */
	public static final int WIDTH = 30;
	/**
	 * ̹�˵ĸ߶�
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
	 * @param x ̹�����Ͻǵ��x����
	 * @param y ̹�����Ͻǵ��y����
	 */
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
		oldX = x;
		oldY = y;
	}
	/**
	 * 
	 * @param x ̹�����Ͻǵ��x����
	 * @param y ̹�����Ͻǵ��y����
	 * @param tc ÿһ��̹�˶��󶼳���һ��tc����
	 */
	public Tank(int x, int y, TankClient tc) {
		this(x, y);
		this.tc = tc;
	}

	/**
	 * 
	 * @param x ̹�����Ͻǵ��x����
	 * @param y ̹�����Ͻǵ��y����
	 * @param tc ÿһ��̹�˶��󶼳���һ��tc����
	 *	@param good ��������̹�˵���Ӫ��true���ҷ���Ӫ����֮
	 */
	public Tank(int x, int y, TankClient tc, boolean good) {
		this(x, y, tc);
		this.good = good;
	}
	/**
	 * 
	 * @param x  ̹�����Ͻǵ��x����
	 * @param y ̹�����Ͻǵ��y����
	 * @param tc
	 * @param good
	 * @param dir ָ��̹��һ��ʼ���˶�����
	 */
	public Tank(int x, int y, TankClient tc, boolean good, Direction dir) {
		this(x, y, tc, good);
		this.dir = dir;
	}
	/**
	 * @param g
	 * ��Ӧ��̹�˻�ͼ�Ļ�������
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
		 * bL = false; bU = false; bR = false; bD = false; �е�����Ӵ
		 */
		switch (ptDir) {// �˴�
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
	 * Ϊ����ṩ���Լ���ĳЩ�ض����İ��µĽӿ�
	 * @param e �����¼�
	 */
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		// �˴�switch���û��break�����������
		switch (key) {
		case KeyEvent.VK_F2:
			if(this.isGood()&&!this.isLive()) {
				this.setLife(100);
				this.setLive(true);
			}
		case KeyEvent.VK_CONTROL:
			// tc.missiles.add(fire());���ʺϵ���
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
	 * �ͷ�ʱ��ļ����ӿ�
	 * @param e �����¼�
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		// �˴�switch���û��break�����������
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
			} // �˴���Ӷ�y�Ĵ�����������
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
			} // �˴���Ӷ�y�Ĵ�����������
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
		Missile m = new Missile(x, y, ptDir, tc, good);// ��������û�д���tc�����������쳣
		tc.missiles.add(m);
	}

	void fire(Direction dir) {
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, dir, tc, good);// ��������û�д���tc�����������쳣
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
	 * �ж�̹���Ƿ��ڴ��״̬
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * �趨̹�˵�����
	 * @param live �������͵�ֵ
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	
	Rectangle getRectangle() {

		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	/**
	 * �����ж�̹��������Ӫ
	 * @return �������͵�ֵ
	 */
	public boolean isGood() {

		return good;
	}
	/**
	 * �ж��ж��᲻���ǽ��ײ
	 * @param w ǽ
	 * @return�������͵�ֵ
	 */
	public boolean collideWall(Wall w) {
		if (live && this.getRectangle().intersects(w.getRectangle())) {
			this.stay();
			return true;
		}
		return false;
	}
	/**
	 * �ж�̹��֮���Ƿ���ײ
	 * @param tanks һ��װ��̹�˵��б�
	 * @return�������͵�ֵ
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
	 * ���̹�˵�����ֵ
	 */
	public int getLife() {
		return life;
	}
	/**
	 * �����趨̹�˵�����ֵ
	 * @param life ̹�˵�����ֵ
	 */
	public void setLife(int life) {
		this.life = life;
	}
	/**
	 * ר�������趨̹��Ѫ������
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
	 * �����Թ��ӵķ���
	 * @param f Fruit����
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
