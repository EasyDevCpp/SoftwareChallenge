package sc.player2018.logic.Parts.extendedAI;
import java.lang.Math;

public class Sigmoid {
    /*
     * method: _sigmoid(double)
     * return: double
     * desc:   calculating 1/1+e^-value -> 1/2*(1+tanh(value/2))
     * use to: calculate the influence of a specific value
     * 
     * author: Robin Krause
     */
    private double _sigmoid(double value) {
        return 0.5*(1+Math.tanh(value/2));
    }
    /*
     * method: getMostEfficientAction()
     * return: Action
     * desc:   returns the most significant Action
     * use to: do more efficient turns
     * 
     * author: Robin Krause
     */
    public Action getMostEfficientAction() {
        int average=0;
        int max=0;
        int advance=0;
        int card=0;
        int exchange=0;
        int fallback=0;
        int eatsalad=0;
        int skip=0;
        ArrayList<Action> actions=new ArrayList<>();
        
        for(Move m: gs.getPossibleMoves()) {
            for(Action a: m.actions) {
                actions.add(a);   
            }
        }
        for(Action a: actions) {
            if(a instanceof Advance) {
                advance++;
            } else if(a instanceof Card) {
                card++;
            } else if(a instanceof ExchangeCarrots) {
                exchange++;
            } else if(a instanceof FallBack) {
                fallback++;
            } else if(a instanceof EatSalad) {
                eatsalad++;
            } else if(a instanceof Skip) {
                skip++;
            }
            max++;
        }
        for(Action a: actions) {
            if(a instanceof Advance) {
                average+=(int)max/advance;
            } else if(a instanceof Card) {
                average+=(int)max/card;
            } else if(a instanceof ExchangeCarrots) {
                average+=(int)max/exchange;
            } else if(a instanceof FallBack) {
                average+=(int)max/fallback;
            } else if(a instanceof EatSalad) {
                average+=(int)max/eatsalad;
            } else if(a instanceof Skip) {
                average+=(int)max/skip;
            }
        }
        if(_sigmoid(average/max)>0.6) {
            return actions.get((int)average/max);
        } else {
            return null;
        }
    }
}
