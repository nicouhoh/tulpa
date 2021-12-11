class Wranglist{
  
  Clipping[] library;
  String path;
  int clipSize;
  
  Wranglist(){
    path = sketchPath() + "/data/";
    library = BirthingPool();
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
  
  Clipping[] BirthingPool(){
    String[] filenames = listFileNames(path);
    Clipping[] brood = new Clipping[0];
    for (int i = 0; i < filenames.length; i++){
      if (filenames[i].contains(".jpg")){
        Clipping spawn = new Clipping(filenames[i]);
        print(spawn.img);
        brood = (Clipping[])append(brood, spawn);
      }
    }
    return brood;
  }
  
  //void FussMenagerie(){
    
  //}
  
  //void PresentMenagerie(){

  //  }
  //}
  
}
