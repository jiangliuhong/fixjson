package top.jiangliuhong.fixjson.supprot;

import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.config.properties.FixJsonProperties;
import top.jiangliuhong.fixjson.utils.PropertiesUtils;

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

}
