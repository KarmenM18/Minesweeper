public class LinkedGrid<T> {

	private int width;
	private int height;
	private LinearNode<T>[] grid; // array of LinearNodes, represents the "heads" of each linked list

	public LinkedGrid(int w, int h) {
		this.width = w;
		this.height = h;
		this.grid = new LinearNode[width];

		// initializing the grid array
		for (int i = 0; i < width; i++) {
			grid[i] = new LinearNode<T>();

			LinearNode<T> curr = grid[i];

			// connects LinearNode objects into singly linked list
			for (int j = 0; j < (height); j++) {
				curr.setNext(new LinearNode<T>());
				curr = curr.getNext();

			}
		}

	}

	/*
	 * PURPOSE: sets the element of a node at a given column-row pairing,
	 * PARAMETERS: column index, row index, generic data value
	 */

	public void setElement(int col, int row, T data) {

		if (col > (width - 1) || col < 0 || row > (height - 1) || row < 0) {
			throw new LinkedListException("Error occured here");
		}

		else {

			// sets the elements of nodes the first row
			if (row == 0) {
				grid[col].setElement(data);

			}

			// sets the elements of the nodes in every row except the first
			else {
				LinearNode<T> curr = grid[col];

				for (int i = 0; i < (row); i++) {
					curr = curr.getNext();

				}

				curr.setElement(data);

			}

		}

	}

	/*
	 * PURPOSE: finds the cell at the given column-row pairing, PARAMETERS: column
	 * index, row index, RETURNS: data value at specified node
	 */

	public T getElement(int col, int row) {

		T element = null;

		if (col > (width - 1) || col < 0 || row > (height - 1) || row < 0) {
			throw new LinkedListException("Error occured here");
		}

		else {

			// gets the element if the node is in the first row
			if (row == 0) {
				element = grid[col].getElement();
			}

			// gets the element if the node is in any other row except the first
			else {
				LinearNode<T> curr = grid[col];

				for (int i = 0; i < row; i++) {
					curr = curr.getNext();
				}
				element = curr.getElement();
			}

		}

		return element;
	}

	/*
	 * PURPOSE: gets the width of the grid
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * PURPOSE: gets the height of the grid
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * PURPOSE: builds a string that displays the entire grid of elements, RETURNS:
	 * completed string
	 */
	public String toString() {
		String str = "";

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				// if the row is zero, it simply prints out the elements of the grid array
				if (i == 0) {
					str += grid[j].getElement() + "  ";

				}

				else {
					LinearNode<T> curr = grid[j];

					// starting from row 1, every node in a row will be printed from left to right,
					// until target row (last row in grid) is reached and printed
					for (int k = 0; k < i; k++) {
						curr = curr.getNext();

					}

					str += curr.getElement() + "  ";

				}

			}

			str += "\n";
		}

		return str;
	}

}
