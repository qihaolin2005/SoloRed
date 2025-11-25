package cs3500.solored.model.hw02;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

/**
 * Represents the SoloRed Game.
 * This is the model file for the Model-View-Controller structure.
 * This file contains all the rules of the game and determines how
 * the game should be played. It also throws exceptions to the
 * controller.
 */
public class SoloRedGameModel<C extends Card> implements RedGameModel<SoloRedCard> {
  private Random rand;
  private boolean started;
  private boolean finished;

  // meant to be public for testing
  public static final ArrayList<SoloRedCard> DECK_EXAMPLE
          = new ArrayList<>(Arrays.asList(
          new SoloRedCard(Canvas.Red, 1),
          new SoloRedCard(Canvas.Red, 2),
          new SoloRedCard(Canvas.Red, 3),
          new SoloRedCard(Canvas.Red, 4),
          new SoloRedCard(Canvas.Red, 5),
          new SoloRedCard(Canvas.Red, 6),
          new SoloRedCard(Canvas.Red, 7),
          new SoloRedCard(Canvas.Orange, 1),
          new SoloRedCard(Canvas.Orange, 2),
          new SoloRedCard(Canvas.Orange, 3),
          new SoloRedCard(Canvas.Orange, 4),
          new SoloRedCard(Canvas.Orange, 5),
          new SoloRedCard(Canvas.Orange, 6),
          new SoloRedCard(Canvas.Orange, 7),
          new SoloRedCard(Canvas.Blue, 1),
          new SoloRedCard(Canvas.Blue, 2),
          new SoloRedCard(Canvas.Blue, 3),
          new SoloRedCard(Canvas.Blue, 4),
          new SoloRedCard(Canvas.Blue, 5),
          new SoloRedCard(Canvas.Blue, 6),
          new SoloRedCard(Canvas.Blue, 7),
          new SoloRedCard(Canvas.Indigo, 1),
          new SoloRedCard(Canvas.Indigo, 2),
          new SoloRedCard(Canvas.Indigo, 3),
          new SoloRedCard(Canvas.Indigo, 4),
          new SoloRedCard(Canvas.Indigo, 5),
          new SoloRedCard(Canvas.Indigo, 6),
          new SoloRedCard(Canvas.Indigo, 7),
          new SoloRedCard(Canvas.Violet, 1),
          new SoloRedCard(Canvas.Violet, 2),
          new SoloRedCard(Canvas.Violet, 3),
          new SoloRedCard(Canvas.Violet, 4),
          new SoloRedCard(Canvas.Violet, 5),
          new SoloRedCard(Canvas.Violet, 6),
          new SoloRedCard(Canvas.Violet, 7)));

  protected List<SoloRedCard> deck;
  protected ArrayList<SoloRedCard> hand;
  protected boolean canvasOpen = true;


  //meant to be public for testing purposes
  public static final int MAX_CARD_VALUE = 7;

  private int numPalettes;
  protected int handSize;
  private int winningPalette;
  private int lastWinningPalette;

  private Canvas canvas;
  protected SoloRedCard canvasCard;
  protected List<List<SoloRedCard>> palettes;

  /**
   * Constructor for the model.
   */
  public SoloRedGameModel() {
    rand = new Random();
    started = false;
    finished = false;
    deck = new ArrayList<>(DECK_EXAMPLE);
  }

  /**
   * Constructor for the model with a Random param.
   * @param rand a Random object
   */
  public SoloRedGameModel(Random rand) {
    if (rand == null) {
      throw new IllegalArgumentException("rand cannot be null");
    }
    this.rand = rand;
    started = false;
    finished = false;
    deck = new ArrayList<>(DECK_EXAMPLE);
  }

