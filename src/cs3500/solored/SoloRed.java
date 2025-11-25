package cs3500.solored;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.SoloRedCard;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * This is the main method to play the SoloRed Game.
 * All the inputs are handled through String[] args and all the outputs
 * are in System.out.
 */
public final class SoloRed {

  /**
   * This plays the SoloRed Game.
   * @param args this is where the inputs should go.
   */
  public static void main(String[] args) {
    RedGameCreator.GameType gameType = RedGameCreator.GameType.BASIC;
    int paletteSize = 4;
    int handsize = 7;
    if (args.length >= 1) {
      if (args[0].equals("basic")) {
        gameType = RedGameCreator.GameType.BASIC;
      }
      else if (args[0].equals("advanced")) {
        gameType = RedGameCreator.GameType.ADVANCED;
      }
      else {
        throw new IllegalArgumentException("unknown game type");
      }
    }
    if (args.length >= 2) {
      try {
        paletteSize = Integer.parseInt(args[1]);
      }
      catch (NumberFormatException e) {
        throw new IllegalArgumentException("invalid palette size " + args[1]);
      }
    }
    if (args.length >= 3) {
      try {
        handsize = Integer.parseInt(args[2]);
      }
      catch (NumberFormatException e) {
        throw new IllegalArgumentException("invalid palette size " + args[2]);
      }
    }

    String fullGame = "palette 1 1\n" + "palette 2 1\n" + "palette 3 1\n" + "canvas 1\n"
            + "palette 1 4\n" + "canvas 6\n" + "palette 2 1\n" + "palette 3 1\n" + "canvas 4\n"
            + "palette 1 4 \n" + "palette 2 4\n"
            + "palette 3 6\n" + "canvas 6\n" + "palette 4 4\n"
            + "palette 3 2\n" + "palette 4 3\n" + "palette 3 6\n"
            + "palette 4 2\n" + "canvas 1\n" + "q" + "palette 2 2\n" + "palette 3 6\n";
    StringReader sr = new StringReader(fullGame);

    SoloRedTextController<SoloRedCard> controller = new SoloRedTextController<>(
            new InputStreamReader(System.in), System.out);
    List<SoloRedCard> reverseDeck = SoloRedGameModel.DECK_EXAMPLE;
    //Collections.reverse(reverseDeck);
    try {
      controller.playGame(RedGameCreator.createGame(gameType),
              SoloRedGameModel.DECK_EXAMPLE, true, paletteSize, handsize);
    }
    catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}