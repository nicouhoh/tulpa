import processing.core.PGraphics;
import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.core.PVector;
import java.util.ArrayList;

public class Mouse {

    ArrayList<Palpable> palps = new ArrayList<Palpable>();

//    Draggish heldItem;
    Organelle hotItem;
    Organelle activeItem;

    float dragStickiness = 10;

    int scrollSpeed = 50;

    PVector dragOrigin = new PVector(0, 0);
    PVector grabOffset = new PVector(0, 0);

//    public void palpate(MouseState state, Organelle root){
//        switch(state.getAction()){
//            case MouseEvent.MOVE, MouseEvent.DRAG -> notifyMove(state);//updateMouse(root, state);
//            case MouseEvent.KEY -> mouseDown(state);
////            case MouseEvent.WHEEL -> receiveWheel(root, state);
////            case MouseEvent.RELEASE -> mouseUp(state);
//        }
//    }

    public void palpate(MouseState state, Organelle root){
        root.captureAndBubble(state);
    }


    public void setActiveItem(Palpable palp){
        activeItem = (Organelle)palp;
    }

//    public void updateMouse(Organelle root, MouseState state){
//        updateHotItem(root.pinpoint(state, Palpable.class));
//        if (heldItem != null){
//            heldItem.drag(state.getX(), state.getY(), grabOffset.x, grabOffset.y);
//        } else if (activeItem instanceof Draggish) checkForGrab(state);
//    }

    public void updateHotItem(Organelle item){
        if (hotItem != item) clearHotItem();
        if (item == null) return;
        setHotItem(item);
    }

    public void setHotItem(Organelle item){
        hotItem = item;
        hotItem.hot = true;
    }

    public void clearHotItem(){
        if (hotItem == null) return;
        hotItem.hot = false;
        hotItem = null;
    }

    public void mouseDown(MouseState state){
        setDragOrigin(state.getX(), state.getY());
        if (hotItem != null) setActiveItem(hotItem);
    }

//    public void mouseUp(MouseState state){
//        if (heldItem != null) clearHeldItem();
//        if (activeItem != null) {
//            if (activeItem == hotItem){
//                System.out.println("Pressed " + activeItem + " like a button");
//            }
//            clearActiveItem();
//        }
//    }

    public void setActiveItem(Organelle o){
        activeItem = o;
        activeItem.active = true;
    }

    public void clearActiveItem(){
        activeItem.active = false;
        activeItem = null;
    }

//    public void checkForGrab(MouseState state){
//        if (PApplet.dist(state.getX(), state.getY(), getDragOrigin().x, getDragOrigin().y ) > dragStickiness){
//            grabItem(state);
//        }
//    }

//    public void grabItem(MouseState state) {
//        if (activeItem == null) return;
//        grabOffset = new PVector(getDragOrigin().x - activeItem.x, getDragOrigin().y - activeItem.y);
//        setHeldItem((Draggish)activeItem);
//        activeItem.held = true;
//        heldItem.grab();
//    }

    public void debug(PGraphics g, MouseState state){
        g.fill(255,0, 255, 200);
        g.textSize(24);
        g.text("mouse: " + state.getX() + ", " + state.getY() + '\n' +
                "hot item: " + hotItem + '\n' +
                "active item: " + activeItem + '\n' // +
//                "held item: " + getHeldItem() + ", offset: " + getGrabOffset().x + ", " + getGrabOffset().y
                , 50, 50);
//        System.out.println("held item: " + heldItem + ", offset: " + grabOffset.x + ", " + grabOffset.y);

    }

//    public void receiveWheel(Organelle organelle, MouseState state){
//        Organelle o = organelle.pinpoint(state, Wheelish.class);
//        if (o == null) return;
//        ((Wheelish)o).wheel(state.getCount());
//    }
//
//    public void setHeldItem(Draggish item){
//        heldItem = item;
//        ((Organelle)heldItem).held = true;
//    }
//
//    public void setHeldItem(Organelle organelle){
//        if (organelle instanceof Draggish){
//            heldItem = (Draggish)organelle;
//        }
//    }
//
//    public Draggish getHeldItem(){
//        return heldItem;
//    }
//
//    public void clearHeldItem(){
//        ((Organelle)heldItem).held = false;
//        heldItem = null;
//        dragOrigin = null;
//    }
//
//    public Organelle getHeldOrganelle(){
//        return (Organelle)heldItem;
//    }

    public void setDragOrigin(float x, float y){
        dragOrigin = new PVector(x, y);
    }

    public PVector getDragOrigin(){
        return dragOrigin;
    }

    public void setGrabOffset(float x, float y){
        grabOffset = new PVector(x, y);
    }

    public PVector getGrabOffset(){
        return grabOffset;
    }
//    public void drawHeldItem(PGraphics g, MouseState state){
//        g.tint(255, 190);
//        if (heldItem != null){
//            heldItem.drawCasper(g, mousePos.x, mousePos.y, grabOffset.x, grabOffset.y);
//        }
//        g.tint(255, 255);
//    }

    public void registerPalp(Palpable palp){
        palps.add(palp);
    }

    public void registerPalp(Organelle palp){
        palps.add((Palpable)palp);
    }

    public void removePalp(Palpable palp){
        palps.remove(palp);
    }

    public void shoutToPalps(){

    }

}