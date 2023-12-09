import processing.core.PGraphics;

public class ThumbnailDropzone extends Dropzone {

    Thumbnail thumb;

    public ThumbnailDropzone(Droppish droppish, Thumbnail thumb, float allowance){
        super(droppish, thumb.x + allowance, thumb.y, thumb.w - allowance * 2, thumb.h);
        this.thumb = thumb;
    }

    @Override
    public void draw(PGraphics g){
        if (hovered){
            g.stroke(192, 192, 255);
            g.noFill();
            g.strokeWeight(2);
            g.rect(thumb.x - 2, thumb.y - 2, thumb.w + 4, thumb.h + 4);
        }
    }

    public void onHovered(){ System.out.println("Hovering " + thumb);}

    @Override
    public void drop(Controller controller, Draggish draggish){
    }
}
