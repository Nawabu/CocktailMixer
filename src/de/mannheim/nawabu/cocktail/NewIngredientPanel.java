package de.mannheim.nawabu.cocktail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Font;

public class NewIngredientPanel extends ViewTemplate implements ActionListener {
	private JTextField textName;
	private JTextField textSize;

	public NewIngredientPanel(JFrame main) {
		super(main);
		initButton("tl", "abbrechen", this, "cancel");
		initButton("tr", "Speichern", this, "save");
		btnBottomBL.setVisible(false);
		btnBottomBR.setVisible(false);
		
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
		
		
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cancel":
			ConfigPanel config = new ConfigPanel(mainFrame);
			break;
		default:
			break;
		}
	}

}
