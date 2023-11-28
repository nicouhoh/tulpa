import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Mouse {

    Controller controller;
    ClawMachine claw;

    Organelle hotItem;
    Organelle activeItem;

    Organelle preventUnclick; // use in special cases to prevent a buttonPress afer mouseDown. see ctrl/cmd click on Thumbnails

    public Mouse(Controller controller){
        this.controller = controller;
        claw = new ClawMachine();
    }

    public void interpretSqueak(MouseEvent e, Organelle root){

        // TODO I think I could do all this better by subclassing the Squeak, but that's for another time

        Squeak squeak = new Squeak(e, this);

        // TODO it works, now clean it up
        switch (e.getAction()){

            case MouseEvent.MOVE -> {
                findHotItem(root, squeak);
            }
            case MouseEvent.KEY -> {
                if(hotItem == null) return;
                setActiveItem(hotItem);
                for (Mousish mousish : hotItem.mousishes) {
                    mousish.mouseDown(controller, this, squeak.getModifiers());
                }
            }
            case MouseEvent.RELEASE -> {
                if (activeItem == hotItem && activeItem != preventUnclick){
                    for (Mousish mousish : activeItem.mousishes){
                        mousish.buttonPress(controller, squeak.getModifiers());
                    }
                }
                else if (!claw.isEmpty()) claw.release(); // if you're holding something drop it
                clearActiveItem();
                clearPreventUnclick();
            }
            case MouseEvent.WHEEL -> {
                Organelle target = captureAndBubble(root, squeak);
                if (target.wheelish == null) return;
                target.wheelish.wheel(controller, squeak.getCount());
            }
            case MouseEvent.DRAG -> {
                findHotItem(root, squeak);
                if (activeItem != null && claw.isEmpty()) {
                    Organelle target = activeItem;
                    claw.setDragOffset(squeak.getX() - target.x, squeak.getY() - target.y);
                    claw.startDrag(target.draggish, squeak.getX(), squeak.getY() + squeak.getLatitude());
                }
                else if (!claw.isEmpty()) claw.drag(squeak.getX(), squeak.getY()); // moving the mouse while holding something
            }
            case MouseEvent.EXIT -> clearHotItem();
        }
    }

    // Returns the deepest organelle under the mouse that accepts this kind of squeak
    public Organelle captureAndBubble(Organelle root, Squeak squeak){
        if (squeak.consumed) return null;
        if (!root.mouseOver(squeak.getX(), squeak.getY() + squeak.getLatitude())) return null;
        squeak.addLatitude(root.getLatitude());

        for (Organelle child : root.getChildren()){
            Organelle result = captureAndBubble(child, squeak);
            if (squeak.consumed) return result;
        }
        squeak.consume(root); // in here is where we check whether the organelle accepts this kind of squeak and make sure if it does we don't trigger additional squeaks up the chain.
        return root;
    }


    public void findHotItem(Organelle root, Squeak squeak){
        Organelle target = captureAndBubble(root, squeak);
        if (target != hotItem) setHotItem(target);
    }

    public void setHotItem(Organelle organelle){
        if (hotItem != null && hotItem != organelle){
            clearHotItem();
        }
        hotItem = organelle;
        if (hotItem != null) hotItem.setHot(true);
    }

    public void clearHotItem(){
        if (hotItem == null) return;
        hotItem.setHot(false);
        hotItem = null;
    }

    public void setActiveItem(Organelle organelle){
        if (activeItem != null && activeItem != organelle){
            clearActiveItem();
        }
        activeItem = organelle;
        activeItem.setHot(true);
    }

    public void clearActiveItem(){
        if (activeItem == null) return;
        activeItem.setActive(false);
        activeItem = null;
    }

    public void setPreventUnclick(Organelle organelle){
        preventUnclick = organelle;
    }

    public void clearPreventUnclick(){
        preventUnclick = null;
    }

    public boolean justClicked(Mousish mousish){
        if (activeItem == mousish) return true;
        else return false;
    }

    public void debug(PGraphics g, Squeak state){
        g.fill(255,0, 255, 200);
        g.textSize(24);
        g.text("mouse: " + state.getX() + ", " + state.getY() + '\n' +
                "hot item: " + hotItem + '\n' +
                "active item: " + activeItem + '\n' // +
                , 50, 50);
    }

}