import processing.core.PGraphics;

public interface Draggish extends Palpable {

    void grab();
    void drag(float dragX, float dragY, float offsetX, float offsetY);

    void drawCasper(PGraphics g, float dragX, float dragY, float offsetX, float offsetY);
}
