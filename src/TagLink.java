import processing.core.PGraphics;
import processing.core.PFont;

public class TagLink extends Organelle implements Mousish{

    String tag;
    PFont font;

    public TagLink(String tag){
        this.tag = tag;
        this.font = tulpa.SOLE.getSkrivBordFont();
        addMousish(this);
    }

    @Override
    public void draw(PGraphics g){
        g.textFont(font);
        g.textSize(18);
        g.fill(64, 64, 64);
        g.text("#", x, y + g.textAscent());
        g.fill(192, 192, 192);
        g.text(tag, x + g.textWidth("#"), y + g.textAscent());
    }

    public void size(PGraphics g){
        g.textFont(tulpa.SOLE.getSkrivBordFont());
        g.textSize(18);
        float tagW = g.textWidth("#" + tag);
        float tagH = g.textAscent() + g.textDescent();
        setSize(tagW, tagH);
    }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {

    }

    @Override
    public void buttonPress(Controller controller, int mod) {
        controller.displaySearchResults(tag);
    }
}
