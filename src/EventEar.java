import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.event.KeyEvent;
public class EventEar {

    public Operator operator;
    public Cockpit cockpit;

//    boolean d;
//    float dX;
//    float dY;
//    float dDist = 10;
//    boolean dragging = false;

    public EventEar(Cockpit cockpit){
        this.cockpit = cockpit;
        operator = new Operator();
    }

    public void mouseEvent(MouseEvent e) {
        int whisper = e.getAction();
        // ignore these, we'll do it ourselves:
        switch (whisper) {
            case MouseEvent.CLICK:
//            case MouseEvent.ENTER:
//            case MouseEvent.EXIT:
//            case MouseEvent.DRAG:
                return;
        }

        // construct the input information....
        int eventX = e.getX();
        int eventY = e.getY();
        int button = e.getButton();

        if (whisper == MouseEvent.PRESS) {
        } else if (whisper == MouseEvent.RELEASE) {

        } else if (whisper == MouseEvent.DRAG){
        }
            // ...and send it off to the Operator to be shuttled around in pneumatic tubes

        operator.interpretMouseySqueaks(cockpit, button, whisper, eventX, eventY);

    }

    public void keyEvent(KeyEvent e){
        // construct input data

        int act = e.getAction();
        if (act == KeyEvent.TYPE || act == KeyEvent.RELEASE) return;

        System.out.println("Key: " + e.getKey() + ", KeyCode: " + e.getKeyCode());
      // send it to the Operator
        if (e.getAction() != KeyEvent.RELEASE){
            operator.interpretTelegram(cockpit, e.getKey(), e.getKeyCode());
        }
    }

}