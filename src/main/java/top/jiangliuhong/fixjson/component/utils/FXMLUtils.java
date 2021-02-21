package top.jiangliuhong.fixjson.component.utils;

import org.apache.commons.lang3.StringUtils;

import top.jiangliuhong.fixjson.component.enums.SystemType;

/**
 * FXMLUtils <br/>
 *
 * @author jiangliuhong
 * @date 2021/2/21 下午10:36
 * @version 1.0.0
 */
public final class FXMLUtils {
    private FXMLUtils() {}

    /**
     * 获取操作系统类型
     * 
     * @return SystemType
     */
    public static SystemType systemType() {
        String os = System.getProperty("os.name");
        if (StringUtils.startsWith(os, "Mac")) {
            return SystemType.MAC;
        } else if (StringUtils.startsWith(os, "Linux")) {
            return SystemType.LINUX;
        } else {
            return SystemType.WINDOWS;
        }
    }
}
