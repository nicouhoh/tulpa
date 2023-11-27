import processing.event.KeyEvent;

public class Ouija {

    public Keyish focus;

    public void receiveMessage(KeyEvent e){

        switch (e.getAction()){
            case KeyEvent.PRESS -> {

            }
        }
    }

    public void setFocus(Keyish keyish){
        focus = keyish;
    }
}
