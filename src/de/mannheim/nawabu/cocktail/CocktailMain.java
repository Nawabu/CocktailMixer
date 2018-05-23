package de.mannheim.nawabu.cocktail;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class CocktailMain {
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	public static void main(String[] args) {
		JFrame main = new JFrame();
		main.setSize(800, 480);		
//		device.setFullScreenWindow(main);
		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
		
		SelectCocktailPanel cocktailPanel = new SelectCocktailPanel(main);
	}

}
