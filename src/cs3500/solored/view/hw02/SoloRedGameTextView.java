package cs3500.solored.view.hw02;

import java.io.IOException;
import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * Represents a way to view the model.
 */
public class SoloRedGameTextView<C extends Card> implements RedGameView {
  private RedGameModel<C> model;
  private Appendable appendable;

  /**
   * Constructor for the textview.
   * @param model the model to be viewed
   */
  public SoloRedGameTextView(RedGameModel<C> model) {
    this.model = model;
    //model.startGame();
  }

  /**
   * Constructor for the textView with appendable.
   * @param model the model to be viewed
   * @param appendable the appendable to write to
   * @throws IOException the exception to throw if appendable is closed
   */
  public SoloRedGameTextView(RedGameModel<C> model, Appendable appendable) throws IOException {
    if (appendable == null) {
      throw new IllegalArgumentException("View Appendable may not be null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model may not be null");
    }
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public String toString() {
    String canvas = "Canvas: " + model.getCanvas().toString().charAt(0) + "\n";
    String palletes = "";
    String hand = "Hand: ";
    int winningPallete = model.winningPaletteIndex();
    for (int i = 0; i < model.numPalettes(); i++) {
      List<C> paletteList = model.getPalette(i);
      if (i == winningPallete) {
        palletes += "> ";
      }
      palletes += "P" + (i + 1) + ": ";
      for (int j = 0; j < paletteList.size(); j++) {
        palletes += paletteList.get(j) + " ";
      }
      if (!paletteList.isEmpty()) {
        palletes = palletes.trim() + "\n";
      }
      else {
        palletes = palletes + "\n";
      }
    }
    for (int i = 0; i < model.getHand().size(); i++) {
      hand += model.getHand().get(i) + " ";
    }
    if (!model.getHand().isEmpty()) {
      hand = hand.trim();
    }
    return canvas + palletes + hand;
  }

  @Override
  public void render() throws IOException {
    appendable.append(toString());
  }
}