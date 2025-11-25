package cs3500.solored.controller;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.view.hw02.SoloRedGameTextView;

/**
 * This is the controller for the SoloRedGame, it controls the inputs.
 * This also takes in a Readable and reads from it as the inputs.
 * Then it appends to an Appendable as the output.
 */
public class SoloRedTextController<C extends Card> implements RedGameController<Card> {

  private Appendable ap;
  private Scanner scan;
  private SoloRedGameTextView<C> textView;
  private boolean quit;
  private RedGameModel<Card> model;

  /**
   * This is the constructor for the SoloRedTextController.
   * @param rd a readable to read from
   * @param ap an appendable to write to
   * @throws IllegalArgumentException if rd or ap are null
   */
  public SoloRedTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null) {
      throw new IllegalArgumentException("Readable is null;");
    }
    if (ap == null) {
      throw new IllegalArgumentException("Appendable is null;");
    }

    this.ap = ap;
    scan = new Scanner(rd);
    this.quit = false;
  }

  @Override
  public void playGame(
          RedGameModel model, List deck, boolean shuffle, int numPalettes, int handSize) {
    if (model == null) {
      throw new IllegalArgumentException("RedGameModel is null;");
    }
    if (deck == null) {
      throw new IllegalArgumentException("deck is null;");
    }
    try {
      this.model = model;
      try {
        model.startGame(deck, shuffle, numPalettes, handSize);
      }
      catch (IllegalStateException e) {
        throw new IllegalArgumentException("Game has already been played.");
      }
      textView = new SoloRedGameTextView(model, ap);
      renderText();
      checkGameOver();
      while (!quit && !model.isGameOver() && scan.hasNext()) {
        boolean retry = false; // checks if it needs to be re-run
        String str = scan.next();
        //this might glitch out if str is say: "palette canvas 1 1" or something stupid like that
        if (str.equals("palette")) {
          retry = playPalette();
          checkGameOver();
        }
        else if (str.equals("canvas")) {
          retry = playCanvas();
        }
        else if (str.equals("Q") || str.equals("q")) {
          quitGame();
          break;
        }
        else {
          ap.append("Invalid command. Try again. " + "\n");
          renderText();
        }
      }
      if (!quit && !model.isGameOver()) {
        throw new IllegalStateException("No more inputs");
      }
    }
    catch (IOException e) {
      // does not appear in appendable
      // maybe something wrong with testing
      throw new IllegalStateException("Qihao's test: output failed");
    }
  }

  //helpers for playGame, does game operations, returns a boolean
  private boolean playPalette() throws IOException {
    int intOne = invalidIndexChecker();
    int intTwo = invalidIndexChecker();

    if (intOne == Integer.MIN_VALUE || intTwo == Integer.MIN_VALUE) {
      return false;
    }

    try {
      model.playToPalette(intOne, intTwo);
      if (!model.isGameOver()) {
        model.drawForHand();
      }
      renderText();
      return false;
    }
    catch (IllegalArgumentException | IllegalStateException e) {
      ap.append("Invalid move. Try again. " + e.getMessage() + "\n");
      renderText();
    }
    return true;
  }

  private int invalidIndexChecker() throws IOException {
    if (scan.hasNext()) {
      if (scan.hasNextInt()) {
        int intZero = scan.nextInt();
        int intOne = intZero - 1;
        if (intZero < 0) {
          return invalidIndexChecker();
        }
        else {
          return intOne;
        }
      }
      else {
        String str = scan.next();
        if (str.equals("q") || str.equals("Q")) {
          quitGame();
        }
        else {
          return invalidIndexChecker();
        }
      }
    }
    else {
      throw new IllegalStateException("No more inputs");
    }
    return Integer.MIN_VALUE;
  }

  private boolean playCanvas() throws IOException {
    int intOne = invalidIndexChecker();
    if (intOne == Integer.MIN_VALUE) {
      return false;
    }
    try {
      model.playToCanvas(intOne);
      renderText();
      return false;
    }
    catch (IllegalArgumentException e) {
      ap.append("Invalid move. Try again. " + e.getMessage() + "\n");
      renderText();
    }
    catch (IllegalStateException e) {
      ap.append("Invalid move. Try again. " + e.getMessage() + "\n");
      renderText();

    }
    return true;
  }

  private void renderText() throws IOException {
    textView.render();
    ap.append("\n");
    ap.append("Number of cards in deck: " + model.numOfCardsInDeck() + "\n");
  }

  private void quitGame() throws IOException {
    ap.append("Game quit!" + "\n");
    ap.append("State of game when quit:" + "\n");
    renderText();
    quit = true;
    scan.close();
  }

  private void checkGameOver() throws IOException {
    if (model.isGameOver()) {
      if (model.isGameWon()) {
        ap.append("Game won." + "\n");
      }
      else {
        ap.append("Game lost." + "\n");
      }
      renderText();
      scan.close();
    }
  }

}