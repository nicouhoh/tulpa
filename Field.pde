class Field{

  float w;
  int columns;
  Scroller scroller;
  float latitude;
  
  Clipping[] library;
  String path;
  float clipSize;
  float pillow;
  float foot;
  float scrollDist;
  
  Field(){
    scroller = new Scroller();
    latitude = 0;
    columns = 5;
    path = sketchPath() + "/data/";
    library = incubateDir(new File(path));
    pillow = 10;
    clipSize = 200;
    fussMenagerie();
  }
  
  void initializeField(){
    w = width - scroller.scrollW;
  }
  
  void updateField(){
    w = width - scroller.scrollW;
    if (resized){
      fussMenagerie();
    }
    scroller.updateScroller(foot);
    followScroller();
    Showtime();
  }
  
  void addToLibrary(Clipping clip){
    library = (Clipping[])append(library, clip);
  }
  
  void addToLibrary(Clipping[] clips){
    library = (Clipping[])concat(library, clips);
  }
  
  
    
  Clipping incubateFile(File file){
    println("INCUBATE FILE: " + file.getName());
    if (file.getName().contains(".jpg")){
      Clipping clipping = new Clipping(file);
      return clipping;
    }else{ return null; }
  }
  
  Clipping[] incubateDir(File dir){
    println("INCUBATE DIR");
    File[] files = listFiles(dir);
    Clipping[] brood = new Clipping[0];
    for (int i = 0; i < files.length; i++){ //<>//
      if (files[i].getName().contains(".jpg")){
        brood = (Clipping[])append(brood, incubateFile(files[i]));
      } else if(files[i].isDirectory()){
        brood = (Clipping[])concat(brood, incubateDir(files[i]));
      }
    }
    println("BROOD: " + brood);
    return brood; //<>//
  }
  
  void fussMenagerie(){
    clipSize = constrain((w - (pillow * (columns + 1))) / columns, 10, 9999999);
    float x = pillow;
    float y = pillow;
    for (int i = 0; i < library.length; i++){
      x = pillow + (i % columns) * (pillow + clipSize);
      y = pillow + (i / columns) * (pillow + clipSize);
      library[i].setSize(clipSize, clipSize);
      library[i].setPos(x, y);
    }
    foot = y + clipSize + pillow;
  }

  void Showtime(){
    push();
    background(50);
    translate(0, latitude);
    for (int i = 0; i < library.length; i++){
      library[i].display(latitude);
    }
    pop();
    
    scroller.drawScroller();
  }
  
  void followScroller(){
     latitude = -(scroller.gripY / height) * foot;
  }
  
}
