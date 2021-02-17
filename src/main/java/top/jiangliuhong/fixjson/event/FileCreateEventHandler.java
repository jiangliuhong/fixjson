package top.jiangliuhong.fixjson.event;

import javafx.event.ActionEvent;
import lombok.extern.slf4j.Slf4j;
import top.jiangliuhong.fixjson.component.handler.ActionEventHandler;

@Slf4j
public class FileCreateEventHandler extends ActionEventHandler {

    @Override
    public void handle(ActionEvent actionEvent) {
        log.info("create");
    }
}