  /**
   * Checks if the game has started, if not throws an IllegalStateException.
   */
  public void checkStart() {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (finished) {
      throw new IllegalStateException("Game has finished");
    }

  }

  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    checkStart();
    if (paletteIdx < 0 || paletteIdx >= numPalettes) {
      throw new IllegalArgumentException("illegal palette index");
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("illegal card index");
    }
    if (winningPalette == paletteIdx) {
      throw new IllegalStateException("Cannot play to winning palette");
    }
    SoloRedCard currCard = hand.remove(cardIdxInHand);
    palettes.get(paletteIdx).add(currCard);
    lastWinningPalette = winningPalette;
    checkWinningPaletteIndex();
    if (isGameOver()) {
      isGameWon();
    }

  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    checkStart();
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("illegal card index");
    }
    if (hand.size() == 1) {
      throw new IllegalStateException("Cannot play to canvas with only 1 card");
    }
    if (canvasOpen) {
      SoloRedCard currCard = hand.remove(cardIdxInHand);
      canvas = currCard.getCanvas();
      canvasCard = currCard;
      canvasOpen = false;
      lastWinningPalette = -1;
      checkWinningPaletteIndex();
    }
    else {
      throw new IllegalStateException("Canvas is closed");
    }
  }

  @Override
  public void drawForHand() {
    checkStart();
    canvasOpen = true;
    while (!deck.isEmpty() && hand.size() < handSize) {
      hand.add(deck.remove(0));
    }
  }

  @Override
  public void startGame(List<SoloRedCard> deck, boolean shuffle, int numPalettes, int handSize) {
    if (deck == null || deck.contains(null)) {
      throw new IllegalArgumentException("deck cannot be null");
    }
    if (deck.isEmpty()) {
      throw new IllegalArgumentException("deck should not be empty");
    }
    if (numPalettes <= 1) {
      throw new IllegalArgumentException("numPalettes should be at least 2");
    }
    if (handSize <= 0) {
      throw new IllegalArgumentException("handSize should be at least 1");
    }
    HashSet<SoloRedCard> deckSet = new HashSet<>(deck);
    if (deckSet.size() < deck.size()) {
      throw new IllegalArgumentException("deck has duplicates");
    }
    if (deck.size() < numPalettes) {
      throw new IllegalArgumentException("deck is smaller than numPalettes");
    }
    if (deckSet.size() == 2 && numPalettes == 2 && handSize >= 1) {
      throw new IllegalArgumentException("Something wrong");
    }
    if (handSize > deck.size()) {
      throw new IllegalArgumentException("handsize cannot be greater than decksize");
    }
    if (started) {
      throw new IllegalStateException("Game has already started");
    }
    else {
      started = true;
    }
    if (finished) {
      throw new IllegalStateException("Game has already finished");
    }

    this.deck = new ArrayList<SoloRedCard>(deck);
    this.numPalettes = numPalettes;
    this.handSize = handSize;
    this.canvas = Canvas.Red;
    this.palettes = new ArrayList<>(new ArrayList<>());
    for (int i = 0; i < numPalettes; i++) {
      this.palettes.add(new ArrayList<>());
    }
    this.winningPalette = -1;
    this.lastWinningPalette = -2;
    this.hand = new ArrayList<>();
    this.canvasOpen = true;
    if (shuffle) {
      shuffle();
    }
    for (int i = 0; i < numPalettes; i++) {
      palettes.get(i).add(this.deck.remove(0));
    }
    drawForHand();
    checkWinningPaletteIndex();
    if (isGameOver()) {
      isGameWon();
    }
  }

  @Override
  public int numOfCardsInDeck() {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    return deck.size();
  }

  @Override
  public int numPalettes() {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    return numPalettes;
  }

  /**
   * Checks what the winning palette index is.
   * @return the winning palette index.
   */
  public int winningPaletteIndex() {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    return winningPalette;
  }

  private void checkWinningPaletteIndex() {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    List<List<SoloRedCard>> palettesParam = new ArrayList<>();
    for (int i = 0; i < palettes.size(); i++) {
      palettesParam.add(new ArrayList<>());
      for (int j = 0; j < palettes.get(i).size(); j++) {
        palettesParam.get(i).add(palettes.get(i).get(j));
      }
    }

    if (canvas.equals(Canvas.Red)) {
      redRule(palettesParam);
    }
    else if (canvas.equals(Canvas.Orange)) {
      orangeRule(palettesParam);
    }
    else if (canvas.equals(Canvas.Blue)) {
      blueRule(palettesParam);
    }
    else if (canvas.equals(Canvas.Indigo)) {
      indigoRule(palettesParam);
    }
    else if (canvas.equals(Canvas.Violet)) {
      violetRule(palettesParam);
    }
    else {
      throw new IllegalArgumentException("Canvas color is wrong");
    }

  }

  @Override
  public boolean isGameOver() {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (winningPalette == lastWinningPalette) {
      finished = true;
      return true;
    }
    if (hand.isEmpty() && deck.isEmpty()) {
      finished = true;
      return true;
    }
    return false;
  }

  @Override
  public boolean isGameWon() {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (!finished) {
      throw new IllegalStateException("Game has not finished");
    }
    if (winningPalette == lastWinningPalette) {
      //System.out.println("Game has lost");
      return false;
    }
    return hand.isEmpty() && deck.isEmpty();
  }

  @Override
  public List<SoloRedCard> getHand() {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    return new ArrayList<>(hand);
  }

  @Override
  public List<SoloRedCard> getPalette(int paletteNum) {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    if (paletteNum < 0 || paletteNum >= palettes.size()) {
      throw new IllegalArgumentException("invalid palette index");
    }
    return new ArrayList<>(palettes.get(paletteNum));
  }

  @Override
  public SoloRedCard getCanvas() {
    if (!started) {
      throw new IllegalStateException("Game has not started");
    }
    return new SoloRedCard(canvas, -1);
  }

  @Override
  public List<SoloRedCard> getAllCards() {
    return new ArrayList<>(deck);
  }

  private void shuffle() {
    Collections.shuffle(deck, rand);
  }

  /**
   * Determines the redRule.
   * @param palettesParam cope of palettes
   */
  private void redRule(List<List<SoloRedCard>> palettesParam) {
    SoloRedCard maxCard = null;
    for (int i = 0; i < palettesParam.size(); i++) {
      for (int j = 0; j < palettesParam.get(i).size(); j++) {
        SoloRedCard card = palettesParam.get(i).get(j);
        if (card != null) {
          if (maxCard == null || card.getValue() > maxCard.getValue()) {
            maxCard = card;
            winningPalette = i;
          }
          else if (card.getValue() == maxCard.getValue()) {
            if (redRuleHelper(maxCard, card)) {
              maxCard = card;
              winningPalette = i;
            }
          }
        }
      }
    }
  }

  /**
   * Helper for redrule.
   * @param maxCard current max in red rule
   * @param card card to check with max
   * @return true is card is max, false if maxCard remains max.
   */
  private boolean redRuleHelper(SoloRedCard maxCard, SoloRedCard card) {
    Canvas maxCardCanvas = maxCard.getCanvas();
    Canvas cardCanvas = card.getCanvas();
    List<Canvas> order = List.of(
            Canvas.Red,Canvas.Orange, Canvas.Blue, Canvas.Indigo, Canvas.Violet);
    int maxIndex = order.indexOf(maxCardCanvas);
    int index = order.indexOf(cardCanvas);
    return index < maxIndex;
  }

  /**
   * Helper for rules, determines the max and deletes palettes that aren't max.
   * @param max local max
   * @param trueMax max of rule
   * @param i paletteindex
   * @param palettesParam copy of palettes
   * @return a new palette with non-max deleted
   */
  private List<List<SoloRedCard>> ruleHelper(
          int max, int trueMax, int i, List<List<SoloRedCard>> palettesParam) {
    if (max > trueMax) {
      for (int k = 0; k < i; k++) {
        palettesParam.set(k, new ArrayList<>());
      }
    }
    else if (max < trueMax) {
      palettesParam.set(i, new ArrayList<>());
    }
    return palettesParam;
  }

  /**
   * Represents the orange rule.
   * @param palettesParam copy of palettes
   */
  private void orangeRule(List<List<SoloRedCard>> palettesParam) {
    int trueMax = 0;
    for (int i = 0; i < palettesParam.size(); i++) {
      int[] count = new int[MAX_CARD_VALUE];
      for (int j = 0; j < palettesParam.get(i).size(); j++) {
        SoloRedCard card = palettesParam.get(i).get(j);
        count[card.getValue() - 1]++;
      }
      int max = 0;
      int maxValue = -1;
      for (int j = 0; j < count.length; j++) {
        if (count[j] >= max) {
          max = count[j];
          maxValue = j + 1;
        }
      }
      for (int j = 0; j < palettesParam.get(i).size() && maxValue != -1; j++) {
        // System.out.println(j);
        // System.out.println(maxValue);
        if (maxValue != palettesParam.get(i).get(j).getValue()) {
          palettesParam.get(i).set(j, null);
        }
      }

      palettesParam = ruleHelper(max, trueMax, i, palettesParam);
      if (trueMax < max) {
        trueMax = max;
      }

    }
    redRule(palettesParam);
  }

  /**
   * Represents the blue rule.
   * @param palettesParam copy of palettes
   */
  private void blueRule(List<List<SoloRedCard>> palettesParam) {
    int trueMax = 0;
    for (int i = 0; i < palettesParam.size(); i++) {
      HashSet<Canvas> canvasSet = new HashSet<>();
      for (int j = 0; j < palettesParam.get(i).size(); j++) {
        SoloRedCard card = palettesParam.get(i).get(j);
        canvasSet.add(card.getCanvas());
      }
      int max = canvasSet.size();
      palettesParam = ruleHelper(max, trueMax, i, palettesParam);
      if (trueMax < max) {
        trueMax = max;
      }
    }
    redRule(palettesParam);
  }

  /**
   * Represents the indigo rule.
   * @param palettesParam copy of palettes
   */
  private void indigoRule(List<List<SoloRedCard>> palettesParam) {
    int maxStraight = 0;
    for (int i = 0; i < palettesParam.size(); i++) {
      List<SoloRedCard> sortedCards = new ArrayList<>(palettesParam.get(i));
      sortedCards = SoloRedCard.sortValue(sortedCards);

      int highestStraightValue = -1;
      int localHighestStraightValue = -1;
      int prevValue = -1;
      int localMaxStraight = 0;
      int currentStraight = 0;
      for (int j = 0; j < sortedCards.size(); j++) {
        if (prevValue == -1) {
          prevValue = sortedCards.get(j).getValue();
          highestStraightValue = sortedCards.get(j).getValue();
        }
        else {
          if (prevValue == sortedCards.get(j).getValue() - 1) {
            prevValue = sortedCards.get(j).getValue();
            currentStraight++;
            highestStraightValue++;
          }
          else if (prevValue != sortedCards.get(j).getValue()) {
            currentStraight = 0;
            highestStraightValue = -1;
            prevValue = -1;
            j--;
          }
        }
        if (currentStraight >= localMaxStraight) {
          localMaxStraight = currentStraight;
          if (highestStraightValue > localHighestStraightValue) {
            localHighestStraightValue = highestStraightValue;
          }
        }
      }

      for (int j = 0; j < palettesParam.get(i).size(); j++) {
        if (palettesParam.get(i).get(j).getValue() != localHighestStraightValue) {
          palettesParam.get(i).set(j, null);
        }
      }

      palettesParam = ruleHelper(localMaxStraight, maxStraight, i, palettesParam);
      if (maxStraight < localMaxStraight) {
        maxStraight = localMaxStraight;
      }
    }
    redRule(palettesParam);
  }

  /**
   * Represents the violet rule.
   * @param palettesParam copy of palettes
   */
  private void violetRule(List<List<SoloRedCard>> palettesParam) {
    int trueMax = 0;
    int below = 4;
    for (int i = 0; i < palettesParam.size(); i++) {
      int max = 0;
      for (int j = 0; j < palettesParam.get(i).size(); j++) {
        SoloRedCard card = palettesParam.get(i).get(j);
        if (card.getValue() < below) {
          max++;
        }
        else {
          palettesParam.get(i).set(j, null);
        }
      }
      palettesParam = ruleHelper(max, trueMax, i, palettesParam);
      if (trueMax < max) {
        trueMax = max;
      }
    }
    redRule(palettesParam);
  }


}
