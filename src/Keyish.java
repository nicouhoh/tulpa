import processing.event.KeyEvent;

public interface Keyish extends Palpable {

    void receiveKey(KeyEvent e);
}
