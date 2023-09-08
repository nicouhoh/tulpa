public class ThumbnailDraggish implements Draggish{

    @Override
    public void grab(Conductor c, Organelle o, float mouseX, float mouseY) {
        Thumbnail thumbnail = (Thumbnail)o;
        if (!thumbnail.clipping.isSelected) c.library.select(thumbnail.clipping);
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
