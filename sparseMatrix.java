import java.util.LinkedList;
import java.util.Iterator;

public class sparseMatrix implements SparseInterface {

  private int size;
  private LinkedList<element> list;

  /* this constructor initializes the list of elements. the size of the
  sparseMatrix will be set using the setSize() method. */
  public sparseMatrix() {

    this.list = new LinkedList<element>();
  }


  public sparseMatrix(int size, LinkedList<element> list) {

    this.size = size;
    this.list = list;
  }

  /* Should clear the matrix of all entries (make all entries 0) */
  @Override
  public void clear() {

    this.list = new LinkedList<element>();
  }

  /*
      Sets maximum size of the matrix, should also clear the matrix (make all elements 0)
   */
  @Override
  public void setSize(int size) {

    clear();
    this.size = size;
  }


  /*
      Adds an element to the row and column passed as arguments (overwrites if element is already present at that position).
      Throws an error if row/column combination is out of bounds.
   */
  @Override
  public void addElement(int row, int col, int data) {

    //check to see if the row and column are valid arguments
    try {

      if (!(row < size && col < size) || (row < 0) || (col < 0)) throw new Exception("Not a valid row/column combination.");
    }
    catch (Exception e) {

      //if the either the row or the column weren't valid arguments, print the message and return with no element added
      System.out.println(e);
      return;
    }

    //remove the element, if it exists, from the list
    removeElement(row, col);

    //add the element to the list
    list.add(new element(row, col, data));
  }


  /*
      Remove (make 0) the element at the specified row and column.
      Throws an error if row/column combination is out of bounds.
   */
  @Override
  public void removeElement(int row, int col) {

    //check to see if the row and column are valid arguments
    try {

      if (!(row < size && col < size) || (row < 0) || (col < 0)) throw new Exception("Not a valid row/column combination.");
    }
    catch (Exception e) {

      //if the either the row or the column weren't valid arguments, print the message and return with no element added
      System.out.println(e);
      return;
    }

    //two elements are identical if they are in the same position. 0 is a sentinel value
    list.remove(new element(row, col, 0));
  }


  /*
      Return the element at the specified row and column
      Throws an error if row/column combination is out of bounds.
   */
  @Override
  public int getElement(int row, int col) {

    //check to see if the row and column are valid arguments
    try {

      if (!(row < size && col < size) || (row < 0) || (col < 0)) throw new Exception("Not a valid row/column combination.");
    }
    catch (Exception e) {

      //if the either the row or the column weren't valid arguments, print the message and return with no element added
      System.out.println(e);
    }

    int pos = list.indexOf(new element(row, col, 0));

    if (pos == -1) return 0;

    return ((element)list.get(pos)).getData();
  }

  /*
      Returns the determinant of the matrix calculated recursively (Use the formula provided in the project description).
   */
  @Override
  public int determinant() {

    // Iterator listIter = list.iterator();
    //
    // while (listIter.hasNext()) {
    //
    //   element elem = listIter.next();
    //
    //   if (elem.getRow() == row && elem.getCol() == col)
    // }

    if (size == 2) {

      int a = getElement(0,0);
      int b = getElement(0,1);
      int c = getElement(1,0);
      int d = getElement(1,1);

      return a*d-b*c;
      // return getElement(0,0)*getElement(1,1) - getElement(0,1)*getElement(1,0);
    }

    return 0;
  }

  /* Returns a new matrix which is the minor of the original
      (See project description for minor definition). */
  @Override
  public SparseInterface minor(int row, int col) {

    //check to see if the row and column are valid arguments
    try {

      if (!(row < size && col < size) || (row < 0) || (col < 0)) throw new Exception("Not a valid row/column combination.");
    }
    catch (Exception e) {

      //if the either the row or the column weren't valid arguments, print the message and return with no element added
      System.out.println(e);
    }

    int newSize = size - 1;
    LinkedList<element> newList = new LinkedList<element>();

    Iterator listIter = list.iterator();

    while (listIter.hasNext()) {

      element elem = (element)listIter.next();

      if (elem.getRow() != row && elem.getCol() != col) newList.add(elem);
    }

    return new sparseMatrix(newSize, newList);
  }


  /*
  Should return the nonzero elements of your sparse matrix as a string.
  The String should be k lines, where k is the number of nonzero elements.
  Each line should be in the format "row column data" where row and column are the "coordinate" of the data and all are separated by spaces.
  An empty matrix should return an empty string.
  The print should be from left to right and from top to bottom (like reading a book) i.e. the matrix

                                                   3 0 1
                                                   0 2 0
                                                   0 0 4

                                               Should print as:
                                                   0 0 3
                                                   0 2 1
                                                   1 1 2
                                                   2 2 4

   */
  @Override
  public String toString() {

    String temp = "";
    Iterator iter = list.iterator();
    element elem;

    while (iter.hasNext()) {

      elem = (element)iter.next();
      temp += elem.getRow() + " " + elem.getCol() + " " + elem.getData();
      if (iter.hasNext()) temp += "\n";
    }

    return temp;
  }


  /*
  Should return the size of the matrix.
   */
  @Override
  public int getSize() {

    return this.size;
  }
}
