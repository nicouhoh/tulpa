import processing.core.PGraphics;

public interface Updater {

    void performUpdate(PGraphics g, Conductor c, Organelle o);

    void updateChildren(PGraphics g, Conductor c, Organelle o);
}
