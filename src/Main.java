import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

public class Main {
  public static void main(String[] args) throws MidiUnavailableException {
    JFrame frame = new JFrame("Test");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    JPanel panel = new JPanel();

    panel.addKeyListener(new Keyboard());

    frame.add(panel);
    frame.add(new JLabel("Press letters A-G to choose note.  SHIFT for minor, ALT for sharp.  1-9 for octave selection."));

    frame.setSize(600, 200);
    frame.setVisible(true);

    panel.requestFocusInWindow();
  }
}