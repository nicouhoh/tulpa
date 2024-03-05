import processing.core.PVector;

public abstract class Virgo extends Organelle {

    PVector gap;
    PVector padding;

    public Virgo(Cell bounds){
        gap = new PVector(10, 10);
        padding = new PVector(10, 10);
        setBounds(bounds);
    }

    public Virgo(Cell bounds, Organelle ... organelles){
        gap = new PVector(10, 10);
        padding = new PVector(10, 10);
        setBounds(bounds);
        addChildren(organelles);
    }

    @Override
    public void resize(float parentX, float parentY, float parentW, float parentH){
        setBounds(parentX, parentY, parentW, parentH);
    }

    @Override
    public abstract void resizeChildren();

    public void setGap(float x, float y){
        this.gap = new PVector(x, y);
    }

    public void setGap(float gap){
        this.gap = new PVector(gap, gap);
    }

    public void setPadding(float x, float y){
        this.padding = new PVector(x, y);
    }

    public void setPadding(float padding){
        this.padding = new PVector(padding, padding);
    }
}