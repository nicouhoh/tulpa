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
    library = birthingPool(path);
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
  
  void importClipping(String filename){
    Clipping clip = incubateClipping(filename);
    addToLibrary(clip);
  }
  
  void addToLibrary(Clipping clip){
    library = (Clipping[])append(library, clip);
  }
  
  // SAY: How could these next two be combined. can we do the overload thing here?
  
  Clipping[] birthingPool(String filepath){
    
     // Takes in a path, gives you a list of clippings
     
    String[] filenames = listFileNames(filepath);
    Clipping[] brood = new Clipping[0];
    for (int i = 0; i < filenames.length; i++){
      if (filenames[i].contains(".jpg")){
        Clipping spawn = incubateClipping(filenames[i]);
        brood = (Clipping[])append(brood, spawn);
      }
    }
    return brood;
  }
  
  Clipping incubateClipping(String filename){
    
    // takes in a file path, gives you a clipping
    
        Clipping spawn = new Clipping(filename);
        if (spawn.img == null){
           println("ERROR ERROR HELP ME");
        }
        return spawn; // Gives you a clippinng
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
  
  //void oldFuss(){
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
