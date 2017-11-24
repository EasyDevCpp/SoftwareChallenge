package sc.player2018.logic;

import sc.player2018.logic.Parts.FirstPart;
import sc.player2018.logic.Parts.Part;
import sc.player2018.logic.Parts.SecondPart;
import sc.plugin2018.Advance;
import sc.plugin2018.Board;
import sc.plugin2018.GameState;
import sc.plugin2018.Move;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

        String movedescription = "";
        if(move.actions.get(0) instanceof Advance){
            if(oldGS == null) movedescription = "advance " + 0 + " " + gameState.getOtherPlayer().getFieldIndex();
            else movedescription = "advance " + oldGS.getOtherPlayer().getFieldIndex() + " " + gameState.getOtherPlayer().getFieldIndex();
        }

        write(partNumber + " " + movedescription);
        oldGS = gameState;
    }

    public void logEnemy(GameState gameState){
        if(oldGS == null) write("enemy " + 0 + " " + gameState.getOtherPlayer().getFieldIndex());
        else write("enemy " + oldGS.getOtherPlayer().getFieldIndex() + " " + gameState.getOtherPlayer().getFieldIndex());
    }

    public void printFields(Board b){
        String msg = "";
        for (int i = 0; i < 65; i++) {
            msg += b.getTypeAt(i).name() + "|";
        }
        write(msg);
    }

    private void write(String msg){
        try {
            out.write(msg+"\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLastPlayedPart(Part lastPlayedPart) {
        this.lastPlayedPart = lastPlayedPart;
    }
}
