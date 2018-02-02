package sc.player2018.logic;

import sc.player2018.logic.Parts.FirstPart;
import sc.player2018.logic.Parts.Part;
import sc.player2018.logic.Parts.SecondPart;
import sc.plugin2018.*;

import java.io.*;

public class Log {
    private BufferedWriter playerOut;
    private BufferedWriter enemyOut;
    private GameState oldGS;
    private GameState gameState;
    private Part lastPlayedPart;

    public Log(){
        try {
            String file = Log.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();                 //erzeugt log-Datei am Speicherort der .jar
            playerOut = new BufferedWriter(new FileWriter(new File(file.substring(0, file.lastIndexOf("/")) + "/log.txt"), false));
            enemyOut = new BufferedWriter(new FileWriter(new File(file.substring(0, file.lastIndexOf("/")) + "/log_enemy.txt"), false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logMove(GameState gameState, Move move, boolean aiMove){        
        String partNumber = "";
        if(lastPlayedPart instanceof FirstPart) partNumber = "firstpart";
        else if(lastPlayedPart instanceof SecondPart) partNumber = "secondpart";
        else partNumber = "thirdpart";
        partNumber += "(" + gameState.getCurrentPlayer().getPlayerColor().name() + ")";
        if(aiMove) partNumber += " [aimove]";

        String movedescription = "";
        for(Action a : move.actions) {
            movedescription += findMoveDescription(gameState.getCurrentPlayer(), a);
        }

        write(partNumber + " " + movedescription);
        oldGS = gameState;
    }

    public void logEnemy(GameState gameState){
        this.gameState = gameState;
        writeEnemy("ENEMY " + findMoveDescription(gameState.getOtherPlayer(), gameState.getOtherPlayer().getLastNonSkipAction())); //First Move of enemy cannot be displayed
    }
    
    private String findMoveDescription(Player p, Action a){
        String log = "";
        
        Player old = null;
        if(oldGS != null){
            if(p.getPlayerColor() == oldGS.getCurrentPlayer().getPlayerColor()) old = oldGS.getCurrentPlayer();
            else old = oldGS.getOtherPlayer();
        }

        if (a instanceof Advance) {
            if(p.getFieldIndex() != 65){
                if (oldGS == null) log += "[action]advance [oldpos]" + 0 + " [newpos]" + p.getFieldIndex() + " [oldcarrotcount]" + 68 + " [newcarrotcount]" + p.getCarrots();
                else log += "[action]advance [oldpos]" + old.getFieldIndex() + " [newpos]" + p.getFieldIndex() + " [oldcarrotcount]" + old.getCarrots() + " [newcarrotcount]" + p.getCarrots();
            } else{
                log += "[action]goal [oldpos]" + old.getFieldIndex() + " [newpos]" + p.getFieldIndex();
            }
        } else if (a instanceof EatSalad) {
            log += "[action]eatsalad [oldsaladcount]" + old.getSalads() + " [newsaladcount]" + p.getSalads();
        } else if (a instanceof ExchangeCarrots) {
            log += "[action]exchangecarrots [oldcarrotcount]" + old.getCarrots() + " [newcarrotcount]" + p.getCarrots();
        } else if (a instanceof FallBack){
            log += "[action]fallback [oldpos]" + old.getFieldIndex() + " [newpos]" + p.getFieldIndex() + " [oldcarrotcount]" + old.getCarrots() + " [newcarrotcount]" + p.getCarrots();
        } else if(a instanceof Card){
            if(oldGS != null && old.getCarrots() != p.getCarrots()) log += "|[cardaction]cardcarrot [oldcarrotcount]" + old.getCarrots() + " [newcarrotcount]" + p.getCarrots();
            else if(oldGS != null && old.getSalads() != p.getSalads()) log += "|[cardaction]cardsalad [oldsaladcount]" + old.getSalads() + " [newsaladcount]" + p.getSalads();
            else if(oldGS != null) log += "|[cardaction]cardmove [oldpos]" + old.getFieldIndex() + " [newpos]" + p.getFieldIndex();
            //else log += "Keine Ausgabe aufgrund von #25";
        }
        return log;
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

    public void write(String msg){
        try {
            playerOut.write(msg+System.getProperty("line.separator"));
            playerOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeEnemy(String msg){
        try {
            enemyOut.write(msg+System.getProperty("line.separator"));
            enemyOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLastPlayedPart(Part lastPlayedPart) {
        this.lastPlayedPart = lastPlayedPart;
    }
}
