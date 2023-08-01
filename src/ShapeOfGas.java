public class ShapeOfGas implements ShapeShifter {

    public void update(Organelle organelle){
        organelle.x = organelle.getParent().x;
        organelle.y = organelle.getParent().y;
        organelle.w = organelle.getParent().w;
        organelle.h = organelle.getParent().h;
    }
}
