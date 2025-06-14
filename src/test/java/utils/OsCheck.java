package utils;

import java.util.Locale;

public final class OsCheck {
    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    // cached result of OS detection
    private static OSType detectedOS;

    /**
     * detect the operating system from the os.name System property and cache
     * the result
     *
     * @returns - the operating system detected
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (OS.contains("nix") || (OS.contains("nux") || (OS.contains("aix")))) {
                detectedOS = OSType.Linux;
            }
        } else {
            detectedOS = OSType.Other;
        }
        return detectedOS;
    }
}
