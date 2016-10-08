package com.xueqiu.bean;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class TestDialog {

	public static void main(String[] args) {

		JFrame a = new JFrame("中继数据库系统");
		Container c = new Container();
		a.setSize(200, 200);
		a.setLocation(100, 200);
		// a.setLayout(new BorderLayout());
		JButton b = new JButton("GO");
		

		
		c = a.getContentPane();
		c.add(b, BorderLayout.SOUTH);
		
		
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
			}
			
		});
		
//		a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		a.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		a.setVisible(true);

		a.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.out.println(1);
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(2);
				System.exit(0);
			}
		});
	}

}