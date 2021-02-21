package top.jiangliuhong.fixjson.view;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.component.anno.FXMLCreated;
import top.jiangliuhong.fixjson.component.anno.FXMLMounted;
import top.jiangliuhong.fixjson.component.anno.FXMLView;
import top.jiangliuhong.fixjson.component.enums.SystemType;
import top.jiangliuhong.fixjson.component.utils.FXMLUtils;

/**
 * HomeView <br/>
 * 首页
 * 
 * @author jiangliuhong
 * @date 2021/2/21 下午9:57
 * @version 1.0.0
 */
@Slf4j
@FXMLView("home")
public class HomeView implements IFxmlView {

    @FXML
    private Pane pane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TabPane tabPane;

    @FXMLMounted
    public void mounted() {
        menuBar.getMenus().addAll(menus());
        menuBar.prefWidthProperty().bind(pane.widthProperty());
        tabPane.prefWidthProperty().bind(pane.widthProperty());
        tabPane.prefHeightProperty().bind(pane.heightProperty());
        if (SystemType.MAC.equals(FXMLUtils.systemType())) {
            menuBar.useSystemMenuBarProperty().set(true);
        } else {
            Insets tabInsets = tabPane.paddingProperty().getValue();
            double insetsTop = tabInsets.getTop() + menuBar.getHeight();
            tabPane.paddingProperty()
                .setValue(new Insets(insetsTop, tabInsets.getRight(), tabInsets.getBottom(), tabInsets.getLeft()));
        }
    }

    @FXMLCreated
    public void created() {

    }

    private List<Menu> menus() {
        List<Menu> menus = new ArrayList<>();
        Menu fileMenu = new Menu("File");
        menus.add(fileMenu);
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        newMenuItem.setOnAction(actionEvent -> {
            log.info("New click");
        });
        saveMenuItem.setOnAction(actionEvent -> {
            log.info("Save click");
        });
        exitMenuItem.setOnAction(actionEvent -> {
            log.info("Exit click");
            Platform.exit();
        });
        fileMenu.getItems().addAll(newMenuItem, saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
        Menu helpMenu = new Menu("Help");
        menus.add(helpMenu);
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("关于FIXJSON");
            alert.setContentText("FIXJSON是一个使用Java开发的跨平台的JSON编辑工具");
            alert.show();
        });
        helpMenu.getItems().add(aboutMenuItem);
        return menus;
    }

}
