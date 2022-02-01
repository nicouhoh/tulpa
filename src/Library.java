
import processing.core.PApplet;

import java.io.File;


public class Library{
  
  Clipping[] clippings;
  String path;
  int nextid;
  
  public Library(){
    nextid = 0; //<>//
    path = tulpa.SOLE.sketchPath() + "/data/";
    clippings = incubateDir(new File(path));
  }
  
  public void addToLibrary(Clipping clip){
    clippings = (Clipping[])PApplet.append(clippings, clip);
  }
  
  public void addToLibrary(Clipping[] clips){
    clippings = (Clipping[])PApplet.concat(clippings, clips);
  }
  
  public Clipping incubateFile(File file){
    System.out.println("INCUBATE FILE: " + file.getName());
    if (file.getName().contains(".jpg")){
      Clipping clipping = new Clipping(this, file);
      return clipping;
    }else{ return null; }
  }
  
  public Clipping[] incubateDir(File dir){
    System.out.println("INCUBATE DIR");
    File[] files = tulpa.SOLE.listFiles(dir);
    Clipping[] brood = new Clipping[0];
    for (int i = 0; i < files.length; i++){
      if (files[i].getName().contains(".jpg")){
        brood = (Clipping[])PApplet.append(brood, incubateFile(files[i]));
      } else if(files[i].isDirectory()){
        brood = (Clipping[])PApplet.concat(brood, incubateDir(files[i]));
      }
    }
    System.out.println("BROOD: " + brood);
    return brood;
  }
  
  public String getid(){
    String date = PApplet.str(PApplet.year()) + PApplet.nf(PApplet.month(), 2) + PApplet.nf(PApplet.day(), 2);
    String id = date + PApplet.nf(nextid, 3);
    nextid++;
    return id;
  }
}
