import processing.core.PGraphics;
import java.util.ArrayList;

public abstract class Symbiote extends Organelle {
    
    Organelle host;

    @Override
    public void addChild(Organelle organelle){
        host.addChild(organelle);
    }

    @Override
    public void addChildren(ArrayList<Organelle> organelles){
        host.addChildren(organelles);
    }

    @Override
    public void removeChild(Organelle ... organelles){
        host.removeChild(organelles);
    }

    @Override
    public Organelle getChild(int index) {
        return host.getChild(index);
    }

    @Override
    public ArrayList<Organelle> getChildren(){
        return host.getChildren();
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH) {
        host.resize(parentX, parentY, parentW, parentH);
    }

    @Override
    public void resizeChildren(){
        host.resizeChildren();
    }

    @Override
    public void draw(PGraphics g){
        host.draw(g);
    }

    @Override
    public void drawChildren(PGraphics g, float clipMin, float clipMax){
        host.drawChildren(g, clipMin, clipMax);
    }

    @Override
    public void setPos(float newX, float newY){
        x = newX;
        y = newY;
        host.setPos(newX, newY);
    }

    @Override
    public void setSize(float newW, float newH){
        w = newW;
        h = newH;
        host.setSize(newW, newH);
    }

    @Override
    public void drawDebug(PGraphics g){
        host.drawDebug(g);
    }
}
