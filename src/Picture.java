import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Picture extends Organelle {

    PImage image;
    float margin = 50;

    @Override
    public void update(float parentX, float parentY, float parentW, float parentH){
        resize(parentW, parentH);
        setPos(parentW/2 - w/2, parentH/2 - h/2);
    }

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

    public void resize(float parentW, float parentH){
        if (image == null){
            x = 0; y = 0; w = 0; h = 0;
        }else {
            float maxW = parentW - margin * 2;
            float maxH = parentH - margin * 2;
            if (image.width > maxW || image.height > maxH) {
                setSize(sizeCalc(image.width, image.height, maxW, maxH));
            } else setSize(image.width, image.height);
        }
    }
}
