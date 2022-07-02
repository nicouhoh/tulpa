import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import drop.DropEvent;
import java.io.File;

public class Operator {
    // operator sends our input information to wherever it's going... Cockpit, Scroller, etc....

    Callosum callosum;
    Draggable heldDraggable;
    Draggable toGrab;
    State state;

    float anchorx;
    float anchory;
    float distX;
    float distY;

    int LMB = 37;
    int RMB = 39;

    public Operator(Callosum callosum) {
        this.callosum = callosum;
        changeState(State.LIBRARY);
    }


// -------------------------------------- <3 MOUSEHOLE HEAVEN <3 --------------------------------------

    // TODO Then we're figuring out the text box in visual mode, aka Graffito.

    public void interpretMouseySqueaks(Cockpit cockpit, int button, int act, int count, int x, int y, int mod) {

//        if (act == MouseEvent.PRESS) System.out.println("PRESS");

        Clickable target = cockpit.getClickableAtPoint(x, y, cockpit.field.latitude);
        if (target == null) return;

        switch (getState()) {

            case TEXT:
                checkClickOutsideText(target, cockpit, button, act, count, x, y, mod);
                break;

            case LIBRARY:

                switch (act) {
                    case MouseEvent.WHEEL:
                        libraryWheel(cockpit, count, x, y);
                        break;

                    case MouseEvent.DRAG:
                        libraryDrag(target, x, y, mod);
                        break;

                    case MouseEvent.RELEASE:
                        libraryRelease(target, x, y, mod);
                        break;

                    case 1:
                        System.out.println("PRESS");
                        libraryPress(target, x, y, mod);
                        break;
                }
                break;

            case CLIPPING:
                if (act == MouseEvent.RELEASE){
                    clippingClick(target, mod, x, y, callosum);
                }
                break;
        }
    }

    public void libraryWheel(Cockpit cockpit, int count, int x, int y) {
        Scrollable sTarget = cockpit.getScrollableAtPoint(x, y, cockpit.field.latitude);
        if (sTarget != null) sTarget.scroll(this, count);
    }

    public void libraryDrag(Clickable target, int x, int y, int mod){

        // if we're preparing to grab something, TODO grab it when we exceed the anchor distance
        if(toGrab != null){
            if(grabDistCheck((Monad)target, distX, distY, x, y)) {
                toGrab.grabbed(this, mod, x, y, callosum);
                setHeldDraggable(toGrab);
                setToGrab(null);
            }

            // if we're holding something, drag it around
        }else if (heldDraggable != null){

            heldDraggable.dragged(this, mod, x, y, anchorx, anchory, callosum); // TODO figure out the anchoring

            target.hoveredWithGift(this, mod, x, y, heldDraggable, anchorx, anchory, callosum);
            if (target != callosum.field)
                callosum.field.setBetweenClips(null);// cheating... is there a better way
        }
    }

    public void libraryRelease(Clickable target, int x, int y, int mod){
        if (toGrab != null) setToGrab(null);

        if (heldDraggable != null) {
            target.offeredGift(this, mod, x, y, heldDraggable, callosum);
            heldDraggable.dropped(this, mod, x, y, callosum);
            clearHeldDraggable();
        }else{
            target.clicked(this, mod, x, y, callosum);
        }
        callosum.field.clearCasper();
    }

    public void libraryPress(Clickable target, float x, float y, int mod){

        target.pressed(this, mod, x, y, callosum);

        if (target instanceof Draggable){
            if(heldDraggable != null || toGrab != null) return;
            Draggable d = (Draggable)target;
            setAnchor((Monad)target, x, y);
            setupDistCheck(x, y);
            setToGrab(d);
        }
    }

// ----------------------------------- KEYBOARD INFERNO ---------------------------------------

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

