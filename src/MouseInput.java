import processing.event.MouseEvent;

public class MouseInput {

    private float scrollSpeed = 50;

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

    public void receiveMouseInput(MouseEvent e, Organelle organelle){
        if (e.getAction() == MouseEvent.CLICK)
            click(e, findClickish(e.getX(), e.getY(), organelle));
        if (e.getAction() == MouseEvent.WHEEL){
            wheel(e, findScrollish(e, organelle));
        }
    }

    public void wheel(MouseEvent e, Scrollish scroller){
        if (scroller == null) return;
        scroller.scroll(e.getCount());
    }

    public void click(MouseEvent e, Clickish clickish){
        if (clickish == null) return;
        clickish.click();
    }
}
