package de.mannheim.nawabu.cocktail.view;

import javax.swing.*;
import java.awt.*;

public class CocktailProgressPanel extends ViewTemplate {
    private JProgressBar progressBar;
    private JLabel lblCocktailStep;

    public CocktailProgressPanel(JFrame main, int max) {
        super(main);
        lblWindowname.setVisible(false);
        btnBottomBL.setVisible(false);
        btnBottomBR.setVisible(false);
        btnTopBL.setVisible(false);
        btnTopBR.setVisible(false);


        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        Box verticalBox = Box.createVerticalBox();
        contentPanel.add(verticalBox);

        Component verticalStrut = Box.createVerticalStrut(100);
        verticalBox.add(verticalStrut);

        lblCocktailStep = new JLabel();
        lblCocktailStep.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCocktailStep.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCocktailStep.setHorizontalAlignment(SwingConstants.CENTER);
        verticalBox.add(lblCocktailStep);

        progressBar = new JProgressBar();
        progressBar.setFont(new Font("Tahoma", Font.BOLD, 14));
        progressBar.setMaximum(max);
        verticalBox.add(progressBar);
        progressBar.setStringPainted(true);

        revalidate();
        repaint();
    }

    public void setLblCocktailStep(String text) {
        lblCocktailStep.setText(text);

        revalidate();
        repaint();
    }

    public void setProgress(int progress) {
        progressBar.setValue(progress);

        revalidate();
        repaint();
    }

    public void setToMax() {
        progressBar.setValue(progressBar.getMaximum());

        revalidate();
        repaint();
    }
}
