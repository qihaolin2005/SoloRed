package cs3500.solored.model.hw02;

import java.util.Comparator;
import java.util.List;

/**
 * Represents a Card with a Color and Value.
 */
public class SoloRedCard implements Card {

  private int value;
  private Canvas canvas;

  /**
   * Constructor for a SoloRedCard.
   * @param canvas the canvas of the card
   * @param value the card value
   */
  public SoloRedCard(Canvas canvas, int value) {
    this.canvas = canvas;
    this.value = value;
  }

  public Canvas getCanvas() {
    return canvas;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  public int getValue() {
    return value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SoloRedCard that = (SoloRedCard) o;
    return value == that.getValue() && canvas == that.getCanvas();
  }

  /**
   * Sorts a list of SoloRedCards, only sorting by value and disregarding the Canvas.
   * @param listParam the list to be sorted
   * @return a new sorted variant of listParam
   */
  public static List<SoloRedCard> sortValue(List<SoloRedCard> listParam) {
    listParam.sort(new SoloRedCardValueComparator());
    return listParam;
  }

  public String toString() {
    return canvas.toString() + value;
  }

  private static class SoloRedCardValueComparator implements Comparator<SoloRedCard> {

    /**
     * Compares 2 solo red cards, only sorted by value and disregards Canvas.
     * This cannot be tested because it is inside a private class
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return 1 is o1 is greater, 0 equal, and -1 if o2 is greater
     */
    @Override
    public int compare(SoloRedCard o1, SoloRedCard o2) {
      if (o1.getValue() > o2.getValue()) {
        return 1;
      }
      else if (o1.getValue() == o2.getValue()) {
        return 0;
      }
      return -1;
    }
  }


}


