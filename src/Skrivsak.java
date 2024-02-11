import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PConstants;

public class Skrivsak extends Organelle implements Mousish{

    PFont font;

    GapBuffer buffer;

    String palimpsest;
    Twombly scribe;

    int paperColor = 49;
    int paperAlpha = 255;
    int textColor = 223;
    int palimpsestColor = 96;
    int textAlpha = 255;

    int margin = 10;

    boolean focused;

    public Skrivsak(){
        scribe = new Twombly();
    }

    @Override
    public void draw(PGraphics g){
        g.fill(paperColor, paperAlpha);
        g.noStroke();
        g.rect(x, y, w, h, 8);
        g.textFont(font);
        float textX = x + margin;
        float textY = y + margin;
        float textW  = w - margin * 2;
        float textH = h - margin * 2;
        if (buffer != null && !buffer.isEmpty()){
            g.fill(textColor, textAlpha);
            int cursorPos = focused ? buffer.gapStart : -1;
            drawText(g, buffer.toString(), cursorPos, textX, textY, textW, textH);
        }
        else if (palimpsest != null){
            g.fill(palimpsestColor, textAlpha);
            int cursorPos = focused ? 0 : -1;
            drawText(g, palimpsest, cursorPos, textX, textY, textW, textH);
        }
    }

    public void drawText(PGraphics g, String string, int cursorPos, float textX, float textY, float textW, float textH){
        scribe.text(g, string, cursorPos, textX, textY, textW, textH);
    }

    public void drawText(PGraphics g, String string, int cursorPos, Cell cell){
        scribe.text(g, string, cursorPos, cell.x, cell.y, cell.w, cell.h);
    }

    public void setBuffer(Passage passage){
        buffer = new GapBuffer(passage.text);
    }

    public void setBuffer(String string){
        buffer = new GapBuffer(string);
    }

    public void setBuffer(Clipping clipping){
        if (clipping.passage != null) buffer = new GapBuffer(clipping.passage.text);
        else buffer = new GapBuffer(50);
    }

    public void setFont(PFont font){
        this.font = font;
    }

    public void setPaperColor(int grayscale){
        paperColor = grayscale;
    }

    public void setTextColor(int grayscale){
        textColor = grayscale;
    }

    public void setMargin(int m){
        margin = m;
    }

    public void type(Controller controller, char c){
        switch (c) {
            case PConstants.ENTER -> enter(controller);
            case PConstants.ESC -> esc(controller);
            default -> buffer.insert(c);
        }
    }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {
        controller.focusSkrivsak(this);
    }

    @Override
    public void buttonPress(Controller controller, int mod) {

    }

    public void enter(Controller controller){
        buffer.insert('\n');
    }

    public void esc(Controller controller){

    }

    public void drawCursor(PGraphics g, float cursorX, float cursorY){
        // TODO
    }

    public void setFocused(boolean focus){
        focused = focus;
    }

    public void clear(){
        buffer.clear();
    }
}
