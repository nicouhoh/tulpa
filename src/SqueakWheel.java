import processing.event.MouseEvent;

public class SqueakWheel extends Squeak {

    public SqueakWheel(MouseEvent e, MouseStatus status) {
        super(e);
        this.status = status;
    }

    public void squeak(Controller controller, Organelle target){
        if (target == null || target.wheelish == null) return;
        target.wheelish.wheel(controller, getCount());
    }
}
