import processing.core.PApplet;
import java.util.ArrayList;
import java.io.File;

public class DirectoryEater extends FileEater {

    public DirectoryEater(File dir){
        this.file = dir;
    }

    @Override
    public ArrayList<Clipping> importAsClippings(){
        ArrayList<Clipping> brood = new ArrayList<Clipping>();

        for (File contents : PApplet.listFiles(file)){
            FileEater eater = createFileEater(contents);
            brood.addAll(eater.importAsClippings());
        }
        return brood;
    }

    public FileEater createFileEater(File file){
        if (file.isDirectory()) return new DirectoryEater(file);
        else if (isJPG(file)) return new JPGEater(file);
        else if (isText(file)) return new TXTEater(file);
        else return null;
    }

    public boolean extensionIs(File file, String extension){
        return file.getName().toLowerCase().endsWith(extension);
    }

    public boolean isJPG(File file){
        return extensionIs(file, ".jpg");
    }

    public boolean isText(File file){
        return extensionIs(file, ".txt");
    }
}
