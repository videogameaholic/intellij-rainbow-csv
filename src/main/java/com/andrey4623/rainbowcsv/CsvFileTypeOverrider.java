package com.andrey4623.rainbowcsv;

import com.andrey4623.rainbowcsv.settings.CsvSettings;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.impl.FileTypeOverrider;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CsvFileTypeOverrider implements FileTypeOverrider {

    @Nullable
    @Override
    public FileType getOverriddenFileType(@NotNull VirtualFile file) {
        if (null != file) {
            List<String> delimitedFileExtensions = CsvSettings.getInstance().getFileExtensions();
            if (delimitedFileExtensions.contains(file.getExtension())) {
                return CsvFileType.INSTANCE;
            }
        }
        return null;
    }
}
