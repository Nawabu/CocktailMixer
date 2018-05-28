package de.mannheim.nawabu.cocktail.view;

import de.mannheim.nawabu.cocktail.ArduinoController;
import de.mannheim.nawabu.cocktail.model.CocktailDB;
import de.mannheim.nawabu.cocktail.model.Recipe;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SelectCocktailPanel extends ViewTemplate implements ActionListener {
    private CocktailDB db = CocktailDB.getInstance();
    private ArduinoController arduino = ArduinoController.getInstance();
    private int x = 0;
    private int y = 0;
    private int amountRecipes;
    private int page = 1;
	
	public SelectCocktailPanel(JFrame main) {
		super(main);
		amountRecipes = db.amountCraftableRecipes();

		lblWindowname.setText("Cocktails");
		initButton("tl", "Konfiguration", this, "config");
		btnTopBR.setVisible(false);
		initButton("bl", "<--", this, "previous");
		initButton("br", "-->", this, "next");

		toggleNavigation();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gridBagLayout);

		showPage();

		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "config":
			ConfigPanel conf = new ConfigPanel(mainFrame);
			break;
        case "previous":
            page--;
            showPage();
            toggleNavigation();
            break;
        case "next":
            page++;
            showPage();
            toggleNavigation();
            break;
		default:
			break;
		}
	}

	private void showPage() {
        x = 0;
        y = 0;
        contentPanel.removeAll();

        ArrayList<Recipe> recipes = db.getCraftableRecipes(page);

        for (Recipe i : recipes) {
            drawCocktailButton(i);
        }
    }

	private void drawCocktailButton(Recipe recipe) {
        JButton btnCocktail = new JButton(recipe.getName());
        btnCocktail.addActionListener(e -> arduino.makeCocktail(mainFrame, recipe));
        GridBagConstraints gbc_btnCocktail = new GridBagConstraints();
        gbc_btnCocktail.fill = GridBagConstraints.BOTH;
        gbc_btnCocktail.insets = new Insets(0, 0, 10, 10);
        gbc_btnCocktail.gridx = x;
        gbc_btnCocktail.gridy = y;
        contentPanel.add(btnCocktail, gbc_btnCocktail);

        x++;
        if(x>=4) {
            x = 0;
            y++;
        }

        revalidate();
        repaint();
    }

    private void toggleNavigation() {
        if(page>1)
            btnBottomBL.setVisible(true);
        else
            btnBottomBL.setVisible(false);

        if((page*12) < amountRecipes)
            btnBottomBR.setVisible(true);
        else
            btnBottomBR.setVisible(false);
    }
}
