public class tester {

  public static void main(String[] args) {

    sparseMatrix matt = new sparseMatrix();

    matt.setSize(3);

    matt.addElement(0, 0, 8);
    matt.addElement(0, 1, 4);
    matt.addElement(0, 2, 9); 
    matt.addElement(1, 0, 5);
    matt.addElement(1, 1, 8);
    matt.addElement(1, 2, 7);
    matt.addElement(2, 0, 4);
    matt.addElement(2, 1, 3);
    matt.addElement(2, 2, 2);

    System.out.println(matt.determinant());
  }
}
