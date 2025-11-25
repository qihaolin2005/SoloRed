package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * This is a factory class. By using a BASIC GameType, the factory gives a basic game
 * with basic rules. By using an ADVANCED GameType, the factory gives an advanced game
 * with harder rules.
 */
public class RedGameCreator {

  /**
   * This class determines the GameType,
   * BASIC for basic rules and ADVANCED for advanced rules.
   */
  public enum GameType {
    BASIC,
    ADVANCED;
  }

  /**
   * This method creates a game, it can only create a BASIC or ADVANCED game.
   * @param gt the type of game
   * @return the correct game associated with gt
   */
  public static RedGameModel<SoloRedCard> createGame(GameType gt) {
    if (gt == GameType.ADVANCED) {
      return new AdvancedSoloRedGameModel();
    }
    else if (gt == GameType.BASIC) {
      return new SoloRedGameModel<SoloRedCard>();
    }
    else {
      throw new IllegalArgumentException("Invalid game type");
    }
  }
}
