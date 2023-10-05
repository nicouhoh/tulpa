import java.io.File;
import java.util.ArrayList;

public class TulpaHeart implements TulpaHeartInterface {

    Library library;
    String path;

    public TulpaHeart(){
        path = tulpa.SOLE.sketchPath() + "/data/";
        File data = new File(path);
        library = new Library();
        library.add(ingestDirectory(data));
        //TODO we'll register observers here
    }

    @Override
    public Clipping createClipping() {
        return new Clipping();
    }

    @Override
    public Library getLibrary(){
        return library;
    }

    @Override
    public Clipping ingestFile(File file) {
        System.out.println("INGESTING FILE: " + file.getName());
        if (file.getName().contains(".jpg") || file.getName().contains(".txt")) {
            return new Clipping(file);
        }
        else return null;
    }

    @Override
    public ArrayList<Clipping> ingestDirectory(File dir) {
        System.out.println("INCUBATE DIR");
        File[] files = tulpa.SOLE.listFiles(dir);
        ArrayList<Clipping> brood = new ArrayList<Clipping>();
        if (files != null)
            for (File file : files) {
                if (file.getName().contains(".jpg") || file.getName().contains(".txt")){
                    brood.add(ingestFile(file));
                } else if (file.isDirectory()) {
                    brood.addAll(ingestDirectory(file));
                }
            }
        System.out.println("BROOD: " + brood);
        return brood;
    }
}
