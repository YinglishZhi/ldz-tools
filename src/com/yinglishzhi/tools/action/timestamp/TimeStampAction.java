package com.yinglishzhi.tools.action.timestamp;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.yinglishzhi.tools.ui.featurewidget.TimeStampWindowWidget;
import org.jetbrains.annotations.NotNull;

/**
 * @author LDZ
 * @date 2020/6/3 2:37 下午
 */
public class TimeStampAction extends AnAction {
    private final TimeStampWindowWidget timeStampWindowWidget;

    public TimeStampAction(TimeStampWindowWidget timeStampWindowWidget) {
        super("time stamp action", "time stamp", AllIcons.General.ArrowDown);
        this.timeStampWindowWidget = timeStampWindowWidget;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        timeStampWindowWidget.timeStampAction();
    }
}
