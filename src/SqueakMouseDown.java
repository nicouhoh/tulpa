import processing.event.MouseEvent;

public class SqueakMouseDown extends Squeak {

    public SqueakMouseDown(MouseEvent e, MouseStatus status){
        super(e);
        this.status = status;
    }

    public void squeak(Controller controller, Mouse mouse){
        if(status.getHotItem() == null) return;
        status.setActiveItem(status.getHotItem());
        for (Mousish mousish : status.getHotItem().mousishes) {
            mousish.mouseDown(controller, mouse, getModifiers());
            System.out.println("Clicked " + mousish);
        }
    }
}
