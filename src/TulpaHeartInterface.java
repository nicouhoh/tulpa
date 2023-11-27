import java.util.ArrayList;
import java.io.File;
public interface TulpaHeartInterface {

    Clipping createClipping();

    Clipping ingestFile(File file);

    ArrayList<Clipping> ingestDirectory(File dir);

    Library getLibrary();

    void selectClipping(Clipping clipping);

}
