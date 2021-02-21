package top.jiangliuhong.fixjson.config;

import top.jiangliuhong.fixjson.component.IStageConfig;
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

}
