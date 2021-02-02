package top.jiangliuhong.fixjson.component.bean;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import lombok.Getter;
import lombok.Setter;
import top.jiangliuhong.fixjson.component.anno.FXMLView;

/**
 * FxmlViewInfo <br/>
 * fxml试图对象信息
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
