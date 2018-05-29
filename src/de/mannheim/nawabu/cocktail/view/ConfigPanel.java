package de.mannheim.nawabu.cocktail.view;

import de.mannheim.nawabu.cocktail.ArduinoController;
import de.mannheim.nawabu.cocktail.Config;
import de.mannheim.nawabu.cocktail.model.CocktailDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.SwingConstants;

public class ConfigPanel extends ViewTemplate implements ActionListener {
	private JTextField textGlassSize;
	private JLabel lblInfo;
	private ArduinoController arduino = ArduinoController.getInstance();
	private CocktailDB db = CocktailDB.getInstance();
	
	public ConfigPanel(JFrame main) {
		super(main);
		lblWindowname.setText("Konfiguration");
		initButton("tl", "zurück", this, "cancel");
		btnTopBR.setVisible(false);
		btnBottomBL.setVisible(false);
		btnBottomBR.setVisible(false);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		contentPanel.setLayout(gridBagLayout);
		
		JLabel lblGlass = new JLabel("Glasgröße (ml):");
		lblGlass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblGlass = new GridBagConstraints();
		gbc_lblGlass.insets = new Insets(0, 0, 10, 10);
		gbc_lblGlass.gridx = 0;
		gbc_lblGlass.gridy = 0;
		contentPanel.add(lblGlass, gbc_lblGlass);
		
		textGlassSize = new JTextField();
		textGlassSize.setText(String.valueOf(db.getGlassSize()));
		textGlassSize.setHorizontalAlignment(SwingConstants.CENTER);
		textGlassSize.setFont(new Font("Tahoma", Font.BOLD, 14));
		textGlassSize.setPreferredSize(new Dimension(150, 40));
		GridBagConstraints gbc_textGlassSize = new GridBagConstraints();
		gbc_textGlassSize.insets = new Insets(0, 0, 10, 10);
		gbc_textGlassSize.gridx = 1;
		gbc_textGlassSize.gridy = 0;
		contentPanel.add(textGlassSize, gbc_textGlassSize);
		textGlassSize.setColumns(10);
		
		JButton btnSaveGlas = new JButton("speichern");
		btnSaveGlas.setPreferredSize(new Dimension(120, Config.buttonHeight));
		GridBagConstraints gbc_btnSaveGlas = new GridBagConstraints();
		gbc_btnSaveGlas.insets = new Insets(0, 0, 10, 10);
		gbc_btnSaveGlas.gridx = 2;
		gbc_btnSaveGlas.gridy = 0;
		btnSaveGlas.addActionListener(this);
		btnSaveGlas.setActionCommand("glassSize");
		contentPanel.add(btnSaveGlas, gbc_btnSaveGlas);
		
		JButton btnInputs = new JButton("Anschlüsse");
		btnInputs.setPreferredSize(new Dimension(120, Config.buttonHeight));
		GridBagConstraints gbc_btnInputs = new GridBagConstraints();
		gbc_btnInputs.insets = new Insets(0, 0, 10, 10);
		gbc_btnInputs.gridx = 1;
		gbc_btnInputs.gridy = 1;
		btnInputs.addActionListener(this);
		btnInputs.setActionCommand("inputs");
		contentPanel.add(btnInputs, gbc_btnInputs);
		
		JButton btnRecipes = new JButton("Rezepte");
		btnRecipes.setPreferredSize(new Dimension(120, Config.buttonHeight));
		GridBagConstraints gbc_btnRecipes = new GridBagConstraints();
		gbc_btnRecipes.insets = new Insets(0, 0, 10, 10);
		gbc_btnRecipes.gridx = 1;
		gbc_btnRecipes.gridy = 2;
		btnRecipes.addActionListener(this);
		btnRecipes.setActionCommand("recipes");
		contentPanel.add(btnRecipes, gbc_btnRecipes);

		JButton btnClean = new JButton("Spülen");
        btnClean.setPreferredSize(new Dimension(120, Config.buttonHeight));
		GridBagConstraints gbc_btnClean = new GridBagConstraints();
        gbc_btnClean.insets = new Insets(0, 0, 10, 10);
        gbc_btnClean.gridx = 1;
        gbc_btnClean.gridy = 3;
        btnClean.addActionListener(this);
        btnClean.setActionCommand("clean");
		contentPanel.add(btnClean, gbc_btnClean);

        lblInfo = new JLabel(" ");
        lblInfo.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_lblInfo = new GridBagConstraints();
        gbc_lblInfo.insets = new Insets(0, 0, 10, 10);
        gbc_lblInfo.gridx = 1;
        gbc_lblInfo.gridy = 4;
        contentPanel.add(lblInfo, gbc_lblInfo);

		revalidate();
		repaint();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cancel":
			new SelectCocktailPanel(mainFrame);
			break;
		case "inputs":
			new InputsPanel(mainFrame);
			break;
        case "recipes":
            new RecipePanel(mainFrame);
            break;
        case "clean":
            new Thread(new Runnable() {
                @Override
                public void run() {
                    arduino.cleanPumps(mainFrame, 10000);
                }
            }).start();

            break;
        case "glassSize":
            try {
                int size = Integer.parseInt(textGlassSize.getText());
                db.setGlassSize(size);
                lblInfo.setForeground(Color.BLUE);
                lblInfo.setText("Glasgröße gespeichert!");
            }
            catch (NumberFormatException ex) {
                lblInfo.setForeground(Color.RED);
                lblInfo.setText("Für Größe bitte nur Zahlen eingeben.");
            }
            break;
		default:
			break;
		}
	}
}
