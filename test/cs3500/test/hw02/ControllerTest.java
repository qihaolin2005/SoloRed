package cs3500.test.hw02;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.Mocked;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw02.Canvas;

/**
 * Testing class for the controller.
 */
public class ControllerTest {
  StringBuilder append;
  StringReader reader;
  StringBuilder mockedAppend;
  private Mocked mock;
  SoloRedTextController controller;

  /**
   * Initializes the data.
   */
  @Before
  public void init() {
    reader = new StringReader("");
    append = new StringBuilder();
    mockedAppend = new StringBuilder();
  }

  /**
   * Tests if the controller can play to the palette.
   */
  @Test
  public void testplayPalette() {
    String str = "palette 1 1 q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" +
            "playing to palette: 0 using card: 0\n" +
            "drawing for hand\n", mockedAppend.toString());

  }

  /**
   * Tests if the controller can play to the canvas.
   */
  @Test
  public void testplayCanvas() {
    String str = "canvas 1 q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" +
            "playing to canvas using card: 0\n", mockedAppend.toString());

  }


  /**
   * Tests if the controller can play to the canvas using a negative input.
   */
  @Test
  public void testplayCanvasNegative() {
    String str = "canvas -1 q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" , mockedAppend.toString());
  }

  /**
   * Tests if the controller can play to the palette and quit.
   */
  @Test
  public void testplayPaletteQuit1() {
    String str = "palette 1 q palette 1 1";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" , mockedAppend.toString());
  }

  /**
   * Tests if the controller can play to the palette and quit.
   */
  @Test
  public void testplayPaletteQuit2() {
    String str = "palette q 1 palette 1 1";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" , mockedAppend.toString());
  }

  /**
   * Tests if the controller can play to the palette with string and negative inputs.
   */
  @Test
  public void testplayPaletteContinues() {
    String str = "palette -300 asd f g -300 1 asdf -1 -2 -300 2 q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" +
            "playing to palette: 0 using card: 1\n" +
            "drawing for hand\n" , mockedAppend.toString());
  }

  /**
   * Tests if the controller can play to the palette using negative inputs.
   */
  @Test
  public void testplayPaletteNegative() {
    String str = "palette -1 -1 -1 1 -1 1 q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" +
            "playing to palette: 0 using card: 0\n" +
            "drawing for hand\n" , mockedAppend.toString());
  }

  /**
   * Tests if the controller can play to the palette using a 0 input.
   */
  @Test
  public void testplayPaletteZero() {
    String str = "palette 0 0 q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" +
            "playing to palette: -1 using card: -1\n" +
            "drawing for hand\n" , mockedAppend.toString());
  }

  /**
   * Tests if the controller can play to the canvas using a 0 input.
   */
  @Test
  public void testplayCanvasZero() {
    String str = "canvas 0 q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" +
            "playing to canvas using card: -1\n" , mockedAppend.toString());
  }

  /**
   * Tests if the controller can play to the canvas with string and negative inputs.
   */
  @Test
  public void testplayCanvasContinues() {
    String str = "canvas -1 -1 -1 -1 asd asdf 0 q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController<>(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" +
            "playing to canvas using card: -1\n" , mockedAppend.toString());
  }

  /**
   * Tests if the controller appends an invalid command.
   */
  @Test
  public void testInvalidCommand() {
    String str = "asdf q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController<>(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n", mockedAppend.toString());
    Assert.assertEquals("Canvas: R\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Invalid command. Try again. \n" +
            "Canvas: R\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n", append.toString());
  }

  /**
   * Tests if the controller correctly quits the game.
   */
  @Test
  public void testquitGame() {
    String str = "q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController<>(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n", mockedAppend.toString());
    Assert.assertEquals("Canvas: R\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n", append.toString());
  }

  /**
   * Tests if the controller appends an invalid command and continues the game.
   */
  @Test
  public void testInvalidCommandContinuesGame() {
    String str = "asdf palette 1 1 q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController<>(reader, append);
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals("starting game\n" +
            "playing to palette: 0 using card: 0\n" +
            "drawing for hand\n", mockedAppend.toString());
    Assert.assertEquals("Canvas: R\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Invalid command. Try again. \n" +
            "Canvas: R\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Canvas: R\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n", append.toString());
  }

  /**
   * Tests if the controller throws an exception upon input closing.
   */
  @Test
  public void testInputClose() {
    String str = "asdf palette 1 1 q";
    reader = new StringReader(str);
    reader.close();
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    Assert.assertThrows(IllegalStateException.class, () ->
            controller.playGame(mock, List.of(), false, 4, 7));
  }

  /**
   * Tests if the controller throws an exception upon output closing.
   */
  @Test
  public void testOutputClose() {
    String str = "asdf palette 1 1 q";
    reader = new StringReader(str);
    mock = new Mocked(mockedAppend);
    controller = new SoloRedTextController(reader, append);
    append = null;
    controller.playGame(mock, List.of(), false, 4, 7);
    Assert.assertEquals(append, null);
  }

  /**
   * Tests if the controller throws an exception upon null reader.
   */
  @Test
  public void testNullReader() {
    Assert.assertThrows(
            IllegalArgumentException.class, () -> new SoloRedTextController(null, append));
  }

  /**
   * Tests if the controller throws an exception upon null appendable.
   */
  @Test
  public void testNullAppend() {
    Assert.assertThrows(
            IllegalArgumentException.class, () -> new SoloRedTextController(reader, null));
  }

  /**
   * Tests if the controller correctly ends a game.
   */
  @Test
  public void testGameEndsWithNoMoves() {
    List<SoloRedCard> smalldeck = new ArrayList<>(Arrays.asList(new SoloRedCard(Canvas.Red, 1),
            new SoloRedCard(Canvas.Red, 2), new SoloRedCard(Canvas.Red, 3)));
    SoloRedTextController controller = new SoloRedTextController(reader, append);
    SoloRedGameModel model = new SoloRedGameModel();
    controller.playGame(model, smalldeck, false, 3, 1);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "P2: R2\n" +
            "> P3: R3\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Game won.\n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "P2: R2\n" +
            "> P3: R3\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n", append.toString());
  }

  /**
   * Tests if the controller correctly ends a game.
   */
  @Test
  public void testGameWinsWithOneMove() {
    List<SoloRedCard> smalldeck = new ArrayList<>(Arrays.asList(new SoloRedCard(Canvas.Red, 1),
            new SoloRedCard(Canvas.Red, 2), new SoloRedCard(Canvas.Red, 3)));
    String str = "palette 1 1";
    reader = new StringReader(str);
    SoloRedTextController controller = new SoloRedTextController(reader, append);
    SoloRedGameModel model = new SoloRedGameModel();
    controller.playGame(model, smalldeck, false, 2, 1);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3\n" +
            "Number of cards in deck: 0\n" +
            "Canvas: R\n" +
            "> P1: R1 R3\n" +
            "P2: R2\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Game won.\n" +
            "Canvas: R\n" +
            "> P1: R1 R3\n" +
            "P2: R2\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n", append.toString());
  }

  /**
   * Tests if the controller correctly ends a game with invalid inputs.
   */
  @Test
  public void testGameWinsWithOneMoveInvalidInputs() {
    List<SoloRedCard> smalldeck = new ArrayList<>(Arrays.asList(new SoloRedCard(Canvas.Red, 1),
            new SoloRedCard(Canvas.Red, 2), new SoloRedCard(Canvas.Red, 3)));
    String str = "asdf palette 1 1";
    reader = new StringReader(str);
    SoloRedTextController controller = new SoloRedTextController(reader, append);
    SoloRedGameModel model = new SoloRedGameModel();
    controller.playGame(model, smalldeck, false, 2, 1);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3\n" +
            "Number of cards in deck: 0\n" +
            "Invalid command. Try again. \n" +
            "Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: R3\n" +
            "Number of cards in deck: 0\n" +
            "Canvas: R\n" +
            "> P1: R1 R3\n" +
            "P2: R2\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Game won.\n" +
            "Canvas: R\n" +
            "> P1: R1 R3\n" +
            "P2: R2\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n", append.toString());
  }

  /**
   * Tests if the controller correctly ends a game.
   */
  @Test
  public void testGameLosesWithOneMove() {
    List<SoloRedCard> smalldeck = new ArrayList<>(Arrays.asList(new SoloRedCard(Canvas.Red, 1),
            new SoloRedCard(Canvas.Red, 2), new SoloRedCard(Canvas.Violet, 1)));
    String str = "palette 1 1";
    reader = new StringReader(str);
    SoloRedTextController controller = new SoloRedTextController(reader, append);
    SoloRedGameModel model = new SoloRedGameModel();
    controller.playGame(model, smalldeck, false, 2, 1);
    Assert.assertEquals("Canvas: R\n" +
            "P1: R1\n" +
            "> P2: R2\n" +
            "Hand: V1\n" +
            "Number of cards in deck: 0\n" +
            "Canvas: R\n" +
            "P1: R1 V1\n" +
            "> P2: R2\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n" +
            "Game lost.\n" +
            "Canvas: R\n" +
            "P1: R1 V1\n" +
            "> P2: R2\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n", append.toString());
  }

  /**
   * Tests is an exception is thrown with a null model.
   */
  @Test
  public void deckNull() {
    String str = "palette 1 1";
    reader = new StringReader(str);
    SoloRedTextController controller = new SoloRedTextController(reader, append);
    SoloRedGameModel model = new SoloRedGameModel();
    List<SoloRedCard> deck = null;
    Assert.assertThrows(
            IllegalArgumentException.class, () -> controller.playGame(
                    model, deck, false, 2, 1));

  }

  /**
   * Tests is an exception is thrown with a null deck.
   */
  @Test
  public void modelNull() {
    String str = "palette 1 1";
    reader = new StringReader(str);
    SoloRedTextController controller = new SoloRedTextController(reader, append);
    SoloRedGameModel model = null;
    List<SoloRedCard> deck = List.of();
    Assert.assertThrows(
            IllegalArgumentException.class, () -> controller.playGame(
                    model, deck, false, 2, 1));

  }

  /**
   * Tests is an exception is thrown with a palette that's too small.
   */
  @Test
  public void tooSmallPalettes() {
    String str = "palette 1 1";
    reader = new StringReader(str);
    SoloRedTextController controller = new SoloRedTextController<>(reader, append);
    SoloRedGameModel model = new SoloRedGameModel();
    List<SoloRedCard> deck = SoloRedGameModel.DECK_EXAMPLE;
    Assert.assertThrows(
            IllegalArgumentException.class, () -> controller.playGame(
                    model, deck, false, 0, 1));

  }
}
