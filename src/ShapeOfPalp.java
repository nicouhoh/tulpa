public class ShapeOfPalp implements Shape {

    @Override
    public void shift(Organelle organelle) {
        organelle.x = 0;
        organelle.y = 0;
        organelle.w = tulpa.SOLE.w;
        organelle.h = tulpa.SOLE.h;
    }
}
