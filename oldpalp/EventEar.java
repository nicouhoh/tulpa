import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.event.KeyEvent;
import drop.DropEvent;

public class EventEar {

    public Operator operator;
    public Cockpit cockpit;


    public EventEar(Cockpit cockpit){
        this.cockpit = cockpit;
    }

    public void mouseEvent(MouseEvent e) {
        int whisper = e.getAction();
        // ignore these, we'll do it ourselves:
        switch (whisper) {
            case MouseEvent.CLICK:
            case MouseEvent.ENTER:
            case MouseEvent.EXIT:
            case MouseEvent.MOVE:
            // case MouseEvent.DRAG:
                return;
        }

        // construct the input information....
        int eventX = e.getX();
        int eventY = e.getY();
        int button = e.getButton();
        int count = e.getCount();
        int mod = e.getModifiers();

            // ...and send it off to the Operator to be shuttled around in pneumatic tubes

        operator.interpretMouseySqueaks(cockpit, button, whisper, count, eventX, eventY, mod);

    }

    public void keyEvent(KeyEvent e){

        // construct input data
        int act = e.getAction();
        //if (act == KeyEvent.TYPE || act == KeyEvent.RELEASE) return;
        if (act == KeyEvent.RELEASE) return;

        // System.out.println("Key: " + e.getKey() + ", KeyCode: " + e.getKeyCode() + ", Action: " + e.getAction());

      // send it to the Operator
        if (e.getAction() != KeyEvent.RELEASE){
            operator.interpretTelegram(cockpit, e.getKey(), e.getKeyCode(), act, e.getModifiers());
        }
    }

    public void dropEvent(DropEvent e){
        System.out.println("DropEvent");
        operator.receiveCarePackage(cockpit, e);
    }
}