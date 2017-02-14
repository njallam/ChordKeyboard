package theory;

public enum Note {
  C(0), D(2), E(4), F(5), G(7), A(9), B(11);

  private final int number;

  Note(int number) {
    this.number = number;
  }

  public int getNumber() {
    return number;
  }
}