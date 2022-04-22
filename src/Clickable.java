public interface Clickable {

    default void mouseOver(){}
    default void mouseDown(){}
    default void clicked(Operator operator, float clickX, float clickY){}
    default void pressed(Operator operator, float pressX, float pressY){}
    default void grabbed(Operator operator, float grabX, float grabY) {}
    default void dragged(Operator operator, float dragX, float dragY){}
    default void released(Operator operator, float releaseX, float releaseY){}
}