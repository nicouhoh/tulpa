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
    public void removeChild(Organelle organelle){
        host.removeChild(organelle);
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
    public void update(float parentX, float parentY, float parentW, float parentH) {
        host.update(parentX, parentY, parentW, parentH);
    }

    @Override
    public void updateChildren(){
        host.updateChildren();
    }

    @Override
    public void draw(PGraphics g){
        host.draw(g);
    }

    @Override
    public void drawChildren(PGraphics g){
        host.drawChildren(g);
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
