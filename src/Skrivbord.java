import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PFont;

public class Skrivbord extends Organelle implements Mousish {

    PFont font;

    String palimpsest;
    int palimpsestColor = 96;

    GapBuffer buffer;

    int margin = 50;
    int minW = 400;
    int maxW = 800;
    int minH =  50;

    int maxTextWidth;

    public Skrivbord(){
        font = tulpa.SOLE.getSkrivBordFont();
        maxTextWidth = font.getSize() * 3;
        buffer = new GapBuffer(50);
        addMousish(this);
    }

    public Skrivbord(String palimpsest){
        font = tulpa.SOLE.getSkrivBordFont();
        maxTextWidth = font.getSize() * 3;
        addMousish(this);
        this.palimpsest = palimpsest;
    }


    @Override
    public void draw(PGraphics g){
        g.fill(49);
        g.noStroke();
        g.rect(x, y, w, h, 8);
        g.textFont(font);
        float textX = x + margin;
        float textY = y + margin;
        float textW  = w - margin * 2;
        float textH = h - margin * 2;
        if (buffer != null && !buffer.isEmpty()){
            g.fill(223);
            drawText(g, buffer.toString(), buffer.gapStart, textX, textY, textW, textH);
        }
        else if (palimpsest != null){
            g.fill(palimpsestColor);
            drawText(g, palimpsest, 0, textX, textY, textW, textH);
        }
    }

    @Override
    public void setSize(float parentW, float parentH){
        w = PApplet.constrain(parentW, minW, maxW);
        h = parentH;
    }

    public void setBuffer(Passage passage){
        buffer = new GapBuffer(passage.text);
    }

    public void setBuffer(Clipping clipping){
        if (clipping.passage != null) buffer = new GapBuffer(clipping.passage.text);
        else buffer = new GapBuffer(50);
    }

    public void type(char c){
        buffer.insert(c);
    }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {
        controller.changeContext(new SkrivbordContext(controller, this));
    }

    @Override
    public void buttonPress(Controller controller, int mod){}

    public void drawText(PGraphics g, String string, int cursorPos, float textX, float textY, float textW, float textH){
        g.text(string, textX, textY, textW, textH);
    }

    public void drawCursor(PGraphics g, float cursorX, float cursorY){
        // TODO is it insane to calculate this constantly
        g.stroke(128);
        g.strokeWeight(2);
        g.line(cursorX, cursorY - font.ascent() * font.getSize(), cursorX, cursorY + font.descent() * font.getSize());
        g.noStroke();
    }
}