import processing.core.PGraphics;

public class Vision extends Monad{

    float imageW;
    float imageH;

    VisionImage vi;
    Graffito vt;

    float cPillow;
    float bgAlpha;

    Clipping clip;
    boolean enabled;

    public Vision(Field parent){
       this.parent = parent;
       parent.children.add(this);
       vi = new VisionImage(this);
       enabled = false;
       bgAlpha = 220;
       cPillow = 20;
       setBounds(parent.x, parent.y, parent.w, parent.h);
    }

    @Override
    public void update(){
       setBounds(parent.x, parent.y, parent.w, parent.h);
    }

    @Override
    public void cascadeDraw(PGraphics g, float latitude){
        if (!enabled) return;
        super.cascadeDraw(g, latitude);
    }

    public void setImage(){
        if (clip == null) return;
        vi.setUp(clip.img);
    }

    public void setClipping(Clipping c){
       clip = c;
       setImage();
    }

    @Override
    public void draw(PGraphics g){
            g.fill(0, bgAlpha);
            g.rect(x, y, w, h);
            g.image(clip.img, x + w / 2 - imageW / 2, y + h / 2 - imageH / 2, imageW, imageH);
    }

    public void enable(){ enabled = true; }

    public void disable(){ enabled = false; }
}