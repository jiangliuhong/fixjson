package top.jiangliuhong.fixjson.component;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

/**
 * GUIState <br/>
 * 窗口状态
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

}
