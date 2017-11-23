package sc.player2018.logic.Parts;

import sc.plugin2018.*;
import sc.plugin2018.util.GameRuleLogic;

public class ThirdPart extends Part{
    @Override
    public void processAI(){
        if(super.getPlayer().getSalads() == 0 && isMovePlayable(1, FieldType.GOAL)){
            if(super.getPlayer().getCarrots() - super.getKarrotCosts()[super.getGameState().getBoard().getNextFieldByType(FieldType.GOAL, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= 10 && super.getPlayer().getCarrots() - super.getKarrotCosts()[super.getGameState().getBoard().getNextFieldByType(FieldType.GOAL, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] > 0){
                super.getActions().add(new Advance(super.getDistance(FieldType.GOAL), 0));
            } else if(super.getPlayer().ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS) && isMovePlayable(1, FieldType.HARE)){
                super.getActions().add(new Advance(super.getDistance(FieldType.HARE), 0));
                super.getActions().add(new Card(CardType.TAKE_OR_DROP_CARROTS, -20 ,1));
            } else if(isMovePlayable(1, FieldType.CARROT)){
                super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                super.setNewTask(3);
            } else if(isMovePlayable(0, FieldType.HEDGEHOG)){
                super.getActions().add(new FallBack());
            } else{
                super.getActions().add(new Skip());
            }
        } else if(super.getPlayer().getSalads() == 0){
            if(super.getPlayer().ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS) && isMovePlayable(1, FieldType.HARE)){
                super.getActions().add(new Advance(super.getDistance(FieldType.HARE), 0));
                super.getActions().add(new Card(CardType.TAKE_OR_DROP_CARROTS, +20 ,1));
            } else if(isMovePlayable(1, FieldType.CARROT)){
                super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                super.setNewTask(2);
            } else if(isMovePlayable(0, FieldType.HEDGEHOG)){
                super.getActions().add(new FallBack());
            } else{
                super.getActions().add(new Skip());
            }
        } else if(super.getPlayer().getSalads() == 1){
            if(super.getPlayer().ownsCardOfType(CardType.EAT_SALAD) && isMovePlayable(1, FieldType.HARE)){
                super.getActions().add(new Advance(super.getDistance(FieldType.HARE), 0));
                super.getActions().add(new Card(CardType.EAT_SALAD, 1));
            } else if(isMovePlayable(1, FieldType.SALAD)){
                super.getActions().add(new Advance(super.getDistance(FieldType.SALAD)));
                super.setNewTask(1);
            } else if(isMovePlayable(1, FieldType.CARROT)){
                super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                super.setNewTask(2);
            } else if(isMovePlayable(0, FieldType.HEDGEHOG)){
                super.getActions().add(new FallBack());
            } else{
                super.getActions().add(new Skip());
            }
        } else {
             if(GameRuleLogic.isValidToFallBack(super.getGameState()) && isMovePlayable(0, FieldType.HEDGEHOG)){
                super.getActions().add(new FallBack());
            } else if(isMovePlayable(1, FieldType.CARROT)){
                 super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                 super.setNewTask(2);
             } else{
                super.getActions().add(new Skip());
            }
        }
    }
}