package com.dig.www.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SoundPlayer {
	public Thread playerThread;
	private Player player;
	private JComponent component;

	private boolean musicEnabled = true;

	public void playSound(JComponent component, String url) {

		if (!musicEnabled)
			return;

		// System.out.println("Playing sound " + url);
		this.component = component;
		InputStream is;
		BufferedInputStream bis;
		try {
			if (url.startsWith("file:"))
				is = new FileInputStream(url.substring(5));
			else {
				is = getClass().getResourceAsStream("/sound/" + url);
			}
			bis = new BufferedInputStream(is);

			if (url.endsWith(".mp3") || url.endsWith("m4a"))
				playWithJL(url, bis);
			else {
				AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
				Clip clip = AudioSystem.getClip();
				clip.open(ais);
				clip.start();
			}
		} catch (Exception e) {
			String message = "Got Exception playing sound file " + url + "\n"
					+ e.getMessage();
			JOptionPane.showMessageDialog(component, message,
					"SoundPlayer Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void playWithJL(String url, BufferedInputStream bis) {
		try {
			player = new Player(bis);
			if (playerThread != null && playerThread.isAlive())
				playerThread.stop();
		} catch (JavaLayerException e1) {
			String message = "Got Exception playing sound mp3 file " + url
					+ "\n" + e1.getMessage();
			JOptionPane.showMessageDialog(component, message,
					"SoundPlayer Error", JOptionPane.ERROR_MESSAGE);
		}

		// run in new thread to play in background
		playerThread = new Thread() {
			@Override
			public void run() {
				try {
					player.play();
				} catch (Exception e) {
					String message = "Got Exception playing sound mp3 file "
							+ url + " \n" + e.getMessage();
					JOptionPane.showMessageDialog(component, message,
							"SoundPlayer Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		playerThread.start();
	}

	public boolean isPlaying() {
		return playerThread.isAlive();
	}

	@SuppressWarnings("deprecation")
	public void pauseMusic() {
		// TODO Auto-generated method stub
		if (playerThread != null)
			playerThread.suspend();
	}

	@SuppressWarnings("deprecation")
	public void resumeMusic() {
		if (playerThread != null)
			playerThread.resume();
	}

	@SuppressWarnings("deprecation")
	public void stopMusic() {
		if (playerThread != null)
			playerThread.stop();
	}
}