        switch (getState()) {

            case LIBRARY:
                if (act == KeyEvent.PRESS) {
                    if (kc == UP) callosum.selectUpDown(-1);
                    else if (kc == DOWN) callosum.selectUpDown(1);
                    else if (kc == LEFT) callosum.selectLeftRight(-1);
                    else if (kc == RIGHT) callosum.selectLeftRight(1);
                    else if (kc == BACKSPACE) callosum.powerWordKill();
                    else if (kc == TAB) {
                        callosum.togglePanel(); // -> TEXT state
                    } else if (key == '0' && mod == CTRL || mod == META) callosum.toggleBrine();
                    else if (key == '-' && mod == CTRL || mod == META) callosum.zoom(-1);
                    else if (key == '=' && mod == CTRL || mod == META) callosum.zoom(1);
                    else if (key == ' ') {
                        callosum.viewClipping(); // -> CLIPPING state
                    } else if (key == '?') {
                        if (callosum.library.selected.size() > 0) {
                            for (Clipping c : callosum.library.selected) {
                                c.spegel.monadDebugInfo(callosum.field.latitude);
                            }
                        }
                    }
                } else if (act == KeyEvent.TYPE) {
                    if (mod == CTRL) return;
                    if (key == '#'){
                        openTagBubbleOnType('#');
                        return;
                    }
                    if (key == '\n' || key == '\t' || key == ' ') return;
                    callosum.viewClipping();
                    Graffito g = callosum.getGraffito();
                    callosum.focusText(g);
                    g.type(key);
                }
                break;


            case CLIPPING:
                if (act == KeyEvent.PRESS) {
                    // TODO figure out multiple selex

                    if (kc == LEFT) callosum.selectLeftRight(-1);
                    if (kc == RIGHT) callosum.selectLeftRight(1);

                    if (key == ' ') {
                        changeState(State.LIBRARY);
                        callosum.exitClippingView();
                    }
                }
                else if (act == KeyEvent.TYPE){
                    if (key == '\n' || key == '\t' || key == ' ') return;
                    Graffito g = callosum.getGraffito();
                    callosum.focusText(g);
                    g.type(key);
                }

                break;

            case TEXT:
                Scrawler cur = callosum.getCurrentText();
                if (kc == TAB && cur instanceof SearchBar) {
                    callosum.togglePanel();
                } else if (kc == ENTER) {
                    cur.commit(callosum);
                    changeState(State.LIBRARY);
                    callosum.unfocusText();
                } else if (act == KeyEvent.TYPE && cur != null) {
                    cur.type(key);
                }
                break;
        }
    }

    public void receiveCarePackage(Cockpit cockpit, DropEvent e) {
        if (getState() != State.LIBRARY) return;
        if (e.isImage() && e.file().getName().contains(".jpg")) { // TODO can't wait to kill this. i got the bloodlust....
            callosum.addClipping(e.file());
        } else if (e.isFile()) {
            File file = new File(e.toString());
            System.out.print("ABSOLUTE PATH: " + file.getAbsolutePath());
            if (file.isDirectory()) {
                callosum.addClippings(file);
            }
        }
    }

    public void clearHeldDraggable() {
        heldDraggable = null;
    }

    public State getState() {
        return state;
    }

    public void changeState(State s) {
        state = s;
    }

    public void checkClickOutsideText(Clickable target, Cockpit cockpit, int button, int act, int count, int x, int y, int mod) {
        if (!(target instanceof Graffito) && !(target instanceof VisionImage)) {
            callosum.unfocusText();
            changeState(State.LIBRARY);
            interpretMouseySqueaks(cockpit, button, act, count, x, y, mod);
        }
    }

    public void clippingClick(Clickable target, int mod, int x, int y, Callosum callosum){
        System.out.println(target);
        if (target instanceof VisionImage) System.out.println("Something happens");
        else if (target instanceof Graffito) {
            target.clicked(this, mod, x, y, callosum);
        }
        else {
            changeState(State.LIBRARY);
            callosum.exitClippingView();
        }
    }

    public void openTagBubbleOnType(char key){
        if (key == '\n' || key == '\t' || key == ' ') return; // let's not open it up on ENTER, SPACE or TAB
        if (callosum.library.selected.size() == 1) {
            Clipping c = callosum.getSelectedClip();
            if (c.spegel.tagBubble == null) {
                c.spegel.openTagBubble();
                c.spegel.tagBubble.type(key);
            } else {
                c.spegel.tagBubble.enable();
                c.spegel.tagBubble.type(key);
            }
            callosum.focusText(c.spegel.getScrawler());
        }
    }

    public void setHeldDraggable(Draggable d){
        heldDraggable = d;
    }

    public void setToGrab(Draggable d) { toGrab = d;}

    public void setAnchor(Monad target, float x, float y){
        anchorx = target.x - x;
        anchory = target.y - y;
    }

    public void setupDistCheck(float x, float y){
        distX = x;
        distY = y;
    }

    public void setDragXYCheck(float x, float y){

    }

    public boolean grabDistCheck(Monad target, float distX, float distY, float mouseX, float mouseY){
        if (PApplet.dist(distX, distY, mouseX, mouseY) >= target.grabDist) return true;
        else return false;
    }


}