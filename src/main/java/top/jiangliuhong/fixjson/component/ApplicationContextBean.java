package top.jiangliuhong.fixjson.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
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
        viewInfo.setContextBean(this);
        this.views.put(viewName, viewInfo);
    }

    /**
     * 获取css文件地址
     * 
     * @return List<String>
     */
    public List<String> getCssPath() {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(this.properties.getCss())) {
            String[] split = this.properties.getCss().split(";");
            list.addAll(Arrays.asList(split));
        }
        return list;
    }

}
