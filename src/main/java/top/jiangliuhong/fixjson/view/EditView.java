package top.jiangliuhong.fixjson.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.component.anno.FXMLView;

/**
 * EditView <br/>
 * json编辑窗口
 * 
 * @author jiangliuhong
 * @date 2021/2/21 下午11:17
 * @version 1.0.0
 */
@Slf4j
@FXMLView("edit")
public class EditView implements Initializable {

    @FXML
    private AnchorPane leftPane;
    @FXML
    private TextArea textArea;
    @FXML
    private AnchorPane rightPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
