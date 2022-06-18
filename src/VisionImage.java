import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class VisionImage extends Monad {

    PImage img;
    float xpillow = 50;
    float ypillow = 500;

    public VisionImage(Vision parent){
        this.parent = parent;
        parent.children.add(this);
    }

    @Override
    public void draw(PGraphics g){
        if(img == null) return;
        g.image(img, x, y, w, h);
    }

    public void setImage(PImage p){
        img = p;
    }

    public void findSize(float maxW, float maxH){
        float imgW = 0;
        float imgH = 0;

        if (img.height >= img.width || imgH > maxH){
            imgH = PApplet.constrain(img.height, 10, maxH);
            imgW = (imgH / img.height) * img.width;
        }
        if (img.width > img.height || imgW > maxW){
            imgW = PApplet.constrain(img.width, 10, maxW);
            imgH = (imgW / img.width) * img.height;
        }
        setSize(imgW, imgH);
    }

    public void findPos(float top, float bottom){
        setPos(parent.x + parent.w/2 - w/2, ((top + bottom) / 2) - h/2);
    }
}