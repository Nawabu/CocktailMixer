package de.mannheim.nawabu.cocktail;

import com.fazecast.jSerialComm.SerialPort;
import de.mannheim.nawabu.cocktail.model.CocktailDB;
import de.mannheim.nawabu.cocktail.model.Ingredient;
import de.mannheim.nawabu.cocktail.model.Recipe;
import de.mannheim.nawabu.cocktail.view.CocktailProgressPanel;
import de.mannheim.nawabu.cocktail.view.RefillPanel;
import de.mannheim.nawabu.cocktail.view.SelectCocktailPanel;

import javax.swing.*;
import java.io.InputStream;
import java.io.PrintWriter;

public class ArduinoController {
    private static ArduinoController ourInstance = new ArduinoController();
    private SerialPort comPort;
    private PrintWriter output;
    private InputStream input;
    private CocktailDB db = CocktailDB.getInstance();

    public static ArduinoController getInstance() {
        return ourInstance;
    }

    private ArduinoController() {
        connectComPort();
    }

    public void cleanPumps(JFrame main, int duration) {
        if(comPort.isOpen()) {
            output.print(String.format("p,%d\n", duration));
            output.flush();
        }

        CocktailProgressPanel progressPanel = new CocktailProgressPanel(main, duration/100);
        waitForFinishProgress(progressPanel);
    }

    public void makeCocktail(JFrame main, Recipe cocktail) {
        System.out.println("Make started");
        int glassSize = db.getGlassSize();
        int fillAmount = glassSize;
        int progress = 0;
        CocktailProgressPanel progressPanel = new CocktailProgressPanel(main, glassSize);
        RefillPanel refillPanel;

        for(Ingredient i: cocktail.getIngredients()) {
            fillAmount -= i.getAmount()*10;
            if(!i.isEnough()) {
                refillPanel = new RefillPanel(main, i, "make", cocktail);
                return;
            }
        }

        if(!cocktail.getFiller().isMore(fillAmount)) {
            refillPanel = new RefillPanel(main, cocktail.getFiller(), "make", cocktail);
            return;
        }

        for(Ingredient i: cocktail.getIngredients()) {
            progressPanel.setLblCocktailStep(i.getName());
            System.out.println(String.format("c,%d,%d", i.getPump(), i.getAmount()));
            output.print(String.format("c,%d,%d\n", i.getPump(), i.getAmount()));
            output.flush();
            waitForFinish();
            int newLevel = i.getLevel()-(i.getAmount()*10);
            db.updateIngredientLevel(i.getId(), newLevel);
            progress += i.getAmount()*10;
            progressPanel.setProgress(progress);
        }

        System.out.println(String.format("c,%d,%d", cocktail.getFiller().getPump(), (glassSize-progress)/10));

        output.print(String.format("c,%d,%d\n", cocktail.getFiller().getPump(), glassSize-progress));
        output.flush();
        waitForFinish();

        int newLevel = cocktail.getFiller().getLevel()-fillAmount;
        db.updateIngredientLevel(cocktail.getFiller().getId(), newLevel);
        progressPanel.setLblCocktailStep(cocktail.getName() + " ist fertig!");
        progressPanel.setToMax();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        SelectCocktailPanel cocktailPanel = new SelectCocktailPanel(main);
    }

    private void waitForFinishProgress(CocktailProgressPanel progressPanel) {
        long start = System.currentTimeMillis();
        int progress = 1;
        char c = ' ';
        try {
            while(c != 'f') {
                Thread.sleep(100);

                int cN = 0;

                while(cN == 0) {
                    if((System.currentTimeMillis()-start) >= 100) {
                        progressPanel.setProgress(progress++);
                        start = System.currentTimeMillis();
                    }
                    cN = input.read();
                }

                c = (char) cN;
            }
            input.skip(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println("finished");
    }

    private void waitForFinish() {
        char c = ' ';
        try {
            while(c != 'f') {
                Thread.sleep(100);
                int cN;

                do cN = input.read();
                while (cN == 0);

                c = (char) cN;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println(c);
//        System.out.println("finished");
    }

    private void connectComPort() {
        SerialPort ports[] = SerialPort.getCommPorts();

        for (SerialPort p : ports) {
            String name = p.getDescriptivePortName().toLowerCase();

            if (name.contains("arduino"))
                comPort = p;
        }

        comPort.setBaudRate(9600);

        if (!comPort.isOpen()) {

            try {
                comPort.openPort();
                Thread.sleep(1000);
                output = new PrintWriter(comPort.getOutputStream());
                input = comPort.getInputStream();
                input.skip(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void closeComPort() {
        if(comPort.isOpen()) {

            try {
                output.close();
                input.close();
                comPort.closePort();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
