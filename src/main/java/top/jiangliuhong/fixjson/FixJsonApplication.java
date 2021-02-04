package top.jiangliuhong.fixjson;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.component.ApplicationContext;
import top.jiangliuhong.fixjson.component.anno.FXMLView;
import top.jiangliuhong.fixjson.component.FxmlViewInfo;
import top.jiangliuhong.fixjson.component.GUIState;
import top.jiangliuhong.fixjson.utils.ClassUtils;
import top.jiangliuhong.fixjson.view.IFxmlView;
import top.jiangliuhong.fixjson.view.ISplashScreen;
import top.jiangliuhong.fixjson.view.SplashScreen;

/**
 * AppMain <br/>
 * 应用主入口
 * 
 * @author jiangliuhong
 * @date 2021/1/30 下午11:21
 * @version 1.0.0
 */
@Slf4j
public class FixJsonApplication extends Application {

    /**
     * 开场动画回调线程
     */
    private final CompletableFuture<Runnable> splashIsShowing = new CompletableFuture<>();
    /** 启动时展示的首页 */
    private static Class<? extends IFxmlView> homeView;
    /** 本次执行的args参数 */
    private static String[] args = new String[0];
    /** 过度动画 */
    private static ISplashScreen splashScreen;
    /** 错误提示 */
    private static Consumer<Throwable> errorAction = defaultErrorAction();

    public static void launch(final Class<? extends Application> appClass, final ISplashScreen splashScreen,
        final Class<? extends IFxmlView> view, final String[] args) {
        log.info("启动FIXJSON");
        FixJsonApplication.homeView = view;
        FixJsonApplication.args = args;
        FixJsonApplication.splashScreen = splashScreen;
        Application.launch(appClass);
    }

    @Override
    public void init() throws Exception {
        CompletableFuture.runAsync(() -> {
            log.info("开始初始化");
            ApplicationContext.init();
            if (splashScreen == null) {
                FixJsonApplication.splashScreen = new SplashScreen();
            }
            String packageName = getClass().getPackageName();
            log.info("开始扫描包:{}", packageName);
            Set<String> beanClassNames = ClassUtils.packageEach(packageName);
            beanClassNames.forEach(beanClassName -> {
                try {
                    Class<?> beanClass = Thread.currentThread().getContextClassLoader().loadClass(beanClassName);
                    FXMLView fxmlViewAnnotation = beanClass.getAnnotation(FXMLView.class);
                    if (fxmlViewAnnotation != null) {
                        log.info("开始加载view类:{}", beanClassName);
                        ApplicationContext.loadView(fxmlViewAnnotation, beanClass);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }).whenComplete((contextBean, throwable) -> {
            // 结束初始化 初始化结束后做异常处理
            if (throwable != null) {
                log.error("初始化异常: ", throwable);
                Platform.runLater(() -> errorAction.accept(throwable));
            }
        }).thenAcceptBothAsync(splashIsShowing, (contextBean, closeSplash) -> {
            Platform.runLater(closeSplash);
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext.getState().setStage(primaryStage);
        Stage splashStage = new Stage(StageStyle.TRANSPARENT);
        // 显示过度窗口
        log.info("过滤窗口开启");
        Scene splashScene = new Scene(splashScreen.getParent(), Color.TRANSPARENT);
        splashStage.setScene(splashScene);
        splashStage.initStyle(StageStyle.TRANSPARENT);
        splashStage.show();
        splashIsShowing.complete(() -> {
            try {
                // 初始化首页
                GUIState guiState = ApplicationContext.getState();
                Stage stage = guiState.getStage();
                stage.setIconified(true);
                stage.setResizable(true);
                stage.setHeight(ApplicationContext.getProperties().getHeight());
                stage.setWidth(ApplicationContext.getProperties().getWidth());
                stage.setTitle(ApplicationContext.getProperties().getTitle());
                stage.initStyle(StageStyle.DECORATED);
                FxmlViewInfo view = ApplicationContext.getView(homeView);
                Scene scene = view.getView().getScene() != null ? view.getView().getScene() : new Scene(view.getView());
                stage.setScene(scene);
                stage.show();
            } catch (Throwable t) {
                log.error("Failed to load application: ", t);
                errorAction.accept(t);
            }
            // 关闭过度窗口
            log.info("过滤窗口关闭");
            splashStage.hide();
            splashStage.setScene(null);
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private static Consumer<Throwable> defaultErrorAction() {
        return e -> {
            String message = "FIXJSON ERROR,MESSAGE:" + e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR, message);
            alert.showAndWait().ifPresent(response -> Platform.exit());
        };
    }
}
