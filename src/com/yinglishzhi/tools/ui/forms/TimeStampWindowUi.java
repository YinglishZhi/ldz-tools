package com.yinglishzhi.tools.ui.forms;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LDZ
 * @date 2020/6/3 2:13 下午
 */
public class TimeStampWindowUi extends BaseWindowUi implements ActionListener {

    /**
     * 容器
     */
    private JPanel container;

    /**
     * 主面板
     */
    private JPanel mainPanel;

    /**
     * 当前 label
     */
    private JLabel nowLabel;

    /**
     * 控制按钮 用来控制 现在时间自动更新
     */
    private JButton control;

    /**
     * 时间戳 label
     */
    private JLabel timeStamp;

    /**
     * 时间 label
     */
    private JLabel Label3;

    /**
     * 当前时间时间戳 自动更新用
     */
    private JTextField nowTimeStamp;

    /**
     * 转换按钮 时间戳 --> 时间
     */
    private JButton transfer;

    private Timer AUTO_TIMER;


    /**
     * 默认自动时间戳启动
     */
    private boolean autoTimeSwitch = true;

    public TimeStampWindowUi(Project project, Disposable disposable, ToolWindow toolWindow) {
        // 自动定时器
        initAutoTimeStamp();

    }


    private void initAutoTimeStamp() {
        // 文本不可编辑
        nowTimeStamp.setEditable(false);
        // 定时器启动
        AUTO_TIMER = new Timer(1000, this);
        AUTO_TIMER.start();
        // 定时器控制按键
        control.setIcon(AllIcons.General.InspectionsOK);
        control.addActionListener(e -> {
            autoTimeSwitch = !autoTimeSwitch;
            switchAuto(autoTimeSwitch);
            control.setIcon(autoTimeSwitch ? AllIcons.General.InspectionsOK : AllIcons.General.InspectionsTrafficOff);
        });
    }

    public void switchAuto(boolean autoReadSwitch) {
        if (autoReadSwitch) {
            AUTO_TIMER.start();
        } else {
            AUTO_TIMER.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nowTimeStamp.setText(System.currentTimeMillis() + "");
    }

    @Override
    public JPanel getContent() {
        return container;
    }


}
