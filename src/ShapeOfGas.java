public class ShapeOfGas implements Shape{

    @Override
    public void shift(Organelle organelle, float parentX, float parentY, float parentW, float parentH) {
        organelle.x = parentX;
        organelle.y = parentY;
        organelle.w = parentW;
        organelle.h = parentH;
    }
}
