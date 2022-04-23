public interface Clickable {

    default void mouseOver(){}
    default void mouseDown(){}
    default void clicked(Operator operator, float clickX, float clickY, Callosum c){}
    default void pressed(Operator operator, float pressX, float pressY, Callosum c){}
    default void grabbed(Operator operator, float grabX, float grabY, Callosum c) {}
    default void dragged(Operator operator, float dragX, float dragY, Callosum c){}
    default void released(Operator operator, float releaseX, float releaseY, Callosum c){}
}