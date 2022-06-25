import processing.core.PGraphics;

public class Vision extends Monad{

    float imageW;
    float imageH;

    VisionImage vi;
    Graffito graffito;

    float pillow = 20;
    float minGraffitoH = 100;
    float bgAlpha = 230;

    Clipping clip;

    public Vision(Field parent){
       this.parent = parent;
       parent.children.add(this);
       vi = new VisionImage(this);
       graffito = new Graffito(this);
       setBounds(parent.x, parent.y, parent.w, parent.h);
       enabled = false;
    }

    @Override
    public void update(){
       setBounds(parent.x, parent.y, parent.w, parent.h);
       setUpVision();
    }

    public void setClipping(Clipping c){
       clip = c;
    }

    // TODO Maybe blur everything underneath
    @Override
    public void draw(PGraphics g){
            g.fill(0, bgAlpha);
            g.rect(x, y, w, h);
    }

    public void setUpVision(){
        vi.setImage(clip.img);
        float vy = pillow;
        vi.findSize(w - pillow*2, y + h - minGraffitoH - pillow*3);
        vi.findPos(pillow, y + h - minGraffitoH - pillow*2);
        graffito.c = clip;
        graffito.setBounds(x + pillow + w/2 - graffito.gW/2, y + h - pillow - minGraffitoH, graffito.gW, minGraffitoH);
    }

    public void enable(){
        enabled = true;
        cascadeUpdate();
    }

    public void disable(){ enabled = false; }
}