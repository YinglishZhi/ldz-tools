package com.yinglishzhi.tools.feature;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.yinglishzhi.tools.action.timestamp.TimeStampAction;
import com.yinglishzhi.tools.feature.base.BaseFeatureContentFactory;
import com.yinglishzhi.tools.ui.featurewidget.TimeStampWindowWidget;
import com.yinglishzhi.tools.ui.featurewidget.base.BaseToolWindowWidget;
import com.yinglishzhi.tools.ui.forms.TimeStampWindowUi;

/**
 * @author LDZ
 * @date 2020/6/3 2:12 下午
 */
public class TimeStampContent extends BaseFeatureContentFactory<TimeStampWindowUi> {

    public TimeStampContent(ToolWindow toolWindow, Project project) {
        super(toolWindow, project, "Time Stamp");
    }

    @Override
    public TimeStampWindowUi getTui(Project project, Disposable disposable, ToolWindow toolWindow) {
        return new TimeStampWindowUi(project, disposable, toolWindow);
    }

    @Override
    public BaseToolWindowWidget<TimeStampWindowUi> createContent(Content content, ToolWindow toolWindow, TimeStampWindowUi timeStampWindowUi, Project project) {
        return new TimeStampWindowWidget(project, content, toolWindow, timeStampWindowUi);
    }

    @Override
    public DefaultActionGroup getDefaultActionGroup(BaseToolWindowWidget<TimeStampWindowUi> toolWindowWidget, TimeStampWindowUi timeStampWindowUi) {
        DefaultActionGroup group = new DefaultActionGroup();
        TimeStampAction touchFishAction = new TimeStampAction((TimeStampWindowWidget)
                toolWindowWidget);
        touchFishAction.registerCustomShortcutSet(() -> BaseFeatureContentFactory.ALT_LEFT, timeStampWindowUi.getContent());
        group.add(touchFishAction);
        return group;
    }


}
