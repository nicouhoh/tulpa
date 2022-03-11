import processing.core.PApplet;

import processing.event.MouseEvent;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Math.abs;


public class Library {

  ArrayList<Clipping> clippings;
  String path;
  int nextid;

  ArrayList<Clipping> selected;

  boolean zoom;


  public Library() {
    nextid = 0;
    path = tulpa.SOLE.sketchPath() + "/data/";
    clippings = new ArrayList<Clipping>();
    selected = new ArrayList<Clipping>();
  }

  public void addToLibrary(Clipping clip) {
    clippings.add(clip);
  }

  public void addToLibrary(ArrayList<Clipping> clips) {
    clippings.addAll(clips);
  }

  public Clipping incubateFile(File file) {
    System.out.println("INCUBATE FILE: " + file.getName());
    if (file.getName().contains(".jpg")){//|| file.getName().contains(".png")) {
      return new Clipping(file, getid());
    } else {
      return null;
    }
  }

  public ArrayList<Clipping> incubateDir(File dir) {
    System.out.println("INCUBATE DIR");
    File[] files = tulpa.SOLE.listFiles(dir);
    ArrayList<Clipping> brood = new ArrayList<Clipping>();
    if (files != null)
      for (File file : files) {
        if (file.getName().contains(".jpg")){
          brood.add(incubateFile(file));
        } else if (file.isDirectory()) {
          brood.addAll(incubateDir(file));
        }
      }
    System.out.println("BROOD: " + brood);
    return brood;
  }

  public String getid() {
    String date = PApplet.str(PApplet.year()) + PApplet.nf(PApplet.month(), 2) + PApplet.nf(PApplet.day(), 2);
    String id = date + PApplet.nf(nextid, 3);
    nextid++;
    return id;
  }

  public void select(Clipping clipping) {
    clipping.selected = true;
    selected.add(clipping);
  }

  public void deselect(Clipping clipping) {
    clipping.selected = false;
    selected.remove(clipping);
  }

  public void clearSelection() {
    for (Clipping clipping : selected) {
      clipping.selected = false;
    }
    selected.clear();
  }

  public void selectLeftRight(int n){
    if (selected.size() == 1){
      int current = clippings.indexOf(selected.get(0));
      if (current + n < 0 || current + n >= clippings.size()) return;
      deselect(selected.get(0));
      select(clippings.get(current + n));
    }
  }

  public void selectUpDown(int columns){
    if(selected.size() == 1) {
      int index = clippings.indexOf(selected.get(0));
      Clipping c = clippings.get(index + columns);
      deselect(selected.get(0));
      select(c);
    }
  }

  public void selectUpDownSardine(int direction){ // TODO idea for making this better: pick the clipping with the longest section of shared edge w/ current clipping
    Clipping selClip = selected.get(0);
    System.out.println(selClip.imgPath);
    int i = clippings.indexOf(selClip);
    Clipping best = selClip;
    float dif = 9999;
    Clipping currentClipping = selClip;

    while(true){
      if(i < 0 || i >= clippings.size()){
        deselect(selClip);
        select(best);
        return;
      }
      currentClipping = clippings.get(i);
      System.out.println("Current clipping: " + currentClipping.imgPath);
      if (currentClipping == null) return;
      if (currentClipping.ypos != selClip.ypos) {
        if (abs(currentClipping.xpos - selClip.xpos) < dif) {
          dif = (abs(currentClipping.xpos - selClip.xpos));
          best = currentClipping;
        } else{
          deselect(selClip);
          select(best);
          return;
        }
      }
      i += direction;
    }
  }

  public void whackClipping() {
    clippings.removeAll(selected);
  }

  public void clickSelect(MouseEvent e) {
    boolean bromp = false;
    for (Clipping clip : clippings) {
      if (!clip.onscreen || !clip.clicked()) continue;
      if (!e.isMetaDown()) {
        clearSelection();
      } else if (clip.selected) {
        deselect(clip);
        return;
      }
      select(clip);
      bromp = true;
    }
    if (!bromp) {
      clearSelection();
    }
  }

  public void zoom(){
    if (selected.size() == 1) zoom = true;
  }

  public void unZoom(){
    zoom = false;
  }
}