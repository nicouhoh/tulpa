import processing.core.PApplet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class Library {

  String path;
  int nextid;

  ArrayList<Clipping> clippings;
  ArrayList<Clipping> selected;

  ArrayList<Tag> tags = new ArrayList<Tag>();

  boolean zoom;


  public Library() {
    nextid = 0;
    path = tulpa.SOLE.sketchPath() + "/data/";
    clippings = new ArrayList<Clipping>();
    selected = new ArrayList<Clipping>();

    debugInit(path);
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

  public ArrayList<Tag> parseTags(Text text){
    ArrayList<Tag> output = new ArrayList<Tag>();
    if (!text.bodyText.contains("#")) return output;
    for (String word : text.getWords()){
      if (word.startsWith("#")) output.add(new Tag(word));
    }
    return output;
  }

  public void addSelect(Clipping clipping) {
    selected.add(clipping);
  }

  public void removeSelect(Clipping clipping) {
    selected.remove(clipping);
  }

  public void clearSelection() {
    selected.clear();
  }

  public void select(Clipping c){
    clearSelection();
    addSelect(c);
  }

  public void toggleSelect(Clipping c){
    if (isSelected(c)) removeSelect(c);
    else addSelect(c);
  }

  public boolean isSelected(Clipping c){
    return (selected.contains(c));
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

  // move Clipping c before clipping c2
  public void moveClipping(Clipping c1, Clipping c2){
    int ci = clippings.indexOf(c1);
    int c2i = clippings.indexOf(c2);
    if (ci < c2i){
      Collections.rotate(clippings.subList(ci, c2i), -1);
    } else if( ci > c2i){
      Collections.rotate(clippings.subList(c2i, ci+1), 1);
    }
  }

  public void moveClipping(ArrayList<Clipping> c, Clipping c2){
    for (Clipping clip : c){
      moveClipping(clip, c2);
    }
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

  public void debugInit(String p){
    File data = new File(path);
    newClippings(data);
  }
}