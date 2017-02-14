package theory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

public enum ChordType {
  MAJOR(new Integer[]{0, 4, 7}), MINOR(new Integer[]{0, 3, 7});

  private final Set<Integer> notes;

  ChordType(Integer[] keys) {
    notes = new HashSet<>(Arrays.asList(keys));
  }

  public void forEach(Consumer<? super Integer> action) {
    notes.forEach(action);
  }

  public Stream<Integer> streamNotes() {
    return notes.stream();
  }
}
