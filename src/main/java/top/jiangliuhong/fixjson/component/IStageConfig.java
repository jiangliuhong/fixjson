package top.jiangliuhong.fixjson.component;

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

}
