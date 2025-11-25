package cs3500.solored.model.hw02;

/**
 * Represents a canvas with 5 colors.
 */
public enum Canvas {
  Red,
  Orange,
  Blue,
  Indigo,
  Violet;

  @Override
  public String toString() {
    if (this == Red) {
      return "R";
    }
    if (this == Orange) {
      return "O";
    }
    if (this == Blue) {
      return "B";
    }
    if (this == Indigo) {
      return "I";
    }
    if (this == Violet) {
      return "V";
    }
    return "Something went wrong";
  }
}
