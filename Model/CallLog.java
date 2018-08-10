package Model;

public class CallLog {
    private int loadingPlace =0 ;
    private int unloadingPlace = 0;


    public CallLog() {
    }

    public CallLog(int loadingPlace, int unloadingPlace) {
        this.loadingPlace = loadingPlace;
        this.unloadingPlace = unloadingPlace;
    }

    public int getLoadingPlace() {
        return loadingPlace;
    }

    public synchronized void setLoadingPlace(int loadingPlace) {
        this.loadingPlace = loadingPlace;
    }

    public int getUnloadingPlace() {
        return unloadingPlace;
    }

    public synchronized void setUnloadingPlace(int unloadingPlace) {
        this.unloadingPlace = unloadingPlace;
    }
}
