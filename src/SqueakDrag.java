import processing.event.MouseEvent;

public class SqueakDrag extends Squeak {

    public SqueakDrag(MouseEvent e, MouseStatus status, ClawMachine claw){
        super(e);
        this.status = status;
        this.claw = claw;
    }

    public void squeak(Mouse mouse, Dropzone zone){
        if (status.getActiveItem() != null && claw.isEmpty()) {
            Organelle target = status.getActiveItem();
            claw.setDragOffset(getX() - target.x, getY() - target.y + getLatitude());
            claw.grab(target.draggish, getX(), getY() + getLatitude());
        }
        else if (!claw.isEmpty()){
            // TODO show loud dropzones
            if (zone != status.getHoveredZone()) {
                status.setHoveredZone(zone);
            }
            claw.drag(getX(), getY()); // moving the mouse while holding something
        }
    }

}