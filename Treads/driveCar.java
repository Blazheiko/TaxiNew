package Treads;

import Model.DriveLog;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class driveCar implements Runnable {
    private int speed;
    private ImageView viewCar = null;
    private int index;
    private DriveLog driveLog;
    private PathTransition pathTransition ;
    private boolean activ=true;


    public driveCar(int speed, ImageView viewCar, DriveLog driveLog, int index) {
        this.speed = speed;
        this.viewCar = viewCar;
        this.driveLog = driveLog;
        this.index = index ;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {

            pathTransition = new PathTransition();
            activ = true ;
            Image imageFreeCar = new Image("image/car_new_free.png");
            Image imageWorkCar = new Image("image/car_new.png");
            // ждем когда появится задание от диспетчера
            while (!driveLog.getTask(index)){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            viewCar.setImage(imageWorkCar);
            int time = driveLog.getDistance(index)*speed ;
            activ = true ;
            if (driveLog.getPathStart(index) != null ){

                pathTransition.setDuration(Duration.millis(time));
                pathTransition.setNode(viewCar);
                pathTransition.setPath(driveLog.getPathStart(index));
                pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

                pathTransition.play();
                pathTransition.setOnFinished(event -> {activ = false;});
                // ждем когда отработает pathTransition
                while (activ){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                activ = true ;
            }
            //забираем пассажира
            driveLog.getViewManLoad(index).setVisible(false);
            pathTransition.setDuration(Duration.millis(time));
            pathTransition.setNode(viewCar);
            pathTransition.setPath(driveLog.getPathUnload(index));
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

            pathTransition.play();
            pathTransition.setOnFinished(event -> {activ = false;});

            // ждем когда отработает pathTransition
            while (activ){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //выгружаем пассажира

            viewCar.setImage(imageFreeCar);
            driveLog.setCellTaskFalse(index);
            driveLog.setCellFreeCarTrue(index);
            System.out.println("TEST");
        }
    }

}
