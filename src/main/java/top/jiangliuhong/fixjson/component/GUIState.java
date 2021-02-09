package top.jiangliuhong.fixjson.component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
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
    private List<Menu> menus;
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
        if (this.menus != null && this.menus.size() > 0) {
            // 设置菜单
            ObservableList<Node> children = null;
            if (root instanceof Pane) {
                children = ((Pane)root).getChildren();
            } else if (root instanceof Group) {
                children = ((Group)root).getChildren();
            }
            if (children != null) {
                MenuBar menuBar = new MenuBar();
                menuBar.getMenus().addAll(this.menus);
                menuBar.setUseSystemMenuBar(true);
                children.add(menuBar);
            }
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
