import processing.core.PGraphics;

public class ScrollRail extends Organelle {

    int color = 0xff1A1A1A;

    public ScrollRail(){
        updater = new OrganelleUpdater();
        drawer = new ScrollRailDrawer();
    }

    @Override
    public void shift() {
        x = getParent().x + getParent().w - getParent().scrollW;
        y = getParent().y;
        w = getParent().scrollW;
        h = getParent().h;
    }
}
