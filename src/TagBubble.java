import processing.core.PGraphics;

public class TagBubble extends Scrawler {

    Clipping c;

    public TagBubble(Spegel parent, float x, float y, float w, float h){
        this.parent = parent;
        parent.children.add(this);
        c = parent.clipping;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.blankText = "Type a #tag";
        this.textSize = h - h/4;
        this.text = "";
    }

    @Override
    public void type(char key){
        if (key == '\t') return;
        else if(key == '\b') {
            if(text.length() < 1) return;
            text = text.substring(0, text.length() - 1);
        }else {
            text += key;
        }
    }

    @Override
    public void drawText(PGraphics g) {
        g.textSize(textSize);
        if(text.length() > 0){
            g.fill(bodyTextColor);
            g.text(text, x, y, w, h);
        }else{
            g.fill(blankTextColor);
            g.text(text, x, y, w, h);
        }
    }

    @Override
    public void commit(Callosum cal) {
        c.bodyText += " " + text;
        cal.library.tagClipping(c, parseTags());
        text = "";
        disable();
    }
}
