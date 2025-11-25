package cs3500.test.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw02.Canvas;
import cs3500.solored.view.hw02.SoloRedGameTextView;

/**
 * Represents the testing file for the SoloRed game.
 */
public class SoloRedTest {

  private Canvas redCanvas;
  private SoloRedCard redOne;
  private ArrayList<SoloRedCard> listRedOneBlueTwo;
  private ArrayList<SoloRedCard> listBlueTwoRedOne;
  private SoloRedGameModel model;
  private SoloRedGameModel textModel;
  private SoloRedGameTextView textView;
  private SoloRedGameModel almostWinningModel;
  private SoloRedGameModel almostLosingModel;

  /**
   * Initializes the data.
   */
  @Before
  public void initData() {
    redCanvas = Canvas.Red;
    redOne = new SoloRedCard(Canvas.Red, 1);
    SoloRedCard blueTwo = new SoloRedCard(Canvas.Blue, 2);
    listRedOneBlueTwo = new ArrayList<>(Arrays.asList(redOne, blueTwo));
    listBlueTwoRedOne = new ArrayList<>(Arrays.asList(blueTwo, redOne));
    model = new SoloRedGameModel(new Random(2));

    textModel = new SoloRedGameModel(new Random(2));
    textModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 2, 2);
    textView = new SoloRedGameTextView(textModel);

    almostWinningModel = new SoloRedGameModel();
    almostWinningModel.startGame(new ArrayList<>(Arrays.asList(
                    new SoloRedCard(Canvas.Red, 1), new SoloRedCard(Canvas.Orange, 2),
                    new SoloRedCard(Canvas.Blue, 3),
                    new SoloRedCard(Canvas.Indigo, 4), new SoloRedCard(Canvas.Violet, 5))),
            false, 4, 1);
    SoloRedGameTextView almostWinningTextView = new SoloRedGameTextView(almostWinningModel);

