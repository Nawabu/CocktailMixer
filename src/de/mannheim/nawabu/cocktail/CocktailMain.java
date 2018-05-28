package de.mannheim.nawabu.cocktail;

import de.mannheim.nawabu.cocktail.model.CocktailDB;
import de.mannheim.nawabu.cocktail.view.*;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class CocktailMain {
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	public static void main(String[] args) {
		JFrame main = new JFrame();
		main.setSize(800, 480);		
		//device.setFullScreenWindow(main);
		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);

		CocktailDB db = CocktailDB.getInstance();
		
//		SelectCocktailPanel cocktailPanel = new SelectCocktailPanel(main);
        //InputsPanel inputs = new InputsPanel(main);
        //NewRecipePane recipe = new NewRecipePane(main);
		//RecipePanel recipePanel = new RecipePanel(main);

		ArduinoController arduino = ArduinoController.getInstance();
		arduino.cleanPumps(main, 10000);
	}

}
