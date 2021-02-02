package top.jiangliuhong.fixjson;

import java.util.concurrent.CompletableFuture;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.component.ApplicationContext;
import top.jiangliuhong.fixjson.view.HomeView;
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
    private static Class<? extends IFxmlView>  homeView;
    private static String[] args = new String[0];
    private static SplashScreen splashScreen;

    public static void main(String[] args) {
        log.info("启动FIXJSON");
        launch(FixJsonApplication.class, new SplashScreen(), HomeView.class, args);
    }

    public static void launch(final Class<? extends Application> appClass, final ISplashScreen splashScreen,
        final Class<? extends IFxmlView> view, final String[] args) {
        Application.launch(FixJsonApplication.class);
        FixJsonApplication.homeView = view;
        FixJsonApplication.args = args;
    }

    @Override
    public void init() throws Exception {

        CompletableFuture.runAsync(ApplicationContext::init).whenComplete((contextBean, throwable) -> {

        }).thenAcceptBothAsync(splashIsShowing, (contextBean, closeSplash) -> {
            Platform.runLater(closeSplash);
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ApplicationContext.getState().setStage(primaryStage);
        Stage splashStage = new Stage(StageStyle.TRANSPARENT);

        // 显示过度窗口
        SplashScreen splash = new SplashScreen();
        Scene splashScene = new Scene(splash.getParent(), Color.TRANSPARENT);
        splashStage.setScene(splashScene);
        splashStage.initStyle(StageStyle.TRANSPARENT);
        splashStage.show();

        splashIsShowing.complete(() -> {
            // 初始化首页

            // 关闭过度窗口
            splashStage.hide();
            splashStage.setScene(null);
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
