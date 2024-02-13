import processing.event.MouseEvent;

public class SqueakWheel extends Squeak {

    public SqueakWheel(MouseEvent e, MouseStatus status, Organelle root) {
        super(e);
        this.status = status;
        this.root = root;
    }

    @Override
    public void squeak(Controller controller){
        Organelle captured = captureAndBubble(root);
        if (captured == null || captured.wheelish == null) return;
        captured.wheelish.wheel(controller, getCount());
    }

    @Override
    public void consume(Organelle organelle){
        if (organelle.wheelish == null) return;
        consume();
    }
}
