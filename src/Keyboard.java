import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import theory.Chord;
import theory.ChordType;
import theory.Note;
import theory.Shift;
import theory.Tone;

public class Keyboard implements KeyListener {
  private final MidiChannel[] channels;

  private boolean keysDown[] = new boolean[0x10000]; //FIXME Hack

  private int octave;
  private Shift shift;
  private ChordType chordType;

  private Set<Chord> chords;

  Keyboard() throws MidiUnavailableException {
    Synthesizer synth = MidiSystem.getSynthesizer();
    synth.open();
    channels = synth.getChannels();
    channels[0].programChange(1);
    chords = new HashSet<>();
    octave = 4;
    shift = Shift.NONE;
    chordType = ChordType.MAJOR;
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if (0 <= key && key <= 0xFFFF) {
      if (keysDown[key]) return;
      keysDown[key] = true;
    }
    char keyChar = Character.toUpperCase(e.getKeyChar());
    int keyCode = e.getKeyCode();
    System.out.print(String.format("%s (%s) pressed: ", keyChar, keyCode));
    if (noteKeyCode(keyCode)) {
      Tone tone = new Tone(charToNote(keyChar), shift, octave);
      Chord chord = new Chord(tone, chordType);
      chords.add(chord);
      chord.streamNoteNumbers().forEach(n -> channels[0].noteOn(n, 600));
      System.out.println("playing: " + chord);
    } else if (keyCode == KeyEvent.VK_SHIFT) {
      chordType = ChordType.MINOR;
      System.out.println("minor chord selected");
    } else if (keyCode == KeyEvent.VK_ALT) {
      shift = Shift.SHARP;
      System.out.println("sharp root selected");
    } else if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_9) {
      System.out.print("octave change: " + octave);
      octave = keyCode - KeyEvent.VK_1;
      System.out.println(" -> " + octave);
    } else {
      System.out.println("no action");
    }
  }

  private boolean noteKeyCode(int keyCode) {
    return keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_G;
  }

  private Note charToNote(char c) {
    return Note.valueOf(Character.toString(c));
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    if (0 <= key && key <= 0xFFFF) {
      if (!keysDown[key]) return;
      keysDown[key] = false;
    }
    char keyChar = Character.toUpperCase(e.getKeyChar());
    int keyCode = e.getKeyCode();
    System.out.print(String.format("%s (%s) released: ", keyChar, keyCode));
    if (noteKeyCode(keyCode)) {
      chords.stream().filter(c -> c.getRootNote().equals(charToNote(keyChar))).forEach
          (c -> c.streamNoteNumbers().forEach(channels[0]::noteOff));
      chords.removeIf(c -> c.getRootNote().equals(charToNote(keyChar)));
      System.out.println(String.format("stopping %s chords", keyChar));
    } else if (keyCode == KeyEvent.VK_SHIFT) {
      chordType = ChordType.MAJOR;
      System.out.println("major chord selected");
    } else if (keyCode == KeyEvent.VK_ALT || false) {
      shift = Shift.NONE;
      System.out.println("natural root selected");
    } else {
      System.out.println("no action");
    }
  }
}
