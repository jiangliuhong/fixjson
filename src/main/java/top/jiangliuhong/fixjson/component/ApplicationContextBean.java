package top.jiangliuhong.fixjson.component;

import lombok.Getter;
import top.jiangliuhong.fixjson.component.bean.GUIState;
import top.jiangliuhong.fixjson.config.properties.FixJsonProperties;

@Getter
public class ApplicationContextBean {

    public final FixJsonProperties properties;
    public final GUIState state;

    public ApplicationContextBean(FixJsonProperties properties) {
        this.properties = properties;
        this.state = new GUIState();
        this.state.setTitle(this.properties.getTitle());
    }
}
