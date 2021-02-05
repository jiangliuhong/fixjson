package top.jiangliuhong.fixjson.component.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.stage.StageStyle;

/**
 * FXMLController <br/>
 * 标记为FXML视图
 * 
 * @author jiangliuhong
 * @date 2021/1/30 下午11:13
 * @version 1.0.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLView {
    /**
     * fxml地址
     * 
     * @return fxml地址
     */
    String value();

    /**
     * 国际化配置
     * 
     * @return bundle
     */
    String bundle() default "";

    /**
     * 国际化配置对应编码
     * 
     * @return encoding
     */
    String encoding() default "UTF-8";

    /**
     * 窗口标题
     * 
     * @return title
     */
    String title() default "";

    /**
     * 窗口样式
     * 
     * @return stageStyle
     */
    StageStyle stageStyle() default StageStyle.UTILITY;

    /**
     * css样式文件
     * 
     * @return css
     */
    String[] css() default {};
}
