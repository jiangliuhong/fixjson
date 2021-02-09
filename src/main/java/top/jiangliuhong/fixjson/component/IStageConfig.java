package top.jiangliuhong.fixjson.component;

import java.util.List;


import javafx.scene.control.Menu;
import top.jiangliuhong.fixjson.view.IFxmlView;
import top.jiangliuhong.fixjson.view.ISplashScreen;

public interface IStageConfig {

    /**
     * 首页动画
     * 
     * @return 首页动画
     */
    ISplashScreen splashScreen();

    /**
     * 首页View
     * 
     * @return 首页View
     */
    Class<? extends IFxmlView> homeView();

    /**
     * 菜单
     * 
     * @return 菜单
     */
    List<Menu> menus();

}
