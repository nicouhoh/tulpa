import processing.event.MouseEvent;
import processing.event.KeyEvent;

import java.awt.event.MouseWheelEvent;

public class Operator {
    // operator sends our input information to wherever it's going... Cockpit, Scroller, etc....
    // i.e. eliminate areas where we're not clicking first... then check for onscreen...


    // -------------------------------------- MOUSEHOLE PARADISE --------------------------------------

    Callosum callosum;
    Clickable lockedClickable;

    int LMB = 37;
    int RMB = 39;

    public Operator(Callosum callosum){
        this.callosum = callosum;
    }

    public void interpretMouseySqueaks(Cockpit cockpit, int button, int act, int count, int x, int y, int mod){

        // DRAG doesn't really care if the mouse is currently on something.
        // it's happy as long as it's holding on to something.

        if(button == LMB && act == MouseEvent.DRAG){
            if (lockedClickable != null) lockedClickable.dragged(this, mod, x, y, callosum);
        }

        else if(act == MouseEvent.WHEEL){
            Scrollable sTarget = cockpit.getScrollableAtPoint(x, y, cockpit.field.latitude);
            if(sTarget != null) sTarget.scroll(this, count);
        }

        else {

            // from here on we do care if we evented on something that's interested

            Clickable target = cockpit.getClickableAtPoint(x, y, cockpit.field.latitude);
            if (target == null) return;

            if (button == LMB && act == MouseEvent.RELEASE) {
                if (lockedClickable != null) {
                    lockedClickable.released(this, mod, x, y, callosum);
                    unlock();
                } else {
                    target.clicked(this, mod, x, y, callosum);
                }
            }

            if (button == LMB && act == MouseEvent.PRESS) {
                target.grabbed(this, mod, x, y, callosum);
                target.pressed(this, mod, x, y, callosum);
            }
        }

    }


    // ----------------------------------- KEYBOARD HAPPINESS ---------------------------------------

    int BACKSPACE = 8;
    int LEFT = 37;
    int UP = 38;
    int RIGHT = 39;
    int DOWN = 40;


    public void interpretTelegram(Cockpit cockpit, char key, int kc){
        if(kc == UP){
           cockpit.field.stepLatitude(-100);
//           System.out.println(field.getLatitude());
        }
        else if(kc == DOWN){
           cockpit.field.stepLatitude(100);
//           System.out.println(field.getLatitude());
        }else if(kc == BACKSPACE){
           callosum.powerWordKill();
        }
        else System.out.println("Unknown key: " + key + ", Keycode: " + kc);
    }

    public void lockClickable(Clickable c){
        lockedClickable = c;
    }

    public void unlock(){
        lockedClickable = null;
    }

}