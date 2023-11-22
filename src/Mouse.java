import processing.core.PGraphics;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class Mouse {

    ArrayList<Palpable> palps = new ArrayList<Palpable>();

    ClawMachine claw;

    Organelle hotItem;
    Organelle activeItem;

    float dragStickiness = 10;

    int scrollSpeed = 50;

    public Mouse(){
        claw = new ClawMachine();
    }

    public void receiveMouseySqueak(MouseEvent e, Organelle root){

        // TODO I think I could do all this better by subclassing the Squeak, but that's for another time

        Squeak squeak = new Squeak(e, this);

        // TODO it works, now clean it up
        switch (e.getAction()){

            case MouseEvent.KEY-> {
                captureAndBubble(root, squeak);
            }

            case MouseEvent.WHEEL -> captureAndBubble(root, squeak);

            case MouseEvent.DRAG -> {
                if (!claw.grabLock && claw.isEmpty()) captureAndBubble(root, squeak);
                else if (!claw.isEmpty()) claw.drag(squeak.getX(), squeak.getY()); // moving the mouse while holding something
                claw.grabLock = true;
            }
            case MouseEvent.RELEASE -> { // if you're holding something drop it
                if (!claw.isEmpty()) claw.release();
                claw.grabLock = false;
            }
        }
    }

//    public boolean checkDrag(MouseEvent e){
//        return PApplet.dist(dragOrigin.x, dragOrigin.y, e.getX(), e.getY()) > dragStickiness;
//    }

    public Organelle captureAndBubble(Organelle root, Squeak squeak){
        if (squeak.consumed) return null;
        if (!root.mouseOver(squeak.getX(), squeak.getY() + squeak.getLatitude())) return null;
        squeak.addLatitude(root.getLatitude());

        for (Organelle child : root.getChildren()){
            Organelle result = captureAndBubble(child, squeak);
            if (squeak.consumed) return result;
        }
        palpate(root, squeak);
        return root;
    }

    public void palpate(Organelle organelle, Squeak squeak){

        // TODO I think I could do this better by subclassing the Squeak

        // TODO another question: should I just pass the entire event to the component so it can just use whatever info it needs?

        switch (squeak.getAction()){
            case Squeak.KEY -> {
                for (Mousish mousish : organelle.mousishes) {
                    mousish.click();
                    squeak.consume();
                }
            }
            case Squeak.WHEEL -> {
                if (organelle.wheelish == null) return;
                organelle.wheelish.wheel(squeak.getCount());
                squeak.consume();
            }
            case Squeak.DRAG -> {
                if (organelle.draggish == null) return;
                claw.setDragOffset(squeak.getX() - organelle.x, squeak.getY() - organelle.y);
                claw.startDrag(organelle.draggish, squeak.getX(), squeak.getY() + squeak.getLatitude());
                squeak.consume();
            }
        }
    }

    public void setActiveItem(Palpable palp){
        activeItem = (Organelle)palp;
    }

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


//    public void mouseDown(Squeak state){
//        setDragOrigin(state.getX(), state.getY());
//        if (hotItem != null) setActiveItem(hotItem);
//    }

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

    // TODO ---------------- NEXT STEP HERE: Figure out dragging -----------------------

//    public void grabItem(Squeak squeak) {
//        if (activeItem == null) return;
//        grabOffset = new PVector(getDragOrigin().x - activeItem.x, getDragOrigin().y - activeItem.y);
//        setHeldItem((Draggish)activeItem);
//        activeItem.held = true;
//        heldItem.grab();
//    }

    public void debug(PGraphics g, Squeak state){
        g.fill(255,0, 255, 200);
        g.textSize(24);
        g.text("mouse: " + state.getX() + ", " + state.getY() + '\n' +
                "hot item: " + hotItem + '\n' +
                "active item: " + activeItem + '\n' // +
//                "held item: " + getHeldItem() + ", offset: " + getGrabOffset().x + ", " + getGrabOffset().y
                , 50, 50);
//        System.out.println("held item: " + heldItem + ", offset: " + grabOffset.x + ", " + grabOffset.y);

    }

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

//    public void setDragOrigin(float x, float y){
//        dragOrigin = new PVector(x, y);
//    }
//
//    public PVector getDragOrigin(){
//        return dragOrigin;
//    }
//
//    public void clearDragOrigin(){
//        dragOrigin = null;
//    }
//
//    public void setGrabOffset(float x, float y){
//        grabOffset = new PVector(x, y);
//    }
//
//    public PVector getGrabOffset(){
//        return grabOffset;
//    }
////    public void drawHeldItem(PGraphics g, MouseState state){
////        g.tint(255, 190);
////        if (heldItem != null){
////            heldItem.drawCasper(g, mousePos.x, mousePos.y, grabOffset.x, grabOffset.y);
////        }
////        g.tint(255, 255);
////    }

}