
import processing.core.PApplet;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.File;
import java.util.ArrayList;


public class Library{
  
  ArrayList<Clipping> clippings;
  String path;
  int nextid;

  ArrayList<Clipping> selected;
  
  public Library(){
    nextid = 0; //<>//
    path = tulpa.SOLE.sketchPath() + "/data/";
    clippings = new ArrayList<Clipping>();
    selected = new ArrayList<Clipping>();
  }
  
  public void addToLibrary(Clipping clip){
    clippings.add(clip);
  }
  
  public void addToLibrary(ArrayList<Clipping> clips){
    clippings.addAll(clips);
  }
  
  public Clipping incubateFile(File file){
    System.out.println("INCUBATE FILE: " + file.getName());
    if (file.getName().contains(".jpg")){
      Clipping clipping = new Clipping(file, getid());
      return clipping;
    }else{ return null; }
  }
  
  public ArrayList<Clipping> incubateDir(File dir){
    System.out.println("INCUBATE DIR");
    File[] files = tulpa.SOLE.listFiles(dir);
    ArrayList<Clipping> brood = new ArrayList<Clipping>();
    if (files != null)
      for (int i = 0; i < files.length; i++){
        if (files[i].getName().contains(".jpg")){
          brood.add(incubateFile(files[i]));
        } else if(files[i].isDirectory()){
          brood.addAll(incubateDir(files[i]));
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

  public void select(Clipping clipping){
    clipping.selected = true;
    selected.add(clipping);
  }

  public void deselect(Clipping clipping) {
    clipping.selected = false;
    selected.remove(clipping);
  }

  public void clearSelection(){
    for (int i = 0; i < selected.size(); i++){
      selected.get(i).selected = false;
    }
    selected.clear();
  }

  public void mouseEvent (MouseEvent e){
    if (e . getAction ()  !=  MouseEvent.RELEASE) return;
    for (int i = 0; i < clippings.size(); i++){
      Clipping clip = clippings.get(i);
      if (!clip.onscreen || !clip.clicked()) continue;
      if (!e.isMetaDown()){
        clearSelection();
      }else if(clip.selected){
        deselect(clip);
        return;
      }
      select(clip);
    }
  }

  public void keyEvent (KeyEvent e)
    { int tion = e . getAction ();
      if (tion  !=  KeyEvent.TYPE) {
        System.out.println("Zounds! key '" + e.getKey()
                + ((tion == KeyEvent.PRESS) ? "' pressed..."
                : "' released..."));
      }
    }
}
