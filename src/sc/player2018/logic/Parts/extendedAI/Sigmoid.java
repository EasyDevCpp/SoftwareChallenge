package sc.player2018.logic.Parts.extendedAI;
import sc.plugin2018.*;

import java.lang.Math;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public final class Sigmoid {
    private static int pos=0;
    private static final Random rand=new SecureRandom();
    private static ArrayList<Move> perfectMoves=new ArrayList<Move>();
    private static ArrayList<Action> actionContainer=new ArrayList<Action>();

    private Sigmoid() {
        actionContainer.add(new Advance(1));                                        //0
        actionContainer.add(new Advance(2));                                        //1
        actionContainer.add(new Advance(3));                                        //2
        actionContainer.add(new Advance(4));                                        //3
        actionContainer.add(new Advance(5));                                        //4
        actionContainer.add(new Advance(6));                                        //5
        actionContainer.add(new Advance(7));                                        //6
        actionContainer.add(new Advance(8));                                        //7
        actionContainer.add(new Advance(9));                                        //8
        actionContainer.add(new Advance(10));                                       //9
        actionContainer.add(new Advance(11));                                       //10
        actionContainer.add(new Advance(12));                                       //11
        actionContainer.add(new Advance(13));                                       //12
        actionContainer.add(new Advance(14));                                       //13
        actionContainer.add(new Advance(15));                                       //14
        actionContainer.add(new Advance(16));                                       //15
        actionContainer.add(new Advance(17));                                       //16
        actionContainer.add(new Advance(18));                                       //17
        actionContainer.add(new ExchangeCarrots(10));                               //18
        actionContainer.add(new ExchangeCarrots(-10));                              //19
        actionContainer.add(new EatSalad());                                        //20
        actionContainer.add(new Card(CardType.TAKE_OR_DROP_CARROTS,20,1));          //21
        actionContainer.add(new Card(CardType.TAKE_OR_DROP_CARROTS,-20,1));         //22
        actionContainer.add(new Card(CardType.EAT_SALAD,1));                        //23
        actionContainer.add(new Card(CardType.HURRY_AHEAD,1));                      //24
        actionContainer.add(new Card(CardType.FALL_BACK,1));                        //25
        actionContainer.add(new FallBack());                                        //26
        actionContainer.add(new Skip());
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
     * method: getMostEfficientMove(GameState gs,int threshold,int quality)
     * return: Move
     * desc:   returns the most efficient move
     * use to: do more efficient turns
     * 
     * author: Robin Krause
     */
     //Doen't work... guess it takes too long... idk
    public static Move getMostEfficientMove(GameState gameState,int threshold,int quality) {
        if(gameState.getCurrentPlayerColor().name()=="BLUE") {
            ArrayList<Action> tmp=new ArrayList<Action>();
            tmp.add(actionContainer.get(0));
            tmp.add(actionContainer.get(21));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(1));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(18));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(0));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(1));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(18));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(3));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(20));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(3));
            tmp.add(actionContainer.get(23));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(0));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(18));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(7));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(20));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(10));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(1));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(3));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(18));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(2));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(20));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(5));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(8));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(20));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
            tmp.add(actionContainer.get(6));
            perfectMoves.add(new Move(tmp));
            tmp.clear();
        }
        if(quality!=1) {
            int perfect_pos=0;
            //Find out which index is the most "perfect" one
            for(Action a: perfectMoves.get(pos-1).getActions()) {
                for(int i=0;i<actionContainer.size();i++) {
                    if(a.compareTo(actionContainer.get(i))==1) {
                        perfect_pos=i;
                    }
                }
            }
            //Search in threshold range
            for(int i=0;i<=threshold;i++) {
                for(int j=0;j<gameState.getPossibleMoves().size();j++) {
                    for(Action a: gameState.getPossibleMoves().get(j).getActions()) {
                        if(perfect_pos+i<actionContainer.size()&&a.compareTo(actionContainer.get(perfect_pos+i))==1) {
                            pos++;
                            return gameState.getPossibleMoves().get(j);
                        } else if(perfect_pos-i>0&&a.compareTo(actionContainer.get(perfect_pos-i))==1) {
                            pos++;
                            return gameState.getPossibleMoves().get(j);
                        }
                    }
                }
            }
        } else {
            for(Move m: gameState.getPossibleMoves()) {
                if(m.equals(perfectMoves.get(pos))) {
                    pos++;
                    return perfectMoves.get(pos-1);
                }
            }
        }
        //Impossible to find an "efficient" move
        pos++;
        return null;
    }
    /*
     * method: getMostEfficientAction(GameState gs,double quality)
     * return: Move
     * desc:   returns the most significant Action
     * use to: do more efficient turns
     * 
     * author: Robin Krause
     */
    @Deprecated
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
    /*
     * method: isPossible(Move m)
     * return: boolean
     * desc:   returns either a move is possible or not
     * use to: -
     * 
     * author: Robin Krause
     */
    public static boolean isPossible(Move m,GameState gameState) {
        Move move=m;
        for(Move mi: gameState.getPossibleMoves()) {
            if(move.equals(mi)) {
                return true;
            }
        }
        return true;
    }
}
