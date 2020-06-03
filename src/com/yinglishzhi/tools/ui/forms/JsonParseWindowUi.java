package com.yinglishzhi.tools.ui.forms;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author LDZ
 * @date 2020/6/3 10:08 上午
 */
public class JsonParseWindowUi extends BaseWindowUi {
    private JPanel container;
    private JPanel inputContainer;
    private JPanel outputContainer;
    private JPanel inputEditorContainer;
    private JButton parseButton;

    private Project mProject;
    private Disposable mParent;

    private Editor mInputEditor;
    JsonParseOutputUi jsonParseOutputUi;
    private static final Icon JSON_PARSE_ICON = new ImageIcon(JsonParseWindowUi.class.getResource("/icons/json.png"));

    public JsonParseWindowUi(Project project, Disposable disposable, ToolWindow toolWindow) {
        parseButton.setIcon(JSON_PARSE_ICON);

        this.mInputEditor = createEditor();
        inputEditorContainer.add(mInputEditor.getComponent(), BorderLayout.CENTER);
        jsonParseOutputUi = new JsonParseOutputUi(project, this);
        outputContainer.add(jsonParseOutputUi.container, BorderLayout.CENTER);

        setEventListeners();
    }


    @Override
    public JPanel getContent() {
        return container;
    }

    private Editor createEditor() {
        EditorFactory editorFactory = EditorFactory.getInstance();
        Document doc = editorFactory.createDocument("");
        Editor editor = editorFactory.createEditor(doc, mProject);
        EditorSettings editorSettings = editor.getSettings();
        editorSettings.setVirtualSpace(false);
        editorSettings.setLineMarkerAreaShown(false);
        editorSettings.setIndentGuidesShown(false);
        editorSettings.setFoldingOutlineShown(true);
        editorSettings.setAdditionalColumnsCount(3);
        editorSettings.setAdditionalLinesCount(3);
        editorSettings.setLineNumbersShown(true);
        editorSettings.setCaretRowShown(true);
        return editor;
    }

    private void setEventListeners() {
        parseButton.addActionListener(e -> {
            String jsonString = mInputEditor.getDocument().getText();
            System.out.println(jsonString);
            showBody(jsonString);
        });
        mInputEditor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(DocumentEvent e) {
                if (e != null && !TextUtils.isEmpty(e.getDocument().getText())) {

                }
            }
        });
    }

    private void showBody(String jsonString) {
        jsonParseOutputUi.showRaw(jsonString);
    }

}
