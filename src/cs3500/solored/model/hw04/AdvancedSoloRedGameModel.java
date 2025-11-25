package cs3500.solored.model.hw04;

import java.util.List;

import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;

/**
 * This is an Advanced gamed with harder rules. Mainly the draw function that only draws one card
 * unless specific requirements are met.
 */
public class AdvancedSoloRedGameModel extends SoloRedGameModel<SoloRedCard> {

  private int additionalDraw = 0;

  /**
   * This is a modified playToCanvas method that determines the number of additional
   * cards to draw.
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   */
  @Override
  public void playToCanvas(int cardIdxInHand) {
    super.playToCanvas(cardIdxInHand); // no need to checkStart as playToCanvas does it
    int value = canvasCard.getValue();
    //System.out.println(value);
    if (value > palettes.get(winningPaletteIndex()).size() && additionalDraw < 1) {
      additionalDraw++;
    }
  }

  /**
   * This is a modified drawForHand method that only draws one card unless additionalDraws
   * is greater than one, then it draws 2 cards.
   */
  @Override
  public void drawForHand() {
    checkStart();
    canvasOpen = true;
    while (!deck.isEmpty() && hand.size() < handSize && additionalDraw >= 0) {
      hand.add(deck.remove(0));
      //System.out.println(additionalDraw);
      //System.out.println(hand.size());
      additionalDraw--;
    }
    additionalDraw = 0;
  }

  /**
   * This is a pseudo-modified startGame method, it essentially does the same thing but ensures that
   * AdvancedSoloRedGameModel.drawForHand() is not called when it is super'ed.
   * @param deck the cards used to set up and play the game
   * @param shuffle whether the deck should be shuffled prior to setting up the game
   * @param numPalettes number of palettes in the game
   * @param handSize the maximum number of cards allowed in the hand
   */
  @Override
  public void startGame(List<SoloRedCard> deck, boolean shuffle, int numPalettes, int handSize) {
    super.startGame(deck, shuffle, numPalettes, handSize);
    super.drawForHand();
  }
}
