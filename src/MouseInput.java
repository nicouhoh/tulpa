import processing.event.MouseEvent;


public class MouseInput {

    Draggish heldItem;
    Clickish hotItem;
    Clickish activeItem;


    private float scrollSpeed = 50;

//    public void receiveMouseInput(MouseEvent e, Organelle organelle){
////        System.out.println(e.getAction());
//        switch(e.getAction()){
//            case MouseEvent.KEY -> mouseDown(e, findClickish(e.getX(), e.getY(), organelle));
//            case MouseEvent.RELEASE -> {
//                if (heldItem == null) mouseUp(e, findClickish(e.getX(), e.getY(), organelle));
//            }
//            case MouseEvent.WHEEL -> wheel(e, findScrollish(e, organelle));
//            case MouseEvent.DRAG ->{
//                if (organelle instanceof Draggish && heldItem == null){
//                    heldItem = (Draggish)organelle;
//                }
//            }
//        }
//    }

    public void receiveClick(MouseEvent e){
       if (activeItem != null) {
           if (e.getAction() == MouseEvent.RELEASE) {
               if (activeItem == hotItem) {
                   activeItem.click();
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

    public boolean isActive(Clickish clickish){
        return clickish == activeItem;
    }

    public boolean isHot(Clickish clickish){
        return clickish == hotItem;
    }

    public void setActive(Clickish clickish){
        activeItem = clickish;
        Organelle o = (Organelle)clickish;
        o.active = true;
    }

    public void setHot(Clickish clickish){
        hotItem = clickish;
        Organelle o = (Organelle)clickish;
        o.hot = true;
    }

    public void clearHot(){
        Organelle o = (Organelle)hotItem;
        o.hot = false;
        hotItem = null;
    }

    public void clearActive(){
        Organelle o = (Organelle)activeItem;
        o.active = false;
        activeItem = null;
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

    public void wheel(MouseEvent e, Scrollish scrollish){
        if (scrollish == null) return;
        scrollish.scroll(e.getCount());
    }

    public void drag(MouseEvent e, Draggish draggish){
        if (draggish == null) return;
        draggish.drag();
    }

}
