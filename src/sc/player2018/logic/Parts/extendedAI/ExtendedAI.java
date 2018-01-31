package sc.player2018.logic.Parts.extendedAI;
import sc.plugin2018.*;
import sc.plugin2018.util.Constants;

import java.lang.Math;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public final class ExtendedAI {
    private static int pos=0;
    private static final Random rand=new SecureRandom();
    private static ArrayList<Move> perfectMoves=new ArrayList<Move>();
    private static ArrayList<Action> actionContainer=new ArrayList<Action>();

    public ExtendedAI() {
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
     * method: calculateSigmoidValue(double)
     * return: double
     * desc:   calculating 1/1+e^-value -> 1/2*(1+tanh(value/2))
     * use to: calculate the influence of a specific value
     * 
     * author: Robin Krause
     */
    private static double calculateSigmoidValue(double value) {
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
            for(Action a: perfectMoves.get(pos).getActions()) {
                for(int i=0;i<actionContainer.size();i++) {
                    if(compareActions(a,actionContainer.get(i))) {
                        perfect_pos=i;
                    }
                }
            }
            //Search in threshold range
            for(int i=0;i<=threshold;i++) {
                for(int j=0;j<gameState.getPossibleMoves().size();j++) {
                    for(Action a: gameState.getPossibleMoves().get(j).getActions()) {
                        if(perfect_pos+i<actionContainer.size()&&compareActions(a,actionContainer.get(perfect_pos+i))) {
                            pos++;
                            return gameState.getPossibleMoves().get(j);
                        } else if(perfect_pos-i>0&&compareActions(a,actionContainer.get(perfect_pos-i))) {
                            pos++;
                            return gameState.getPossibleMoves().get(j);
                        }
                    }
                }
            }
        } else {
            for(Move m: gameState.getPossibleMoves()) {
                if(compareMoves(m,perfectMoves.get(pos))) {
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
     * method: getRandomMove(GameState gameState)
     * return: Move
     * desc:   returns a random Move
     * use to: -
     * 
     * author: Kiel
     */
    public static Move getRandomMove(GameState gameState) {
        Random rand = new SecureRandom();
        ArrayList<Move> possibleMove = gameState.getPossibleMoves();
        ArrayList<Move> saladMoves = new ArrayList<Move>();
        ArrayList<Move> winningMoves = new ArrayList<Move>();
        ArrayList<Move> selectedMoves = new ArrayList<Move>();

        int index = gameState.getCurrentPlayer().getFieldIndex();
        for (Move move : possibleMove) {
            for (Action action : move.actions) {
                if (action instanceof Advance) {
                    Advance advance = (Advance) action;
                    if (advance.getDistance() + index == Constants.NUM_FIELDS - 1) {
                        winningMoves.add(move);
                    } else if (gameState.getBoard().getTypeAt(advance.getDistance() + index) == FieldType.SALAD) {
                        saladMoves.add(move);
                    } else {
                        selectedMoves.add(move);
                    }
                } else if (action instanceof Card) {
                    Card card = (Card) action;
                    if (card.getType() == CardType.EAT_SALAD) {
                        saladMoves.add(move);
                    }
                } else if (action instanceof ExchangeCarrots) {
                    ExchangeCarrots exchangeCarrots = (ExchangeCarrots) action;
                    if (exchangeCarrots.getValue() == 10 && gameState.getCurrentPlayer().getCarrots() < 30 && index < 40
                            && !(gameState.getCurrentPlayer().getLastNonSkipAction() instanceof ExchangeCarrots)) {
                        selectedMoves.add(move);
                    } else if (exchangeCarrots.getValue() == -10 && gameState.getCurrentPlayer().getCarrots() > 30 && index >= 40) {
                        selectedMoves.add(move);
                    }
                } else if (action instanceof FallBack) {
                    if (index > 56 && gameState.getCurrentPlayer().getSalads() > 0) {
                        selectedMoves.add(move);
                    } else if (index <= 56 && index - gameState.getPreviousFieldByType(FieldType.HEDGEHOG, index) < 5) {
                        selectedMoves.add(move);
                    }
                } else {
                    selectedMoves.add(move);
                }
            }
        }
        Move move;
        if (!winningMoves.isEmpty()) {
            return winningMoves.get(rand.nextInt(winningMoves.size()));
        } else if (!saladMoves.isEmpty()) {
            return saladMoves.get(rand.nextInt(saladMoves.size()));
        } else if (!selectedMoves.isEmpty()) {
            return selectedMoves.get(rand.nextInt(selectedMoves.size()));
        } else {
            return possibleMove.get(rand.nextInt(possibleMove.size()));
        }
    }
    /*
     * method: compareActions(Action a1, Action a2)
     * return: boolean
     * desc:   returns true if both actions contain the same value and are from the same type
     * use to: -
     * 
     * author: Robin Krause
     */
    public static boolean compareActions(Action a1,Action a2) {
        //a1 is the one we compare to a2!
        int type=0;
        if(a1 instanceof Advance) type=1;
        else if(a1 instanceof EatSalad) type=2;
        else if(a1 instanceof ExchangeCarrots) type=3;
        else if(a1 instanceof Card) type=4;
        else if(a1 instanceof FallBack) type=5;
        else if(a1 instanceof Skip) type=6;
        //let's compare
        if(a1.order==a2.order) {
            if(type==1) {
                if(a2 instanceof Advance)
                    if(((Advance)a1).getDistance()==((Advance)a2).getDistance())
                        return true;
            } else if(type==2) {
                if(a2 instanceof EatSalad) return true;
            } else if(type==3) {
                if(a2 instanceof ExchangeCarrots)
                    if(((ExchangeCarrots)a1).getValue()==((ExchangeCarrots)a2).getValue())
                        return true;
            } else if(type==4) {
                if(a2 instanceof Card)
                    if(((Card)a1).getType()==((Card)a2).getType())
                        if(((Card)a1).getType()==CardType.TAKE_OR_DROP_CARROTS)
                            if(((Card)a1).getValue()==((Card)a2).getValue())
                                return true;
                        else
                            return true;
            } else if(type==5) {
                if(a2 instanceof FallBack) return true;
            } else if(type==6) {
                if(a2 instanceof Skip) return true;
            }
        }
        return false;
    }
    /*
     * method: compareMoves(Move m1, Move m2)
     * return: boolean
     * desc:   returns true if both moves contain the same actions
     * use to: -
     * 
     * author: Robin Krause
     */
    public static boolean compareMoves(Move m1,Move m2) {
        if(m1.getActions().size()==m2.getActions().size()) {
            for(int j=0;j<m1.getActions().size();j++) {
                if(!compareActions(m1.getActions().get(j),m2.getActions().get(j))) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
    /*
     * method: isMovePossible(Move m,GameState gs)
     * return: boolean
     * desc:   returns either a move is possible or not
     * use to: -
     * 
     * author: Robin Krause
     */
    public static boolean isMovePossible(Move m,GameState gameState) {
        for(Move pm: gameState.getPossibleMoves()) {
            if(compareMoves(pm,m)) {
                return true;
            }
        }
        return true;
    }
}