import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileEater {

    File file;

    public FileEater(File file){
        this.file = file;
    }

    public FileEater() {
    }

    // takes a file, copies it to our data folder, and creates a clipping referring to it.
    // text will be copied directly into the json file.
    public Clipping hatchClippingFromFile() {
        System.out.println("INGESTING FILE: " + file.getName());

        Clipping clip = new Clipping();
//        absorbFile(clip);

        return clip;
    }

    public ArrayList<Clipping> importAsClippings(){
        throw new Error("Unknown filetype");
    }

    public ArrayList<Clipping> ingestDirectory() {
        System.out.println("INCUBATE DIR");

        ArrayList<Clipping> brood = new ArrayList<Clipping>();

        for (File spawn : PApplet.listFiles(file)) {
            FileEater salmon = new FileEater(spawn);
            if (spawn.isFile()){
                brood.add(salmon.hatchClippingFromFile());
            } else if (spawn.isDirectory()) {
                brood.addAll(salmon.ingestDirectory());
            }
        }

        System.out.println("BROOD: " + brood);
        return brood;
    }


    public Path getImportPath(){
        String targetPathName = incrementDuplicateFilename(imagesPath() + file.getName());
        return Paths.get(targetPathName);
    }

    public void createDataDirectory(String dir){
        try {
            Files.createDirectories(Paths.get(getDataPath() + dir));
        } catch (IOException e) {
            System.out.println("Error creating directory " + getDataPath() + dir);
            throw new RuntimeException(e);
        }
    }

    public void copyFile(Path sourcePath, Path targetPath){
        try {
            Files.copy(sourcePath, targetPath);
        } catch (IOException e) {
            System.out.println("Error copying file " + sourcePath.getFileName() + " to " + targetPath.getFileName());
            throw new RuntimeException(e);
        }
    }

    public String incrementDuplicateFilename(String filename){

        String newName = filename;
        int num = 0;
        while (new File(newName).isFile()){
            System.out.println("Duplicate filename, renaming");
            int lastDotIndex = filename.lastIndexOf('.');
            newName = filename.substring(0, lastDotIndex) + PApplet.nf(++num, 3) + filename.substring(lastDotIndex);
        }
        return newName;
    }

    public Path filePath(){
        return Paths.get(file.getAbsolutePath());
    }

    // FIXME these are duplicated from tulpaheart
    public String getDataPath(){
        return tulpa.SOLE.sketchPath() + "/data/";
    }

    public String imagesPath(){
        return getDataPath() + "/images/";
    }


//    public Clipping brandClippingWithFilePath(Clipping clipping, String path){
//        throw new Error("No file type");
//    }

}
