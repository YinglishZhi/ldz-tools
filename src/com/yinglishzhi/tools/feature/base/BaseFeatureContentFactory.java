package com.yinglishzhi.tools.feature.base;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.yinglishzhi.tools.ToolsWindowPanel;
import com.yinglishzhi.tools.action.ToolWindowAction;
import com.yinglishzhi.tools.ui.featurewidget.base.BaseToolWindowWidget;
import com.yinglishzhi.tools.ui.forms.BaseWindowUi;
import com.yinglishzhi.tools.ui.forms.TimeStampWindowUi;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * @author LDZ
 * @date 2020/6/3 1:04 下午
 */
public abstract class BaseFeatureContentFactory<T extends BaseWindowUi> {

    public static final Shortcut[] ALT_LEFT = new Shortcut[]{
            new KeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_MASK), null)
    };

    public BaseFeatureContentFactory(ToolWindow toolWindow, Project project, String panelName) {
        Content content = createContentPanel(toolWindow, project, panelName);
        toolWindow.getContentManager().addContent(content);
    }

    /**
     * 创建面板
     *
     * @param toolWindow
     * @return
     */
    public Content createContentPanel(ToolWindow toolWindow, Project project, String panelName) {

        toolWindow.setToHideOnEmptyContent(true);

        ToolsWindowPanel panel = new ToolsWindowPanel(PropertiesComponent.getInstance(project), toolWindow);

        Content content = ContentFactory.SERVICE.getInstance().createContent(panel, panelName, false);

        T t = getTui(project, content, toolWindow);

        BaseToolWindowWidget<T> toolWindowWidget = createContent(content, toolWindow, t, project);
        // 工具栏
        panel.setToolbar(createToolBar(toolWindowWidget, t).getComponent());
        // 内容
        panel.setContent(t.getContent());
        return content;
    }

    /**
     * @author LDZ
     * @date 2020/6/3 2:26 下午
     */
    public abstract T getTui(Project project, Disposable disposable, ToolWindow toolWindow);

    public abstract BaseToolWindowWidget<T> createContent(Content content, ToolWindow toolWindow, T t, Project project);

    public ActionToolbar createToolBar(BaseToolWindowWidget<T> toolWindowWidget, T t) {
        DefaultActionGroup group = getDefaultActionGroup(toolWindowWidget, t);
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLBAR, group, false);
        toolbar.setOrientation(SwingConstants.VERTICAL);
        return toolbar;
    }

    /**
     * 代码
     * @param toolWindowWidget
     * @param t
     * @return
     */
    public abstract DefaultActionGroup getDefaultActionGroup(BaseToolWindowWidget<T> toolWindowWidget, T t);

}

