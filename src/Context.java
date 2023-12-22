import processing.event.KeyEvent;
public interface Context {

    void draw(Visipalp visipalp, Mouse mouse);
    void resize(Visipalp visipalp);
    void receiveMouseEvent(Mouse mouse, Squeak squeak);
    void receiveKeyEvent(KeyEvent e);
    void receiveKey(KeyEvent e);
    void receiveType(char c);
    void space();
    void backspace();
    void up();
    void down();
    void left();
    void right();
    void esc();
    void enter();
    void zero();
    void plus();
    void minus();
    void equals();
    void type(char c);
}
