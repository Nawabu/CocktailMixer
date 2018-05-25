package de.mannheim.nawabu.cocktail.view;
import de.mannheim.nawabu.cocktail.Config;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;

public abstract class ViewTemplate extends JPanel{
	
	protected JButton btnTopBL, btnTopBR, btnBottomBL, btnBottomBR;
	protected JLabel lblWindowname;
	protected JPanel contentPanel;
	protected JFrame mainFrame;
	
	public ViewTemplate(JFrame main) {
		this.mainFrame = main;
		this.setLayout(new CardLayout(10, 10));
		
		// Panels
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 10));
		
		JPanel topPanel = new JPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel bottomPanel = new JPanel();
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		// Top Panel content
		btnTopBL = new JButton("topBL");
		btnTopBL.setPreferredSize(new Dimension(100, Config.buttonHeight));
		topPanel.add(btnTopBL);
		
		topPanel.add(Box.createHorizontalGlue());
		
		lblWindowname = new JLabel("windowName");
		lblWindowname.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWindowname.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(lblWindowname);
		
		topPanel.add(Box.createHorizontalGlue());
		
		btnTopBR = new JButton("topBR");
		btnTopBR.setPreferredSize(new Dimension(100, Config.buttonHeight));
		topPanel.add(btnTopBR);
		
		// Bottom Panel content
		btnBottomBL = new JButton("bottomBL");
		btnBottomBL.setPreferredSize(new Dimension(100, Config.buttonHeight));
		bottomPanel.add(btnBottomBL);
		
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(Box.createHorizontalGlue());
		
		btnBottomBR = new JButton("bottomBR");
		btnBottomBR.setPreferredSize(new Dimension(100, Config.buttonHeight));
		bottomPanel.add(btnBottomBR);
		
		contentPanel = new JPanel();
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		mainFrame.getContentPane().removeAll();
		mainFrame.getContentPane().add(this);
	}
	
	public void initButton(String btn, String text, ActionListener listener, String command) {
		switch (btn) {
		case "tl":
			btnTopBL.setText(text);
			btnTopBL.addActionListener(listener);
			btnTopBL.setActionCommand(command);
			break;
		case "tr":
			btnTopBR.setText(text);
			btnTopBR.addActionListener(listener);
			btnTopBR.setActionCommand(command);
			break;
		case "bl":
			btnBottomBL.setText(text);
			btnBottomBL.addActionListener(listener);
			btnBottomBL.setActionCommand(command);
			break;
		case "br":
			btnBottomBR.setText(text);
			btnBottomBR.addActionListener(listener);
			btnBottomBR.setActionCommand(command);
			break;
		default:
			break;
		}
	}
}
