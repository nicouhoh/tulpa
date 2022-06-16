import processing.core.PApplet;
import processing.core.PGraphics;

public class ClippingView extends Monad{

    float imageW;
    float imageH;

    float cPillow;
    float bgAlpha;

    Clipping clip;
    boolean enabled;

    public ClippingView(Field parent){
       this.parent = parent;
       parent.children.add(this);
       enabled = false;
       bgAlpha = 220;
       cPillow = 20;
       setBounds(parent.x, parent.y, parent.w, parent.h);
    }

    @Override
    public void update(){
       setBounds(parent.x, parent.y, parent.w, parent.h);
    }

    public void setupImage(Clipping c){

        if (c.img.height >= c.img.width){
            imageH = PApplet.constrain(c.img.height, 10, h - cPillow);
            imageW = (imageH / c.img.height) * c.img.width;
        }

        if(c.img.width > c.img.height || imageW > w - cPillow * 2){

            imageW = PApplet.constrain(c.img.width, 10, w - cPillow);
            imageH = (imageW / c.img.width) * c.img.height;
        }
        clip = c;
    }

    @Override
    public void draw(PGraphics g){
        if(enabled) {
            System.out.println("Drawing Clipping View");
            g.fill(0, bgAlpha);
            g.rect(x, y, w, h);
            g.image(clip.img, parent.w / 2 - imageW / 2, parent.h / 2 - imageH / 2, imageW, imageH);
        }
    }

    public void enable(){ enabled = true; }

    public void disable(){ enabled = false; }
}
