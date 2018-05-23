package de.mannheim.nawabu.cocktail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Insets;
import java.awt.Font;
import javax.swing.SwingConstants;

public class ConfigPanel extends ViewTemplate implements ActionListener {
	private JTextField textGlasSize;
	
	public ConfigPanel(JFrame main) {
		super(main);
		lblWindowname.setText("Konfiguration");
		initButton("tl", "zurück", this, "cancel");
		btnTopBR.setVisible(false);
		btnBottomBL.setVisible(false);
		btnBottomBR.setVisible(false);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		contentPanel.setLayout(gridBagLayout);
		
		JLabel lblGlas = new JLabel("Glasgröße (ml):");
		lblGlas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblGlas = new GridBagConstraints();
		gbc_lblGlas.insets = new Insets(10, 10, 10, 10);
		gbc_lblGlas.gridx = 0;
		gbc_lblGlas.gridy = 0;
		contentPanel.add(lblGlas, gbc_lblGlas);
		
		textGlasSize = new JTextField();
		textGlasSize.setHorizontalAlignment(SwingConstants.CENTER);
		textGlasSize.setFont(new Font("Tahoma", Font.BOLD, 14));
		textGlasSize.setPreferredSize(new Dimension(150, 40));
		GridBagConstraints gbc_textGlasSize = new GridBagConstraints();
		gbc_textGlasSize.insets = new Insets(10, 10, 10, 10);
		gbc_textGlasSize.gridx = 1;
		gbc_textGlasSize.gridy = 0;
		contentPanel.add(textGlasSize, gbc_textGlasSize);
		textGlasSize.setColumns(10);
		
		JButton btnSaveGlas = new JButton("speichern");
		btnSaveGlas.setPreferredSize(new Dimension(120, Config.buttonHeight));
		GridBagConstraints gbc_btnSaveGlas = new GridBagConstraints();
		gbc_btnSaveGlas.insets = new Insets(10, 10, 10, 10);
		gbc_btnSaveGlas.gridx = 2;
		gbc_btnSaveGlas.gridy = 0;
		contentPanel.add(btnSaveGlas, gbc_btnSaveGlas);
		
		JButton btnInputs = new JButton("Anschl\u00FCsse");
		btnInputs.setPreferredSize(new Dimension(120, Config.buttonHeight));
		GridBagConstraints gbc_btnInputs = new GridBagConstraints();
		gbc_btnInputs.insets = new Insets(10, 10, 10, 10);
		gbc_btnInputs.gridx = 1;
		gbc_btnInputs.gridy = 1;
		btnInputs.addActionListener(this);
		btnInputs.setActionCommand("inputs");
		contentPanel.add(btnInputs, gbc_btnInputs);
		
		JButton btnRecipes = new JButton("Rezepte");
		btnRecipes.setPreferredSize(new Dimension(120, Config.buttonHeight));
		GridBagConstraints gbc_btnRecipes = new GridBagConstraints();
		gbc_btnRecipes.insets = new Insets(10, 10, 10, 10);
		gbc_btnRecipes.gridx = 1;
		gbc_btnRecipes.gridy = 2;
		btnRecipes.addActionListener(this);
		btnRecipes.setActionCommand("recipes");
		contentPanel.add(btnRecipes, gbc_btnRecipes);
		
		revalidate();
		repaint();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cancel":
			SelectCocktailPanel cocktail = new SelectCocktailPanel(mainFrame);
			break;
		case "inputs":
			InputsPanel inputs = new InputsPanel(mainFrame);
			break;
		default:
			break;
		}
	}
}
