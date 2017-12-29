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

    public void logMove(GameState gameState, Move move){
        String partNumber = "";
        if(lastPlayedPart instanceof FirstPart) partNumber = "firstpart";
        else if(lastPlayedPart instanceof SecondPart) partNumber = "secondpart";
        else partNumber = "thirdpart";
        partNumber += "(" + gameState.getOtherPlayer().getPlayerColor().name() + ")";

        String movedescription = "";
        for(Action a : move.actions) {
            if (a instanceof Advance) {
                if(gameState.getOtherPlayer().getFieldIndex() != 65){
                    if (oldGS == null) movedescription += "advance " + 0 + " " + gameState.getOtherPlayer().getFieldIndex() + " " + 68 + " " + gameState.getOtherPlayer().getCarrots();
                    else movedescription += "advance " + oldGS.getOtherPlayer().getFieldIndex() + " " + gameState.getOtherPlayer().getFieldIndex() + " " + oldGS.getOtherPlayer().getCarrots() + " " + gameState.getOtherPlayer().getCarrots();
                } else{
                    movedescription += "goal " + oldGS.getOtherPlayer().getFieldIndex() + " " + gameState.getOtherPlayer().getFieldIndex();
                }
            } else if (a instanceof EatSalad) {
                movedescription += "eatsalad " + oldGS.getOtherPlayer().getSalads() + " " + gameState.getOtherPlayer().getSalads();
            } else if (a instanceof ExchangeCarrots) {
                movedescription += "exchangecarrots " + oldGS.getOtherPlayer().getCarrots() + " " + gameState.getOtherPlayer().getCarrots();
            } else if (a instanceof FallBack){
                movedescription += "fallback " + oldGS.getOtherPlayer().getFieldIndex() + " " + gameState.getOtherPlayer().getFieldIndex() + " " + oldGS.getOtherPlayer().getCarrots() + " " + gameState.getOtherPlayer().getCarrots();
            } else if(a instanceof Card){
                if(oldGS.getOtherPlayer().getCarrots() != gameState.getOtherPlayer().getCarrots()) movedescription += "|cardcarrot " + oldGS.getOtherPlayer().getCarrots() + " " + gameState.getOtherPlayer().getCarrots();
                else if(oldGS.getOtherPlayer().getSalads() != gameState.getOtherPlayer().getSalads()) movedescription += "|cardsalad " + oldGS.getOtherPlayer().getSalads() + " " + gameState.getOtherPlayer().getSalads();
                else movedescription += "|cardmove " + oldGS.getOtherPlayer().getFieldIndex() + " " + gameState.getOtherPlayer().getFieldIndex();
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
