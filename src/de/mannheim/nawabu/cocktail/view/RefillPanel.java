package de.mannheim.nawabu.cocktail.view;

import de.mannheim.nawabu.cocktail.CocktailMain;
import de.mannheim.nawabu.cocktail.model.CocktailDB;
import de.mannheim.nawabu.cocktail.model.Ingredient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefillPanel extends ViewTemplate implements ActionListener {
	private JTextField textLevel;
	private JLabel lblError;
	private Ingredient ingredient;
	private String target;
	private CocktailDB db = CocktailDB.getInstance();

	public RefillPanel(JFrame main, Ingredient ingredient, String target) {
		super(main);

		this.ingredient = ingredient;
		this.target = target;

		initButton("tl", "abbrechen", this, "cancel");
		initButton("tr", "Speichern", this, "save");
		btnBottomBL.setVisible(false);
		btnBottomBR.setVisible(false);

		lblWindowname.setText("Füllstand");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		contentPanel.setLayout(gridBagLayout);
		
		JLabel lblName = new JLabel("Füllstand: ");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 10, 10);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		contentPanel.add(lblName, gbc_lblName);
		
		textLevel = new JTextField();
		textLevel.setText(String.valueOf(ingredient.getSize()));
		textLevel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_textSize = new GridBagConstraints();
		gbc_textSize.insets = new Insets(0, 0, 10, 10);
		gbc_textSize.gridx = 1;
		gbc_textSize.gridy = 0;
		contentPanel.add(textLevel, gbc_textSize);
		textLevel.setColumns(10);

		JLabel lblML = new JLabel("ml");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblML = new GridBagConstraints();
        gbc_lblML.anchor = GridBagConstraints.EAST;
        gbc_lblML.insets = new Insets(0, 0, 10, 10);
        gbc_lblML.gridx = 2;
        gbc_lblML.gridy = 0;
		contentPanel.add(lblML, gbc_lblML);

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
        SelectCocktailPanel cocktailMain;
        InputsPanel inputsPanel;

		switch (e.getActionCommand()) {
		case "cancel":
            if(target.equals("main"))
                cocktailMain = new SelectCocktailPanel(mainFrame);
            else
                inputsPanel = new InputsPanel(mainFrame);
			break;
		case "save":
            try {
                int size = Integer.parseInt(textLevel.getText());
                db.updateIngredientLevel(ingredient.getId(), size);
            }
            catch (NumberFormatException ex) {
                lblError.setText("Für Größe bitte nur Zahlen eingeben.");
                return;
            }

            if(target.equals("main"))
                cocktailMain = new SelectCocktailPanel(mainFrame);
            else
                inputsPanel = new InputsPanel(mainFrame);
            break;
		default:
			break;
		}
	}

}
