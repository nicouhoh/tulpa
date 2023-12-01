import processing.core.PVector;

public class ClawMachine {

    // Drag-and-drop manager.

    // TODO Ghost image

    Controller controller;
    Draggish heldItem;
    PVector dragOrigin = new PVector(0, 0);
    PVector dragOffset = new PVector(0, 0);

    public ClawMachine(Controller controller){
        this.controller = controller;
    }

    public void grab(Draggish draggish, float originX, float originY){
        setHeldItem(draggish);
        setDragOrigin(originX, originY);
        if (heldItem != null) heldItem.grab(controller);
    }

    public void drag(float mouseX, float mouseY){
        heldItem.drag(controller, mouseX, mouseY, dragOrigin.x, dragOrigin.y, dragOffset.x, dragOffset.y);
    }

    public void release(){
        heldItem.release(controller);
        clearHeldItem();
        clearDragOrigin();
        clearDragOffset();
    }

    public void handleDrop(int mouseX, int mouseY){}

    public void setHeldItem(Draggish draggish){
        heldItem = draggish;
    }

    public void clearHeldItem(){
        heldItem = null;
    }

    public void setDragOrigin(float x, float y){
        dragOrigin = new PVector(x, y);
    }

    public void clearDragOrigin(){ dragOrigin = null;}

    public boolean isEmpty(){
        return heldItem == null;
    }

    public void setDragOffset(float x, float y){
        dragOffset = new PVector(x, y);
    }

    public void clearDragOffset(){
        dragOrigin = null;
    }
}