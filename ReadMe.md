# SoloRed Game 🎴

SoloRed is a **single-player card game** in Java with dynamic canvas rules and multiple palettes. Each game is strategic and unpredictable depending on the active canvas.

---

## Table of Contents
- [Game Rules](#game-rules)
- [Canvas Rules Table](#canvas-rules-table)
- [Game Variants](#game-variants)
- [Quick Start](#quick-start)
- [Commands](#commands)

---

## Game Rules

- You have a **hand of cards**, a **canvas**, and multiple **palettes**.
- Each turn, you play a card to **either a palette or the canvas**.
- The **winning palette** depends on the **canvas rule**.
- You **cannot play to the canvas if you have only one card**.
- Game ends when **deck and hand are empty**, or **the same palette wins twice in a row**.

---

## Canvas Rules Table

| Canvas Color | Rule Description                                                                 | Tie-breaker            |
|--------------|---------------------------------------------------------------------------------|-----------------------|
| **Red**      | Highest card value wins                                                         | Color order: Red > Orange > Blue > Indigo > Violet |
| **Orange**   | Most duplicates of a single value wins                                         | Red Rule              |
| **Blue**     | Most different colors in the palette wins                                      | Red Rule              |
| **Indigo**   | Longest consecutive sequence (straight) of values wins                         | Red Rule              |
| **Violet**   | Most cards with values **below 4**                                             | Red Rule              |

---

## Game Variants

### Basic Game
- Standard deck.
- Draws cards to maintain **maximum hand size**.

### Advanced Game
- **Draws only one card** normally.
- If you play a card **higher than the size of your winning palette**, you may draw **two cards** instead.
- Same canvas rules as the basic game.

---

## Quick Start

### Open in IntelliJ IDEA
- Set **Project SDK** → Java 17+
- Run `cs3500.solored.SoloRed.main()`
- Enter commands in the console.

---

## Commands

Once the game is running, you interact with it via text commands. The following commands are available:

### Play to a Palette
Play a card from your hand to a specific palette.

palette <palette_number> <card_index_in_hand>

- `<palette_number>`: The index of the palette you want to play to (starting from 1).
- `<card_index_in_hand>`: The index of the card in your hand (starting from 1).

**Example:**
palette 2 3

Plays the 3rd card in your hand to the 2nd palette.

---

### Play to the Canvas
Play a card from your hand to the canvas. This changes the active rule.

canvas <card_index_in_hand>


- `<card_index_in_hand>`: The index of the card in your hand (starting from 1).

**Important:** You **cannot** play to the canvas if you only have one card in your hand.

**Example:**
canvas 4

yaml
Copy code
Plays the 4th card in your hand to the canvas.

---

### Quit the Game
q

Exits the current game immediately.

---

### Notes
- Rules are automatically updated whenever you play a card to the canvas.
- The current canvas rule determines which palette is winning.
- Hand and palette indices start from 1 for simplicity when typing commands.
