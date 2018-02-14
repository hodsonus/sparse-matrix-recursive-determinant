import java.util.LinkedList;
import java.util.Iterator;

public class SparseMatrix implements SparseInterface {

  //private member values
  private int size;
  private LinkedList<element> list;


  /* this constructor initializes the list of elements. the size of the
  SparseMatrix will be set using the setSize() method. */
  public SparseMatrix() {

    this.list = new LinkedList<element>();
    size = 5;
  }


  /* this is a constructor used in the minor method to create a new SparseMatrix, initializes the size and the list */
  public SparseMatrix(int size, LinkedList<element> list) {

    this.size = size;
    this.list = list;
  }


  /* Should clear the matrix of all entries (make all entries 0) */
  @Override
  public void clear() {

    //just point list to a new blank linked list
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

      if (!(row < size && col < size) || (row < 0) || (col < 0)) throw new OutOfBoundsException("Not a valid row/column combination.");
    }
    catch (OutOfBoundsException e) {

      //if the either the row or the column weren't valid arguments, print the message and return with no element added
      System.out.println(e.toString());
      return;
    }

    //remove the element, if it exists, from the list
    removeElement(row, col);

    //find where the element should be placed
    int i = 0;

    for (element elem : list) {

      if (elem.getRow() < row) i++;

      else if (elem.getRow() == row) {

        if (elem.getCol() < col) i++;
      }
    }

    //place the element in sorted order
    list.add(i, new element(row, col, data));
  }


  /*
      Remove (make 0) the element at the specified row and column.
      Throws an error if row/column combination is out of bounds.
   */
  @Override
  public void removeElement(int row, int col) {

    //check to see if the row and column are valid arguments
    try {

      if (!(row < size && col < size) || (row < 0) || (col < 0)) throw new OutOfBoundsException("Not a valid row/column combination.");
    }
    catch (OutOfBoundsException e) {

      //if the either the row or the column weren't valid arguments, print the message and return with no element added
      System.out.println(e.toString());
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

      if (!(row < size && col < size) || (row < 0) || (col < 0)) throw new OutOfBoundsException("Not a valid row/column combination.");
    }
    catch (OutOfBoundsException e) {

      //if the either the row or the column weren't valid arguments, print the message and return with no element added
      System.out.println(e.toString());
    }

    //utilize the overridden Object equals method and the list indexOf method to determine if the element is present in the list.
    int pos = list.indexOf(new element(row, col, 0));

    //if the element isn't in the list the data value is 0
    if (pos == -1) return 0;

    //since the element is in the list, grab it from the list and get it's data value
    return ((element)list.get(pos)).getData();
  }


  /* Returns the determinant of the matrix calculated recursively (Use the formula provided in the project description). */
  @Override
  public int determinant() {

    //base case, just find the determinant of the 2x2 matrix
    if (size == 2) {

      int a = getElement(0,0);
      int b = getElement(0,1);
      int c = getElement(1,0);
      int d = getElement(1,1);

      return a*d-b*c;
      // return getElement(0,0)*getElement(1,1) - getElement(0,1)*getElement(1,0);
    }

    int det = 0;

    for (int i = 0; i < size; i++) {

      //the formula for the determinant of a matrix, defined recursively
      det += Math.pow(-1,i) * getElement(0,i) * minor(0,i).determinant();
    }
    return det;
  }

  /* Returns a new matrix which is the minor of the original
      (See project description for minor definition). */
  @Override
  public SparseInterface minor(int row, int col) {

    //check to see if the row and column are valid arguments
    try {

      if (!(row < size && col < size) || (row < 0) || (col < 0)) throw new OutOfBoundsException("Not a valid row/column combination.");
    }
    catch (OutOfBoundsException e) {

      //if the either the row or the column weren't valid arguments, print the message and return with no element added
      System.out.println(e.toString());
    }

    //the size of the new matrix is one less than the current matrix
    int newSize = size - 1;

    //create a new list to become the list in the minor
    LinkedList<element> newList = new LinkedList<element>();

    //create an iterator to move over all the nodes in the list
    Iterator listIter = list.iterator();

    //loop will iterate over every node in the list
    while (listIter.hasNext()) {

      //grab the current element and store it to work with
      element elem = (element)listIter.next();

      /* check to see if the element is in the row or the column that we are
      removing. If so, don't add it to the new list */
      if (elem.getRow() != row && elem.getCol() != col){

        //if the element is past the row and the column that we are removing, decrement the row and the column
        if (elem.getRow() > row && elem.getCol() > col) {

          newList.add(new element(elem.getRow()-1, elem.getCol()-1, elem.getData()));
        }
        //else if the element is past just the row and not the column that we are removing, decrement the row only
        else if (elem.getRow() > row) {

          newList.add(new element(elem.getRow()-1, elem.getCol(), elem.getData()));
        }
        //else if the element is past just the column and not the row that we are removing, decrement the column only
        else if (elem.getCol() > col) {

          newList.add(new element(elem.getRow(), elem.getCol()-1, elem.getData()));
        }
        //else the element is before the row and the column we are removing, dont decrement either row or column
        else {

          newList.add(elem);
        }
      }
    }

    //return a new SparseMatrix with the new size and the new list as the minor
    return new SparseMatrix(newSize, newList);
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

    //create a temporary variables and an iterator
    String temp = "";
    element elem;
    Iterator iter = list.iterator();

    //iterate over each node in the lsit
    while (iter.hasNext()) {

      //grab the current node
      elem = (element)iter.next();

      // /* add the node's particular data items to the string and add a new line */
      temp += elem.getRow() + " " + elem.getCol() + " " + elem.getData() + "\n";
    }

    //return the string that we have been concatenating data on
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
