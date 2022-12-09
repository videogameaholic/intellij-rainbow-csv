package com.andrey4623.rainbowcsv.settings;

import com.andrey4623.rainbowcsv.CsvFile;
import com.andrey4623.rainbowcsv.Delimiter;
import com.andrey4623.rainbowcsv.EscapeCharacter;
import com.andrey4623.rainbowcsv.RainbowCsvHelper;
import com.intellij.application.options.editor.EditorOptionsProvider;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.FileContentUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Settings implements EditorOptionsProvider {

    private JPanel panel;
    private JCheckBox rainbowCSVEnabledCheckBox;
    private JComboBox delimiterComboBox;
    private JComboBox escapeCharacterComboBox;
    private JTextField commentPrefixTextField;
    private JCheckBox highlightCommentsCheckBox;
    private JTextField fileExtensionsTextField;

    private static void reparseFiles() {
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();

        for (Project openProject : openProjects) {
            VirtualFile[] openFiles = FileEditorManager.getInstance(openProject).getOpenFiles();

            PsiManager psiManager = PsiManager.getInstance(openProject);

            List<PsiFile> list = new ArrayList<>();

            for (VirtualFile openFile : openFiles) {
                PsiFile psiFile = psiManager.findFile(openFile);
                if (psiFile != null && psiFile instanceof CsvFile) {
                    list.add(psiFile);
                }
            }
            FileContentUtil.reparseFiles(
                    openProject,
                    list.stream().map(PsiFile::getVirtualFile).collect(Collectors.toList()),
                    true
            );
        }
    }

    @Override
    public @NotNull String getId() {
        return "RainbowCSV.Settings";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return RainbowCsvHelper.OPTIONS_NAME;
    }

    @Override
    public @Nullable JComponent createComponent() {
        return panel;
    }

    @Override
    public boolean isModified() {
        CsvSettings settings = CsvSettings.getInstance();

        return this.rainbowCSVEnabledCheckBox.isSelected() != settings.isEnabled()
                || !settings.getDelimiter().equals(this.delimiterComboBox.getSelectedItem())
                || !settings.getEscapeCharacter().equals(this.escapeCharacterComboBox.getSelectedItem())
                || settings.isHighlightComments() != this.highlightCommentsCheckBox.isSelected()
                || !settings.getCommentPrefix().equals(this.commentPrefixTextField.getText())
                || !settings.getFileExtensionList().equals(this.fileExtensionsTextField.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        CsvSettings settings = CsvSettings.getInstance();
        settings.setEnabled(rainbowCSVEnabledCheckBox.isSelected());
        settings.setDelimiter((Delimiter) delimiterComboBox.getSelectedItem());
        settings.setEscapeCharacter((EscapeCharacter) escapeCharacterComboBox.getSelectedItem());
        settings.setHighlightComments(highlightCommentsCheckBox.isSelected());
        settings.setCommentPrefix(commentPrefixTextField.getText());
        settings.setFileExtensionList(fileExtensionsTextField.getText());

        reparseFiles();
    }

    @Override
    public void reset() {
        CsvSettings settings = CsvSettings.getInstance();

        rainbowCSVEnabledCheckBox.setSelected(settings.isEnabled());
        delimiterComboBox.setSelectedItem(settings.getDelimiter());
        escapeCharacterComboBox.setSelectedItem(settings.getEscapeCharacter());
        highlightCommentsCheckBox.setSelected(settings.isHighlightComments());
        commentPrefixTextField.setText(settings.getCommentPrefix());
        fileExtensionsTextField.setText(settings.getFileExtensionList());
    }

    protected void createUIComponents() {
        delimiterComboBox = new ComboBox(Delimiter.values());
        delimiterComboBox.setRenderer(new CustomListCellRenderer<>(Delimiter::getName));
        escapeCharacterComboBox = new ComboBox(EscapeCharacter.values());
        escapeCharacterComboBox.setRenderer(new CustomListCellRenderer<>(EscapeCharacter::getName));
    }
}
