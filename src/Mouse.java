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
        Squeak squeak = emitSqueak(e, root);
        squeak.squeak(controller);
        status.setLastSqueak(squeak);
    }

    public Squeak emitSqueak(MouseEvent e, Organelle root){
        Squeak result;
        switch (e.getAction()){
            case MouseEvent.MOVE -> result = new SqueakMove(e, status, root);
            case MouseEvent.KEY -> result = new SqueakMouseDown(e, status);
            case MouseEvent.RELEASE -> result = new SqueakRelease(e, status, claw);
            case MouseEvent.WHEEL -> result = new SqueakWheel(e, status, root);
            case MouseEvent.DRAG -> result = new SqueakDrag(e, status, claw, root);
            case MouseEvent.EXIT -> result = new SqueakExit(e, status);
            default -> result = new Squeak(e);
        }
        return result;
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