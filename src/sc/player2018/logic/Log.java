package sc.player2018.logic;

import sc.player2018.logic.Parts.FirstPart;
import sc.player2018.logic.Parts.Part;
import sc.player2018.logic.Parts.SecondPart;
import sc.plugin2018.*;

import java.io.*;

public class Log {
    private BufferedWriter out;
    private GameState oldGS;
    private Part lastPlayedPart;

    public Log(){
        try {
            String file = Log.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();                 //erzeugt log-Datei am Speicherort der .jar
            file = file.substring(0, file.lastIndexOf("/")) + "/log.txt";
            out = new BufferedWriter(new FileWriter(new File(file), false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logMove(GameState gameState, Move move, boolean aiMove){
        String partNumber = "";
        if(lastPlayedPart instanceof FirstPart) partNumber = "firstpart";
        else if(lastPlayedPart instanceof SecondPart) partNumber = "secondpart";
        else partNumber = "thirdpart";
        partNumber += "(" + gameState.getOtherPlayer().getPlayerColor().name() + ")";
        if(aiMove) partNumber += " [aimove]";

        String movedescription = "";
        for(Action a : move.actions) {
            if (a instanceof Advance) {
                if(gameState.getOtherPlayer().getFieldIndex() != 65){
                    if (oldGS == null) movedescription += "[action]advance " + 0 + " " + gameState.getOtherPlayer().getFieldIndex() + " " + 68 + " " + gameState.getOtherPlayer().getCarrots();
                    else movedescription += "[action]advance [oldpos]" + oldGS.getOtherPlayer().getFieldIndex() + " [newpos]" + gameState.getOtherPlayer().getFieldIndex() + " [oldcarrotcount]" + oldGS.getOtherPlayer().getCarrots() + " [newcarrotcount]" + gameState.getOtherPlayer().getCarrots();
                } else{
                    movedescription += "[action]goal [oldpos]" + oldGS.getOtherPlayer().getFieldIndex() + " [newpos]" + gameState.getOtherPlayer().getFieldIndex();
                }
            } else if (a instanceof EatSalad) {
                movedescription += "[action]eatsalad [oldsaladcount]" + oldGS.getOtherPlayer().getSalads() + " [newsaladcount]" + gameState.getOtherPlayer().getSalads();
            } else if (a instanceof ExchangeCarrots) {
                movedescription += "[action]exchangecarrots [oldcarrotcount]" + oldGS.getOtherPlayer().getCarrots() + " [newcarrotcount]" + gameState.getOtherPlayer().getCarrots();
            } else if (a instanceof FallBack){
                movedescription += "[action]fallback [oldpos]" + oldGS.getOtherPlayer().getFieldIndex() + " [newpos]" + gameState.getOtherPlayer().getFieldIndex() + " [oldcarrotcount]" + oldGS.getOtherPlayer().getCarrots() + " [newcarrotcount]" + gameState.getOtherPlayer().getCarrots();
            } else if(a instanceof Card){
                if(oldGS != null && oldGS.getOtherPlayer().getCarrots() != gameState.getOtherPlayer().getCarrots()) movedescription += "|[cardaction]cardcarrot [oldcarrotcount]" + oldGS.getOtherPlayer().getCarrots() + " [newcarrotcount]" + gameState.getOtherPlayer().getCarrots();
                else if(oldGS != null && oldGS.getOtherPlayer().getSalads() != gameState.getOtherPlayer().getSalads()) movedescription += "|[cardaction]cardsalad [oldsaladcount]" + oldGS.getOtherPlayer().getSalads() + " [newsaladcount]" + gameState.getOtherPlayer().getSalads();
                else if(oldGS != null) movedescription += "|[cardaction]cardmove [oldpos]" + oldGS.getOtherPlayer().getFieldIndex() + " [newpos]" + gameState.getOtherPlayer().getFieldIndex();
                else movedescription += "Keine Ausgabe aufgrund von #25";
            }
        }

        write(partNumber + " " + movedescription);
        oldGS = gameState;
    }

    public void logEnemy(GameState gameState){
        if(oldGS == null) write("enemy " + 0 + " " + gameState.getCurrentPlayer().getFieldIndex());
        else write("enemy " + oldGS.getCurrentPlayer().getFieldIndex() + " " + gameState.getCurrentPlayer().getFieldIndex());
    }

    public void printFields(Board b){
        String msg = "";
        for (int i = 0; i < 65; i++) {
            msg += b.getTypeAt(i).name() + "|";
        }
        write(msg);
    }

    public void onGameEnd(){
        write("END");
    }

    private void write(String msg){
        try {
            out.write(msg+System.getProperty("line.separator"));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLastPlayedPart(Part lastPlayedPart) {
        this.lastPlayedPart = lastPlayedPart;
    }
}
