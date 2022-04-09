public class Visipalp {

    public boolean checkMouse(Monad monad, float mouseX, float mouseY){
        if (mouseX >= monad.x && mouseX <= monad.x + monad.w
                && mouseY >= monad.y - tulpa.SOLE.field.latitude
                && mouseY <= monad.y + monad.h - tulpa.SOLE.field.latitude){
            return true;
        } else{
            return false;
        }
    }
}