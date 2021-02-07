package top.jiangliuhong.fixjson.view;

import top.jiangliuhong.fixjson.component.anno.FXMLCreated;
import top.jiangliuhong.fixjson.component.anno.FXMLMounted;
import top.jiangliuhong.fixjson.component.anno.FXMLView;

@FXMLView("home")
public class HomeView implements IFxmlView {

    @FXMLMounted
    public void test(){
        System.out.println("Mounted");
    }

    @FXMLCreated
    public void test2(){
        System.out.println("created");
    }

}
