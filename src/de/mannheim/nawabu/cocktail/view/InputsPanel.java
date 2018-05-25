package de.mannheim.nawabu.cocktail.view;

import de.mannheim.nawabu.cocktail.Config;
import de.mannheim.nawabu.cocktail.model.CocktailDB;
import de.mannheim.nawabu.cocktail.model.Ingredient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;

public class InputsPanel extends ViewTemplate implements ActionListener {
    private CocktailDB db = CocktailDB.getInstance();
	
	public InputsPanel(JFrame main) {
		super(main);
		
		lblWindowname.setText("Anschlüsse");
		initButton("tl", "zurück", this, "cancel");
		btnTopBR.setVisible(false);
		initButton("bl", "<--", this, "previous");
		initButton("br", "-->", this, "next");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {100, 200, 100, 100, 100};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0};
		contentPanel.setLayout(gridBagLayout);
		
		JLabel lblAnschluss = new JLabel("Anschluss");
		lblAnschluss.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblAnschluss = new GridBagConstraints();
		gbc_lblAnschluss.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnschluss.gridx = 0;
		gbc_lblAnschluss.gridy = 0;
		contentPanel.add(lblAnschluss, gbc_lblAnschluss);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 0;
		contentPanel.add(lblName, gbc_lblName);
		
		JLabel lblGre = new JLabel("Größe");
		lblGre.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblGre = new GridBagConstraints();
		gbc_lblGre.insets = new Insets(0, 0, 5, 5);
		gbc_lblGre.gridx = 2;
		gbc_lblGre.gridy = 0;
		contentPanel.add(lblGre, gbc_lblGre);
		
		JLabel lblInhalt = new JLabel("Inhalt");
		lblInhalt.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblInhalt = new GridBagConstraints();
		gbc_lblInhalt.insets = new Insets(0, 0, 5, 5);
		gbc_lblInhalt.gridx = 3;
		gbc_lblInhalt.gridy = 0;
		contentPanel.add(lblInhalt, gbc_lblInhalt);
		
		drawInputsPage(1);
		btnBottomBL.setVisible(false);

		revalidate();
		repaint();
	}
	
	private void drawInputsPage(int p) {
		contentPanel.removeAll();
        Map<Integer, Ingredient> ingredientMap = db.getPumpIngredients();
		
		int offset = (7 * (p-1)) + 1;
		for(int x=0; x<7; x++) {
			int row = offset + x;
			JButton input = new JButton(String.valueOf(row));
			input.setPreferredSize(new Dimension(120, Config.buttonHeight));
			input.addActionListener(e -> {
				IngredientsPanel ingredients = new IngredientsPanel(mainFrame, "pump", row);
			});
			GridBagConstraints gbc_input = new GridBagConstraints();
			gbc_input.fill = GridBagConstraints.HORIZONTAL;
			gbc_input.insets = new Insets(0, 0, 5, 5);
			gbc_input.gridx = 0;
			gbc_input.gridy = row;
			contentPanel.add(input, gbc_input);

			if(ingredientMap.containsKey(row)) {
			    Ingredient i = ingredientMap.get(row);

                JLabel lblName = new JLabel(i.getName());
                lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
                GridBagConstraints gbc_name = new GridBagConstraints();
                gbc_name.insets = new Insets(0, 0, 5, 5);
                gbc_name.gridx = 1;
                gbc_name.gridy = row;
                contentPanel.add(lblName, gbc_name);

                JLabel lblBottleSize = new JLabel(String.valueOf(i.getSize()));
                lblBottleSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
                GridBagConstraints gbc_bottleSize = new GridBagConstraints();
                gbc_bottleSize.insets = new Insets(0, 0, 5, 5);
                gbc_bottleSize.gridx = 2;
                gbc_bottleSize.gridy = row;
                contentPanel.add(lblBottleSize, gbc_bottleSize);

                JLabel lblBottleContent = new JLabel(String.valueOf(i.getLevel()));
                lblBottleContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
                GridBagConstraints gbc_bottleContent = new GridBagConstraints();
                gbc_bottleContent.insets = new Insets(0, 0, 5, 5);
                gbc_bottleContent.gridx = 3;
                gbc_bottleContent.gridy = row;
                contentPanel.add(lblBottleContent, gbc_bottleContent);

                JButton btnRefill = new JButton("auffüllen");
                btnRefill.setPreferredSize(new Dimension(120, Config.buttonHeight));
                btnRefill.addActionListener(e -> {
					RefillPanel refillPanel = new RefillPanel(mainFrame, i, "inputs");
				});
                GridBagConstraints gbc_refill = new GridBagConstraints();
                gbc_refill.insets = new Insets(0, 0, 5, 5);
                gbc_refill.gridx = 4;
                gbc_refill.gridy = row;
                contentPanel.add(btnRefill, gbc_refill);
            }

            revalidate();
            repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cancel":
			ConfigPanel config = new ConfigPanel(mainFrame);
			break;
		case "previous":
			drawInputsPage(1);
			btnBottomBR.setVisible(true);
			btnBottomBL.setVisible(false);
			break;
		case "next":
			drawInputsPage(2);
			btnBottomBR.setVisible(false);
			btnBottomBL.setVisible(true);
			break;
		default:
			break;
		}
	}

}
