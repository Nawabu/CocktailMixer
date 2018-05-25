package de.mannheim.nawabu.cocktail.view;

import de.mannheim.nawabu.cocktail.model.CocktailDB;
import de.mannheim.nawabu.cocktail.model.Ingredient;
import de.mannheim.nawabu.cocktail.model.Recipe;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.*;

public class IngredientsPanel extends ViewTemplate implements ActionListener {
    private CocktailDB db = CocktailDB.getInstance();
	private int amountIngredients;
	private int page = 1;
	private int row = 1;
	private int targetID;
	private String target;
	private Recipe recipe;

	public IngredientsPanel(JFrame main, String target, Recipe recipe) {
	    this(main, target, 0);
	    this.recipe = recipe;
    }

	public IngredientsPanel(JFrame main, String target, int targetID) {
		super(main);

		this.targetID = targetID;
		this.target = target;

		amountIngredients = db.amountIngredients();

		lblWindowname.setText("Zutaten");
		initButton("tl", "zurück", this, "cancel");
		initButton("tr", "neu", this, "new");
		initButton("bl", "<--", this, "previous");
		initButton("br", "-->", this, "next");

		toggleNavigation();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {100, 200, 100, 100};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
		contentPanel.setLayout(gridBagLayout);

		showPage();
		
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    InputsPanel inputsPanel;
	    NewRecipePane newRecipePane;
		switch (e.getActionCommand()) {
		case "cancel":
		    if(target.equals("pump")) {
                inputsPanel = new InputsPanel(mainFrame);
            }
		    else {
                newRecipePane = new NewRecipePane(mainFrame);
                newRecipePane.setRecipe(recipe);
            }
			break;
		case "new":
			NewIngredientPanel newIng = new NewIngredientPanel(mainFrame, target, recipe);
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

	    if((page*7) < amountIngredients)
	        btnBottomBR.setVisible(true);
	    else
	        btnBottomBR.setVisible(false);
    }

	private void createHeader() {
        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.insets = new Insets(0, 0, 10, 10);
        gbc_lblName.gridx = 1;
        gbc_lblName.gridy = 0;
        contentPanel.add(lblName, gbc_lblName);

        JLabel lblGre = new JLabel("Größe");
        lblGre.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_lblGre = new GridBagConstraints();
        gbc_lblGre.insets = new Insets(0, 0, 10, 10);
        gbc_lblGre.gridx = 2;
        gbc_lblGre.gridy = 0;
        contentPanel.add(lblGre, gbc_lblGre);
    }

	private void showPage() {
	    row = 1;

	    contentPanel.removeAll();
	    createHeader();

        ArrayList<Ingredient> ingredients = db.getIngredients(page);

        for(ListIterator<Ingredient> li = ingredients.listIterator(); li.hasNext();) {
            Ingredient i = li.next();

            addIngredient(i);
        }
    }

	private void addIngredient(Ingredient i) {
        JButton btnSelect = new JButton("+");
        btnSelect.addActionListener(e -> {
            if(target.equals("pump")) {
                db.updatePump(i.getId(), targetID);
                InputsPanel inputs = new InputsPanel(mainFrame);
            }
            else if(target.equals("ingredient")) {
                i.setAmount(2);
                recipe.addIngredient(i);
                NewRecipePane newRecipe = new NewRecipePane(mainFrame);
                newRecipe.setRecipe(recipe);
            }
            else if(target.equals("filler")) {
                recipe.setFiller(i);
                NewRecipePane newRecipe = new NewRecipePane(mainFrame);
                newRecipe.setRecipe(recipe);
            }
        });
        GridBagConstraints gbc_btnSelect = new GridBagConstraints();
        gbc_btnSelect.insets = new Insets(0, 0, 10, 10);
        gbc_btnSelect.gridx = 0;
        gbc_btnSelect.gridy = row;
        contentPanel.add(btnSelect, gbc_btnSelect);

        JLabel lblName = new JLabel(i.getName());
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.insets = new Insets(0, 0, 10, 10);
        gbc_lblName.gridx = 1;
        gbc_lblName.gridy = row;
        contentPanel.add(lblName, gbc_lblName);

        JLabel lblGre = new JLabel(String.valueOf(i.getSize()) + " ml");
        lblGre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_lblGre = new GridBagConstraints();
        gbc_lblGre.insets = new Insets(0, 0, 10, 10);
        gbc_lblGre.gridx = 2;
        gbc_lblGre.gridy = row;
        contentPanel.add(lblGre, gbc_lblGre);

        JButton btnDelete = new JButton("löschen");
        btnDelete.addActionListener(e -> {
            db.deleteIngredient(i.getId());
            showPage();
        });
        GridBagConstraints gbc_btnDelete = new GridBagConstraints();
        gbc_btnDelete.insets = new Insets(0, 0, 10, 10);
        gbc_btnDelete.gridx = 3;
        gbc_btnDelete.gridy = row;
        contentPanel.add(btnDelete, gbc_btnDelete);

        row++;
        revalidate();
        repaint();
    }
}
