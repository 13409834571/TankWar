package com.edu.dmzc;
import java.awt.*;//��java������˸�������֮���ڽ��е��Ե����ѧϰ
import java.awt.event.*;
import java.util.*;

//����һ���汾��F2���Ƽ���̹�˵ĸ�����д�����ĵ�ע��
/**
 * ����������Ϸ���е�������
 * @author dmzc
 *
 */
public class TankClient extends Frame {
	// ������ֳ����������ع�
	/**
	 * ��������Ϸ���ڵĿ��
	 */
	public static final int GAME_WIDTH = 800;
	/**
	 * ��������Ϸ���ڵĸ߶�
	 */
	public static final int GAME_HEIGHT = 600;
	Tank myTank = new Tank(50, 50, this, true, Tank.Direction.STOP);
	ArrayList<Missile> missiles = new ArrayList<Missile>();
	ArrayList<Explode> explodes = new ArrayList<Explode>();
	ArrayList<Tank> tanks = new ArrayList<Tank>();
	/*
	 * public static int TANK_LX = 50; public static int TANK_LY = 50; public static
	 * final int TANK_SW = 30; public static final int TANK_SH = 30;8
	 */
	Image offScreenImage = null;
	Wall w1 = new Wall(400, 300, 50,80, this);
	Wall w2 = new Wall(550, 50, 50, 150, this);
	Fruit f1 = new Fruit(true);
	Fruit f2 = new Fruit(false);
	/**
	 * ����Frame����ķ�����Ϊ�����߳��ػ��Ļ���
	 */
	public void paint(Graphics g) {
		/* if(m != null) m.draw(g); */
		if(tanks.isEmpty()) {
			for (int i = 0; i < 5; i++) {
				tanks.add(new Tank(60 * (i + 1), 80, this, false, Tank.Direction.D));
			}
		}
		g.drawString("the life is" +myTank.getLife(), 60, 110);
		g.drawString("missiles' size:" + missiles.size(), 50, 490);
		g.drawString("explodes' size:" + explodes.size(), 50, 500);
		g.drawString("Tanks' size:" + tanks.size(), 50, 510);
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			/*	
			 * if(m.isLive()) m.draw(g); else { System.out.println("û���ӵ��ˣ���������");
			 * System.exit(0); }
			 */
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}

		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}

		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collideWall(w1);
			t.collideWall(w2);
			t.collideTank(tanks);
			t.draw(g);
		}

		myTank.draw(g);
		myTank.eat(f1);
		myTank.eat(f2);
		w1.draw(g);
		w2.draw(g);
		f1.draw(g);
		f2.draw(g);
	}
	/**
	 * ����update������ʵ��˫���弼��
	 */
	public void update(Graphics g) {

		if (offScreenImage == null) {

			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.pink);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	/**
	 * Ϊ������Ϸ��������ڳ���
	 */
	public void lanchFrame() {

		for (int i = 0; i < 10; i++) {
			tanks.add(new Tank(60 * (i + 1), 80, this, false, Tank.Direction.D));
		}

		this.setLocation(100, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		/*
		 * this.addMouseListener(new MouseAdapter() {
		 * 
		 * public void mouseClicked(MouseEvent e) {
		 * 
		 * int x = e.getX(); int y = e.getY(); System.out.println(x + "," + y + "\n"); }
		 * 
		 * });
		 */
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}
	/**
	 * ��Ϊ������������
	 * @param args �˰汾�������в���
	 */
	public static void main(String[] args) {

		TankClient tc = new TankClient();
		tc.lanchFrame();
	}

	private class PaintThread implements Runnable {

		public void run() {

			while (true) {

				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {

					System.out.println("����������쳣");

				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter {

		
		public void keyPressed(KeyEvent e) {

			myTank.keyPressed(e);
		}

	
		public void keyReleased(KeyEvent e) {

			myTank.keyReleased(e);
		}

	}

}
