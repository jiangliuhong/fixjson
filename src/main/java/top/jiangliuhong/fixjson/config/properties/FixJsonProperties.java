package top.jiangliuhong.fixjson.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * FixJsonProperties <br/>
 * 配置项
 * 
 * @author jiangliuhong
 * @date 2021/1/30 下午11:52
 * @version 1.0.0
 */
@Setter
@Getter
@ToString
public class FixJsonProperties {

    /**
     * title
     */
    private String title;
    private Integer width = 400;
    private Integer height = 400;
    private String css;
    /**
     * 基础fxml存放路径
     */
    private String baseView = "/views";

}
