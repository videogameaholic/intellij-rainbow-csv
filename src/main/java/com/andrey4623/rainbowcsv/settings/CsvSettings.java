package com.andrey4623.rainbowcsv.settings;

import com.andrey4623.rainbowcsv.Delimiter;
import com.andrey4623.rainbowcsv.EscapeCharacter;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@State(
        name = "CsvSettings",
        storages = {@Storage("rainbow-csv.xml")}
)
public class CsvSettings implements PersistentStateComponent<CsvSettingsData> {

    private static final CsvSettings csvSettingsComponent = new CsvSettings();
    private CsvSettingsData csvSettings = new CsvSettingsData();

    public CsvSettings() {
    }

    public static CsvSettings getInstance() {
        if (ApplicationManager.getApplication() == null) {
            return csvSettingsComponent;
        }

        CsvSettings service = ApplicationManager.getApplication().getService(CsvSettings.class);
        if (service == null) {
            return csvSettingsComponent;
        }

        return service;
    }

    @Nullable
    @Override
    public CsvSettingsData getState() {
        return csvSettings;
    }

    @Override
    public void loadState(@NotNull CsvSettingsData state) {
        this.csvSettings = state;
    }

    public boolean isEnabled() {
        return getState().enabled;
    }

    public void setEnabled(boolean enabled) {
        getState().enabled = enabled;
    }

    public boolean isWelcomeNotifyShowed() {
        return getState().welcomeNotifyShowed;
    }

    public void setWelcomeNotifyShowed(boolean welcomeNotifyShowed) {
        getState().welcomeNotifyShowed = welcomeNotifyShowed;
    }

    public Delimiter getDelimiter() {
        return getState().delimiter;
    }

    public void setDelimiter(Delimiter delimiter) {
        getState().delimiter = delimiter;
    }

    public EscapeCharacter getEscapeCharacter() {
        return getState().escapeCharacter;
    }

    public void setEscapeCharacter(EscapeCharacter escapeCharacter) {
        getState().escapeCharacter = escapeCharacter;
    }

    public boolean isHighlightComments() {
        return getState().highlightComments;
    }

    public void setHighlightComments(boolean enabled) {
        getState().highlightComments = enabled;
    }

    public String getCommentPrefix() {
        return getState().commentPrefix;
    }

    public void setCommentPrefix(String commentPrefix) {
        getState().commentPrefix = commentPrefix;
    }

    public String getFileExtensionList() {
        return getState().fileExtensionList;
    }

    public void setFileExtensionList(String fileExtensionList) {
        getState().fileExtensionList = fileExtensionList;
    }
    
    public List<String> getFileExtensions() {
        return Arrays.asList(getState().fileExtensionList.split(","));
    }
}
