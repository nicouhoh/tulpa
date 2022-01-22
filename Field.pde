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
    library = incubateDir(path);
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
  
  String[] listFileNames(String dir){
    File file = new File(dir);
    if (file.isDirectory()) {
      String names[] = file.list();
      return names;
    } else {
      return null;
    }
  }
  
  void addToLibrary(Clipping clip){
    library = (Clipping[])append(library, clip);
  }
  
  void addToLibrary(Clipping[] clips){
    library = (Clipping[])concat(library, clips);
  }
    
    Clipping incubateFile(String filename){
      if (filename.contains(".jpg")){
        Clipping clipping = new Clipping(filename);
        return clipping;
      }else{ return null; }
    }
    
    Clipping[] incubateDir(String filename){
      String[] files = listFileNames(filename);
      Clipping[] brood = new Clipping[0];
      for (int i = 0; i < files.length; i++){
        if (files[i].contains(".jpg")){
          brood = (Clipping[])append(brood, incubateFile(files[i]));
        }
      }
      return brood;
    } //<>//
  
  void fussMenagerie(){
    clipSize = constrain((w - (pillow * (columns + 1))) / columns, 10, 9999999);
    float x = pillow;
    float y = pillow;
    for (int i = 0; i < library.length; i++){
      x = pillow + (i % columns) * (pillow + clipSize);
      y = pillow + (i / columns) * (pillow + clipSize);
      println("WORKING ON: " + library[i]);
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
  
  //void oldFussMenagerie(){
  //  float x = pillow;
  //  float y = pillow;
  //  for (int i = 0; i < library.length; i++){
  //    library[i].setSize(clipSize, clipSize);
  //    if (x + clipSize >= w){
  //      x = pillow;
  //      y += clipSize + pillow;
  //    }
  //      library[i].setPos(x, y);
  //      x += clipSize + pillow;
  //  }
  //  foot = y + clipSize + pillow;
    
  //}  
 
  
}
