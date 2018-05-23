package de.mannheim.nawabu.cocktail;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class IngredientsPanel extends ViewTemplate implements ActionListener {
	
	public IngredientsPanel(JFrame main) {
		super(main);

		lblWindowname.setText("Zutaten");
		initButton("tl", "zurück", this, "cancel");
		initButton("tr", "neu", this, "new");
		initButton("bl", "<--", this, "previous");
		initButton("br", "-->", this, "next");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {200, 100, 100};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0};
		contentPanel.setLayout(gridBagLayout);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		contentPanel.add(lblName, gbc_lblName);
		
		JLabel lblGre = new JLabel("Gr\u00F6\u00DFe");
		lblGre.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblGre = new GridBagConstraints();
		gbc_lblGre.insets = new Insets(0, 0, 5, 5);
		gbc_lblGre.gridx = 1;
		gbc_lblGre.gridy = 0;
		contentPanel.add(lblGre, gbc_lblGre);
		
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cancel":
			InputsPanel config = new InputsPanel(mainFrame);
			break;
		case "new":
			NewIngredientPanel newIng = new NewIngredientPanel(mainFrame);
			break;
		case "previous":
			
			break;
		case "next":
			
			break;
		default:
			break;
		}
	}

}
