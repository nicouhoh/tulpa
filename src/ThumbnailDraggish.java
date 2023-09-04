public class ThumbnailDraggish implements Draggish{

    @Override
    public void grab(Conductor c, Organelle o, float mouseX, float mouseY) {
        c.apparition.mirages.add(o);
    }

    @Override
    public void drag(Conductor c, Organelle o, float mouseX, float mouseY) {
        c.apparition.setPos(mouseX, mouseY);
    }

    @Override
    public void release(Conductor c, Organelle o, float mouseX, float mouseY) {
        c.apparition.mirages.remove(o);
    }
}
