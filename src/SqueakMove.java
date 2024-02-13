import processing.event.MouseEvent;
public class SqueakMove extends Squeak {

    public SqueakMove(MouseEvent e, MouseStatus status, Organelle root){
        super(e);
        this.status = status;
        this.root = root;
    }

    @Override
    public void squeak(Controller controller){
        findHotItem(root);
    }
}