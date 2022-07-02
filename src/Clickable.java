public interface Clickable {

    default void mouseOver(){}
    default void mouseDown(){}
    default void clicked(Operator operator, int mod, float clickX, float clickY, Callosum c){}
    default void pressed(Operator operator, int mod, float pressX, float pressY, Callosum c){}
    default void hoveredWithGift(Operator operator, int mod, float hoverX, float hoverY, Draggable gift, float lockedX, float lockedY, Callosum c){}
    default void offeredGift(Operator operator, int mod, float giftX, float giftY, Draggable gift, Callosum c){}

    default Clipping getClipping() {
        if (this instanceof Spegel) return ((Spegel) this).clipping;
        else return null;
    }


}