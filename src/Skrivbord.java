import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PFont;
import java.util.ArrayList;

public class Skrivbord extends Organelle implements Mousish {

    Passage passage;
    PFont font;

    GapBuffer buffer;

    int margin = 50;
    int minW = 400;
    int maxW = 800;
    int minH =  50;

    int maxTextWidth;

    public Skrivbord(){
        font = tulpa.SOLE.getSkrivBordFont();
        maxTextWidth = font.getSize() * 3;
        addMousish(this);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
        setSize(parentW, parentH);
        setPos(parentX + parentW/2 - w/2, parentY);
    }

    @Override
    public void draw(PGraphics g){
        g.fill(49);
        g.noStroke();
        g.rect(x, y, w, h, 8);
        g.fill(223);
        g.textFont(font);
        if (buffer != null){
//            g.text(insertCursor(buffer), x + margin, y + margin, w - margin * 2, h - margin * 2);
            drawText(g, buffer.toString(), buffer.gapStart, x + margin, y + margin, w - margin * 2, h - margin * 2);
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

    public void type(char c){
        buffer.insert(c);
    }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {
        controller.changeMode(new SkrivbordContext(controller, this));
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