package de.mannheim.nawabu.cocktail.view;

import de.mannheim.nawabu.cocktail.model.CocktailDB;
import de.mannheim.nawabu.cocktail.model.Ingredient;
import de.mannheim.nawabu.cocktail.model.Recipe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;

public class NewIngredientPanel extends ViewTemplate implements ActionListener {
	private JTextField textName;
	private JTextField textSize;
	private JLabel lblError;
	private CocktailDB db = CocktailDB.getInstance();
	private String target;
	private int targetID;
	private Recipe recipe;

	public NewIngredientPanel(JFrame main, String target, Recipe recipe) {
	    this(main, target, 0);
	    this.recipe = recipe;
    }

	public NewIngredientPanel(JFrame main, String target, int targetID) {
		super(main);
		this.target = target;
		this.targetID = targetID;

		initButton("tl", "abbrechen", this, "cancel");
		initButton("tr", "Speichern", this, "save");
		btnBottomBL.setVisible(false);
		btnBottomBR.setVisible(false);

		lblWindowname.setText("Neue Zutat");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		contentPanel.setLayout(gridBagLayout);
		
		JLabel lblName = new JLabel("Name: ");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 10, 10);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		contentPanel.add(lblName, gbc_lblName);
		
		textName = new JTextField();
		textName.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_textName = new GridBagConstraints();
		gbc_textName.insets = new Insets(0, 0, 10, 10);
		gbc_textName.gridx = 1;
		gbc_textName.gridy = 0;
		contentPanel.add(textName, gbc_textName);
		textName.setColumns(10);
		
		JLabel lblSize = new JLabel("Standardgr\u00F6\u00DFe (ml):");
		lblSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblSize = new GridBagConstraints();
		gbc_lblSize.anchor = GridBagConstraints.EAST;
		gbc_lblSize.insets = new Insets(0, 0, 10, 10);
		gbc_lblSize.gridx = 0;
		gbc_lblSize.gridy = 1;
		contentPanel.add(lblSize, gbc_lblSize);
		
		textSize = new JTextField();
		textSize.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_textSize = new GridBagConstraints();
		gbc_textSize.insets = new Insets(0, 0, 10, 10);
		gbc_textSize.gridx = 1;
		gbc_textSize.gridy = 1;
		contentPanel.add(textSize, gbc_textSize);
		textSize.setColumns(10);

        lblError = new JLabel("");
        lblError.setForeground(Color.RED);
        lblError.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_lblError = new GridBagConstraints();
        gbc_lblError.gridwidth = 3;
        gbc_lblError.insets = new Insets(0, 0, 10, 10);
        gbc_lblError.gridx = 0;
        gbc_lblError.gridy = 2;
        contentPanel.add(lblError, gbc_lblError);
		
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        IngredientsPanel ingredients;
		switch (e.getActionCommand()) {
		case "cancel":
			ingredients = new IngredientsPanel(mainFrame, target, targetID);
            if(recipe == null)
                ingredients = new IngredientsPanel(mainFrame, target, targetID);
            else
                ingredients = new IngredientsPanel(mainFrame, target, recipe);
			break;
		case "save":
		    try {
                int size = Integer.parseInt(textSize.getText());
                db.saveIngredient(textName.getText(), size, size);
            }
            catch (NumberFormatException ex) {
                lblError.setText("Für Größe bitte nur Zahlen eingeben.");
                return;
            }
            if(recipe == null)
                ingredients = new IngredientsPanel(mainFrame, target, targetID);
		    else
		        ingredients = new IngredientsPanel(mainFrame, target, recipe);

        break;
		default:
			break;
		}
	}

}
