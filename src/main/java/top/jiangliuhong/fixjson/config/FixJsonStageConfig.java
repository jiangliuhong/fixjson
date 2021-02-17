package top.jiangliuhong.fixjson.config;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import top.jiangliuhong.fixjson.component.IStageConfig;
import top.jiangliuhong.fixjson.event.FileCreateEventHandler;
import top.jiangliuhong.fixjson.view.HomeView;
import top.jiangliuhong.fixjson.view.IFxmlView;
import top.jiangliuhong.fixjson.view.ISplashScreen;
import top.jiangliuhong.fixjson.view.SplashScreen;

/**
 * FixJsonStageConfig <br/>
 * 界面配置
 * 
 * @author jiangliuhong
 * @date 2021/2/9 下午10:59
 * @version 1.0.0
 */
public class FixJsonStageConfig implements IStageConfig {
    @Override
    public ISplashScreen splashScreen() {
        return new SplashScreen();
    }

    @Override
    public Class<? extends IFxmlView> homeView() {
        return HomeView.class;
    }

    @Override
    public List<Menu> menus() {
        List<Menu> menus = new ArrayList<>();
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        newMenuItem.setOnAction(actionEvent -> new FileCreateEventHandler().handle(actionEvent));
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().addAll(newMenuItem, saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menus.add(fileMenu);
        return menus;
    }
}
