import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;


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

  public Clipping newClipping(File file){
    Clipping clip = incubateFile(file);
    addToLibrary(clip);
    return clip;
  }

  public ArrayList<Clipping> newClippings(File path){
    ArrayList<Clipping> c = incubateDir(path);
    addToLibrary(c);
    return c;
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

  public void addSelect(Clipping clipping) {
    clipping.setSelected(true);
    selected.add(clipping);
  }

  public void removeSelect(Clipping clipping) {
    clipping.setSelected(false);
    selected.remove(clipping);
  }

  public void clearSelection() {
    for (Clipping clipping : selected) {
      clipping.setSelected(false);
    }
    selected.clear();
  }

  public void select(Clipping c){
    clearSelection();
    addSelect(c);
  }

  public void toggleSelect(Clipping c){
    if (c.isSelected()) removeSelect(c);
    else addSelect(c);
  }

  public void selectLeftRight(int n){
    if (selected.size() == 1){
      int current = clippings.indexOf(selected.get(0));
      if (current + n < 0 || current + n >= clippings.size()) return;
      removeSelect(selected.get(0));
      addSelect(clippings.get(current + n));
    }
  }

//  public void selectUpDownGrid(int columns){
//    if(selected.size() == 1) {
//      int index = clippings.indexOf(selected.get(0));
//      Clipping c = clippings.get(PApplet.constrain(index + columns, 0, clippings.size() - 1));
//      deselect(selected.get(0));
//      select(c);
//    }
//  }
//
//  public void selectUpDownSardine(int direction){
//    Clipping selClip = selected.get(0);
//    float bestScore = 0;
//    Clipping best = selClip;
//
//    for (Clipping c : clippings){
//      if (c == selClip) continue;
//      if (c.y < selClip.y - c.displayH * 1.2 || c.y > selClip.y + selClip.displayH * 1.2) continue;
//      if (findOverlap(selClip, c) > bestScore && (selClip.y - c.y) * direction < 0){
//        bestScore = findOverlap(selClip, c);
//        best = c;
//      }
//    }
//    clearSelection();
//    select(best);
//  }
//
//  public float findOverlap(Clipping clip1, Clipping clip2){
//    if (clip1.x > clip2.x + clip2.displayW || clip1.x + clip1.displayW <= clip2.x) return 0;
//    float left = PApplet.max(clip1.x, clip2.x);
//    float right = PApplet.min(clip1.x + clip1.displayW, clip2.x + clip2.displayW);
//    float ans = PApplet.max(left, right) - PApplet.min(left, right);
//    return ans;
//  }
//
//  public void overlapDebug(){
//    if (selected.size() == 2){
//      float ans = findOverlap(selected.get(0), selected.get(1));
//      System.out.println("Overlap between " + selected.get(0).imgPath + " and " + selected.get(1).imgPath + ": " + ans);
//    }
//  }

//  public void whackClipping() {
//    clippings.removeAll(selected);
//    System.out.println("clipping whacked");
//  }

  public void whackClipping(Clipping c){
    clippings.remove(c);
  }

  public void whackClipping(ArrayList<Clipping> c){
    clippings.removeAll(c);
  }

//  public void clickSelect()


//  public void clickSelect(MouseEvent e) {
//    boolean bromp = false;
//    for (Clipping clip : clippings) {
////      if (!clip.onscreen || !clip.clicked()) continue;
//      if (!e.isMetaDown() && !e.isControlDown()) {
//        clearSelection();
//      } else if (clip.selected) {
//        deselect(clip);
//        return;
//      }
//      select(clip);
//      bromp = true;
//    }
//    if (!bromp) {
//      clearSelection();
//    }
//  }

  public void zoom(){
    if (selected.size() == 1) zoom = true;
  }

  public void unZoom(){
    zoom = false;
  }
}