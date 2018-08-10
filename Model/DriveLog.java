package Model;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Path;

public class DriveLog {
    private volatile boolean [] freeCar = {true,true,true}; // сначала все машины свободные
    private volatile boolean [] task = {false,false,false}; //ни у кого нету задания
    private volatile Path [] pathStart = new Path [3] ;
    private volatile Path [] pathUnload = new Path [3];
    private volatile ImageView [] viewManLoad = new ImageView [3];
    private volatile ImageView [] viewManUnload = new ImageView [3];
    private volatile int [] pozStart = new int [3];
    private volatile int [] distance = new int [3];

    public DriveLog() {
    }
    public synchronized void setCellFreeCarTrue(int i) {
        freeCar [i] = true;
    }

    public  void setCellFreeCarFalse(int i) {
        freeCar [i] = false;
    }

    public void setCellTaskTrue (int i) {
        task [i] = true;
    }

    public  synchronized void setCellTaskFalse (int i) {
        task [i] = false;
    }

    public boolean getTask(int i) {
        return task [i];
    }

    public boolean[] getFreeCar() {
        return freeCar;
    }


    public synchronized Path getPathStart(int  i) {
        return pathStart[i];
    }

    public void setPathStart(Path pathStart,int  i) {
        this.pathStart[i] = pathStart;
    }


    public synchronized Path getPathUnload(int  i) {
        return pathUnload [i];
    }

    public void setPathUnload(Path pathUnload,int  i) {
        this.pathUnload [i] = pathUnload;
    }

    public synchronized ImageView getViewManLoad (int  i) {
        return viewManLoad[i];
    }

    public  void setViewManLoad (ImageView viewMan,int  i) {
        this.viewManLoad[i] = viewMan;
    }

    public synchronized ImageView getViewManUnload(int i) {
        return viewManUnload [i];
    }

    public void setViewManUnload(ImageView viewManUnload, int i) {
        this.viewManUnload [i] = viewManUnload ;
    }

    public void setPozStart(int pozStart,int i) {
        this.pozStart [i] = pozStart;
    }


    public int getPozStart(int i) {
        return pozStart [i];
    }

    public int getDistance(int i) {
        return distance [i];
    }

    public void setDistance(int distance,int i) {
        this.distance [i] = distance;
    }
}
