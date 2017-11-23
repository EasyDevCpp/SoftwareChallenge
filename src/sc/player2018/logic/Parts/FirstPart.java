package sc.player2018.logic.Parts;

import sc.plugin2018.*;

public class FirstPart extends Part{
    private int step;
    private boolean step0WasAtHare = false;

    @Override
    public void processAI(){
        Board b = super.getGameState().getBoard();

        if(step == 0) { //to first salad
            if (!enemyOnNextFieldType(FieldType.SALAD) && super.getKarrotCosts()[b.getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()) {
                super.getActions().add(new Advance(b.getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
                setNewTask(1);
                step = 1;
            } else {
                if (enemyOnNextFieldType(FieldType.SALAD)) {
                    if(step0WasAtHare || b.getNextFieldByType(FieldType.POSITION_2, super.getPlayer().getFieldIndex()) < b.getNextFieldByType(FieldType.HARE, super.getPlayer().getFieldIndex())){
                        if(super.getKarrotCosts()[b.getNextFieldByType(FieldType.POSITION_2, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()){
                            super.getActions().add(new Advance(b.getNextFieldByType(FieldType.POSITION_2, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
                            step0WasAtHare = false;
                        } else{
                            super.getActions().add(new Skip(1));
                        }
                    } else{
                        if(super.getKarrotCosts()[b.getNextFieldByType(FieldType.HARE, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()){ //stage0WasAtHare == false
                            super.getActions().add(new Advance(b.getNextFieldByType(FieldType.HARE, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
                            super.getActions().add(new Card(CardType.EAT_SALAD, 1));

                            step0WasAtHare = true;
                        } else{
                            super.getActions().add(new Skip(1));
                        }
                    }
                } else {
                    super.getActions().add(new Advance(b.getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
                    setNewTask(1);
                    step = 1;
                }
            }
        } else if(step == 1){
            if(super.getPlayer().getFieldIndex() > super.getEnemy().getFieldIndex()){
                if(!enemyOnNextFieldType(FieldType.SALAD) && super.getKarrotCosts()[b.getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex())] <= super.getPlayer().getCarrots()){
                    super.getActions().add(new Advance(b.getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));  //go to second part
                    setNewTask(1);
                    step = 2;
                } else if(!enemyOnNextFieldType(FieldType.POSITION_1) && super.getKarrotCosts()[b.getNextFieldByType(FieldType.POSITION_1, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()){
                    super.getActions().add(new Advance(b.getNextFieldByType(FieldType.POSITION_1, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
                } else if(!enemyOnNextFieldType(FieldType.HARE) && super.getPlayer().getCards().contains(CardType.EAT_SALAD) && super.getKarrotCosts()[b.getNextFieldByType(FieldType.HARE, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()){
                    super.getActions().add(new Advance(b.getNextFieldByType(FieldType.HARE, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
                    super.getActions().add(new Card(CardType.EAT_SALAD, 1));
                } else if(!enemyOnNextFieldType(FieldType.CARROT) && super.getKarrotCosts()[b.getNextFieldByType(FieldType.CARROT, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()){
                    super.getActions().add(new Advance(b.getNextFieldByType(FieldType.CARROT, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
                    setNewTask(2);
                }
            } else{
                if(!enemyOnNextFieldType(FieldType.SALAD) && super.getKarrotCosts()[b.getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()){
                    super.getActions().add(new Advance(b.getNextFieldByType(FieldType.SALAD, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex())); //go to second part
                    setNewTask(3);
                    step = 2;
                } else if(b.getNextFieldByType(FieldType.CARROT, super.getPlayer().getFieldIndex()) < b.getNextFieldByType(FieldType.SALAD,super.getPlayer().getFieldIndex()) && !enemyOnNextFieldType(FieldType.CARROT) && super.getKarrotCosts()[b.getNextFieldByType(FieldType.CARROT, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()){
                    super.getActions().add(new Advance(b.getNextFieldByType(FieldType.CARROT, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
                    setNewTask(2);
                } else if(b.getPreviousFieldByType(FieldType.HEDGEHOG, super.getPlayer().getFieldIndex()) < b.getNextFieldByType(FieldType.SALAD,super.getPlayer().getFieldIndex()) && !enemyOnPreviousFieldType(FieldType.HEDGEHOG)){
                    super.getActions().add(new FallBack(0));
                } else{
                    super.getActions().add(new Skip(1));
                }
            }
        } else if(step == 2){
            if(!enemyOnNextFieldType(FieldType.POSITION_1) && super.getKarrotCosts()[b.getNextFieldByType(FieldType.POSITION_1, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()){
                super.getActions().add(new Advance(b.getNextFieldByType(FieldType.POSITION_1, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
            } else if(!enemyOnNextFieldType(FieldType.CARROT) && super.getKarrotCosts()[b.getNextFieldByType(FieldType.CARROT, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()){
                super.getActions().add(new Advance(b.getNextFieldByType(FieldType.CARROT, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
            } else if(!enemyOnNextFieldType(FieldType.HEDGEHOG) && super.getKarrotCosts()[b.getNextFieldByType(FieldType.HEDGEHOG, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()] <= super.getPlayer().getCarrots()){
                super.getActions().add(new Advance(b.getNextFieldByType(FieldType.HEDGEHOG, super.getPlayer().getFieldIndex()) - super.getPlayer().getFieldIndex()));
            } else{
                super.getActions().add(new Skip(1));
            }
        }
    }
}
