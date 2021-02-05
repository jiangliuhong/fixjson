package top.jiangliuhong.fixjson.component;

import static java.util.ResourceBundle.getBundle;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.component.anno.FXMLView;
import top.jiangliuhong.fixjson.config.properties.FixJsonProperties;
import top.jiangliuhong.fixjson.constants.FixJsonConstants;
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

    public static void init() {
        FixJsonProperties properties = PropertiesUtils.loadProperties(configPath, FixJsonProperties.class);
        log.info("加载配置项成功,配置文件为：" + configPath);
        contextBean = new ApplicationContextBean(properties);
    }

    public static FixJsonProperties getProperties() {
        return contextBean.getProperties();
    }

    public static GUIState getState() {
        return contextBean.getState();
    }

    public static FxmlViewInfo getView(Class<?> clazz) {
        if (clazz == null) {
            throw new RuntimeException("view class is not be null");
        }
        return contextBean.getViews().get(clazz.getName());
    }

    /**
     * 根据class加载view
     * 
     * @param annotation view注解
     * @param clazz class
     */
    public static void loadView(FXMLView annotation, final Class<?> clazz) {
        FxmlViewInfo info = new FxmlViewInfo();
        info.setAnnotation(annotation);
        info.setClazz(clazz);
        info.setBundle(getResourceBundle(annotation, clazz));
        String fxmlPath = annotation.value();
        if (StringUtils.isBlank(fxmlPath)) {
            fxmlPath = ClassUtils.determineFilePathFromPackageName(clazz);
            fxmlPath += clazz.getSimpleName();
        } else {
            fxmlPath = contextBean.getProperties().getBaseView() + "/" + fxmlPath;
        }
        if (!StringUtils.endsWith(fxmlPath, FixJsonConstants.FXML_SUFFIX)) {
            fxmlPath += FixJsonConstants.FXML_SUFFIX;
        }
        info.setFxml(fxmlPath);
        URL resource = ApplicationContext.class.getResource(fxmlPath);
        info.setResource(resource);
        contextBean.addView(clazz.getName(), info);
    }

    private static ResourceBundle getResourceBundle(FXMLView annotation, Class<?> clazz) {
        try {
            String bundleName;
            if (StringUtils.isEmpty(annotation.bundle())) {
                bundleName = clazz.getPackage().getName() + "." + clazz.getSimpleName();
            } else {
                bundleName = annotation.bundle();
            }
            ResourceBundleControl control = new ResourceBundleControl(Charset.forName(annotation.encoding()));
            return getBundle(bundleName, control);
        } catch (MissingResourceException ex) {
            log.error("No resource bundle could be determined:" + ex.getMessage());
            return null;
        }
    }

}
