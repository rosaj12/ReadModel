package com.readcore.domain.entities;

/**
 * Supported book formats
 */
public enum BookFormat {
    PDF(".pdf"),
    EPUB(".epub"),
    TXT(".txt"),
    MOBI(".mobi");
    
    private final String extension;
    
    BookFormat(String extension) {
        this.extension = extension;
    }
    
    public String getExtension() {
        return extension;
    }
    
    public static BookFormat fromExtension(String extension) {
        for (BookFormat format : values()) {
            if (format.extension.equalsIgnoreCase(extension)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unsupported format: " + extension);
    }
    
    public static BookFormat fromFilename(String filename) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot == -1) {
            throw new IllegalArgumentException("No extension found in filename: " + filename);
        }
        return fromExtension(filename.substring(lastDot));
    }
}
