import drop.DropEvent;
import processing.core.PConstants;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class BaseContext {

    Controller controller;

    Skrivsak focusedSkrivsak;
    SkrivsakContext sc;

    public BaseContext(Controller controller){
        this.controller = controller;
    }

    public void setFocusedSkrivsak(Skrivsak skrivsak){
        if (skrivsak == focusedSkrivsak) return;
        clearFocusedSkrivsak();
        focusedSkrivsak = skrivsak;
        focusedSkrivsak.setFocused(true);
        sc = new SkrivsakContext(controller, skrivsak);
    }

    public void clearFocusedSkrivsak(){
        if (focusedSkrivsak == null) return;
        focusedSkrivsak.setFocused(false);
        focusedSkrivsak = null;
    }

    public void receiveKeyEvent(KeyEvent e){
        if (focusedSkrivsak != null){
            sc.receiveKeyEvent(e);
            return;
        }

        if (e.getAction() == KeyEvent.PRESS) receiveKey(e);
        else if (e.getAction() == KeyEvent.TYPE) receiveType(e);
    }

    public void receiveKey(KeyEvent e){
        switch (e.getKey()){
            case PConstants.CODED -> {
                switch(e.getKeyCode()){
                    case PConstants.LEFT -> left();
                    case PConstants.RIGHT -> right();
                    case PConstants.UP -> up();
                    case PConstants.DOWN -> down();
                }
            }
            case PConstants.BACKSPACE -> backspace();
            case '0' -> zero();
            case '-' -> minus();
            case '=' -> equals();
            case ' ' -> space();
            case PConstants.ESC -> esc();
        }
    }

    public void receiveType(KeyEvent e){
        if (e.getModifiers() == MouseEvent.CTRL) ctrlType(e.getKey());
        else type(e.getKey());
    }

    public void receiveMouseEvent(Mouse mouse, MouseEvent e){}
    public void receiveDropEvent(DropEvent e){}
    public void draw(Visipalp visipalp, Mouse mouse) {}
    public void resize(Visipalp visipalp) {}

    public void checkForUnfocus(Mouse mouse, Squeak squeak){
        if (squeak.getAction() == 1 &&
                focusedSkrivsak != null &&
                !squeak.mouseOver((Organelle)focusedSkrivsak))
            clearFocusedSkrivsak();
    }

    public void exitContext(){
        clearFocusedSkrivsak();
    }
    public void space() {}
    public void backspace() {}
    public void up() {}
    public void down() {}
    public void left() {}
    public void right() {}
    public void esc() {}
    public void enter(){}
    public void zero() {}
    public void plus() {}
    public void minus() {}
    public void equals() {}
    public void type(char c) {}

    public void ctrlType(char c){}
}
