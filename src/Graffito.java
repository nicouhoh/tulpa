import processing.core.PGraphics;
public class Graffito extends Scrawler {

    float gW = 950;
    Vision v;
    Clipping c;

    public Graffito(Vision parent){
        this.parent = parent;
        parent.children.add(this);
        v = (Vision)parent;
        enabled = true;
        textSize = 40;
    }

    @Override
    public void drawText(PGraphics g){
        g.textSize(textSize);
        if(c.bodyText.length() > 0){
            g.fill(bodyTextColor);
            g.text(c.bodyText, x, y, w, h);
        }else{
            g.fill(blankTextColor);
            g.text(blankText, x, y, w, h);
        }
    }

    @Override
    public void type(char key) {
        if (key == '\b') {  // BACKSPACE
            if (c.bodyText.length() < 1) return;
            c.bodyText = c.bodyText.substring(0, c.bodyText.length() - 1);
        } else {
            c.bodyText += key;
        }
    }
}