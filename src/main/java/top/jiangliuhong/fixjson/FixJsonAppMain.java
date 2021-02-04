package top.jiangliuhong.fixjson;

import top.jiangliuhong.fixjson.view.HomeView;
import top.jiangliuhong.fixjson.view.SplashScreen;

public class FixJsonAppMain {

    public static void main(String[] args) {
        FixJsonApplication.launch(FixJsonApplication.class, new SplashScreen(), HomeView.class, args);
    }
}
