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

    public void draw(PGraphics g){
        if(img == null) return;
        g.image(img, x, y, w, h);
    }

    public void update(){
        setBounds(parent.x + xpillow, parent.y + ypillow, parent.w, parent.h);
        System.out.println("VisionImage: " + x + ", " + y);
    }

    public void setUp(PImage p){
        setImage(p);
        findSize();
        findPos();
    }

    public void setImage(PImage p){
        img = p;
    }

    public void findSize(){
        float imgW = 0;
        float imgH = 0;
        if (img.height >= img.width || imgH > parent.h - xpillow * 2){
            imgH = PApplet.constrain(img.height, 10, parent.h - xpillow);
            imgW = (imgH / img.height) * img.width;
        }
        else if (img.width > img.height || imgW > parent.w - xpillow * 2){
            imgW = PApplet.constrain(img.width, 10, parent.w - xpillow);
            imgH = (imgW / img.width) * img.height;
        }
        setSize(imgW, imgH);
    }

    public void findPos(){
        setPos(parent.x + parent.w/2 - w/2, parent.y + parent.h / 2 - h / 2);
    }
}