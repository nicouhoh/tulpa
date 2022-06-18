import processing.core.PGraphics;
public class Graffito extends Scrawler {

    float gW = 950;

    public Graffito(Vision parent){
        this.parent = parent;
        parent.children.add(this);
        enabled = true;
        textSize = 40;
    }

}