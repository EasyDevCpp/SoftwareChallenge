package sc.player2018.logic.Parts;
import sc.plugin2018.*;
import sc.plugin2018.util.GameRuleLogic;
import java.util.ArrayList;

public class ThirdPart extends Part {
    public ThirdPart() {
        super.setQuality(0.85); //35% threshold
    }

    @Override
    public void processAI() {
        if(super.getPlayer().getSalads() == 0) {
            if(super.getPlayer().getCarrots() - super.getKarrotCosts()[super.getGameState().getBoard().getNextFieldByType(FieldType.GOAL, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= 10 && super.getPlayer().getCarrots() - super.getKarrotCosts()[super.getGameState().getBoard().getNextFieldByType(FieldType.GOAL, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] > 0) {
                super.getActions().add(new Advance(super.getDistance(FieldType.GOAL), 0));
            } else if(super.getPlayer().ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS) && isMovePlayable(1, FieldType.HARE) && getPlayer().getCarrots() - 20 > 5) {
                super.getActions().add(new Advance(super.getDistance(FieldType.HARE), 0));
                super.getActions().add(new Card(CardType.TAKE_OR_DROP_CARROTS, -20 ,1));
            } else if(isMovePlayable(1, FieldType.CARROT)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                if(super.getPlayer().getCarrots() > 13) { //#Fix 30
                    super.setNewTask(3);
                } else{
                    super.setNewTask(2);
                }
            } else if(isMovePlayable(0, FieldType.HEDGEHOG)) {
                super.getActions().add(new FallBack());
            } else {
                super.getActions().add(new Skip());
                //super.setActions(new ArrayList<Action>(Sigmoid.getRandomAction(super.getGameState()).getActions())); Alternate
            }
        } else if(super.getPlayer().getSalads() == 1) {
            if(super.getPlayer().ownsCardOfType(CardType.EAT_SALAD) && isMovePlayable(1, FieldType.HARE)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.HARE), 0));
                super.getActions().add(new Card(CardType.EAT_SALAD, 1));
            } else if(isMovePlayable(1, FieldType.SALAD)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.SALAD)));
                super.setNewTask(1);
            } else if(getDistance(FieldType.POSITION_2) < getDistance(FieldType.SALAD) && isMovePlayable(1, FieldType.POSITION_2) && getPlayer().getFieldIndex() + getDistance(FieldType.POSITION_2) < getEnemy().getFieldIndex() && getPlayer().getCarrots() < 30) {
                super.getActions().add(new Advance(super.getDistance(FieldType.POSITION_2)));
            } else if(getDistance(FieldType.POSITION_1) < getDistance(FieldType.SALAD) && isMovePlayable(1, FieldType.POSITION_1) && getPlayer().getFieldIndex() > getEnemy().getFieldIndex() && getPlayer().getCarrots() < 30) {
                super.getActions().add(new Advance(super.getDistance(FieldType.POSITION_1)));
            } else if(getDistance(FieldType.CARROT) < getDistance(FieldType.SALAD) && isMovePlayable(1, FieldType.CARROT)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                if(getPlayer().getCarrots() < 30) super.setNewTask(2);
                else super.setNewTask(3);
            } else if(isMovePlayable(0, FieldType.HEDGEHOG)) {
                super.getActions().add(new FallBack());
            } else {
                super.getActions().add(new Skip());
                //super.setActions(new ArrayList<Action>(Sigmoid.getRandomAction(super.getGameState()).getActions())); Alternate
            }
        } else {
            if(super.getPlayer().ownsCardOfType(CardType.EAT_SALAD) && isMovePlayable(1, FieldType.HARE)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.HARE), 0));
                super.getActions().add(new Card(CardType.EAT_SALAD, 1));
            } else if(getPlayer().ownsCardOfType(CardType.EAT_SALAD) && isMovePlayable(1, FieldType.HARE)){
                getActions().add(new Advance(getDistance(FieldType.HARE), 0));
                getActions().add(new Card(CardType.EAT_SALAD, 0));
            } else if(isMovePlayable(1, FieldType.CARROT)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                super.setNewTask(2);
            } else if(GameRuleLogic.isValidToFallBack(super.getGameState()) && isMovePlayable(0, FieldType.HEDGEHOG) /*&& super.getEnemy().getFieldIndex()+5 < super.getPlayer().getFieldIndex()*/) { //Fix #18 --> give it a reason, value 5 can be adjusted!!!
                super.getActions().add(new FallBack());
            } else {
                super.getActions().add(new Skip());
                //super.setActions(new ArrayList<Action>(Sigmoid.getRandomAction(super.getGameState()).getActions())); Alternate
            }
        }
    }
}