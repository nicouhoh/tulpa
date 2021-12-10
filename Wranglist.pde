class Wranglist{
  
 Clipping[] library;
 int clipSize;
 int pillow;
 
 Wranglist(){
  String[] filenames = listFileNames(path);
  library = BirthingPool(filenames);
  clipSize = 200;
  pillow = 5;

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
  
  Clipping[] BirthingPool(String[] filenames){
    Clipping[] horde = new Clipping[0];
    for (int i = 0; i < filenames.length; i++){
      if (filenames[i].contains(".jpg")){
      Clipping spawn = new Clipping(filenames[i]);
      horde = (Clipping[])append(horde, spawn);
      }
    }
    return horde;
  }
  
  void FussMenagerie(){
    
    print("ASSUMING FORMATION... JUST LIKE WE PRACTICED, BIG SMILES....");
    int x = pillow;
    int y = pillow;
    int columns = width / (clipSize + pillow) + pillow;
    
    for (int i = 0; i < library.length; i++){
      library[i].setPos(x, y);
      if (x + clipSize + pillow < width){
        x += clipSize + pillow;
      } else {
        x = pillow;
        y += clipSize + pillow;
      }
    }
  }
  
  void PresentMenagerie(){
    //library[40].display(200, 200);
    for (int i = 0; i < library.length; i++){
      library[i].display(clipSize, clipSize);
    }
  }
  
}
