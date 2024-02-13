import processing.event.MouseEvent;

public class SqueakMouseDown extends Squeak {

    public SqueakMouseDown(MouseEvent e, MouseStatus status){
        super(e);
        this.status = status;
    }

    @Override
    public void squeak(Controller controller){
        if(status.getHotItem() == null) return;
        status.setActiveItem(status.getHotItem());
        for (Mousish mousish : status.getActiveItem().mousishes) {
            mousish.mouseDown(controller, this);
            System.out.println("Clicked " + mousish);
        }
        status.setActiveItem(status.getHotItem());
    }

    @Override
    public void consume(Organelle organelle){
        if (organelle.mousishes.isEmpty()) return;
        consume();
    }
}
