package top.jiangliuhong.fixjson.supprot;

import lombok.Getter;
import top.jiangliuhong.fixjson.config.properties.FixJsonProperties;

@Getter
public class ApplicationContextBean {

    public final FixJsonProperties properties;

    public ApplicationContextBean(FixJsonProperties properties) {
        this.properties = properties;
    }
}
