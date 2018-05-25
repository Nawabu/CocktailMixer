package de.mannheim.nawabu.cocktail.view;

import de.mannheim.nawabu.cocktail.model.CocktailDB;
import de.mannheim.nawabu.cocktail.model.Ingredient;
import de.mannheim.nawabu.cocktail.model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class NewRecipePane extends ViewTemplate implements ActionListener {
	private JTextField textName;
    private HashMap<Integer, JTextField> ingredientText = new HashMap<>();

	private JPanel dataPanel;
	private  JPanel ingredientPanel;

	private CocktailDB db = CocktailDB.getInstance();
	private Recipe recipe = new Recipe();

	public NewRecipePane(JFrame main) {
		super(main);

		initButton("tl", "abbrechen", this, "cancel");
		initButton("tr", "Speichern", this, "save");
		btnBottomBL.setVisible(false);
		btnBottomBR.setVisible(false);

		lblWindowname.setText("Neues Rezept");

		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        Box verticalBox = Box.createVerticalBox();
        contentPanel.add(verticalBox);

        dataPanel = new JPanel();
        verticalBox.add(dataPanel);
		contentPanel.add(verticalBox);

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {100, 200, 100};
		dataPanel.setLayout(gbl_panel);

        JPanel ingredientFlow = new JPanel();
        verticalBox.add(ingredientFlow);

        ingredientPanel = new JPanel();
        ingredientFlow.add(ingredientPanel);
        GridBagLayout gbl_ingredientPanel = new GridBagLayout();
        //gbl_ingredientPanel.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        ingredientPanel.setLayout(gbl_ingredientPanel);

        drawContent();
		
		revalidate();
		repaint();
	}

	public void setRecipe(Recipe r) {
	    this.recipe = r;
	    drawContent();
    }

    private void tempSaveRecipe() {
        recipe.setName(textName.getText());
        for(ListIterator<Ingredient> li = recipe.getIngredients().listIterator(); li.hasNext();) {
            Ingredient i = li.next();
            i.setAmount(Integer.parseInt(ingredientText.get(i.getId()).getText()));
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        IngredientsPanel ip;
        RecipePanel recipeP;

		switch (e.getActionCommand()) {
		case "cancel":
		    recipeP = new RecipePanel(mainFrame);
			break;
		case "save":
            saveRecipe();
            recipeP = new RecipePanel(mainFrame);
            break;
        case "filler":
            tempSaveRecipe();
            ip = new IngredientsPanel(mainFrame, "filler", recipe);
            break;
        case "ingredient":
            tempSaveRecipe();
            ip = new IngredientsPanel(mainFrame, "ingredient", recipe);
            break;
		default:
			break;
		}
	}

	private void saveRecipe() {
	    tempSaveRecipe();
        db.saveRecipe(recipe);
    }

	private void drawContent() {
	    dataPanel.removeAll();
	    ingredientPanel.removeAll();

	    drawData();
	    drawIngredients();

        revalidate();
        repaint();
    }

	private void drawData() {
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.anchor = GridBagConstraints.EAST;
        gbc_lblName.insets = new Insets(0, 0, 10, 10);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 0;
        dataPanel.add(lblName, gbc_lblName);

        textName = new JTextField();
        textName.setText(recipe.getName());
        textName.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_textName = new GridBagConstraints();
        gbc_textName.insets = new Insets(0, 0, 10, 10);
        gbc_textName.gridx = 1;
        gbc_textName.gridy = 0;
        dataPanel.add(textName, gbc_textName);
        textName.setColumns(10);

        JButton btnIngredient = new JButton("+ Zutat");
        btnIngredient.setActionCommand("ingredient");
        btnIngredient.addActionListener(this);
        GridBagConstraints gbc_btnIngredient = new GridBagConstraints();
        gbc_btnIngredient.insets = new Insets(0, 0, 10, 10);
        gbc_btnIngredient.gridx = 1;
        gbc_btnIngredient.gridy = 2;
        dataPanel.add(btnIngredient, gbc_btnIngredient);

        JLabel lblFiller = new JLabel("Auffüllen:");
        lblFiller.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_lblFiller = new GridBagConstraints();
        gbc_lblFiller.anchor = GridBagConstraints.EAST;
        gbc_lblFiller.insets = new Insets(0, 0, 10, 10);
        gbc_lblFiller.gridx = 0;
        gbc_lblFiller.gridy = 1;
        dataPanel.add(lblFiller, gbc_lblFiller);

        JLabel lblFillerName = new JLabel("");
        if(recipe.getFiller() != null)
            lblFillerName.setText(recipe.getFiller().getName());
        lblFillerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_lblFillerName = new GridBagConstraints();
        gbc_lblFillerName.insets = new Insets(0, 0, 10, 10);
        gbc_lblFillerName.gridx = 1;
        gbc_lblFillerName.gridy = 1;
        dataPanel.add(lblFillerName, gbc_lblFillerName);

        JButton btnFiller = new JButton("wählen");
        btnFiller.setActionCommand("filler");
        btnFiller.addActionListener(this);
        GridBagConstraints gbc_btnFiller = new GridBagConstraints();
        gbc_btnFiller.insets = new Insets(0, 0, 10, 10);
        gbc_btnFiller.gridx = 2;
        gbc_btnFiller.gridy = 1;
        dataPanel.add(btnFiller, gbc_btnFiller);
    }

	private void drawIngredients() {
        for(int x=0; x<7; x++){
            Component horizontalStrut = Box.createHorizontalStrut(100);
            GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
            gbc_horizontalStrut.insets = new Insets(0, 0, 10, 10);
            gbc_horizontalStrut.gridx = 3;
            gbc_horizontalStrut.gridy = x;
            ingredientPanel.add(horizontalStrut, gbc_horizontalStrut);
        }

	    ArrayList<Ingredient> ingredients = recipe.getIngredients();
        int x=0;
        for(ListIterator<Ingredient> li = ingredients.listIterator(); li.hasNext();){
            Ingredient i = li.next();
            int column = (x%2 == 0) ? 0 : 4;

            JLabel lblName = new JLabel(i.getName());
            lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
            GridBagConstraints gbc_lblName = new GridBagConstraints();
            gbc_lblName.insets = new Insets(0, 0, 10, 10);
            gbc_lblName.gridx = column;
            gbc_lblName.gridy = x/2;
            ingredientPanel.add(lblName, gbc_lblName);

            JTextField textField = new JTextField();
            textField.setFont(new Font("Tahoma", Font.BOLD, 14));
            GridBagConstraints gbc_textField = new GridBagConstraints();
            gbc_textField.insets = new Insets(0, 0, 10, 10);
            gbc_textField.fill = GridBagConstraints.HORIZONTAL;
            gbc_textField.gridx = column+1;
            gbc_textField.gridy = x/2;
            ingredientPanel.add(textField, gbc_textField);
            textField.setColumns(5);
            textField.setText(String.valueOf(i.getAmount()));
            ingredientText.put(i.getId(), textField);

            JLabel lblAmount = new JLabel( "cl");
            lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
            GridBagConstraints gbc_lblAmount = new GridBagConstraints();
            gbc_lblAmount.insets = new Insets(0, 0, 10, 10);
            gbc_lblAmount.gridx = column+2;
            gbc_lblAmount.gridy = x/2;
            ingredientPanel.add(lblAmount, gbc_lblAmount);
            x++;
        }
    }
}
