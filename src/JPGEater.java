import java.io.File;
import java.nio.file.Path;
import java.sql.Array;
import java.util.ArrayList;

public class JPGEater extends FileEater {

    public JPGEater(File jpg){
        this.file = jpg;
    }

    @Override
    public ArrayList<Clipping> importAsClippings(){
        Clipping clipping = new Clipping();
        Path importPath = getImportPath();
        absorbFile(importPath);
        brandClippingWithFilePath(clipping, importPath.toString());
        ArrayList<Clipping> result = new ArrayList<Clipping>();
        result.add(clipping);
        return result;
    }

    public void absorbFile(Path importPath){
        createDataDirectory("/images");
        copyFile(filePath(), importPath);
    }

    public void brandClippingWithFilePath(Clipping clipping, String path){
        clipping.data.setString("imagePath", path);
    }
}
