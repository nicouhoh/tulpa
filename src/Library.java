import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;


public class Library {

  ArrayList<Clipping> clippings;
  String path;
  int nextid;

  ArrayList<Clipping> selected;

  ArrayList<Tag> tags = new ArrayList<Tag>();

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

  public Tag createTag(String name){
    if (tagExists(tags, name)) return null;
    return new Tag(name);
  }

  public Tag getTag(String s){
    for (Tag t : tags){
      if (t.name.toLowerCase() == s.toLowerCase()){
        return t;
      }
    }
    return null;
  }

  public void tagClipping(Clipping c, Tag t){
    if (!tagExists(tags, t)){
      tags.add(t);
    }
    if (!c.taggedWith(t.name)){
      c.addTag(t);
    }
  }

  public void tagClipping(Clipping c, String name){
    if (!tagExists(tags, name)){
      Tag tagatha = createTag(name);
      tags.add(tagatha);
    }
    if (!c.taggedWith(name)){
      c.addTag(getTag(name));
    }
  }

  public void tagClipping(Clipping c, ArrayList<Tag> l){
    for (Tag t : l){
      tagClipping(c, t);
    }
  }

  public boolean tagExists(ArrayList<Tag> taggies, String s){
    for (Tag t : taggies){
      if (t.name.toLowerCase() == s.toLowerCase()) return true;
    }
    return false;
  }

  public boolean tagExists(ArrayList<Tag> taggies, Tag korv){
    for (Tag t : taggies){
      if (t.name.equalsIgnoreCase(korv.name)) return true;
    }
    return false;
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

  public void selectUpDownSardine(int direction){
    Clipping selClip = selected.get(0);
    float bestScore = 0;
    Clipping best = selClip;

    for (Clipping c : clippings){
      Spegel s = c.spegel;
      if (c == selClip) continue;
      if (c.spegel.y < selClip.spegel.y - s.displayH * 1.2 || s.y > selClip.spegel.y + selClip.spegel.displayH * 1.2) continue;
      if (findOverlap(selClip.spegel, s) > bestScore && (selClip.spegel.y - s.y) * direction < 0){
        bestScore = findOverlap(selClip.spegel, s);
        best = c;
      }
    }
    clearSelection();
    select(best);
  }

  public ArrayList<Clipping> search(String s){
    ArrayList<Clipping> output = new ArrayList<Clipping>();
    String[] terms = s.split(" ");
    for (Clipping c : clippings){
      for (String word : terms){
        if(!c.taggedWith(word)) continue;
        output.add(c);
      }
    }
    return output;
  }

  public float findOverlap(Spegel s1, Spegel s2){
    if (s1.x > s2.x + s2.displayW || s1.x + s1.displayW <= s2.x) return 0;
    float left = PApplet.max(s1.x, s2.x);
    float right = PApplet.min(s1.x + s1.displayW, s2.x + s2.displayW);
    return PApplet.max(left, right) - PApplet.min(left, right);
  }

  // move Clipping c before clipping c2
  public void moveClipping(Clipping c, Clipping c2){
  }

  public void whackClipping(Clipping c){
    clippings.remove(c);
  }

  public void whackClipping(ArrayList<Clipping> c){
    clippings.removeAll(c);
  }

  public void zoom(){
    if (selected.size() == 1) zoom = true;
  }

  public void unZoom(){
    zoom = false;
  }

  public void debugTagList(){
    if (tags == null){
      System.out.println("No tags");
      return;
    }
    for (Tag t : tags){
      System.out.println(t.name);
    }
  }
}