package top.jiangliuhong.fixjson.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 
 * <p>
 * ClassUtils
 * </p>
 * 类相关工具类
 * 
 * @author jiangliuhong
 * @date 2021-2-4 20:57
 * @version 1.0.0
 */
public final class ClassUtils {

    private ClassUtils() {}

    /**
     * 从包名确定文件路径从类包实例创建文件路径等价。路径将以斜杠作为前缀和后缀。
     *
     * @param clazz 类
     * @return 一个包结构对应的路径
     */
    public static String determineFilePathFromPackageName(final Class<?> clazz) {
        return "/" + clazz.getPackage().getName().replace('.', '/') + "/";
    }

    /**
     * 遍历包
     * 
     * @param packageName 包名
     * @return 这个包下所有的类名称
     */
    public static Set<String> packageEach(String packageName) {
        Set<String> classNameSets = new HashSet<>();
        String packagePath = packageName.replace(".", "/");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String protocol = url.getProtocol();
            if (protocol.equals("file")) {
                classNameSets.addAll(packageEach(url.getPath(), packageName));
            } else if (protocol.equals("jar")) {
                JarFile jarFile;
                try {
                    jarFile = ((JarURLConnection)url.openConnection()).getJarFile();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (jarFile != null) {
                    classNameSets.addAll(packageEachJar(jarFile.entries(), packageName));
                }
            }
        }
        return classNameSets;
    }

    private static Set<String> packageEachJar(Enumeration<JarEntry> jarEntries, String packageName) {
        Set<String> classNames = new HashSet<>();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if (!jarEntry.isDirectory()) {
                // 这里是为了方便，先把"/" 转成 "." 再判 .class $
                String entryName = jarEntry.getName().replace("/", ".");
                if (entryName.endsWith(".class") && !entryName.contains("$") && entryName.startsWith(packageName)) {
                    entryName = entryName.replace(".class", "");
                    classNames.add(entryName);
                }
            }
        }
        return classNames;
    }

    private static Set<String> packageEachJars(URL[] urls, String packageName) {
        Set<String> classNames = new HashSet<>();
        for (URL url : urls) {
            String classPath = url.getPath();
            // 不必搜索classes文件
            if (classPath.endsWith("classes/")) {
                continue;
            }
            JarFile jarFile;
            try {
                jarFile = new JarFile(classPath.substring(classPath.indexOf("/")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            classNames.addAll(packageEachJar(jarFile.entries(), packageName));
        }
        return classNames;
    }

    private static Set<String> packageEach(String filePath, String packageName) {
        Set<String> classNameSets = new HashSet<>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        if (files == null) {
            return classNameSets;
        }
        for (File childFile : files) {
            if (childFile.isDirectory()) {
                String pack = packageName + "." + childFile.getName();
                Set<String> res = packageEach(childFile.getPath(), pack);
                classNameSets.addAll(res);
            } else {
                String fileName = childFile.getName();
                // endsWith() 方法用于测试字符串是否以指定的后�?结束�? !fileName.contains("$") 文件名中不包�? '$'
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    String classPath = packageName + "." + fileName.replace(".class", "");
                    classNameSets.add(classPath);
                }
            }
        }
        return classNameSets;
    }

}
