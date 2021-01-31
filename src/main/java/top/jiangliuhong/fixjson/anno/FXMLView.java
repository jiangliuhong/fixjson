package top.jiangliuhong.fixjson.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * FXMLController <br/>
 * 标记为FXML视图
 * 
 * @author jiangliuhong
 * @date 2021/1/30 下午11:13
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLView {
    /**
     * fxml地址
     * 
     * @return fxml地址
     */
    String value();

}
