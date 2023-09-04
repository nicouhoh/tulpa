import processing.core.PGraphics;

public class OrganelleUpdater implements Updater{

    @Override
    public void performUpdate(PGraphics g, Conductor c, Organelle o){
        o.shift();
        o.drawer.draw(g, o, o.x, o.y);
        updateChildren(g, c, o);
    }

    @Override
    public void updateChildren(PGraphics g, Conductor c, Organelle o) {
        if (o.getChildren() != null){
            for (Organelle child : o.getChildren()) {
                child.updater.performUpdate(g, c, child);
            }
        }
    }
}
