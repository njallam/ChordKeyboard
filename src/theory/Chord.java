package theory;

import java.util.stream.Stream;

public final class Chord {
  private final Tone root;
  private final ChordType type;

  public Chord(Tone root, ChordType type) {
    this.root = root;
    this.type = type;
  }

  public Stream<Integer> streamNoteNumbers() {
    return type.streamNotes().map(i -> root.getNumber() + i);
  }

  public Note getRootNote() {
    return root.getNote();
  }

  @Override
  public String toString() {
    String chordSuffix = type == ChordType.MAJOR ? "" : "m";
    return root + chordSuffix;
  }
}
