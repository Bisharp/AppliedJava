package com.dig.www.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

import com.dig.www.start.Board;

public class Time implements ActionListener {

	private Timer timer;
	private Board owner;

	private static final int SECOND = 1000;
	private static final int CHANGE = 7;
	private static final int CHANGE_PER = 1;
	private static final int END = 13;
	private int colonTimer = 0;

	private float time;
	private boolean isAM;

	public Time(Board b) {
		timer = new Timer(SECOND, this);
		owner = b;

		String time = String.format("%tr", new Date());
		String[] s = time.split(":");

		this.time = Integer.parseInt(s[0]) + (Float.parseFloat(s[1]) / 100);
		String t = s[s.length - 1];

		isAM = t.endsWith("AM");
	}

	public void start() {
		timer.start();
	}

	private int previousTime = -1;

	@Override
	public synchronized void actionPerformed(ActionEvent arg0) {

		time += 0.01f;

		if (decimalPart(time) >= 0.60)
			time = Math.round(time);

		if (time >= END)
			time = 1;
		else if (time == 12)
			isAM = !isAM;

		if (previousTime != getGeneralTime())
			owner.updateBackground();

		previousTime = getGeneralTime();
	}

	private static final int X = 10;
	private static final int Y = 200;
	public void draw(Graphics2D g2d) {
		
		g2d.setColor(Color.black);
		g2d.fillRect(X, Y, 200, 50);
		g2d.setColor(Color.green);
		g2d.drawString(toString(), 40 + X - (time >= 10 ? 15 : 0), Y + 30);
		g2d.drawString(getColon(), 40 + X + 15, Y + 28);
		g2d.setStroke(new BasicStroke(5));
		g2d.drawRect(X, Y, 200, 50);
	}

	protected float decimalPart(float f) {
		while (f >= 1)
			f--;
		return f;
	}

	public void pause() {
		if (timer != null)
			timer.stop();
	}

	public void resume() {
		if (timer != null)
			timer.restart();
	}

	public void end() {

		if (timer != null)
			timer.stop();
	}

	public float getTime() {
		return time;
	}

	public String toString() {
		colonTimer++;
		if (colonTimer >= 50)
			colonTimer = 0;

		return String.format("%.2f", time).replace('.', ' ') + " " + (isAM ? "A.M." : "P.M.");
	}

	public String getColon() {
		return colonTimer >= 30 ? " " : ":";
	}

	public static final int SUNRISE = 0;
	public static final int DAY = 1;
	public static final int SUNSET = 2;
	public static final int NIGHT = 3;

	public int getGeneralTime() {

		if (time >= CHANGE && time <= CHANGE + CHANGE_PER)
			return isAM ? SUNRISE : SUNSET;
		else if (this.time >= 7 && this.time < 12)
			if (isAM)
				return DAY;
			else
				return NIGHT;
		else if (!isAM)
			return DAY;
		else
			return NIGHT;
	}

	public String trans(int generalTime) {

		if (generalTime == SUNRISE)
			return "Sunrise";
		else if (generalTime == SUNSET)
			return "Sunset";
		else if (generalTime == DAY)
			return "Day";
		else
			return "Night";
	}
}
