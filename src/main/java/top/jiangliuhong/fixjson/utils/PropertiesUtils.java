package top.jiangliuhong.fixjson.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import top.jiangliuhong.fixjson.config.properties.FixJsonProperties;

public class PropertiesUtils {

    public static <T> T loadProperties(String path, Class<T> clazz) {
        try (InputStream inputStream = FixJsonProperties.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new RuntimeException(path + " is not found");
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            T t = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String val = (String)properties.get(field.getName());
                setValue(field, t, field.getType(), val);
            }
            return t;
        } catch (IOException e) {
            throw new RuntimeException("读取文件异常", e);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException
            | IllegalAccessException e) {
            throw new RuntimeException("初始化配置类异常", e);
        }
    }

    private static void setValue(Field field, Object obj, Class<?> type, String value) throws IllegalAccessException {
        if (type == String.class) {
            field.set(obj, value);
        } else if (type == Integer.class) {
            field.set(obj, Integer.valueOf(value));
        }
    }

    public static void main(String[] args) throws Exception {
        String path = "fixjson.properties";
        FixJsonProperties fixJsonProperties = loadProperties(path, FixJsonProperties.class);
        System.out.println(fixJsonProperties);
    }
}
