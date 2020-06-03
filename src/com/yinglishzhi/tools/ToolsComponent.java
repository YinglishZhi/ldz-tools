package com.yinglishzhi.tools;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.yinglishzhi.tools.feature.JsonParseContent;
import com.yinglishzhi.tools.feature.TimeStampContent;

/**
 * tools component
 *
 * @author LDZ
 * @date 2020/6/3 9:43 上午
 */
public class ToolsComponent {

    private final Project mProject;

    public static ToolsComponent getInstance(Project project) {
        return new ToolsComponent(project);
    }

    public ToolsComponent(Project mProject) {
        this.mProject = mProject;
    }

    public void initTools(ToolWindow toolWindow) {
        new JsonParseContent(toolWindow, mProject);
        new TimeStampContent(toolWindow, mProject);
    }

}
