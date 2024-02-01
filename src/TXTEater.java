import java.io.File;
import java.util.ArrayList;

public class TXTEater extends FileEater {

    public TXTEater(File text){
        this.file = text;
    }

    @Override
    public ArrayList<Clipping> importAsClippings(){
        Clipping clipping = new Clipping();
        imbueClippingWithText(clipping);
        ArrayList<Clipping> result = new ArrayList<>();
        result.add(clipping);
        return result;
    }

    public void imbueClippingWithText(Clipping clipping){
        StringBuilder text = new StringBuilder();
        for (String s : tulpa.SOLE.loadStrings(file.getAbsolutePath())){
            text.append(s).append('\n');
        }
        clipping.data.setString("text", text.toString());
    }
}
