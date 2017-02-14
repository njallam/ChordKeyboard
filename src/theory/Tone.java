package theory;

public final class Tone {
  private final Note note;
  private final Shift shift;
  private final int octave;

  public Tone(Note note, int octave) {
    this(note, Shift.NONE, octave);
  }

  public Tone(Note note, Shift shift, int octave) {
    this.note = note;
    this.shift = shift;
    this.octave = octave;
  }

  Note getNote() {
    return note;
  }

  int getNumber() {
    return (12 * octave) + note.getNumber() + shift.getModifier();
  }

  @Override
  public String toString() {
    String shiftSymbol = shift == Shift.NONE ? "" : shift == Shift.SHARP ? "\u266F" : "\u266D";
    return note + shiftSymbol + octave;
  }
}
