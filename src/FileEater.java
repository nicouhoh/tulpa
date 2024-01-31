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

    // takes a file, copies it to our data folder, and creates a clipping referring to it.
    // text will be copied directly into the json file.
    public Clipping ingestFile() {
        System.out.println("INGESTING FILE: " + file.getName());

        Clipping clip = new Clipping();
        importFile(clip);

        return clip;
    }

    public ArrayList<Clipping> ingestDirectory() {
        System.out.println("INCUBATE DIR");

        ArrayList<Clipping> brood = new ArrayList<Clipping>();

        for (File spawn : PApplet.listFiles(file)) {
            FileEater salmon = new FileEater(spawn);
            if (spawn.isFile()){
                brood.add(salmon.ingestFile());
            } else if (spawn.isDirectory()) {
                brood.addAll(salmon.ingestDirectory());
            }
        }

        System.out.println("BROOD: " + brood);
        return brood;
    }

    public void importFile(Clipping clip){
        if (isJPG()){
            importJPG(clip);
        }
        else if (isText()){
            clip.data.setString("text", importTXT());
        }
    }

    // TODO refactor these two import functions some kinda way
    public String importTXT() {
        StringBuilder text = new StringBuilder();
        for (String s : tulpa.SOLE.loadStrings(file.getAbsolutePath())){
            text.append(s).append('\n');
        }
        return text.toString();
    }

    public void importJPG(Clipping clip) {
        createDataDirectory("/images/");
        Path importPath = getImportPath();
        copyFile(filePath(), importPath);
        clip.data.setString("imagePath", importPath.toString());
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

    public boolean isJPG(){
        return extensionIs(".jpg");
    }

    public boolean isText(){
        return extensionIs(".txt");
    }

    public boolean extensionIs(String extension){
        return file.getName().toLowerCase().endsWith(extension);
    }

    public boolean acceptableFile(){
        return isJPG() || isText();
    }

    // FIXME these are duplicated from tulpaheart
    public String getDataPath(){
        return tulpa.SOLE.sketchPath() + "/data/";
    }

    public String imagesPath(){
        return getDataPath() + "/images/";
    }

}
