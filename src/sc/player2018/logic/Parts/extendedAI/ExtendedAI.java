package sc.player2018.logic.Parts.extendedAI;

import sc.plugin2018.*;
import sc.plugin2018.util.Constants;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public final class ExtendedAI {
    private static int pos=0;
    private static final Random rand=new SecureRandom();
    private static ArrayList<Move> perfectMoves=new ArrayList<Move>();
    private static ArrayList<Action> actionContainer=new ArrayList<Action>();

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