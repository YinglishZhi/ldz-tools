package com.yinglishzhi.tools.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.yinglishzhi.tools.ui.featurewidget.base.BaseToolWindowWidget;
import org.jetbrains.annotations.NotNull;

/**
 * @author LDZ
 * @date 2020/6/3 11:06 上午
 */
public class ToolWindowAction extends AnAction {

    private final BaseToolWindowWidget toolWindowWidget;

    public ToolWindowAction(BaseToolWindowWidget toolWindowWidget) {
        super("tool window action", "6666", AllIcons.General.Add);
        this.toolWindowWidget = toolWindowWidget;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        toolWindowWidget.touch();
    }
}
