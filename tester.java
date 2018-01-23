public class tester {

  public static void main(String[] args) {

    sparseMatrix matt = new sparseMatrix();

    matt.setSize(2);

    matt.addElement(0, 0, 1);
    matt.addElement(1, 1, 3);
    matt.addElement(1, 0, 1);
    matt.addElement(0, 1, 1);

    System.out.println(matt.determinant());
  }
}
