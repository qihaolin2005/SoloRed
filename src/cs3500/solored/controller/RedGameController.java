package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * Represents the interface for a controller, controls how the Red Card Game runs.
 * @param <C> some class that extends card
 */
public interface RedGameController<C extends Card> {

  /**
   * This method should play a new game of Solo Red using the provided model,
   * using the startGame method on the model.
   * @param model the model to be used
   * @param deck the deck to be used, list of cards
   * @param shuffle whether to shuffle the deck
   * @param numPalettes the number of palettes
   * @param handSize the size of the hand
   * @throws IllegalArgumentException if the provided model is null.
   * @throws IllegalStateException if the controller is unable to receive input or transmit output
   * @throws IllegalArgumentException if the game cannot be started
   */
  <C extends Card> void playGame(
          RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes, int handSize);


}