    almostLosingModel = new SoloRedGameModel();
    almostLosingModel.startGame(new ArrayList<>(Arrays.asList(
                    new SoloRedCard(Canvas.Red, 1), new SoloRedCard(Canvas.Orange, 2),
                    new SoloRedCard(Canvas.Blue, 3),
                    new SoloRedCard(Canvas.Indigo, 4), new SoloRedCard(Canvas.Violet, 2))),
            false, 4, 1);
    SoloRedGameTextView almostLosingTextView = new SoloRedGameTextView(almostLosingModel);
  }

  /**
   * Tests if SoloRedCard getCanvas() works.
   */
  @Test
  public void testSoloRedCardgetCanvas() {
    Assert.assertEquals(Canvas.Red, redOne.getCanvas());
  }

  /**
   * Tests if SoloRedCard getValue() works.
   */
  @Test
  public void testSoloRedCardgetValue() {
    Assert.assertEquals(1, redOne.getValue());
  }

  /**
   * Tests if SoloRedCard toString() works.
   */
  @Test
  public void testSoloRedCardtoString() {
    Assert.assertEquals("R1", redOne.toString());
  }

  /**
   * Tests if the equals method is working correctly.
   */
  @Test
  public void testSoloRedCardequals() {
    Assert.assertTrue(redOne.equals(new SoloRedCard(Canvas.Red, 1)));
  }

  /**
   * Tests if SoloRedCard sortValue() works.
   */
  @Test
  public void testSoloRedCardsortValue() {
    Assert.assertEquals(listRedOneBlueTwo, SoloRedCard.sortValue(listBlueTwoRedOne));
  }

  /**
   * Tests if Canvas toString() works.
   */
  @Test
  public void testCanvastoString() {
    Assert.assertEquals(redCanvas.toString(), "R");
  }

  /**
   * Tests if SoloRedGameModel() throws an exception for null random.
   */
  @Test
  public void testSoloRedGameModelConstructorException() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new SoloRedGameModel(null));
  }

  /**
   * Tests if startGame throws correct exceptions.
   */
  @Test
  public void teststartGameExceptions() {
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(null, false, 2, 2));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(
            new ArrayList<>(), false, 2, 2));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(
            new ArrayList<>(Arrays.asList(redOne, null)), false, 2, 2));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(
            SoloRedGameModel.DECK_EXAMPLE, false, 1, 2));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(
            SoloRedGameModel.DECK_EXAMPLE, false, 2, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(
            SoloRedGameModel.DECK_EXAMPLE, false, 40, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(
            SoloRedGameModel.DECK_EXAMPLE, false, 4, 40));
    Assert.assertThrows(IllegalArgumentException.class, () -> model.startGame(
            new ArrayList<>(Arrays.asList(redOne, redOne)), false, 40, 0));
    SoloRedGameModel changingModel = new SoloRedGameModel(new Random(2));
    changingModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 2, 2);
    Assert.assertThrows(IllegalStateException.class, () -> changingModel.startGame(
            SoloRedGameModel.DECK_EXAMPLE, false, 4, 7));
    SoloRedGameModel finishedModel = new SoloRedGameModel(new Random(2));
    finishedModel.startGame(SoloRedGameModel.DECK_EXAMPLE, false, 35, 2);
    Assert.assertThrows(IllegalStateException.class, () -> finishedModel.startGame(
            SoloRedGameModel.DECK_EXAMPLE, false, 4, 7));
  }


  /**
   * Tests checkStart, testing if the IllegalStateException throws for the following methods:
   * playToPalette()
   * playToCanvas()
   * drawForHand()
   * numCardsInDeck()
   * numPalettes()
   * winningPaletteIndex()
   * checkWinningPaletteIndex() (private so no tested)
   * isGameOver()
   * isGameWon()
   * getHand()
   * getPalette()
   * getCanvas().
   * This means the tester methods throwing an IllegalStateException case is tested here.
   */
  @Test
  public void testcheckStart() {
    Assert.assertThrows(IllegalStateException.class, () -> model.checkStart());
  }

  /**
   * Tests if playtoPalette() works.
   */
  @Test
  public void testplayToPalette() {
    textModel.playToPalette(0, 0);
    String textString = "Canvas: R\n"
            + "> P1: R1 R3\n"
            + "P2: R2\n"
            + "Hand: R4";
    Assert.assertEquals(textView.toString(), textString);
  }

  /**
   * Tests if playToCanvas() works.
   */
  @Test
  public void testPlayToCanvas() {
    textModel.playToCanvas(0);
    String textString = "Canvas: R\n"
            + "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R4";
    Assert.assertEquals(textView.toString(), textString);
  }

  /**
   * Tests if drawForHand() works correctly.
   */
  @Test
  public void testdrawForHand() {
    textModel.playToPalette(0, 0);
    textModel.drawForHand();
    String textString = "Canvas: R\n"
            + "> P1: R1 R3\n"
            + "P2: R2\n"
            + "Hand: R4 R5";
    Assert.assertEquals(textView.toString(), textString);
    textModel.drawForHand();
    Assert.assertEquals(textView.toString(), textString);
  }

  /**
   * Tests if numCardsInDeck() works correctly.
   */
  @Test
  public void testnumCardsinDeck() {
    Assert.assertEquals(textModel.numOfCardsInDeck(), 31);
  }

  /**
   * Tests if winningPaletteIndex() works correctly.
   */
  @Test
  public void testwinningPaletteIndex() {
    Assert.assertEquals(textModel.winningPaletteIndex(), 1);
  }

  /**
   * Tests if isGameOver() works correctly.
   */
  @Test
  public void testisGameOver() {
    Assert.assertEquals(almostWinningModel.isGameOver(), false);
    almostWinningModel.playToPalette(0, 0);
    Assert.assertEquals(almostWinningModel.isGameOver(), true);
    almostLosingModel.playToPalette(0, 0);
    Assert.assertEquals(almostLosingModel.isGameOver(), true);
  }

  /**
   * Tests if isGameWon works correctly.
   */
  @Test
  public void testisGameWon() {
    Assert.assertThrows(IllegalStateException.class, () -> almostWinningModel.isGameWon());
    almostWinningModel.playToPalette(0, 0);
    Assert.assertEquals(almostWinningModel.isGameWon(), true);
    almostLosingModel.playToPalette(0, 0);
    Assert.assertEquals(almostLosingModel.isGameWon(), false);
  }

  /**
   * Tests if getHand is working correctly.
   */
  @Test
  public void testgetHand() {
    List<SoloRedCard> hand = almostWinningModel.getHand();
    Assert.assertEquals(hand.get(0), new SoloRedCard(Canvas.Violet, 5));
  }

  /**
   * Tests if getPalette is working correctly.
   */
  @Test
  public void testgetPalette() {
    List<SoloRedCard> almostLosingPalette0 = almostLosingModel.getPalette(0);
    Assert.assertTrue(almostLosingPalette0.get(0).equals(new SoloRedCard(Canvas.Red, 1)));
  }

  /**
   * Tests if getCanvas() is working correctly.
   */
  @Test
  public void testgetCanvas() {
    Assert.assertEquals(almostWinningModel.getCanvas(), new SoloRedCard(Canvas.Red, -1));
  }

  /**
   * Tests if getAllCards() is working correctly.
   */
  @Test
  public void testgetAllCards() {
    Assert.assertEquals(model.getAllCards(), SoloRedGameModel.DECK_EXAMPLE);
  }

  /**
   * Tests if shuffle works correctly.
   */
  @Test
  public void testShuffle() {
    SoloRedGameModel shuffleTest1 = new SoloRedGameModel(new Random(2));
    shuffleTest1.startGame(SoloRedGameModel.DECK_EXAMPLE, true, 2, 2);
    SoloRedGameModel shuffleTest2 = new SoloRedGameModel(new Random(2));
    shuffleTest2.startGame(SoloRedGameModel.DECK_EXAMPLE, true, 2, 2);
    List<SoloRedCard> shuffledCards1 = shuffleTest1.getAllCards();
    List<SoloRedCard> shuffledCards2 = shuffleTest2.getAllCards();

    for (int i = 0; i < shuffledCards1.size(); i++) {
      Assert.assertEquals(shuffledCards1.get(i), shuffledCards2.get(i));
    }
  }

  /**
   * Tests if SoloRedGameTextView.toString() works correctly.
   */
  @Test
  public void testSoloRedGameTextViewtoString() {
    Assert.assertEquals(textView.toString(), "Canvas: R\n" +
            "P1: R1\n"
            + "> P2: R2\n"
            + "Hand: R3 R4");
    textModel.playToPalette(0, 0);
    Assert.assertEquals(textView.toString(), "Canvas: R\n"
            + "> P1: R1 R3\n"
            + "P2: R2\n"
            + "Hand: R4");
  }


}
