package com.xueqiu.bean;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BaiscJframe {

	public static final String TITLE = "Cellular Automata - Squaring Example";

	private static int maxWidth = 600;
	private static int maxHeight = 600;

	public static void launch() {
		final JFrame frame = new JFrame(TITLE);
		frame.setLocation(20, 20);
		frame.setPreferredSize(new Dimension(maxWidth, maxHeight));
		frame.setResizable(false);
		frame.setFocusable(true);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		final JPanel panel = new JPanel();
		panel.setLocation(20, 20);
		panel.setVisible(true);
		panel.setPreferredSize(new Dimension(maxWidth, maxHeight));
		panel.setFocusable(true);
		panel.setBackground(Color.white);

		
		// Panel setup, toggle visibility on frame
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}

}
