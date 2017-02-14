package theory;

public enum Shift {
  FLAT(-1), NONE(0), SHARP(1);

  private final int modifier;

  Shift(int modifier) {
    this.modifier = modifier;
  }

  public int getModifier() {
    return modifier;
  }
}
