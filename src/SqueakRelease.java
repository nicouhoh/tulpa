import processing.event.MouseEvent;

public class SqueakRelease extends Squeak {

    public SqueakRelease(MouseEvent e, MouseStatus status, ClawMachine claw) {
        super(e);
        this.claw = claw;
        this.status = status;
    }

    public void squeak(Controller controller){
        if (!claw.isEmpty()) {
            if (status.getHoveredZone() != null){
                System.out.println("Dropped " + claw.heldItem + " onto " + status.getHoveredZone());
                status.getHoveredZone().drop(controller, claw.heldItem);
            }
            claw.release(); // if you're holding something drop it
        }
        else if (status.getActiveItem() == status.getHotItem() && status.getActiveItem()!= status.getPreventUnclick()){
            for (Mousish mousish : status.getActiveItem().mousishes){
                mousish.buttonPress(controller, getModifiers());
            }
        }
        status.clearActiveItem();
        status.clearPreventUnclick();
    }
}