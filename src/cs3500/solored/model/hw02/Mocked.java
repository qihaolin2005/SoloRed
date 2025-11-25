package cs3500.solored.model.hw02;

import java.util.List;

/**
 * This is a mock for testing purposes.
 */
public class Mocked implements RedGameModel<Card> {
  StringBuilder stringBuilder;

  /**
   * This is the constructor for the Mocked class.
   * @param stringBuilder an appendable to write to.
   */
  public Mocked(StringBuilder stringBuilder) {
    this.stringBuilder = stringBuilder;
  }

  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    stringBuilder.append("playing to palette: " + paletteIdx +
            " using card: " + cardIdxInHand + "\n");
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    stringBuilder.append("playing to canvas using card: " + cardIdxInHand + "\n");
  }

  @Override
  public void drawForHand() {
    stringBuilder.append("drawing for hand\n");
  }

  @Override
  public void startGame(List deck, boolean shuffle, int numPalettes, int handSize) {
    stringBuilder.append("starting game\n");
  }

  @Override
  public int numOfCardsInDeck() {
    return 0;
  }

  @Override
  public int numPalettes() {
    return 0;
  }

  @Override
  public int winningPaletteIndex() {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean isGameWon() {
    return false;
  }

  @Override
  public List getHand() {
    return List.of();
  }

  @Override
  public List getPalette(int paletteNum) {
    return List.of();
  }

  @Override
  public Card getCanvas() {
    return new SoloRedCard(Canvas.Red, 1);
  }

  @Override
  public List getAllCards() {
    return List.of();
  }
}
