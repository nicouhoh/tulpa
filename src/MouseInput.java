import processing.event.MouseEvent;

public class MouseInput {

    Conductor conductor;
    Organelle heldItem;
    Organelle hotItem;
    Organelle activeItem;


    public MouseInput(){
    }

    private final float scrollSpeed = 50;

    public void receiveMouseInput(MouseEvent e, Organelle visipalp){
        switch(e.getAction()) {
            case MouseEvent.KEY -> receiveClick(e);
            case MouseEvent.RELEASE -> {
                if (heldItem == null) receiveClick(e);
                else receiveDrop(e, visipalp);
            }
            case MouseEvent.WHEEL -> receiveScroll(e, visipalp);
            case MouseEvent.DRAG -> receiveDrag(e);
            default -> receiveMouseStatus(e, visipalp);
        }
    }

    private void receiveDrop(MouseEvent e, Organelle visipalp) {
        if (heldItem == null) return;
        heldItem.draggish.release(conductor, heldItem, e.getX(), e.getY());
        clearHeld();
    }

    public void receiveClick(MouseEvent e){
        if (activeItem != null) {
            if (e.getAction() == MouseEvent.RELEASE) {
                if (isHot(activeItem)) {
                    activeItem.clickish.click(conductor, activeItem, e.getModifiers());
                }
                clearActive();
            }
       }
       else if (hotItem != null) {
           if (e.getAction() == MouseEvent.KEY) {
               setActive(hotItem);
               activeItem.clickish.active();
           }
       }
    }

    public void receiveMouseStatus(MouseEvent e, Organelle target){
        Organelle organelle = findClickish(e.getX(), e.getY(), target);
        if (organelle != null){
            setHot(organelle);
            organelle.clickish.hot();
        }else if (hotItem != null) {
            clearHot();
        }
    }

    public void receiveScroll(MouseEvent e, Organelle target){
        Scrollish scrollish = findScrollish(e, target);
        if (scrollish != null){
            scrollish.scroll(e.getCount());
        }
    }

    public void receiveDrag(MouseEvent e){
        if (heldItem != null){
            heldItem.draggish.drag(conductor, heldItem, e.getX(), e.getY());
        }
        else {
            if (activeItem != null) {
                if (activeItem.draggish != null) {
                    setHeld(activeItem, e);
                    clearActive();
                    heldItem.draggish.drag(conductor, heldItem, e.getX(), e.getY());
                }
            }
        }
    }


    public boolean isActive(Organelle organelle){
        return organelle == activeItem;
    }

    public boolean isHot(Organelle organelle){
        return organelle == hotItem;
    }

    public void setHot(Organelle organelle){
        if (hotItem != null) clearHot();
        hotItem = organelle;
        organelle.hot = true;
    }

    public void clearHot(){
        Organelle o = (Organelle)hotItem;
        o.hot = false;
        hotItem = null;
    }

    public void setActive(Organelle organelle){
        if (organelle.clickish != null);
        activeItem = organelle;
        organelle.active = true;
    }

    public void clearActive(){
        Organelle o = (Organelle)activeItem;
        o.active = false;
        activeItem = null;
    }

    public void setHeld(Organelle organelle, MouseEvent e){
        if (heldItem != null) clearHeld();
        if (organelle.draggish == null) return;
        heldItem = organelle;
        organelle.held = true;
        organelle.dragX = e.getX() - organelle.x;
        organelle.dragY = e.getY() - organelle.y;
        organelle.draggish.grab(conductor, organelle, e.getX(), e.getY());
    }

    public void clearHeld(){
        heldItem.held = false;
        heldItem = null;
    }

    public Organelle digDeeper(MouseEvent e, Organelle organelle){
        // recognize at whom the finger points
        for (Organelle child : organelle.getChildren()){
            if (theFingerPointsAt(e, child)) return digDeeper(e, child);
        }
        return organelle;
    }

    public Scrollish findScrollish(MouseEvent e, Organelle target){
        Scrollish deepestMatching = null;

        for (Organelle child : target.getChildren()) {
            if (child instanceof Scrollish && theFingerPointsAt(e, child)){
                deepestMatching = (Scrollish) child;
            }

            Scrollish childResult = findScrollish(e, child);
            if (childResult != null) {
                deepestMatching = childResult;
            }
        }

        return deepestMatching;
    }

    public Organelle findClickish(float mouseX, float mouseY, Organelle target){
        float adjustedY = mouseY + target.latitude;
        Organelle deepestMatching = null;

        for (Organelle child : target.getChildren()) {
            if (child.clickish != null && theFingerPointsAt(mouseX, adjustedY, child)){
                deepestMatching = child;
            }

            Organelle childResult = findClickish(mouseX, adjustedY, child);
            if (childResult != null) {
                deepestMatching = childResult;
            }
        }
        return deepestMatching;
    }

    public Draggish findDraggish(float mouseX, float mouseY, Organelle target){
        float adjustedY = mouseY + target.latitude;
        Draggish deepestMatching = null;

        for (Organelle child : target.getChildren()) {
            if (child instanceof Draggish && theFingerPointsAt(mouseX, adjustedY, child)){
                deepestMatching = (Draggish) child;
            }

            Draggish childResult = findDraggish(mouseX, adjustedY, child);
            if (childResult != null) {
                deepestMatching = childResult;
            }
        }

        return deepestMatching;
    }

    public boolean theFingerPointsAt(MouseEvent e, Organelle organelle){
        return theFingerPointsAt(e.getX(), e.getY(), organelle);
    }

    public boolean theFingerPointsAt(float x, float y, Organelle organelle){
        if (x < organelle.x || x > organelle.x + organelle.w || y < organelle.y || y > organelle.y + organelle.h){
            return false;
        } else {
            return true;
        }
    }

    public void debugFinger(MouseEvent e, Organelle organelle){
        System.out.println(digDeeper(e, organelle));
    }
}
