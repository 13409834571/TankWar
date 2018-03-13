package com.edu.dmzc;
import java.awt.*;
/**
 * 处理爆炸效果的类
 * @author dmzc
 *
 */
public class Explode {

	int x;
	int y;
	int step;
	boolean live = true;
	int [] demeter = {120,115,110,105,100,95,80,60,40,30,10};
	TankClient tc;
	Explode(int x,int y,TankClient tc) {
		
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	void draw(Graphics g) {
		
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step == demeter.length) {
			
			live = false;
			step = 0;
			return ;
		}
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, demeter[step],demeter[step]);
		g.setColor(c);
		step ++;
	}
}
