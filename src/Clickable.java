public interface Clickable {

    default void mouseOver(){}
    default void mouseDown(){}
    default void clicked(Operator operator, int mod, float clickX, float clickY, Callosum c){}
    default void pressed(Operator operator, int mod, float pressX, float pressY, Callosum c){}
    default void grabbed(Operator operator, int mod, float grabX, float grabY, Callosum c) {}
    default void dragged(Operator operator, int mod, float dragX, float dragY, Callosum c){}
    default void released(Operator operator, int mod, float releaseX, float releaseY, Callosum c){}
}