import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class NamaraBrowser extends Application {

    private TabPane tabPane = new TabPane();
    private TextField urlBar = new TextField();

    @Override
    public void start(Stage stage) {

        // Tabs initialisieren
        tabPane.setStyle(
            "-fx-background-color:linear-gradient(#6db3f2,#1e69de);" +
            "-fx-font-family:Tahoma;"
        );
        createNewTab("https://lite.duckduckgo.com");

        // Neuer Tab Button
        Button newTab = new Button("+");
        newTab.setStyle("-fx-background-color:#ffcc66; -fx-font-weight:bold;");
        newTab.setOnAction(e -> createNewTab("https://lite.duckduckgo.com"));

        // URL Bar
        urlBar.setStyle(
            "-fx-background-color:#f0f0f0;" +
            "-fx-border-color:#888;" +
            "-fx-font-family:Tahoma;"
        );
        urlBar.setOnAction(e -> {
            String url = urlBar.getText();
            if (!url.startsWith("http")) {
                url = "https://" + url;
            }
            getCurrentEngine().load(url);
        });

        // Toolbar HBox (nur + Button und URL Bar)
        HBox toolbar = new HBox(5, newTab, urlBar);
        toolbar.setStyle(
            "-fx-padding:5;" +
            "-fx-background-color:linear-gradient(#6db3f2,#1e69de);"
        );

        // Statusleiste
        Label status = new Label("Namara Retro Browser");
        status.setStyle(
            "-fx-background-color:#c0d8ff;" +
            "-fx-padding:4;" +
            "-fx-font-family:Tahoma;"
        );

        // Root Layout
        BorderPane root = new BorderPane();
        root.setTop(new VBox(tabPane, toolbar));
        root.setBottom(status);
        root.setStyle("-fx-background-color:#d6e4f0;");

        Scene scene = new Scene(root, 1200, 800);

        stage.setTitle("Namara Retro Browser");
        stage.setScene(scene);
        stage.show();
    }

    // Neue Tabs erzeugen
    private void createNewTab(String url) {
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        engine.setJavaScriptEnabled(true);
        engine.load(url);

        Tab tab = new Tab("Neuer Tab");
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);

        // URL Bar und Tabtitel aktualisieren
        engine.locationProperty().addListener((obs, oldVal, newVal) -> {
            urlBar.setText(newVal);
            tab.setText(engine.getTitle() != null ? engine.getTitle() : "Namara");
        });
    }

    // Engine des aktuell ausgewählten Tabs
    private WebEngine getCurrentEngine() {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        if (tab != null) {
            WebView view = (WebView) tab.getContent();
            return view.getEngine();
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}