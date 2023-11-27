import processing.core.PGraphics;
import processing.event.MouseEvent;

public class Mouse {

    Controller controller;
    ClawMachine claw;

    Organelle hotItem;
    Organelle activeItem;

    public Mouse(Controller controller){
        this.controller = controller;
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
                    mousish.click(controller);
                    squeak.consume();
                }
            }
            case Squeak.WHEEL -> {
                if (organelle.wheelish == null) return;
                organelle.wheelish.wheel(controller, squeak.getCount());
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

    public void debug(PGraphics g, Squeak state){
        g.fill(255,0, 255, 200);
        g.textSize(24);
        g.text("mouse: " + state.getX() + ", " + state.getY() + '\n' +
                "hot item: " + hotItem + '\n' +
                "active item: " + activeItem + '\n' // +
                , 50, 50);
    }

}