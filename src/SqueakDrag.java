import processing.event.MouseEvent;

public class SqueakDrag extends Squeak {

    public SqueakDrag(MouseEvent e, MouseStatus status, ClawMachine claw, Organelle root){
        super(e);
        this.status = status;
        this.claw = claw;
        this.root = root;
    }

    @Override
    public void squeak(Controller controller){
        findHotItem(root);
        Organelle target = status.getActiveItem();
        if (status.getActiveItem() != null && claw.isEmpty()) {
            claw.setDragOffset(getX() - target.x, getY() - target.y + getLatitude());
            claw.grab(target.draggish, getX(), getY() + getLatitude());
        }
        else if (!claw.isEmpty()){
            // TODO show loud dropzones
            Dropzone dropzone = captureDropzone(root);
            if (dropzone != status.getHoveredZone()) {
                status.setHoveredZone(dropzone);
            }
            claw.drag(getX(), getY()); // moving the mouse while holding something
        }
    }

    public Dropzone captureDropzone(Organelle root){
        addLatitude(root.getLatitude());
        Dropzone zone = findDropZone(root);

        for (Organelle child : root.getChildren()){
            Dropzone result = captureDropzone(child);
            if (result != null) zone = result;
        }
        return zone;
    }

    public Dropzone findDropZone(Organelle o){
        for (Dropzone z : o.dropZones){
            if (mouseOver(z, this)){
                consume();
                return z;
            }
        }
        return null;
    }

    @Override
    public void consume(Organelle organelle){
        if (organelle.draggish == null) return;
        consume();
    }
}