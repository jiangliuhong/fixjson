package top.jiangliuhong.fixjson;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.supprot.ApplicationContext;

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

    public static void main(String[] args) {
        log.info("启动FIXJSON");
        Application.launch(FixJsonApplication.class);
    }

    @Override
    public void init() throws Exception {
        ApplicationContext.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 设置程序标题
        // fixJsonPropprimaryStage.setTitle(fixJsonProperties.getTitle());
        String title = ApplicationContext.getProperties().getTitle();
        primaryStage.setTitle(title);

        // 设置窗体宽高
        primaryStage.setWidth(200);
        primaryStage.setHeight(200);

        // 设置窗口模式
        primaryStage.initStyle(StageStyle.UNIFIED);// 正常模式

        // 显示窗口
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
