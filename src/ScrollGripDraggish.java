import processing.core.PApplet;

public class ScrollGripDraggish implements Draggish{

    @Override
    public void grab(Conductor c, Organelle o, float mouseX, float mouseY) {}

    @Override
    public void drag(Conductor c, Organelle o, float mouseX, float mouseY) {
        o.y = PApplet.constrain(mouseY - o.dragY, o.getParent().y, o.getParent().h - o.h);
    }

    @Override
    public void release(Conductor c, Organelle o, float mouseX, float mouseY) {}
}
