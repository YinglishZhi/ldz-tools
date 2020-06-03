package com.yinglishzhi.tools.ui.forms;

import com.google.gson.*;
import com.intellij.icons.AllIcons;
import com.intellij.ide.highlighter.HtmlFileHighlighter;
import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.ide.highlighter.XmlFileHighlighter;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.json.JsonFileType;
import com.intellij.json.highlighting.JsonSyntaxHighlighterFactory;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.ex.util.LayerDescriptor;
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileTypes.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.yinglishzhi.tools.ui.action.JBRadioAction;
import org.apache.http.util.TextUtils;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author LDZ
 * @date 2020/6/3 3:49 下午
 */
public class JsonParseOutputUi {
    public JPanel container;
    private JPanel previewTypeContainer;
    private JPanel prettyContainer;
    private JPanel rawContainer;
    private JTree outputTree;
    private JScrollPane treeContainer;
    private JPanel toolBarContainer;
    private SimpleToolWindowPanel simpleToolWindowPanel1;

    private ButtonGroup buttonGroup;
    private CardLayout mPreviewTypeCardLayout;

    private final Editor prettyEditor;
    private final Editor rawEditor;

    private static final IElementType TextElementType = new IElementType("TEXT", Language.ANY);

    private Project mProject;

    private ActionListener previewTypeListener = e -> mPreviewTypeCardLayout.show(previewTypeContainer, e.getActionCommand());

    private AnAction[] actions = null;

    private JsonParseWindowUi jsonParseWindowUi;

    public JsonParseOutputUi(Project mProject, JsonParseWindowUi jsonParseWindowUi) {
        this.mProject = mProject;
        this.jsonParseWindowUi = jsonParseWindowUi;

        mPreviewTypeCardLayout = ((CardLayout) previewTypeContainer.getLayout());

        prettyEditor = createEditor();
        prettyContainer.add(prettyEditor.getComponent(), BorderLayout.CENTER);
        rawEditor = createEditor();
        rawContainer.add(rawEditor.getComponent(), BorderLayout.CENTER);
        changeIcon();

        setUiComponents();

    }


    private void setUiComponents() {
        simpleToolWindowPanel1 = new SimpleToolWindowPanel(true, true);
        buttonGroup = new ButtonGroup();
        actions = new AnAction[4];
        actions[0] = new JBRadioAction("Pretty", "Pretty", buttonGroup, previewTypeListener, true);
        actions[1] = new JBRadioAction("Raw", "Raw", buttonGroup, previewTypeListener);
        actions[2] = new JBRadioAction("Tree", "Tree", buttonGroup, previewTypeListener);
        actions[3] = new AnAction("Use Soft Wraps", "Toggle using soft wraps in current editor", AllIcons.Actions.ToggleSoftWrap) {
            @Override
            public void actionPerformed(@Nonnull AnActionEvent anActionEvent) {
                EventQueue.invokeLater(() -> {
                    try {
                        if (buttonGroup.getSelection() != null) {
                            String actionCommand = buttonGroup.getSelection().getActionCommand();
                            if ("Pretty".equalsIgnoreCase(actionCommand)) {
                                EditorSettings settings = prettyEditor.getSettings();
                                settings.setUseSoftWraps(!settings.isUseSoftWraps());
                            } else if ("Raw".equalsIgnoreCase(actionCommand)) {
                                EditorSettings settings = rawEditor.getSettings();
                                settings.setUseSoftWraps(!settings.isUseSoftWraps());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        };

        ActionGroup group = new DefaultActionGroup(actions);
        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLBAR, group, true);

        simpleToolWindowPanel1.setToolbar(toolbar.getComponent());
        simpleToolWindowPanel1.setContent(new JPanel(new BorderLayout()));
    }

    private void createUIComponents() {
        setUiComponents();
    }

    private Editor createEditor() {
        PsiFile myFile = null;
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

        ((EditorEx) editor).setHighlighter(createHighlighter(FileTypes.PLAIN_TEXT));
        return editor;
    }

    private EditorHighlighter createHighlighter(LanguageFileType fileType) {

        SyntaxHighlighter originalHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(fileType, null, null);
        if (originalHighlighter == null) {
            originalHighlighter = new PlainSyntaxHighlighter();
        }

        EditorColorsScheme scheme = EditorColorsManager.getInstance().getGlobalScheme();
        LayeredLexerEditorHighlighter highlighter = new LayeredLexerEditorHighlighter(getFileHighlighter(fileType), scheme);
        highlighter.registerLayer(TextElementType, new LayerDescriptor(originalHighlighter, ""));
        return highlighter;
    }

    private SyntaxHighlighter getFileHighlighter(FileType fileType) {
        if (fileType == HtmlFileType.INSTANCE) {
            return new HtmlFileHighlighter();
        } else if (fileType == XmlFileType.INSTANCE) {
            return new XmlFileHighlighter();
        } else if (fileType == JsonFileType.INSTANCE) {
            return JsonSyntaxHighlighterFactory.getSyntaxHighlighter(fileType, mProject, null);
        }
        return new PlainSyntaxHighlighter();
    }

    private LanguageFileType getFileType(/*Header[] contentTypes*/) {

        return JsonFileType.INSTANCE;
    }



    void showRaw(String text) {
        if (null == text)
            return;
        try {
            WriteCommandAction.runWriteCommandAction(mProject, () -> rawEditor.getDocument().setText(text));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private String getPrettyJson(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(jsonString);
        return gson.toJson(je);
    }

    private void changeIcon() {
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) outputTree.getCellRenderer();
        Icon icon = new ImageIcon();
        renderer.setClosedIcon(icon);
        renderer.setOpenIcon(icon);
        renderer.setLeafIcon(icon);
    }




}
