import processing.core.PGraphics;

public class ScrollerUpdater implements Updater{
    @Override
    public void performUpdate(PGraphics g, Conductor c, Organelle o) {
        o.shift();
        updateChildren(g, c, o);
    }

    @Override
    public void updateChildren(PGraphics g, Conductor c, Organelle o) {
        Scroller s = (Scroller)o;
        g.push();
        g.translate(0, -s.contents.latitude);
        s.contents.updater.performUpdate(g, c, s.contents);
        g.pop();
        s.rail.updater.performUpdate(g, c, s.rail);
        s.syncGrip();
    }
}
