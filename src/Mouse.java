import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Mouse {

    Controller controller;
    ClawMachine claw;

    Organelle hotItem;
    Organelle activeItem;

    Organelle preventUnclick; // use in special cases to prevent a buttonPress afer mouseDown. see ctrl/cmd click on Thumbnails

    Squeak lastSqueak;

    Dropzone hoveredZone;

    public Mouse(Controller controller){
        this.controller = controller;
        claw = new ClawMachine(controller);
    }

    public void interpretSqueak(MouseEvent e, Organelle root){

        Squeak squeak = new Squeak(e);
        lastSqueak = squeak;

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
                    System.out.println("Clicked " + mousish);
                }
            }
            case MouseEvent.RELEASE -> {
                if (!claw.isEmpty()) {
                    if (hoveredZone != null){
                        System.out.println("Dropped " + claw.heldItem + " onto " + hoveredZone);
                        hoveredZone.drop(controller, claw.heldItem);
                    }
                    claw.release(); // if you're holding something drop it
                }
                else if (activeItem == hotItem && activeItem != preventUnclick){
                    for (Mousish mousish : activeItem.mousishes){
                        mousish.buttonPress(controller, squeak.getModifiers());
                    }
                }
                clearActiveItem();
                clearPreventUnclick();
            }
            case MouseEvent.WHEEL -> {
                Organelle target = captureAndBubble(root, squeak);
                if (target == null || target.wheelish == null) return;
                target.wheelish.wheel(controller, squeak.getCount());
            }
            case MouseEvent.DRAG -> {
                if (activeItem != null && claw.isEmpty()) {
                    findHotItem(root, squeak);
                    Organelle target = activeItem;
                    claw.setDragOffset(squeak.getX() - target.x, squeak.getY() - target.y + squeak.getLatitude());
                    claw.grab(target.draggish, squeak.getX(), squeak.getY() + squeak.getLatitude());
                }
                else if (!claw.isEmpty()){
                    // TODO show loud dropzones
                    Dropzone zone = captureDropzone(root, squeak);
                    if (zone != hoveredZone) {
                        setHoveredZone(zone);
                    }
                    claw.drag(squeak.getX(), squeak.getY()); // moving the mouse while holding something
                }
            }
            case MouseEvent.EXIT -> clearHotItem();
        }
    }

    // Returns the deepest organelle under the mouse that accepts this kind of squeak
    public Organelle captureAndBubble(Organelle root, Squeak squeak){
        if (squeak.consumed) return null;
        if (!mouseOver(root, squeak.getX(), squeak.getY() + squeak.getLatitude())){
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

    public void setHoveredZone(Dropzone zone){
            clearHoveredZone();
            hoveredZone = zone;
            if (zone != null){
                hoveredZone.setHovered(true);
                hoveredZone.onHovered();
            }
    }

    public void clearHoveredZone(){
        if (hoveredZone != null) hoveredZone.setHovered(false);
        hoveredZone = null;
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

    public void drawHeldItem(PGraphics g){
        if (claw.heldItem != null){
            Organelle o = (Organelle)claw.heldItem;
            o.mirage(g, lastSqueak.getX() - claw.dragOffset.x, lastSqueak.getY() - claw.dragOffset.y, 0, 0);
        }
    }

    public void debug(PGraphics g, Squeak state){
        g.fill(255,0, 255, 200);
        g.textSize(24);
        g.text("mouse: " + state.getX() + ", " + state.getY() + '\n' +
                "hot item: " + hotItem + '\n' +
                "active item: " + activeItem + '\n' // +
                , 50, 50    );
    }

}