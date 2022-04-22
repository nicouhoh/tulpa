import processing.event.MouseEvent;
import processing.event.KeyEvent;

public class Operator {
    // operator sends our input information to wherever it's going... Cockpit, Scroller, etc....
    // i.e. eliminate areas where we're not clicking first... then check for onscreen...


    int LMB = 37;
    int RMB = 39;

    public void interpretMouseySqueaks(Cockpit cockpit, int button, int event, int x, int y){
        if (button == LMB && event == MouseEvent.RELEASE){
            cockpit.getChildAtPoint(x, y, cockpit.field.latitude).clicked();
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

}