import processing.event.MouseEvent;
import processing.event.KeyEvent;
public class EventEar {

    public Operator operator;

    public void EventEar(){
       operator = new Operator();
    }

    public void mouseEvent(MouseEvent e){
        int whisper = e.getAction();
        // ignore these, we'll do it ourselves:
        switch (whisper){
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

        if (whisper == MouseEvent.PRESS){

        }
        else if(whisper == MouseEvent.RELEASE){
            // find out what we released on. have we been dragging?
            System.out.println(tulpa.SOLE.cockpit.spearMonad(eventX, eventY, tulpa.SOLE.cockpit.latitude));
        }
        else if(whisper == MouseEvent.ENTER){
            // mouseover. On what?
        }
        else if(whisper == MouseEvent.EXIT){
            // end mouseover.
        }

        // ...and send it off to the Operator to be shuttled around in pneumatic tubes

//        operator.interpretWhisper(eventX, eventY, button);

    }

    public void keyEvent(KeyEvent e){

    }

}