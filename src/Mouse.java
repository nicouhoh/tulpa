import processing.core.PApplet;
import processing.core.PGraphics;
import processing.event.MouseEvent;
import processing.core.PVector;
import java.util.ArrayList;

public class Mouse {

    ArrayList<Palpable> palps = new ArrayList<Palpable>();

    Katla katla;

    Organelle hotItem;
    Organelle activeItem;

    float dragStickiness = 10;

    int scrollSpeed = 50;

    public Mouse(){
        katla = new Katla();
    }

    public void receiveMouseySqueak(MouseEvent e, Organelle root){
        switch (e.getAction()){

            case MouseEvent.KEY -> {
                // set our drag origin right here in case we grab something
                Squeak squeak = new Squeak(e, this);
                Organelle cheese = captureAndBubble(root, squeak);
                palpateOrganelle(cheese, squeak);
            }
            case MouseEvent.DRAG -> {
                if (katla.heldItem == null){
                    root.captureAndBubble(new Squeak(e, this, katla));
                } else {

                }
            }

//            case MouseEvent.DRAG -> {
//                if (heldItem != null){
//                    heldItem.drag();
//                }
//                else if (PApplet.dist(dragOrigin.x, dragOrigin.y, e.getX(), e.getY()) > dragStickiness){
//                    setHeldItem(draggish);
//                }
//            }

//            case MouseEvent.RELEASE -> {
//                clearDragOrigin();
//                // if youre holding something drop it
//            }

            case MouseEvent.WHEEL -> {
                root.captureAndBubble(new Squeak(e, this));
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
            if (result != null) return result;
            if (squeak.consumed) return null;
        }
        squeak.consume();
        return root;
    }

    public void palpateOrganelle(Organelle organelle, Squeak state){ // TODO could we do this stuff better by subclassing the MouseState?
        switch (state.getAction()){
            case Squeak.KEY -> {
                for (Mousish mousish : organelle.mousishes) {
                    mousish.click();
                }
            }
            case Squeak.WHEEL -> {
                if (organelle.wheelish == null) return;
                organelle.wheelish.wheel(state.getCount());
            }
            case Squeak.DRAG -> {
                if (organelle.draggish == null) return;
                katla.handleDrag(organelle.draggish, state.getX(), state.getY());
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