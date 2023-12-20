public interface Context {

    void draw(Visipalp visipalp, Mouse mouse);
    void resize(Visipalp visipalp);
    void mouseEvent(Mouse mouse, Squeak squeak);
    void space();
    void backspace();
    void up();
    void down();
    void left();
    void right();
    void esc();
    void zero();
    void plus();
    void minus();
    void equals();
    void type(char c);
}
