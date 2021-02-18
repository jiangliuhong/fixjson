package top.jiangliuhong.fixjson;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.component.ApplicationContext;
import top.jiangliuhong.fixjson.component.GUIState;
import top.jiangliuhong.fixjson.component.IStageConfig;
import top.jiangliuhong.fixjson.component.anno.FXMLView;
import top.jiangliuhong.fixjson.utils.ClassUtils;
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
    /** 本次执行的args参数 */
    private static String[] args = new String[0];
    /** 配置 */
    private static IStageConfig config;
    /** 错误提示 */
    private static Consumer<Throwable> errorAction = defaultErrorAction();

    public static void launch(final IStageConfig config, final Class<? extends Application> appClass,
        final String[] args) {
        log.info("启动FIXJSON");
        FixJsonApplication.config = config;
        FixJsonApplication.args = args;
        Application.launch(appClass);
    }

    @Override
    public void init() throws Exception {
        CompletableFuture.runAsync(() -> {
            log.info("开始初始化");
            ApplicationContext.init();
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
        Stage splashStage = new Stage(StageStyle.TRANSPARENT);
        // 显示过度窗口
        log.info("过滤窗口开启");
        ISplashScreen splashScreen = config.splashScreen();
        if (splashScreen == null) {
            splashScreen = new SplashScreen();
        }
        Scene splashScene = new Scene(splashScreen.getParent(), Color.TRANSPARENT);
        splashStage.setScene(splashScene);
        splashStage.initStyle(StageStyle.TRANSPARENT);
        splashStage.show();
        splashIsShowing.complete(() -> {
            try {
                // 已经初始化完成后的处理
                // 设置主窗口对象
                // 初始化首页
                GUIState state = ApplicationContext.getState();
                state.setStage(primaryStage);
                state.setMenus(config.menus());
                Stage stage = state.getStage();
                stage.setIconified(true);
                stage.setResizable(true);
                stage.setHeight(ApplicationContext.getProperties().getHeight());
                stage.setWidth(ApplicationContext.getProperties().getWidth());
                stage.setTitle(ApplicationContext.getProperties().getTitle());
                stage.initStyle(StageStyle.DECORATED);
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, stage.getHeight(), stage.getWidth(), Color.WHITE);
                MenuBar menuBar = new MenuBar();
                root.setTop(menuBar);
                Menu menu = new Menu("File");
                menu.getItems().add(new MenuItem("New"));
                menu.getItems().add(new MenuItem("Save"));
                menu.getItems().add(new SeparatorMenuItem());
                menu.getItems().add(new MenuItem("Exit"));
                menuBar.getMenus().addAll(menu);
                menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
                Pane pane = new Pane();
                pane.prefWidthProperty().bind(primaryStage.widthProperty());
                pane.prefWidthProperty().bind(primaryStage.heightProperty());
                root.setCenter(pane);

                state.setParent(pane);
                state.show(ApplicationContext.getView(config.homeView()));
                primaryStage.setScene(scene);
                primaryStage.show();
                //state.showView(ApplicationContext.getView(config.homeView()));
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
