package top.jiangliuhong.fixjson.component;

import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.component.anno.FXMLView;
import top.jiangliuhong.fixjson.component.bean.FxmlViewInfo;
import top.jiangliuhong.fixjson.component.bean.GUIState;
import top.jiangliuhong.fixjson.config.properties.FixJsonProperties;
import top.jiangliuhong.fixjson.utils.ClassUtils;
import top.jiangliuhong.fixjson.utils.PropertiesUtils;

/**
 * 
 * <p>
 * ApplicationContext
 * </p>
 * 应用上下文工具
 * 
 * @author jiangliuhong
 * @date 2021-2-4 20:50
 * @version 1.0.0
 */
@Slf4j
public final class ApplicationContext {

    private static ApplicationContextBean contextBean;
    private static String configPath = "fixjson.properties";

    public static ApplicationContextBean init() {
        FixJsonProperties properties = PropertiesUtils.loadProperties(configPath, FixJsonProperties.class);
        log.info("加载配置项成功,配置文件为：" + configPath);
        contextBean = new ApplicationContextBean(properties);
        return contextBean;
    }

    public static FixJsonProperties getProperties() {
        return contextBean.getProperties();
    }

    public static GUIState getState() {
        return contextBean.getState();
    }

    public static void loadView(FXMLView annotation,final Class<?> clazz) {
        FxmlViewInfo info = new FxmlViewInfo();
        info.setAnnotation(annotation);
        String filePathFromPackageName = ClassUtils.determineFilePathFromPackageName(clazz);
        contextBean.getViews().put(clazz.getName(),info);
    }

}
