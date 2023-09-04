import processing.core.PGraphics;

public class ThumbnailDrawer implements DrawBehavior{

    @Override
    public void draw(PGraphics g, Organelle o, float x, float y) {
        if (o.y + o.h < o.getParent().y || o.y > o.getParent().y + o.getParent().h) return;
        Thumbnail t = (Thumbnail)o;
        if (t.clipping.img != null)
            g.image(t.clipping.img, x, y, t.w, t.h);
        if (t.clipping.isSelected) t.drawSelect(g);
    }
}
