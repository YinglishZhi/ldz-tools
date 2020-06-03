package com.yinglishzhi.tools.ui.action;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Radio Action
 *
 * @author LDZ
 * @date 2020/6/3 5:02 下午
 */
public class JsonParseRadioAction extends AnAction implements CustomComponentAction {

    private ButtonGroup mButtonGroup;
    private String mActionCommand;
    private ActionListener mActionListener;
    private boolean selected;

    public JsonParseRadioAction(@Nullable String text) {
        this(text, null);
    }

    public JsonParseRadioAction(@Nullable String text, ButtonGroup buttonGroup) {
        super(text);
        this.mButtonGroup = buttonGroup;
    }

    public JsonParseRadioAction(String text, String actionCommand, ButtonGroup buttonGroup, ActionListener actionListener) {
        this(text, buttonGroup);
        this.mActionCommand = actionCommand;
        this.mActionListener = actionListener;
    }

    public JsonParseRadioAction(String text, String actionCommand, ButtonGroup buttonGroup, ActionListener actionListener, boolean selected) {
        this(text, actionCommand, buttonGroup, actionListener);
        this.selected = selected;
    }


    @Override
    public JComponent createCustomComponent(Presentation presentation, String place) {
        JRadioButton jRadioButton = new JRadioButton("");
        jRadioButton.addActionListener(e -> {
            JRadioButton radioButton = (JRadioButton) e.getSource();
            ActionToolbar actionToolbar = UIUtil.getParentOfType(ActionToolbar.class, radioButton);
            DataContext dataContext = actionToolbar != null ? actionToolbar.getToolbarDataContext() : DataManager.getInstance().getDataContext(radioButton);
            JsonParseRadioAction.this.actionPerformed(AnActionEvent.createFromAnAction(JsonParseRadioAction.this, null, "unknown", dataContext));
            if (mActionListener != null) {
                mActionListener.actionPerformed(e);
            }
        });
        presentation.putClientProperty("selected", selected);
        this.updateCustomComponent(jRadioButton, presentation);
        return jRadioButton;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
    }

    protected void updateCustomComponent(JRadioButton radioButton, Presentation presentation) {
        radioButton.setText(presentation.getText());
        radioButton.setToolTipText(presentation.getDescription());
        radioButton.setMnemonic(presentation.getMnemonic());
        radioButton.setDisplayedMnemonicIndex(presentation.getDisplayedMnemonicIndex());
        radioButton.setSelected(Boolean.TRUE.equals(presentation.getClientProperty("selected")));
        radioButton.setEnabled(true);
        radioButton.setVisible(presentation.isVisible());

        if (!StringUtil.isEmpty(mActionCommand)) {
            radioButton.setActionCommand(mActionCommand);
        }
        if (mButtonGroup != null) {
            mButtonGroup.add(radioButton);
        }
    }
}
