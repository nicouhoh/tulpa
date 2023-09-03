import processing.event.MouseEvent;

public class MouseInput {

    Conductor conductor;
    Draggish heldItem;
    Clickish hotItem;
    Clickish activeItem;


    public MouseInput(){
    }

    private final float scrollSpeed = 50;

    public void receiveMouseInput(MouseEvent e, Organelle visipalp){
        switch(e.getAction()) {
            case MouseEvent.KEY, MouseEvent.RELEASE -> receiveClick(e);
            case MouseEvent.WHEEL -> receiveScroll(e, visipalp);
            case MouseEvent.DRAG -> receiveDrag(e,visipalp);
                default -> receiveMouseStatus(e, visipalp);
        }
    }

    public void receiveClick(MouseEvent e){
        if (heldItem != null && e.getAction() == MouseEvent.RELEASE){
            heldItem.release();
            clearHeld();
        }
        if (activeItem != null) {
            if (e.getAction() == MouseEvent.RELEASE) {
                if (isHot(activeItem)) {
                    activeItem.click(conductor, e.getModifiers());
                }
                clearActive();
            }
       }
       else if (hotItem != null) {
           if (e.getAction() == MouseEvent.KEY) {
               setActive(hotItem);
               activeItem.active();
           }
       }
    }

    public void receiveMouseStatus(MouseEvent e, Organelle target){
        Clickish clickish = findClickish(e.getX(), e.getY(), target);
        if (clickish != null){
            setHot(clickish);
            clickish.hot();
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

    public void receiveDrag(MouseEvent e, Organelle target){
        if (heldItem == null) {
            if (activeItem != null) {
                if (activeItem instanceof Draggish) {
                    Draggish draggish = (Draggish)activeItem;
                    clearActive();
                    setHeld(draggish, e);
                    draggish.drag(e.getX(), e.getY());
                    conductor.apparition.setPos(e.getX(), e.getY());
                }
            }
        } else{
            heldItem.drag(e.getX(), e.getY());
            conductor.apparition.setPos(e.getX(), e.getY());
        }
    }


    public boolean isActive(Clickish clickish){
        return clickish == activeItem;
    }

    public boolean isHot(Clickish clickish){
        return clickish == hotItem;
    }

    public void setHot(Clickish clickish){
        if (hotItem != null) clearHot();
        hotItem = clickish;
        Organelle o = (Organelle)clickish;
        o.hot = true;
    }

    public void clearHot(){
        Organelle o = (Organelle)hotItem;
        o.hot = false;
        hotItem = null;
    }

    public void setActive(Clickish clickish){
        activeItem = clickish;
        Organelle o = (Organelle)clickish;
        o.active = true;
    }

    public void clearActive(){
        Organelle o = (Organelle)activeItem;
        o.active = false;
        activeItem = null;
    }

    public void setHeld(Draggish draggish, MouseEvent e){
        if (heldItem != null) clearHeld();
        Organelle o = (Organelle)draggish;
        o.held = true;
        o.dragX = e.getX() - o.x;
        o.dragY = e.getY() - o.y;
        heldItem = draggish;
        conductor.apparition.mirages.add(o);
    }

    public void clearHeld(){
        Organelle o = (Organelle)heldItem;
        o.held = false;
        heldItem = null;
        conductor.apparition.mirages.remove(o);
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

    public Clickish findClickish(float mouseX, float mouseY, Organelle target){
        float adjustedY = mouseY + target.latitude;
        Clickish deepestMatching = null;

        for (Organelle child : target.getChildren()) {
            if (child instanceof Clickish && theFingerPointsAt(mouseX, adjustedY, child)){
                deepestMatching = (Clickish) child;
            }

            Clickish childResult = findClickish(mouseX, adjustedY, child);
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
