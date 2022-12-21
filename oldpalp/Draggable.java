public interface Draggable {

    default void dragged(Operator operator, int mod, float dragX, float dragY, float lockedX, float lockedY, Callosum c){}
    default void grabbed(Operator operator, int mod, float mouseX, float mouseY, Callosum c){
        if (this instanceof  Monad){
//            System.out.println("Draggable grabbed");
            Monad m = (Monad)this;
            m.grab();
        }
    }
    default void dropped(Operator operator, int mod, float dropX, float dropY, Callosum c){}
}
