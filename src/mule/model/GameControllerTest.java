package mule.model;

import static org.junit.Assert.*;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;


public class GameControllerTest {
    GameController gc;
    Player cp;

    @Before
    public void setUp() {
        gc = new GameController();
        cp = new Player("name", "Human", Color.TRANSPARENT, "beginner" );
        gc.setCurrentPlayer(cp);
    }

    @Test
    public void testUpdateTurnTime() {

        gc.setRoundNumber(1);
        gc.updateTurnTime();
        assertEquals(50, gc.getTurnTime());

        gc.setRoundNumber(2);
        cp.setFood(0);
        gc.updateTurnTime();
        assertEquals(5, gc.getTurnTime());
        cp.setFood(2);
        gc.updateTurnTime();
        assertEquals(30, gc.getTurnTime());
        cp.setFood(4);
        gc.updateTurnTime();
        assertEquals(50, gc.getTurnTime());

        gc.setRoundNumber(6);
        cp.setFood(0);
        gc.updateTurnTime();
        assertEquals(5, gc.getTurnTime());
        cp.setFood(3);
        gc.updateTurnTime();
        assertEquals(30, gc.getTurnTime());
        cp.setFood(5);
        gc.updateTurnTime();
        assertEquals(50, gc.getTurnTime());

        gc.setRoundNumber(10);
        cp.setFood(0);
        gc.updateTurnTime();
        assertEquals(5, gc.getTurnTime());
        cp.setFood(4);
        gc.updateTurnTime();
        assertEquals(30, gc.getTurnTime());
        cp.setFood(6);
        gc.updateTurnTime();
        assertEquals(50, gc.getTurnTime());

    }
}

//    private void updateTurnTime() {
//        if (roundNumber == 1) {
//            turnTime = 50;
//        }
//        else {
//            if (roundNumber < 5) {
//                if (currentPlayer.getFood() >= 3) {
//                    turnTime = 50;
//                }
//                else if (currentPlayer.getFood() > 0 && currentPlayer.getFood() < 3) {
//                    turnTime = 30;
//                }
//                else {
//                    turnTime = 5;
//                }
//            }
//            else if (roundNumber < 9) {
//                if (currentPlayer.getFood() >= 4) {
//                    turnTime = 50;
//                }
//                else if (currentPlayer.getFood() > 0 && currentPlayer.getFood() < 4) {
//                    turnTime = 30;
//                }
//                else {
//                    turnTime = 5;
//                }
//            }
//            else {
//                if (currentPlayer.getFood() >= 5) {
//                    turnTime = 50;
//                }
//                else if (currentPlayer.getFood() > 0 && currentPlayer.getFood() < 5) {
//                    turnTime = 30;
//                }
//                else {
//                    turnTime = 5;
//                }
//            }
//        }
