import processing.core.PGraphics;

public class Nothing extends Organelle {

    int bgColor;
    int bgAlpha;

    public Nothing(int bgColor, int bgAlpha){
        x = 0;
        y = 0;
        this.bgColor = bgColor;
        this.bgAlpha = bgAlpha;
    }

    @Override
    public void draw(PGraphics g){
        g.background(bgColor, bgAlpha);
    }
}
