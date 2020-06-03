package com.yinglishzhi.tools.ui.featurewidget;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.yinglishzhi.tools.ui.featurewidget.base.BaseToolWindowWidget;
import com.yinglishzhi.tools.ui.forms.JsonParseWindowUi;

/**
 * @author LDZ
 * @date 2020/6/3 1:26 下午
 */
public class JsonParseWindowWidget extends BaseToolWindowWidget<JsonParseWindowUi> {

    public JsonParseWindowWidget(Project project, Disposable disposable, ToolWindow toolWindow, JsonParseWindowUi jsonParseWindowUi) {
        super(project, disposable, toolWindow, jsonParseWindowUi);
    }


    public void JsonParseAction() {
        System.out.println("json!!");
    }

}
