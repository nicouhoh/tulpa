import processing.event.KeyEvent;
import processing.event.MouseEvent;
import drop.DropEvent;
import java.io.File;
import java.security.Key;

public class Operator {
    // operator sends our input information to wherever it's going... Cockpit, Scroller, etc....

    Callosum callosum;
    Clickable lockedClickable;
    float lockedX;
    float lockedY;
    State state;


    int LMB = 37;
    int RMB = 39;

    public Operator(Callosum callosum){
        this.callosum = callosum;
        changeState(State.LIBRARY);
    }



// -------------------------------------- MOUSEHOLE HELL --------------------------------------


    public void interpretMouseySqueaks(Cockpit cockpit, int button, int act, int count, int x, int y, int mod) {

        // unfocus text if we click outside it
        if(getState() == State.TEXT && act != MouseEvent.MOVE){
            // TODO click out of text focus
            Clickable cTarget = cockpit.getClickableAtPoint(x, y, cockpit.field.latitude);
            if (cTarget != callosum.currentText){
                callosum.unfocusText();
                changeState(State.LIBRARY);
            }
        }


        if (getState() == State.LIBRARY) {


            if(act == MouseEvent.WHEEL){
                Scrollable sTarget = cockpit.getScrollableAtPoint(x, y, cockpit.field.latitude);
                if (sTarget != null) sTarget.scroll(this, count);
                return;
            }

            Clickable target = cockpit.getClickableAtPoint(x, y, cockpit.field.latitude);
            if (target == null) return;

            if (button == LMB) {

                // DRAG doesn't really care if the mouse is currently on something.
                // it's happy as long as it's holding on to something.
                if (act == MouseEvent.DRAG) {
                    if (lockedClickable != null){
                        lockedClickable.dragged(this, mod, x, y, lockedX, lockedY, callosum);
                        target.hoveredWithGift(this,mod, x, y, lockedClickable, lockedX, lockedY, callosum);
                        if (target != callosum.field) callosum.field.setBetweenClips(null); // TODO cheating... is there a better way

                    }
                    return;
                }


                if (act == MouseEvent.RELEASE) {

                    if (lockedClickable != null) {
                        target.offeredGift(this, mod, x, y, lockedClickable, callosum);
                        lockedClickable.dropped(this, mod, x, y, callosum);
                        unlock();
//                    } else { // TODO commenting this out for now to make clicking and dragging work at the same time-ish. Someday will have to actually reckon w it
                        target.clicked(this, mod, x, y, callosum);
                    }
                    callosum.field.clearCasper(); // clear casper & betweener

                }

                if (act == MouseEvent.PRESS) {
                    target.pressed(this, mod, x, y, callosum);
                    setLockedClickable(target);
                    setLockedPos((Monad)target, x, y);
                    target.grabbed(this, mod, x, y, callosum);
                }
            }
        }

        else if (getState() == State.CLIPPING){
            if (button == LMB && act == MouseEvent.RELEASE) {
                System.out.println("Clipping mode click");
            }
        }
    }


// ----------------------------------- KEYBOARD PARADISE ---------------------------------------

    int BACKSPACE = 8;
    int LEFT = 37;
    int UP = 38;
    int RIGHT = 39;
    int DOWN = 40;
    int ENTER = 10;
    int TAB = 9;

    int SHIFT = 1;
    int CTRL = 2;
    int META = 8;


    public void interpretTelegram(Cockpit cockpit, char key, int kc, int act, int mod) {


        if (getState() == State.LIBRARY & act == KeyEvent.PRESS) {
            if (kc == UP) callosum.selectUpDown(-1);
            else if (kc == DOWN) callosum.selectUpDown(1);
            else if (kc == LEFT) callosum.selectLeftRight(-1);
            else if (kc == RIGHT) callosum.selectLeftRight(1);
            else if (kc == BACKSPACE) callosum.powerWordKill();
            else if (kc == TAB){
                callosum.togglePanel(); // >> TEXT state
            }
            else if (key == '0' && mod == CTRL || mod == META) callosum.toggleBrine();
            else if (key == '-' && mod == CTRL || mod == META) callosum.zoom(-1);
            else if (key == '=' && mod == CTRL || mod == META) callosum.zoom(1);
            else if (key == ' ') {
                state = State.CLIPPING;
                callosum.viewClipping();
            }
            else if (key == '?'){
                if(callosum.library.selected.size() > 0){
                    for (Clipping c : callosum.library.selected){
                        c.spegel.monadDebugInfo(callosum.field.latitude);
                    }
                }
            }
            else if (act == KeyEvent.TYPE){
                if (key == '\n' || key == '\t') return; // let's not open it up on ENTER or TAB
                if(callosum.library.selected.size() == 1){
                    Spegel s = callosum.library.selected.get(0).spegel;
                    if (s.tagBubble == null) {
                        s.createTagBubble();
                        s.tagBubble.bodyText += key;
                    }
                    callosum.focusText(s.getScrawler());
                }
            }

            //else System.out.println("Unknown key: " + key + ", Keycode: " + kc);
        }

        else if(getState() == State.CLIPPING){

            // TODO figure out multiple selex

            if (kc == LEFT) callosum.selectLeftRight(-1);
            if (kc == RIGHT) callosum.selectLeftRight(1);

            if (key == ' ') {
                changeState(State.LIBRARY);
                callosum.exitClippingView();
            }
        }

        else if (getState() == State.TEXT){
            if (kc == TAB && callosum.getCurrentText() instanceof SearchBar){
                callosum.togglePanel();
            } else if (kc == ENTER){
                if(callosum.getCurrentText() instanceof TagBubble) {
                    callosum.library.tagClipping(callosum.library.selected.get(0), callosum.getCurrentText().parseTags());
                }
                callosum.getCurrentText().commit();
                changeState(State.LIBRARY);
                callosum.unfocusText();
            }else if (act == KeyEvent.TYPE && callosum.getCurrentText() != null){
                    callosum.getCurrentText().type(key, kc);
            }
        }
    }

    public void receiveCarePackage(Cockpit cockpit, DropEvent e){
        if (getState() != State.LIBRARY) return;
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

    public void setLockedPos(Monad target, float x, float y){
       lockedX = target.x - x;
       lockedY = target.y - y;
    }

    public void unFocusScrawler(){
        callosum.focusText(null);
        changeState(State.LIBRARY);
    }

    public void unlock(){
        lockedClickable = null;
    }

    public State getState(){
        return state;
    }

    public void changeState(State s){
        state = s;
    }

}