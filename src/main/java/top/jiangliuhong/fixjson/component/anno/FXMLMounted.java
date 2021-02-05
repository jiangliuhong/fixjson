package top.jiangliuhong.fixjson.component.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * <p>
 * Mounted
 * </p>
 * 加载完成事件
 * 
 * @author jiangliuhong
 * @date 2021-2-5 11:13
 * @version 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FXMLMounted {}
