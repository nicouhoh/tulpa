import processing.event.MouseEvent;
public class SqueakMove extends Squeak {

    public SqueakMove(MouseEvent e){
        super(e);
    }

    // Returns the deepest organelle under the mouse that accepts this kind of squeak
    public Organelle captureAndBubble(Organelle root){
        if (consumed) return null;
        if (!mouseOver(root)){
            return null;
        }
        addLatitude(root.getLatitude());

        for (Organelle child : root.getChildren()){
            Organelle result = captureAndBubble(child);
            if (consumed) return result;
        }
        consume(root); // in here is where we check whether the organelle accepts this kind of squeak and make sure if it does we don't trigger additional squeaks up the chain.
        return root;
    }
}
