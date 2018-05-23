package de.mannheim.nawabu.cocktail;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectCocktailPanel extends ViewTemplate implements ActionListener {
	
	public SelectCocktailPanel(JFrame main) {
		super(main);
		lblWindowname.setText("Cocktails");
		initButton("tl", "Konfiguration", this, "config");
		btnTopBR.setVisible(false);
		initButton("bl", "<--", this, "previous");
		initButton("br", "-->", this, "next");
		
		contentPanel.setLayout(new GridLayout(3, 5, 10, 10));
		
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "config":
			ConfigPanel conf = new ConfigPanel(mainFrame);
			break;
		case "previous":
			contentPanel.remove((contentPanel.getComponentCount()-1));
			break;
		case "next":
			contentPanel.add(new JButton("Cocktail"));
			break;
		default:
			break;
		}
		
		revalidate();
		repaint();
	}
}
