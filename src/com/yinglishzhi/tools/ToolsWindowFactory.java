package com.yinglishzhi.tools;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

/**
 * windows factory
 *
 * @author LDZ
 * @date 2020/6/3 9:40 上午
 */
public class ToolsWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ToolsComponent toolsComponent = ToolsComponent.getInstance(project);
        toolsComponent.initTools(toolWindow);
    }
}
