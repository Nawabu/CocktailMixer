package de.mannheim.nawabu.cocktail.view;

import de.mannheim.nawabu.cocktail.model.CocktailDB;
import de.mannheim.nawabu.cocktail.model.Ingredient;
import de.mannheim.nawabu.cocktail.model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;

public class RecipePanel extends ViewTemplate implements ActionListener {
    private CocktailDB db = CocktailDB.getInstance();
	private int amountRecipes;
	private int page = 1;
	private int row = 1;

	public RecipePanel(JFrame main) {
		super(main);
        amountRecipes = db.amountRecipes();

		lblWindowname.setText("Cocktail Rezepte");
		initButton("tl", "zurück", this, "cancel");
		initButton("tr", "neu", this, "new");
		initButton("bl", "<--", this, "previous");
		initButton("br", "-->", this, "next");

		toggleNavigation();

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {200, 200, 100};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0};
        contentPanel.setLayout(gridBagLayout);

		showPage();
		
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cancel":
			ConfigPanel config = new ConfigPanel(mainFrame);
			break;
		case "new":
			NewRecipePane recipe = new NewRecipePane(mainFrame);
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

	private void toggleNavigation() {
	    if(page>1)
	        btnBottomBL.setVisible(true);
	    else
	        btnBottomBL.setVisible(false);

	    if((page*7) < amountRecipes)
	        btnBottomBR.setVisible(true);
	    else
	        btnBottomBR.setVisible(false);
    }

	private void createHeader() {
        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.insets = new Insets(0, 0, 10, 10);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 0;
        contentPanel.add(lblName, gbc_lblName);

        JLabel lblContent = new JLabel("Inhalt");
        lblContent.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_lblContent = new GridBagConstraints();
        gbc_lblContent.insets = new Insets(0, 0, 10, 10);
        gbc_lblContent.gridx = 1;
        gbc_lblContent.gridy = 0;
        contentPanel.add(lblContent, gbc_lblContent);
    }

	private void showPage() {
	    row = 1;

	    contentPanel.removeAll();
	    createHeader();

        ArrayList<Recipe> recipes = db.getRecipes(page);

        for (Recipe i : recipes) {
            addRecipe(i.getId(), i.getName(), i.getIngredients());
        }
    }

	private void addRecipe(int id, String name, ArrayList<Ingredient> ingredients) {
        JLabel lblName = new JLabel(name);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.insets = new Insets(0, 0, 10, 10);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = row;
        contentPanel.add(lblName, gbc_lblName);

        StringBuilder content = new StringBuilder();
        for (Ingredient i : ingredients) {
            content.append(i.getName()).append(", ");
        }
        if(content.length()>0)
            content = new StringBuilder(content.substring(0, content.length() - 2));

        JLabel lblContent = new JLabel(content.toString());
        lblContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_lblContent = new GridBagConstraints();
        gbc_lblContent.insets = new Insets(0, 0, 10, 10);
        gbc_lblContent.anchor = GridBagConstraints.WEST;
        gbc_lblContent.gridx = 1;
        gbc_lblContent.gridy = row;
        contentPanel.add(lblContent, gbc_lblContent);

        JButton btnDelete = new JButton("löschen");
        btnDelete.addActionListener(e -> {
            db.delteRecipe(id);
            showPage();
        });
        GridBagConstraints gbc_btnDelete = new GridBagConstraints();
        gbc_btnDelete.insets = new Insets(0, 0, 10, 10);
        gbc_btnDelete.gridx = 2;
        gbc_btnDelete.gridy = row;
        contentPanel.add(btnDelete, gbc_btnDelete);

        row++;
        revalidate();
        repaint();
    }
}
