import processing.core.PApplet;
import processing.core.PGraphics;

public class ScrollGrip extends Organelle {

    int inactiveColor = 0xff6C6C6C, hotColor = 130;

    public ScrollGrip(){
        updater = new OrganelleUpdater();
        drawer = new GripDrawer();
        clickish = new EmptyClickish();
        draggish = new ScrollGripDraggish();
    }

    @Override
    public void shift() {
        x = getParent().x;
        w = getParent().w;
    }

    int getColor(){
        if (hot || held) return hotColor;
        else return inactiveColor;
    }
}