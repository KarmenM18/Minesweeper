import java.awt.Color;

public class Game {

	private LinkedGrid<Character> board;
	private LinkedGrid<GUICell> cells;
	public static int width;
	public static int height;
	private boolean isPlaying;
	private GUI gui;

	// Constructor
	public Game(int width, int height, boolean fixedRandom, int seed) {
		this.board = new LinkedGrid<Character>(width, height);

		// initializes the board grid with underscores in every cell
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board.setElement(i, j, '_');
			}
		}

		this.cells = new LinkedGrid<GUICell>(width, height);

		// initializes the cells grid with GUI elements
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cells.setElement(i, j, new GUICell(i, j));
			}
		}

		this.width = width;
		this.height = height;
		this.isPlaying = true;
		this.gui = new GUI(this, cells);
		fixedRandom = true;

		// randomly places bombs throughout the game board
		BombRandomizer bombRando = new BombRandomizer();
		bombRando.placeBombs(board, fixedRandom, seed);
		determineNumbers();

	}

	// constructor is same as previous, except that board parameter is assigned to
	// corresponding instance variable, and no bomb randomization occurs
	public Game(LinkedGrid<Character> board) {
		this.width = board.getWidth();
		this.height = board.getHeight();
		this.board = board;
		this.cells = new LinkedGrid<GUICell>(width, height);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cells.setElement(i, j, new GUICell(i, j));
			}
		}

		this.isPlaying = true;
		this.gui = new GUI(this, cells);

		determineNumbers();
	}

	/*
	 * PURPOSE: gets the width of the game's grid
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * PURPOSE: gets the height of the game's grid
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * PURPOSE: gets the LinkedGrid cells
	 */
	public LinkedGrid<GUICell> getCells() {
		return cells;
	}

	/*
	 * PURPOSE: calculates how many bombs are in surrounding cells, inserts that
	 * number into corresponding node of the cells grid
	 */

	public void determineNumbers() {

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				GUICell gui = new GUICell(i, j);
				int bombCount = 0;

				// if no bomb exists at this node, checks 8 possible surrounding nodes to see if
				// they have bombs
				if (board.getElement(i, j) != 'x') {

					// checks diagonal-top-left of target
					try {

						if (board.getElement(i - 1, j - 1) == 'x') {
							bombCount++;
						}

					} catch (Exception e) {

					}

					// checks cell above target
					try {
						if (board.getElement(i - 1, j) == 'x') {
							bombCount++;
						}
					} catch (Exception e) {

					}

					// checks diagonal-top-right of target
					try {
						if (board.getElement(i - 1, j + 1) == 'x') {
							bombCount++;
						}
					} catch (Exception e) {

					}

					// checks cell to right of target
					try {

						if (board.getElement(i, j + 1) == 'x') {
							bombCount++;
						}
					} catch (Exception e) {

					}

					// checks diagonal-bottom-right of target
					try {
						if (board.getElement(i + 1, j + 1) == 'x') {
							bombCount++;
						}
					} catch (Exception e) {

					}

					// checks cell below target
					try {
						if (board.getElement(i + 1, j) == 'x') {
							bombCount++;
						}
					} catch (Exception e) {

					}

					// checks diagonal-bottom-left of target
					try {
						if (board.getElement(i + 1, j - 1) == 'x') {
							bombCount++;
						}
					} catch (Exception e) {

					}

					// checks cell to left of target
					try {
						if (board.getElement(i, j - 1) == 'x') {
							bombCount++;
						}
					} catch (Exception e) {

					}

					gui.setNumber(bombCount);
					cells.setElement(i, j, gui);
				}

				// if the bomb is in the target node, inserts -1 at that node
				else {
					gui.setNumber(-1);
					cells.setElement(i, j, gui);

				}

			}
		}

	}

	/*
	 * PURPOSE: simulates a click on a grid cell, RETURNS: integer value representing
	 * how many cells are being revealed
	 */
	public int processClick(int col, int row) {

		int result = 0;

		// returns -10 if a bomb has been previously revealed anywhere in the game
		if (!isPlaying) {
			result = -10;
		}

		// if the current cell contains a bomb - sets the background to red, reveals cell
		else if (cells.getElement(col, row).getNumber() == -1) {
			cells.getElement(col, row).setBackground(Color.red);
			cells.getElement(col, row).reveal();
			result = -1;
			isPlaying = false;
		}

		// performs "region clearing"
		else if (cells.getElement(col, row).getNumber() == 0) {
			result = recClear(col, row);
		}

		else if (cells.getElement(col, row).getNumber() < 9 && cells.getElement(col, row).getNumber() > 0) {

			// executes if cell was previously revealed
			if (cells.getElement(col, row).isRevealed() == true) {
				result = 0;
			}

			// executes if cell was not previously revealed
			else {
				cells.getElement(col, row).reveal();
				cells.getElement(col, row).setBackground(Color.white);
				result = 1;

			}
		}

		return result;

	}

	/*
	 * PURPOSE: helper method that calculates number of surrounding cells that will
	 * be revealed in region clearing PARAMETERS: column and row of current cell
	 * RETURNS: integer value representing how many cells are being revealed
	 */
	private int recClear(int col, int row) {

		int result = 0;

		if (col > (width - 1) || col < 0 || row > (height - 1) || row < 0) {
			result = 0;
		}

		else if (cells.getElement(col, row).isRevealed() == true) {
			result = 0;
		}

		else if (cells.getElement(col, row).getNumber() == -1) {
			result = 0;
		}

		else if (cells.getElement(col, row).getNumber() > 0) {
			cells.getElement(col, row).reveal();
			if (gui != null) {
				cells.getElement(col, row).setBackground(Color.white);
			}
			result = 1;

		}

		else {
			cells.getElement(col, row).reveal();
			if (gui != null) {
				cells.getElement(col, row).setBackground(Color.white);
			}
			result = 1;

			// makes 8 recursive calls to check the 8 cells surrounding the current cell
			result += recClear(col - 1, row - 1);
			result += recClear(col - 1, row);
			result += recClear(col - 1, row + 1);
			result += recClear(col, row + 1);
			result += recClear(col + 1, row + 1);
			result += recClear(col + 1, row);
			result += recClear(col + 1, row - 1);
			result += recClear(col, row - 1);

		}
		return result;
	}
}
