package top.jiangliuhong.fixjson.component.bean;

import javafx.fxml.FXMLLoader;
import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.fixjson.component.anno.FXMLView;

import java.net.URL;

/**
 * FxmlViewInfo <br/>
 *
 * @author jiangliuhong
 * @date 2021/1/31 下午10:02
 * @version 1.0.0
 */
@Getter
@Setter
public class FxmlViewInfo {

    private FXMLView annotation;
    private FXMLLoader fxmlLoader;
    private URL resource;

}
