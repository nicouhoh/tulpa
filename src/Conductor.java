public class Conductor {

    //
    // This class is an artifact of some work I was doing a bit ago
    // I'm leaving it here in case, but it doesn't do anything right now
    //


    tulpa t;
    Library lib;
    Visipalp vis;

    boolean puzzle = false;

    MouseInput mi = new MouseInput(0, 0, 0, 0, 0);
    KeyInput ki = new KeyInput(0, '\0', 0, 0);

    int nextID;

    Conductor(tulpa t, Library library, Visipalp visipalp){
        this.t = t;
        this.lib = library;
        this.vis = visipalp;
        nextID = 1;
    }

    public void draw(){
        if (ki.key == '0') puzzle = !puzzle;

        //vis.background();
//        if (puzzle) vis.puzzleView(0, t.width, t.height, vis.latitude, mi, ki);
//        else vis.thumbnailView(0, 0, t.w, t.h, vis.latitude, mi, ki);

        finish();
    }

    public void finish(){
        ki.key = '\0';
        ki.kc = 0;
        mi.wheel = 0;
    }

}