package top.jiangliuhong.fixjson;

import top.jiangliuhong.fixjson.view.HomeView;
import top.jiangliuhong.fixjson.view.SplashScreen;

/**
 * FixJsonAppMain <br/>
 * 启动入口
 * 
 * @author jiangliuhong
 * @date 2021/2/4 下午10:36
 * @version 1.0.0
 */
public class FixJsonAppMain {

    public static void main(String[] args) {
        FixJsonApplication.launch(FixJsonApplication.class, new SplashScreen(), HomeView.class, args);
    }
}
