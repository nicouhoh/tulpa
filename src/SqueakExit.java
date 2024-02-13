import processing.event.MouseEvent;

public class SqueakExit extends Squeak {

    public SqueakExit(MouseEvent e, MouseStatus status) {
        super(e);
        this.status = status;
    }

    @Override
    public void squeak(Controller controller){
        status.clearHotItem();
    }
}
