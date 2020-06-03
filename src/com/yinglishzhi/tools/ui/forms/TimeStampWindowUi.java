package com.yinglishzhi.tools.ui.forms;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

/**
 * @author LDZ
 * @date 2020/6/3 2:13 下午
 */
public class TimeStampWindowUi extends BaseWindowUi {


    private JPanel container;
    private JPanel mainPanel;
    private JLabel nowLabel;
    private JLabel label1;
    private JButton control;
    private JLabel timeStamp;
    private JLabel Label3;
    private JTextField nowTimeStamp;
    private JLabel l;
    private JButton transfer;
    private static final Icon HIDE = new ImageIcon(JsonParseWindowUi.class.getResource("/icons/tool.png"));

    public TimeStampWindowUi(Project project, Disposable disposable, ToolWindow toolWindow) {
//        button1.setIcon(HIDE);
    }

    @Override
    public JPanel getContent() {
        return container;
    }
}
