public class ShapeOfGas implements Shape {

    public void shift(Organelle organelle){
        organelle.x = organelle.getParent().x;
        organelle.y = organelle.getParent().y;
        organelle.w = organelle.getParent().w;
        organelle.h = organelle.getParent().h;
    }
}
