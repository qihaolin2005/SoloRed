package cs3500.test.hw02;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.Canvas;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * This is the test file for the RedGameCreator file. This is also the test
 * file for the AdvancedSoloRedGameModel file.
 */
public class SoloRedFactoryTest {

  RedGameCreator creator;
  RedGameModel<SoloRedCard> basicModel;
  RedGameModel<SoloRedCard> advancedModel;


  @Before
  public void setUp() {
    creator = new RedGameCreator();
    basicModel = creator.createGame(RedGameCreator.GameType.BASIC);
    advancedModel = creator.createGame(RedGameCreator.GameType.ADVANCED);

  }

  /**
   * Checks if startGame works correctly and gives the same hand.
   */
  @Test
  public void teststartGameAdvanced() {
    advancedModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    Assert.assertEquals(new ArrayList<SoloRedCard>(Arrays.asList(new SoloRedCard(Canvas.Red, 5),
            new SoloRedCard(Canvas.Red, 6),
            new SoloRedCard(Canvas.Red, 7),
            new SoloRedCard(Canvas.Orange, 1),
            new SoloRedCard(Canvas.Orange, 2),
            new SoloRedCard(Canvas.Orange, 3),
            new SoloRedCard(Canvas.Orange, 4)
            )), advancedModel.getHand());
  }

  /**
   *  Checks if startGame works correctly and gives the same palettes.
   */
  @Test
  public void teststartGameAdvanced2() {
    advancedModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    basicModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    Assert.assertEquals(advancedModel.getPalette(0), basicModel.getPalette(0));
    Assert.assertEquals(advancedModel.getPalette(1), basicModel.getPalette(1));
    Assert.assertEquals(advancedModel.getPalette(2), basicModel.getPalette(2));
    Assert.assertEquals(advancedModel.getPalette(3), basicModel.getPalette(3));
  }

  /**
   *  Checks if startGame throws null deck exception.
   */
  @Test
  public void teststartGameAdvanced3() {
    Assert.assertThrows(
            IllegalArgumentException.class, () -> advancedModel.startGame(
                    null, false, 4, 7));
  }

  /**
   *  Checks if startGame throws deck containing null exception.
   */
  @Test
  public void teststartGameAdvanced4() {
    Assert.assertThrows(
            IllegalArgumentException.class, () -> advancedModel.startGame(
                    new ArrayList<>(Arrays.asList(
                            new SoloRedCard(Canvas.Red, 1), null)), false, 4, 7));
  }

  /**
   *  Checks if startGame throws minimum paletteSize exception.
   */
  @Test
  public void teststartGameAdvanced5() {
    Assert.assertThrows(
            IllegalArgumentException.class, () -> advancedModel.startGame(
                    SoloRedGameModel.DECK_EXAMPLE, false, 1, 7));
  }

  /**
   *  Checks if startGame throws minimum handSize exception.
   */
  @Test
  public void teststartGameAdvanced6() {
    Assert.assertThrows(
            IllegalArgumentException.class, () -> advancedModel.startGame(
                    SoloRedGameModel.DECK_EXAMPLE, false, 4, -1));
  }

  /**
   *  Checks if startGame throws maximum handSize exception.
   */
  @Test
  public void teststartGameAdvanced7() {
    Assert.assertThrows(
            IllegalArgumentException.class, () -> advancedModel.startGame(
                    SoloRedGameModel.DECK_EXAMPLE, false, 4, 100));
  }

  /**
   *  Checks if startGame throws maximum paletteSize exception.
   */
  @Test
  public void teststartGameAdvanced8() {
    Assert.assertThrows(
            IllegalArgumentException.class, () -> advancedModel.startGame(
                    SoloRedGameModel.DECK_EXAMPLE, false, 100, 7));
  }

  /**
   *  Checks if advanced playToCanvas plays the same canvas.
   */
  @Test
  public void testplayToCanvasAdvanced() {
    advancedModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    basicModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    advancedModel.playToCanvas(6);
    basicModel.playToCanvas(6);
    Assert.assertEquals(advancedModel.getCanvas(), basicModel.getCanvas());
  }

  /**
   *  Checks if advanced drawForHand correctly only draws 1 card.
   */
  @Test
  public void testdrawForHandAdvanced() {
    advancedModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    advancedModel.playToPalette(0, 0);
    advancedModel.playToPalette(1, 0);
    advancedModel.drawForHand();
    Assert.assertEquals(advancedModel.getHand().size(), 6);
  }

  /**
   *  Checks if advanced drawForHand correctly draws 2 cards.
   */
  @Test
  public void testdrawForHandAdvanced2() {
    advancedModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    advancedModel.playToCanvas(1);
    advancedModel.playToPalette(0, 0);
    advancedModel.drawForHand();
    Assert.assertEquals(advancedModel.getHand().size(), 7);
  }

  /**
   *  Checks if advanced drawForHand correctly only draws until handSize.
   */
  @Test
  public void testdrawForHandAdvanced3() {
    advancedModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    advancedModel.playToCanvas(1);
    advancedModel.drawForHand();
    advancedModel.playToPalette(0, 0);
    advancedModel.drawForHand();
    Assert.assertEquals(advancedModel.getHand().size(), 7);
  }

  /**
   *  Checks if advanced drawForHand correctly only draws until handSize.
   */
  @Test
  public void testdrawForHandBasic() {
    basicModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    basicModel.playToCanvas(1);
    basicModel.playToPalette(0, 0);
    basicModel.drawForHand();
    Assert.assertEquals(basicModel.getHand().size(), 7);
  }

  /**
   *  Checks if the controller works for basic.
   */
  @Test
  public void testControllerBasic() {
    StringReader reader = new StringReader("q");
    StringBuilder builder = new StringBuilder();
    SoloRedTextController basicController = new SoloRedTextController<>(reader, builder);
    basicController.playGame(basicModel, SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    Assert.assertEquals(builder.toString(),"Canvas: R\n" +
            "P1: R1\n" +
            "P2: R2\n" +
            "P3: R3\n" +
            "> P4: R4\n" +
            "Hand: R5 R6 R7 O1 O2 O3 O4\n" +
            "Number of cards in deck: 24\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "P2: R2\n" +
            "P3: R3\n" +
            "> P4: R4\n" +
            "Hand: R5 R6 R7 O1 O2 O3 O4\n" +
            "Number of cards in deck: 24\n");
  }

  /**
   *  Checks if the controller works for advanced.
   */
  @Test
  public void testControllerAdvanced() {
    StringReader reader = new StringReader("q");
    StringBuilder builder = new StringBuilder();
    SoloRedTextController basicController = new SoloRedTextController<>(reader, builder);
    basicController.playGame(advancedModel, SoloRedGameModel.DECK_EXAMPLE, false, 4, 7);
    Assert.assertEquals(builder.toString(),"Canvas: R\n" +
            "P1: R1\n" +
            "P2: R2\n" +
            "P3: R3\n" +
            "> P4: R4\n" +
            "Hand: R5 R6 R7 O1 O2 O3 O4\n" +
            "Number of cards in deck: 24\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "P2: R2\n" +
            "P3: R3\n" +
            "> P4: R4\n" +
            "Hand: R5 R6 R7 O1 O2 O3 O4\n" +
            "Number of cards in deck: 24\n");
  }
}
