package com.yinglishzhi.tools;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;

/**
 * @author LDZ
 * @date 2020/6/3 9:53 上午
 */
public class ToolsWindowPanel extends SimpleToolWindowPanel {
    private PropertiesComponent myPropertiesComponent;
    private ToolWindow myWindow;

    public ToolsWindowPanel(PropertiesComponent propertiesComponent, ToolWindow window) {
        super(false, true);
        myPropertiesComponent = propertiesComponent;
        myWindow = window;
    }

}