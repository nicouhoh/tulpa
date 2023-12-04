import processing.core.PGraphics;

public class ThumbArrangeDropZone extends Dropzone {

    Thumbnail thumb;
    float lineX;

    public ThumbArrangeDropZone(Droppish droppish, float x, float y, float w, float h, float lineX){
        super(droppish, x, y, w, h);
        this.lineX = lineX;
        thumb = (Thumbnail)droppish;
    }

    @Override
    public void draw(PGraphics g){
        if (hovered){
            g.stroke(255, 192, 0);
            g.noFill();
            g.strokeWeight(2);
            g.line(lineX, y, lineX, y + h);
        }
    }

    @Override
    public void onHovered() {
        System.out.println("Hovering left edge of " + droppish);
    }

    @Override
    public void drop(Controller controller, Draggish draggish){
        if (draggish instanceof Thumbnail){
            controller.rearrangeThumbnails((Thumbnail)draggish, thumb);
        }
    }
}
