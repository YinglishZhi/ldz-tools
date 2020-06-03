package com.yinglishzhi.tools.ui.featurewidget.base;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author LDZ
 * @date 2020/6/3 1:24 下午
 */
public abstract class BaseToolWindowWidget<T> extends JPanel {

    private Project myProject;

    private Disposable mParent;

    private JBPanel<JBPanel<?>> mPanel;

    private ToolWindow toolWindow;

    private T tui;


    public BaseToolWindowWidget(Project project, Disposable disposable, ToolWindow toolWindow, T t) {
        super(new BorderLayout());
        myProject = project;
        mParent = disposable;
        this.toolWindow = toolWindow;
        mPanel = new JBPanel<>(new BorderLayout());
        mPanel.add(this, BorderLayout.CENTER);
        this.tui = t;
    }

    public void touch() {
        System.out.println(11);
    }
}
