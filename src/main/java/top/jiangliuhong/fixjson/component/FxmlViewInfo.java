package top.jiangliuhong.fixjson.component;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.component.anno.FXMLView;
import top.jiangliuhong.fixjson.constants.FixJsonConstants;

/**
 * FxmlViewInfo <br/>
 * fxml试图对象信息
 * 
 * @author jiangliuhong
 * @date 2021/1/31 下午10:02
 * @version 1.0.0
 */
@Getter
@Setter
@Slf4j
public class FxmlViewInfo {

    private FXMLView annotation;
    private URL resource;
    private Class<?> clazz;
    private String fxml;
    private ResourceBundle bundle;
    private ApplicationContextBean contextBean;

    /**
     * 展示view
     */
    public void showView() {
        Parent root = this.getView();
        Scene scene = root.getScene();
        if (scene == null) {
            scene = new Scene(this.getView());
        }
        GUIState state = contextBean.getState();
        state.getStage().setScene(scene);
        state.getStage().show();
    }

    /**
     * 获取jfx面板对象
     * 
     * @return Parent
     */
    public Parent getView() {
        FXMLLoader fxmlLoader = ensureFxmlLoaderInitialized();
        final Parent parent = fxmlLoader.getRoot();
        loadCssFile(parent);
        return parent;
    }

    /**
     * 初始化fxmlLoader
     */
    private FXMLLoader ensureFxmlLoaderInitialized() {
        try {
            return loadSynchronously(resource, bundle);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FXMLLoader loadSynchronously(final URL resource, ResourceBundle bundle) throws IllegalStateException,
        NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final FXMLLoader loader = new FXMLLoader(resource, bundle);
        Constructor<?> constructor = clazz.getConstructor();
        loader.setController(constructor.newInstance());
        try {
            loader.load();
        } catch (final IOException | IllegalStateException e) {
            throw new IllegalStateException("Cannot load " + this.getFxml(), e);
        }
        return loader;
    }

    /**
     * 加载css
     * 
     * @param parent parent
     */
    private void loadCssFile(final Parent parent) {
        List<String> cssPath = contextBean.getCssPath();
        if (cssPath != null && !cssPath.isEmpty()) {
            cssPath.forEach(css -> parent.getStylesheets().add(getClass().getResource(css).toExternalForm()));
        }
        loadCssFileByCustom(parent);
        String fxmlCss = StringUtils.replace(this.fxml, FixJsonConstants.FXML_SUFFIX, FixJsonConstants.CSS_SUFFIX);
        final URL uri = getClass().getResource(fxmlCss);
        if (uri == null) {
            return;
        }
        final String uriToCss = uri.toExternalForm();
        parent.getStylesheets().add(uriToCss);
    }

    private void loadCssFileByCustom(final Parent parent) {
        if (annotation.css().length > 0) {
            for (final String cssFile : annotation.css()) {
                final URL uri = getClass().getResource(cssFile);
                if (uri != null) {
                    final String uriToCss = uri.toExternalForm();
                    parent.getStylesheets().add(uriToCss);
                    log.debug("css file added to parent: {}", cssFile);
                } else {
                    log.warn("referenced {} css file could not be located", cssFile);
                }
            }
        }
    }

    /**
     * view方法类
     */
    @Getter
    @Setter
    static class ViewMethod {
        private Method created;
        private Method mounted;
    }

}
