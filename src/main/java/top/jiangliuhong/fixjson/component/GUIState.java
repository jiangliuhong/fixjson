package top.jiangliuhong.fixjson.component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

/**
 * GUIState <br/>
 * 窗口状态
 * 
 * @author jiangliuhong
 * @date 2021/1/31 下午7:22
 * @version 1.0.0
 */
@Getter
@Setter
public class GUIState {
    private Scene scene;
    private String title;
    private Stage stage;

    /**
     * 展示view
     * 
     * @param view view
     */
    public void showView(FxmlViewInfo view) {
        Parent root = view.getView();
        Scene scene = root.getScene();
        if (scene == null) {
            scene = new Scene(view.getView());
        }

        this.stage.setScene(scene);
        Method mounted = view.getViewMethod().getMounted();
        if (mounted != null) {
            try {
                mounted.invoke(view.getFxmlLoader().getController());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("class " + view.getClazz().getName() + " mounted error", e);
            }
        }
        this.stage.show();

    }

}
