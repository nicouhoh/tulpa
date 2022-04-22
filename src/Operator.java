import processing.event.MouseEvent;

public class Operator {
    // operator sends our input information to wherever it's going... Cockpit, Scroller, etc....
    // i.e. eliminate areas where we're not clicking first... then check for onscreen...


    // -------------------------------------- MOUSEHOLE PARADISE --------------------------------------

    Clickable lockedClickable;

    int LMB = 37;
    int RMB = 39;

    public void interpretMouseySqueaks(Cockpit cockpit, int button, int event, int x, int y){

        // DRAG doesn't really care if the mouse is currently on something.
        // it's happy as long as it's holding on to something.

        if(button == LMB && event == MouseEvent.DRAG){
            if (lockedClickable != null) lockedClickable.dragged(this, x, y);
        }

        // Now we do care if we evented on something that's interested

        Clickable target = cockpit.getClickableAtPoint(x, y, cockpit.field.latitude);
        if (target == null) return;

        if (button == LMB && event == MouseEvent.RELEASE){
            if(lockedClickable != null) {
                lockedClickable.released(this, x, y);
                unlock();
            }
            else{
                target.clicked(this, x, y);
            }
        }

        if(button == LMB && event == MouseEvent.PRESS){
                target.grabbed(this, x, y);
                target.pressed(this, x, y);
        }

    }


    // ----------------------------------- KEYBOARD HAPPINESS ---------------------------------------

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
        }
    }

    public void lockClickable(Clickable c){
        lockedClickable = c;
    }

    public void unlock(){
        lockedClickable = null;
    }

}