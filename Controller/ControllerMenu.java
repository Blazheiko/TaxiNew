package Controller;
import Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class ControllerMenu {

    private Main main;

    public ControllerMenu() {
    }

    /**
     * Вызывается главным приложением, чтобы оставить ссылку на самого себя.
     *
     */

    public void setMainParse (Main main) {
        this.main = main;
    }

    @FXML
    private void handleSaveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

        if (file != null) {
            // Убедитесь, что он имеет правильное расширение
            if (!file.getPath().endsWith(".txt")) {
                file = new File(file.getPath() + ".txt");
            }
            main.saveDispatchLog(file);
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Taxi");
        alert.setHeaderText("Training software");
        alert.setContentText("Author Blazheyko Alexander\n" +
                "The program imitates the work of the taxi service\n" +
                "1 thread dispatcher 1 thread  generator of passenger calls \n" +
                "3 thread imitate the work of taxi cars \\ n\n" +
                "the dispatcher receives calls and manages the work \\n\" +" +
                "car thread ");

        alert.showAndWait();
    }
    @FXML
    void initialize() {

    }
}
