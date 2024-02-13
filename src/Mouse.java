import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Mouse {

    Controller controller;
    ClawMachine claw;
    MouseStatus status;

    public Mouse(Controller controller){
        this.controller = controller;
        claw = new ClawMachine(controller);
        status = new MouseStatus();
    }

    public void interpretSqueak(MouseEvent e, Organelle root){

        Squeak squeak = new Squeak(e);
        status.setLastSqueak(squeak);

        switch (e.getAction()){

            case MouseEvent.MOVE -> {
                findHotItem(root, squeak);
            }
            case MouseEvent.KEY -> {
                SqueakMouseDown s = new SqueakMouseDown(e, status);
                s.squeak(controller, this);
                status.setActiveItem(status.getActiveItem());
            }
            case MouseEvent.RELEASE -> {
                SqueakRelease s = new SqueakRelease(e, status, claw);
                s.squeak(controller);
            }
            case MouseEvent.WHEEL -> {
                SqueakWheel s = new SqueakWheel(e, status);
                Organelle target = captureAndBubble(root, squeak);
                s.squeak(controller, target);
            }
            case MouseEvent.DRAG -> {
                findHotItem(root, squeak);
                SqueakDrag s = new SqueakDrag(e, status, claw);
                Dropzone zone = captureDropzone(root, squeak);
                s.squeak(this, zone);
            }
            case MouseEvent.EXIT -> status.clearHotItem();
        }
    }

    // Returns the deepest organelle under the mouse that accepts this kind of squeak
    public Organelle captureAndBubble(Organelle root, Squeak squeak){
        if (squeak.consumed) return null;
        if (!mouseOver(root, squeak)){
            return null;
        }
        squeak.addLatitude(root.getLatitude());

        for (Organelle child : root.getChildren()){
            Organelle result = captureAndBubble(child, squeak);
            if (squeak.consumed) return result;
        }
        squeak.consume(root); // in here is where we check whether the organelle accepts this kind of squeak and make sure if it does we don't trigger additional squeaks up the chain.
        return root;
    }

    public Dropzone captureDropzone(Organelle root, Squeak squeak){
        squeak.addLatitude(root.getLatitude());
        Dropzone zone = findDropZone(root, squeak);

        for (Organelle child : root.getChildren()){
            Dropzone result = captureDropzone(child, squeak);
            if (result != null) zone = result;
        }
        return zone;
    }

    public Dropzone findDropZone(Organelle o, Squeak squeak){
        for (Dropzone z : o.dropZones){
            if (mouseOver(z, squeak)){
                squeak.consume();
                return z;
            }
        }
        return null;
    }

    public void findHotItem(Organelle root, Squeak squeak){
        Organelle target = captureAndBubble(root, squeak);
        if (target != status.getHotItem()) status.setHotItem(target);
    }

    public boolean mouseOver(float boxX, float boxY, float boxW, float boxH, float mouseX, float mouseY){
        return (mouseX > boxX) && (mouseX < boxX + boxW) && (mouseY > boxY) && (mouseY < boxY + boxH);
    }

    public boolean mouseOver(Organelle o, float mouseX, float mouseY){
        return mouseOver(o.x, o.y, o.w, o.h, mouseX, mouseY);
    }

    public boolean mouseOver(Organelle o, Squeak squeak){
        return mouseOver(o, squeak.getX(), squeak.getY() + squeak.getLatitude());
    }

    public boolean mouseOver(Dropzone z, float mouseX, float mouseY){
        return mouseOver(z.x, z.y, z.w, z.h, mouseX, mouseY);
    }

    public boolean mouseOver(Dropzone z, Squeak squeak){
        return mouseOver(z, squeak.getX(), squeak.getY() + squeak.getLatitude());
    }

    public boolean mouseOver(Cell c, Squeak squeak){
        return mouseOver(c.x, c.y, c.w, c.h, squeak.getX(), squeak.getY() + squeak.getLatitude());
    }
    public void drawHeldItem(PGraphics g){
        if (claw.heldItem != null){
            Organelle o = (Organelle)claw.heldItem;
            o.mirage(g, status.getLastSqueak().getX() - claw.dragOffset.x, status.getLastSqueak().getY() - claw.dragOffset.y, 0, 0);
        }
    }

    public void debug(PGraphics g, Squeak state){
        g.fill(255,0, 255, 200);
        g.textSize(24);
        g.text("mouse: " + state.getX() + ", " + state.getY() + '\n' +
                "hot item: " + status.getHotItem() + '\n' +
                "active item: " + status.getActiveItem() + '\n' // +
                , 50, 50    );
    }

}