package Model;

public class WayPoint {
    private int number ;
    private int distance ;
    private int previous ;


    public WayPoint(int number,  int distance, int previous) {
        this.number = number;
        this.distance = distance;
        this.previous = previous;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "WayPoint{" +
                "number=" + number +
                ", distance=" + distance +
                ", previous=" + previous +
                '}';
    }
}
