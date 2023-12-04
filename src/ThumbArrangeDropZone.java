import processing.core.PGraphics;

public class ThumbArrangeDropZone extends Dropzone {

    float lineX;

    public ThumbArrangeDropZone(Droppish droppish, float x, float y, float w, float h, float lineX){
        super(droppish, x, y, w, h);
        this.lineX = lineX;
    }

    @Override
    public void draw(PGraphics g){
        if (hovered){
            g.stroke(192, 160, 0);
            g.noFill();
            g.strokeWeight(4);
            g.line(lineX, y, lineX, y + h);
        }
    }

    @Override
    public void onHovered() {
        System.out.println("Hovering left edge of " + droppish);
    }
}
