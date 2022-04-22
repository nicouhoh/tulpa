import processing.event.MouseEvent;
import processing.event.KeyEvent;

public class Operator {
    // operator sends our input information to wherever it's going... Cockpit, Scroller, etc....
    // i.e. eliminate areas where we're not clicking first... then check for onscreen...

    Monad lockedMonad;

    int LMB = 37;
    int RMB = 39;

    public void interpretMouseySqueaks(Cockpit cockpit, int button, int event, int x, int y){
        if (button == LMB && event == MouseEvent.RELEASE){
            if(lockedMonad != null) {
                lockedMonad.released(this, x, y);
                unlock();
            }
            else{
                cockpit.getChildAtPoint(x, y, cockpit.field.latitude).clicked(this, x, y);
            }
        }
        if(button == LMB && event == MouseEvent.PRESS){
            cockpit.getChildAtPoint(x, y, cockpit.field.latitude).pressed(this, x, y);
        }
        if(button == LMB && event == MouseEvent.DRAG){
            if (lockedMonad != null) lockedMonad.dragged(this, x, y);
        }
    }

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

    public void lockMonad(Monad monad){
        lockedMonad = monad;
    }

    public void unlock(){
        lockedMonad = null;
    }

}