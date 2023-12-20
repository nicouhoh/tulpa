import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Picture extends Organelle {

    PImage image;
    float margin = 50;

    @Override
    public void draw(PGraphics g){
        if (image == null) return;
        g.image(image, x, y, w, h);
    }

    public PVector sizeCalc(float imgW, float imgH, float maxW, float maxH){
        float ratio = PApplet.min(maxW / imgW, maxH / imgH);
        return new PVector(imgW*ratio, imgH * ratio);
    }

    public void setImage(PImage i){
        image = i;
    }

    public void noImage(){
        image = null;
    }

    public void setUp(PImage i){
        setImage(i);
        fitImage(i.width, i.height);
    }

    public PVector fitImage(float maxW, float maxH){
        if (image == null) return null;
        if (image.width > maxW || image.height > maxH) {
            return sizeCalc(image.width, image.height, maxW, maxH);
        }
        else return new PVector(image.width, image.height);
    }
}
