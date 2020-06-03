package com.yinglishzhi.tools.ui.featurewidget;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.yinglishzhi.tools.ui.featurewidget.base.BaseToolWindowWidget;
import com.yinglishzhi.tools.ui.forms.TimeStampWindowUi;

/**
 * @author LDZ
 * @date 2020/6/3 1:26 下午
 */
public class TimeStampWindowWidget extends BaseToolWindowWidget<TimeStampWindowUi> {

    public TimeStampWindowWidget(Project project, Disposable disposable, ToolWindow toolWindow, TimeStampWindowUi timeStampWindowUi) {
        super(project, disposable, toolWindow, timeStampWindowUi);
    }

    public void timeStampAction() {
        System.out.println(System.currentTimeMillis());
    }

}
