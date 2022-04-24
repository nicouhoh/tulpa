import processing.event.MouseEvent;
import processing.event.KeyEvent;

import java.awt.event.MouseWheelEvent;

public class Operator {
    // operator sends our input information to wherever it's going... Cockpit, Scroller, etc....

    Callosum callosum;
    Clickable lockedClickable;
    State state;

    int LMB = 37;
    int RMB = 39;

    public Operator(Callosum callosum){
        this.callosum = callosum;
        state = State.LIBRARY;
    }



// -------------------------------------- MOUSEHOLE PARADISE --------------------------------------


    // TODO it would be just lovely to tidy this up at some point
    public void interpretMouseySqueaks(Cockpit cockpit, int button, int act, int count, int x, int y, int mod) {

//        System.out.println(button + ", " + act);

        if (state == State.LIBRARY) {

//            if (button == LMB && act == MouseEvent.RELEASE) {
//                System.out.println("HERE!");
//            }

            // DRAG doesn't really care if the mouse is currently on something.
            // it's happy as long as it's holding on to something.

            if (button == LMB && act == MouseEvent.DRAG) {
                if (lockedClickable != null) lockedClickable.dragged(this, mod, x, y, callosum);
            } else if (act == MouseEvent.WHEEL) {
                Scrollable sTarget = cockpit.getScrollableAtPoint(x, y, cockpit.field.latitude);
                if (sTarget != null) sTarget.scroll(this, count);
            } else {

                // from here on we DO care if we evented on something that's interested

                Clickable target = cockpit.getClickableAtPoint(x, y, cockpit.field.latitude);

                if (button == LMB && act == MouseEvent.RELEASE) {
                    if (lockedClickable != null) {
                        lockedClickable.released(this, mod, x, y, callosum);
                        unlock();
                    } else {
                        if (target != null) target.clicked(this, mod, x, y, callosum);
                    }
                }

                if (button == LMB && target != null && act == MouseEvent.PRESS) {
                    target.grabbed(this, mod, x, y, callosum);
                    target.pressed(this, mod, x, y, callosum);
                }
            }
        }

        else if (state == State.CLIPPING){
            if (button == LMB && act == MouseEvent.RELEASE) {
                System.out.println("Clipping mode click");
            }
        }
    }


// ----------------------------------- KEYBOARD HAPPINESS ---------------------------------------

    int BACKSPACE = 8;
    int LEFT = 37;
    int UP = 38;
    int RIGHT = 39;
    int DOWN = 40;


    public void interpretTelegram(Cockpit cockpit, char key, int kc) {

        if (state == State.LIBRARY) {
            if (kc == UP) callosum.selectUpDown(-1);
            else if (kc == DOWN) callosum.selectUpDown(1);
            else if (kc == LEFT) callosum.selectLeftRight(-1);
            else if (kc == RIGHT) callosum.selectLeftRight(1);
            else if (kc == BACKSPACE) callosum.powerWordKill();

            else if (key == '0') callosum.toggleBrine();
            else if (key == '-') callosum.zoom(-1);
            else if (key == '=') callosum.zoom(1);
            else if (key == ' ') {
                state = State.CLIPPING;
                callosum.viewClipping();
            }

            else System.out.println("Unknown key: " + key + ", Keycode: " + kc);
        }

        else if(state == State.CLIPPING){

            // TODO figure out multiple selex

            if (kc == LEFT) callosum.selectLeftRight(-1);
            if (kc == RIGHT) callosum.selectLeftRight(1);

            if (key == ' ') {
                state = State.LIBRARY;
                callosum.exitClippingView();
            }
        }
    }

    public void lockClickable(Clickable c){
        lockedClickable = c;
    }

    public void unlock(){
        lockedClickable = null;
    }

}