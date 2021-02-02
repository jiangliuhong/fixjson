package top.jiangliuhong.fixjson.component;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import top.jiangliuhong.fixjson.component.bean.FxmlViewInfo;
import top.jiangliuhong.fixjson.component.bean.GUIState;
import top.jiangliuhong.fixjson.config.properties.FixJsonProperties;

@Getter
public class ApplicationContextBean {

    /**
     * 配置文件
     */
    public final FixJsonProperties properties;
    /**
     * 存储当前窗口状态
     */
    public final GUIState state;
    /**
     * 存储视图信息
     */
    public final Map<String, FxmlViewInfo> views;

    public ApplicationContextBean(FixJsonProperties properties) {
        this.properties = properties;
        this.state = new GUIState();
        this.state.setTitle(this.properties.getTitle());
        this.views = new HashMap<>();
    }

    public void addView(String viewName, FxmlViewInfo viewInfo) {
        this.views.put(viewName, viewInfo);
    }
}
