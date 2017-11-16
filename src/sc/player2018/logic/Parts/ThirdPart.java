package sc.player2018.logic.Parts;

import sc.player2018.logic.FLSLogic;
import sc.plugin2018.*;
import sc.plugin2018.util.GameRuleLogic;

public class ThirdPart extends FLSLogic {

    public void processAI(){
        logMessage("third part ("+player.getPlayerColor().name()+"): ",true);

        if(player.getSalads() == 0 && isMovePlayable(1,FieldType.GOAL)){
            if(player.getCarrots() - karottenVerbrauch[gs.getBoard().getNextFieldByType(FieldType.GOAL, player.getFieldIndex()) - player.getFieldIndex()] <= 10 && player.getCarrots() - karottenVerbrauch[gs.getBoard().getNextFieldByType(FieldType.GOAL, player.getFieldIndex()) - player.getFieldIndex()] > 0){
                actions.add(new Advance(getDistance(FieldType.GOAL), 0));
                logMessage("1 into goal", false);
            } else if(player.ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS) && isMovePlayable(1, FieldType.HARE)){
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
        } else if(player.getSalads() == 0){
            if(player.ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS) && isMovePlayable(1, FieldType.HARE)){
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
        } else if(player.getSalads() == 1){
            if(player.ownsCardOfType(CardType.EAT_SALAD) && isMovePlayable(1, FieldType.HARE)){
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

        m = new Move(actions);
        m.orderActions();
        actions.clear();
    }
}