package Main;

import Treads.Dispatch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    private List <ImageView> listManView = new ArrayList<>(12);
    private ArrayList <ImageView> listCarView = new ArrayList<>(3);

    //Возвращает главную сцену.

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Taxi");
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(1200);

        // Устанавливаем иконку приложения.
        this.primaryStage.getIcons().add(new Image("file:src/image/icons8-64taxi.png"));

        initControllerMenu();
        try {
            showCity ();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });

    }


    public void initControllerMenu() {
        try {
            // Загружаем корневой макет из view файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/View/menu.fxml"));
            rootLayout =  loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout,1500,1000);
            primaryStage.setScene(scene);

            // Даём контроллеру доступ к главному прилодению.
          //  ControllerMenu controller = loader.getController();
           // controller.setMain(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


  public void showCity () throws InterruptedException {
        // добавляем карту города
      Image imageCity = new Image("image/taxi_map.png");
      ImageView viewCity = new ImageView(imageCity);

      //добавляем пассажиров
      Image manImage = new Image("/image/man_new.png");
      ImageView viewMan;
      for (int i=0; i <12; i++){
          viewMan = new ImageView(manImage);
          listManView.add(viewMan);
      }
      // добавляем автомобили
      Image carImageFree = new Image("image/car_new_free.png");
      ImageView viewCar;
      for (int i=0; i < 3; i++){
          viewCar = new ImageView(carImageFree);
          listCarView.add(viewCar);
      }

      AnchorPane ancorCity = new AnchorPane(viewCity);
      ancorCity.getChildren().addAll(listCarView);
      ancorCity.getChildren().addAll(listManView);

      StackPane paneCity = new StackPane(ancorCity);
      rootLayout.setCenter(paneCity);

      // запускаем поток Диспетчера который будет управлять єлементами на карте
      Thread threadDispatch = new Thread(new Dispatch(listManView,listCarView));
      threadDispatch.start();
  }

    public void saveDispatchLog(File file) {
        // если нужно сохранять какие то данные
    }




    public static void main(String[] args) {
        launch(args);
    }
}
