package sc.player2018.logic.Parts.extendedAI;
import sc.plugin2018.*;

import java.lang.Math;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public final class Sigmoid {
    private Sigmoid() {

    }
    /*
     * method: _sigmoid(double)
     * return: double
     * desc:   calculating 1/1+e^-value -> 1/2*(1+tanh(value/2))
     * use to: calculate the influence of a specific value
     * 
     * author: Robin Krause
     */
    private static double _sigmoid(double value) {
        return 0.5*(1+Math.tanh(value/2));
    }
    /*
     * method: getMostEfficientAction(GameState gs,double quality)
     * return: Move
     * desc:   returns the most significant Action
     * use to: do more efficient turns
     * 
     * author: Robin Krause
     */
    public static Move getMostEfficientAction(GameState gameState,double quality) {
        int average=0;
        int max=0;
        int advance=0;
        int card=0;
        int exchange=0;
        int fallback=0;
        int eatsalad=0;
        int skip=0;
        ArrayList<Action> actions=new ArrayList<Action>();
        
        for(Move m: gameState.getPossibleMoves()) {
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
        if((0.69*_sigmoid(average/max)+0.03)<1.25-quality) {
            return gameState.getPossibleMoves().get((int)average/max);
        } else {
            return null;
        }
    }
    /*
     * method: getRandomAction()
     * return: Move
     * desc:   returns a random Action
     * use to: -
     * 
     * author: Robin Krause
     */
    public static Move getRandomAction(GameState gameState) {
        Random rand = new SecureRandom();
        return gameState.getPossibleMoves().get(rand.nextInt(gameState.getPossibleMoves().size()));
    }
}
