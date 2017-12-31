package sc.player2018.logic.Parts;
import sc.plugin2018.*;
import sc.player2018.logic.Parts.extendedAI.Sigmoid;
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
            } else if(super.getPlayer().ownsCardOfType(CardType.TAKE_OR_DROP_CARROTS) && isMovePlayable(1, FieldType.HARE)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.HARE), 0));
                super.getActions().add(new Card(CardType.TAKE_OR_DROP_CARROTS, -20 ,1));
            } else if(isMovePlayable(1, FieldType.CARROT)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                if(super.getPlayer().getCarrots()>20) { //#Fix 30
                    super.setNewTask(3);
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
            } else if(isMovePlayable(1, FieldType.CARROT)) {
                super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                super.setNewTask(2);
            //} else if(isMovePlayable(0, FieldType.HEDGEHOG)) { #Fix 18
                //super.getActions().add(new FallBack());
            } else {
                super.getActions().add(new Skip());
                //super.setActions(new ArrayList<Action>(Sigmoid.getRandomAction(super.getGameState()).getActions())); Alternate
            }
        } else {
            if(GameRuleLogic.isValidToFallBack(super.getGameState()) && isMovePlayable(0, FieldType.HEDGEHOG) && super.getEnemy().getFieldIndex()+5 < super.getPlayer().getFieldIndex()) { //Fix #18 --> give it a reason, value 5 can be adjusted!!!
                super.getActions().add(new FallBack());
            } else if(isMovePlayable(1, FieldType.CARROT)) {
                 super.getActions().add(new Advance(super.getDistance(FieldType.CARROT)));
                 super.setNewTask(2);
             } else {
                super.getActions().add(new Skip());
                //super.setActions(new ArrayList<Action>(Sigmoid.getRandomAction(super.getGameState()).getActions())); Alternate
            }
        }
    }
}