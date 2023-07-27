import processing.core.PApplet;
import processing.core.PGraphics;

public class Scroller {

    int id, gripID;
    float x, y, w = 20, h;
    float gripY, gripH;
    int barColor = 0xff1A1A1A, gripColor = 0xff6C6C6C, gripActiveColor = 225;
    int scrollSpeed = 100;
    float grabY = 0;

    public Scroller(int id, int gripID){
        this.id = id;
        this.gripID = gripID;
    }

    public void update(float sheetW, float sheetH, float sheetY, float latitude, float foot){
        x = sheetW;
        y = sheetY;
        h = sheetH;

        updateGrip(latitude, foot);
    }

    public void updateGrip(float latitude, float foot){
        setGripH(foot);
        setGripPos(latitude,foot);
    }

    public void draw(PGraphics g){
        g.noStroke();
        g.fill(barColor);
        g.rect(x, y, w, h);
        g.fill(gripColor);
        g.rect(x, gripY, w, gripH);
    }

    public void setGripH(float bottomOfScroll){
        gripH = PApplet.constrain(h / bottomOfScroll * h, 0, h);
    }

    public void setGripPos(float scrollValue, float bottomOfScroll){
        gripY = PApplet.constrain(scrollValue / bottomOfScroll * h, 0, h - gripH);
    }
}
