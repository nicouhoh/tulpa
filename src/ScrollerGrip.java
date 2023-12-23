import processing.core.PGraphics;
import processing.core.PApplet;

public class ScrollerGrip extends Organelle implements Mousish, Draggish {

    Scroller scroller;
    int inactiveColor = 0xff6C6C6C, hotColor = 130;

    public ScrollerGrip(Scroller scroller){
        this.scroller = scroller;
        addMousish(this);
        addDraggish(this);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
        x = parentX;
        w = parentW;
    }

    @Override
    public void draw(PGraphics g){
        g.noStroke();
        g.fill(getColor());
        g.rect(x, y, w, h);
    }

    public void setGrip(float scrollerH, float contentH, float latitude){
        y = (scrollerH * latitude) / contentH;
        h = (scrollerH * scrollerH) / contentH;
    }

    @Override
    public void grab(Controller controller){
        System.out.println("Grabbed " + this);
    }

    @Override
    public void drag(Controller controller, float mouseX, float mouseY, float originX, float originY, float offsetX, float offsetY) {
//        System.out.println("Dragging " + this);
        scroller.moveGrip(mouseY - offsetY);
    }

    @Override
    public void release(Controller controller) {

    }

    public void drawCasper(PGraphics g, float dragX, float dragY, float offsetX, float offsetY) {}

    public int getColor(){
        if (hot) return hotColor;
        else return inactiveColor;
    }

    @Override
    public void mouseDown(Controller controller, Mouse mouse, int mod) {
        System.out.println("clicked " + this);
    }

    @Override
    public void buttonPress(Controller controller, int mod){}
}
