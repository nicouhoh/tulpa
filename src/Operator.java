import processing.event.MouseEvent;
import drop.DropEvent;
import java.io.File;

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


    public void interpretMouseySqueaks(Cockpit cockpit, int button, int act, int count, int x, int y, int mod) {


        if (state == State.LIBRARY) {

            if(act == MouseEvent.WHEEL){
                Scrollable sTarget = cockpit.getScrollableAtPoint(x, y, cockpit.field.latitude);
                if (sTarget != null) sTarget.scroll(this, count);
                return;
            }

            else if (button == LMB) {

                // DRAG doesn't really care if the mouse is currently on something.
                // it's happy as long as it's holding on to something.
                if (act == MouseEvent.DRAG) {
                    if (lockedClickable != null) lockedClickable.dragged(this, mod, x, y, callosum);
                    return;
                }

                Clickable target = cockpit.getClickableAtPoint(x, y, cockpit.field.latitude);

                if (act == MouseEvent.RELEASE) {
                    callosum.cockpit.casper = null;
                    if (lockedClickable != null) {
                        lockedClickable.dropped(this, mod, x, y, callosum);
                        unlock();
//                    } else { // TODO commenting this out for now to make clicking and dragging work at the same time-ish. Someday will have to actually reckon w it
                        if (target != null) target.clicked(this, mod, x, y, callosum);
                    }
                }

                if (target != null && act == MouseEvent.PRESS) {
                    target.pressed(this, mod, x, y, callosum);
                    setLockedClickable(target);
                    target.grabbed(this, mod, x, y, callosum);
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

    public void receiveCarePackage(Cockpit cockpit, DropEvent e){
        if (state != State.LIBRARY) return;
        if (e.isImage() && e.file().getName().contains(".jpg")){ // TODO can't wait to kill this. i got the bloodlust....
            callosum.addClipping(e.file());
        } else if (e.isFile()){
            File file = new File(e.toString());
            System.out.print("ABSOLUTE PATH: " + file.getAbsolutePath());
            if (file.isDirectory()){
                callosum.addClippings(file);
            }
        }
    }

    public void setLockedClickable(Clickable c){
        lockedClickable = c;
    }

    public void unlock(){
        lockedClickable = null;
    }

}