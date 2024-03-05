import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Porthole extends Organelle {

    PImage image;

    public Porthole(){
        System.out.println("STOP");
    }

    public Porthole(PImage i){
        setImage(i);
    }

    @Override
    public void resize(Cell parentBounds){
        if (image == null) setBounds(0, 0, 0, 0);
        else setBounds(fitImageToRectangle(image, parentBounds));
    }

    @Override
    public void draw(PGraphics g){
        if (image == null) return;
        g.image(image, x, y, w, h);
    }

    public void setImage(PImage i){
        image = i;
    }

    public void noImage(){
        image = null;
    }

    private Cell fitImageToRectangle(PImage image, Cell cell){
        if (image.width > cell.w || image.height > cell.h) {
            PVector size = sizeCalc(image.width, image.height, cell);
            PVector position = new PVector(cell.w/2 - size.x/2, cell.h/2 - size.y/2);
            return new Cell(position.x, position.y, size.x, size.y);
        }
        else return new Cell(cell.x/2, cell.y/2, image.width, image.height);
    }

    public PVector sizeCalc(float imgW, float imgH, Cell cell){
        float ratio = PApplet.min(cell.w / imgW, cell.h / imgH);
        return new PVector(imgW*ratio, imgH * ratio);
    }
}