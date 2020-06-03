package com.yinglishzhi.tools.action.jsonparse;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.yinglishzhi.tools.ui.featurewidget.JsonParseWindowWidget;
import org.jetbrains.annotations.NotNull;

/**
 * @author LDZ
 * @date 2020/6/3 2:33 下午
 */
public class JsonParseAction extends AnAction {

    private final JsonParseWindowWidget jsonParseWindowWidget;

    public JsonParseAction(JsonParseWindowWidget jsonParseWindowWidget) {
        super("json parse action", "json parse", AllIcons.General.ArrowUp);
        this.jsonParseWindowWidget = jsonParseWindowWidget;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        jsonParseWindowWidget.JsonParseAction();
    }
}
