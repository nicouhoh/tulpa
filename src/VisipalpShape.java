public class VisipalpShape implements Shape{
    @Override
    public void shift(Organelle organelle, float parentX, float parentY, float parentW, float parentH) {
        organelle.x = 0;
        organelle.y = 0;
        organelle.w = parentW;
        organelle.h = parentH;
    }
}
