package sc.player2018.logic.Parts;


import sc.player2018.Starter;
import sc.player2018.logic.RandomLogic;
import sc.plugin2018.*;

import java.util.*;
import sc.plugin2018.util.GameRuleLogic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

public class ThirdPart {
    private int[] karottenVerbrauch = {0,1,3,6,10,15,21,28,36,45,55,66,78,91,105,120,136,153,171,190,210,231,253,276,300,325,351,378,406,435,465,496,528,561,595,630,666,703,741,780,820,861,903,946,990};

    //given vars
    private GameState gs;
    private Player p;
    private Player enemy;
    private int moveId;

    //own vars
    private Move m;
    private BufferedWriter fout;
    private int newTask; //1=Salad, 2=Carrot+, 3=Carrot-

    public ThirdPart(BufferedWriter f){
        moveId = 0;
        fout = f;
        newTask = 0;
    }

    public void update(GameState gs, Player p, Player enemy) {
        this.gs = gs;
        this.p = p;
        this.enemy = enemy;
    }

    private void logMessage(String msg, boolean newLine) {
        try {
            if (newLine) msg = "\n" + msg;
            fout.write(msg);
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processAI(){
        logMessage("third part ("+p.getPlayerColor().name()+"): ",true);
        ArrayList<Action> actions = new ArrayList<>();

        if(newTask != 0 || (GameRuleLogic.isValidToEat(gs) && gs.fieldOfCurrentPlayer() == FieldType.SALAD)) {
            if (newTask == 1 || (GameRuleLogic.isValidToEat(gs) && gs.fieldOfCurrentPlayer() == FieldType.SALAD)) {
                actions.add(new EatSalad(0));
                logMessage("eat salad", false);
            } else if (newTask == 2 || gs.getNextFieldByType(FieldType.CARROT, p.getFieldIndex()) == 0){
                actions.add(new ExchangeCarrots(+10));
                logMessage("eat carrots", false);
            } else if (newTask == 3){
                actions.add(new ExchangeCarrots(-10));
                logMessage("remove carrots", false);
            }
            newTask = 0;
        } else{
            if(p.getSalads() == 0 && isMovePlayable(1,FieldType.GOAL)){
                if(p.getCarrots() - karottenVerbrauch[gs.getBoard().getNextFieldByType(FieldType.GOAL, p.getFieldIndex()) - p.getFieldIndex()] <= 10 && p.getCarrots() - karottenVerbrauch[gs.getBoard().getNextFieldByType(FieldType.GOAL, p.getFieldIndex()) - p.getFieldIndex()] > 0){
                    actions.add(new Advance(getDistance(FieldType.GOAL), 0));
                    logMessage("1 into goal", false);
                } else if(p.ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS) && isMovePlayable(1, FieldType.HARE)){
                    actions.add(new Advance(getDistance(FieldType.HARE), 0));
                    actions.add(new Card(CardType.TAKE_OR_DROP_CARROTS, -20 ,1));
                    logMessage("1 to hare", false);
                } else if(isMovePlayable(1, FieldType.CARROT)){
                    actions.add(new Advance(getDistance(FieldType.CARROT)));
                    newTask = 3;
                    logMessage("1 to carrot", false);
                } else if(isMovePlayable(0, FieldType.HEDGEHOG)){
                    actions.add(new FallBack());
                    logMessage("1 to hedgehog", false);
                } else{
                    actions.add(new Skip());
                    logMessage("1 skip", false);
                }
            } else if(p.getSalads() == 0){
                if(p.ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS) && isMovePlayable(1, FieldType.HARE)){
                    actions.add(new Advance(getDistance(FieldType.HARE), 0));
                    actions.add(new Card(CardType.TAKE_OR_DROP_CARROTS, +20 ,1));
                    logMessage("2 to hare", false);
                } else if(isMovePlayable(1, FieldType.CARROT)){
                    actions.add(new Advance(getDistance(FieldType.CARROT)));
                    newTask = 2;
                    logMessage("2 to carrot", false);
                } else if(isMovePlayable(0, FieldType.HEDGEHOG)){
                    actions.add(new FallBack());
                    logMessage("2 to hedgehog", false);
                } else{
                    actions.add(new Skip());
                    logMessage("2 skip", false);
                }
            } else if(p.getSalads() == 1){
                if(p.ownsCardOfType(CardType.EAT_SALAD) && isMovePlayable(1, FieldType.HARE)){
                    actions.add(new Advance(getDistance(FieldType.HARE), 0));
                    actions.add(new Card(CardType.EAT_SALAD, 1));
                    logMessage("3 to hare", false);
                } else if(isMovePlayable(1, FieldType.SALAD)){
                    actions.add(new Advance(getDistance(FieldType.SALAD)));
                    newTask = 1;
                    logMessage("3 to salad", false);
                } else if(isMovePlayable(1, FieldType.CARROT)){
                    actions.add(new Advance(getDistance(FieldType.CARROT)));
                    newTask = 2;
                    logMessage("3 to carrot", false);
                } else if(isMovePlayable(0, FieldType.HEDGEHOG)){
                    actions.add(new FallBack());
                    logMessage("3 to hedgehog", false);
                } else{
                    actions.add(new Skip());
                    logMessage("3 skip", false);
                }
            } else {
                 if(GameRuleLogic.isValidToFallBack(gs) && isMovePlayable(0, FieldType.HEDGEHOG)){
                    actions.add(new FallBack());
                    logMessage("4 to hedgehog", false);
                } else if(isMovePlayable(1, FieldType.CARROT)){
                     actions.add(new Advance(getDistance(FieldType.CARROT)));
                     newTask = 2;
                     logMessage("4 to carrot", false);
                 } else{
                    actions.add(new Skip());
                    logMessage("4 skip", false);
                }
            }
        }

        m = new Move(actions);
        m.orderActions();
    }

    private int getDistance(FieldType f){
        return gs.getNextFieldByType(f, p.getFieldIndex()) - p.getFieldIndex();
    }

    private boolean isMovePlayable(int actionId, FieldType f){
        if(actionId == 0 && gs.getBoard().getPreviousFieldByType(f, p.getFieldIndex()) > -1 && gs.getBoard().getPreviousFieldByType(f, p.getFieldIndex()) != enemy.getFieldIndex()) return true; //previousField
        else if(actionId == 1 && gs.getBoard().getNextFieldByType(f, p.getFieldIndex()) > -1 && gs.getBoard().getNextFieldByType(f, p.getFieldIndex()) != enemy.getFieldIndex() && karottenVerbrauch[gs.getBoard().getNextFieldByType(f, p.getFieldIndex()) - p.getFieldIndex()] <= p.getCarrots()) return true; //nextField
        return false;
    }

    public Move getMove(){
        return m;
    }
}