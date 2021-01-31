package top.jiangliuhong.fixjson.supprot;

import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SplashScreen {

    private static String DEFAULT_IMAGE = "/static/img/fixjson.png";

    public Parent getParent() {
        final ImageView imageView = new ImageView(getClass().getResource(DEFAULT_IMAGE).toExternalForm());
        final ProgressBar splashProgressBar = new ProgressBar();
        splashProgressBar.setPrefWidth(imageView.getImage().getWidth());

        final VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView, splashProgressBar);

        return vbox;
    }

    public boolean visible() {
        return true;
    }
}
