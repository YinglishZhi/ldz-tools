package com.yinglishzhi.tools.feature;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.yinglishzhi.tools.action.jsonparse.JsonParseAction;
import com.yinglishzhi.tools.feature.base.BaseFeatureContentFactory;
import com.yinglishzhi.tools.ui.featurewidget.JsonParseWindowWidget;
import com.yinglishzhi.tools.ui.featurewidget.base.BaseToolWindowWidget;
import com.yinglishzhi.tools.ui.forms.JsonParseWindowUi;

/**
 * @author LDZ
 * @date 2020/6/3 1:18 下午
 */
public class JsonParseContent extends BaseFeatureContentFactory<JsonParseWindowUi> {

    public JsonParseContent(ToolWindow toolWindow, Project project) {
        super(toolWindow, project, "JSON Parse");
    }

    @Override
    public JsonParseWindowUi getTui(Project project, Disposable disposable, ToolWindow toolWindow) {
        return new JsonParseWindowUi(project, disposable, toolWindow);
    }

    @Override
    public JsonParseWindowWidget createContent(Content content, ToolWindow toolWindow, JsonParseWindowUi jsonParseWindowUi, Project project) {
        return new JsonParseWindowWidget(project, content, toolWindow, jsonParseWindowUi);
    }

    @Override
    public DefaultActionGroup getDefaultActionGroup(BaseToolWindowWidget<JsonParseWindowUi> toolWindowWidget, JsonParseWindowUi jsonParseWindowUi) {
        DefaultActionGroup group = new DefaultActionGroup();
        JsonParseAction jsonParseAction = new JsonParseAction((JsonParseWindowWidget) toolWindowWidget);
        jsonParseAction.registerCustomShortcutSet(() -> BaseFeatureContentFactory.ALT_LEFT, jsonParseWindowUi.getContent());
        group.add(jsonParseAction);
        return group;
    }
}
