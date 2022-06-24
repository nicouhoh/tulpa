import processing.core.PGraphics;
public class Graffito extends Scrawler {

    float gW = 950;
    Vision v;
    String bodyText;

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
        if(v.clip.bodyText.length() > 0){
            g.fill(bodyTextColor);
            g.text(v.clip.bodyText, x, y, w, h);
        }else{
            g.fill(blankTextColor);
            g.text(blankText, x, y, w, h);
        }
    }

    @Override
    public void type(char key, int kc) {
        if (key == '\b') {  // BACKSPACE
            if (v.clip.bodyText.length() < 1) return;
            v.clip.bodyText = v.clip.bodyText.substring(0, v.clip.bodyText.length() - 1);
        } else {
            v.clip.bodyText += key;
        }
    }
}