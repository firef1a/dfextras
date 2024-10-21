package dev.fire.dfextras.external;

import java.io.IOException;

public class SystemIOManager {
    public static void sendDesktopNotification(String title, String body, int timeout, String urgency) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String[] cmd = { "/usr/bin/notify-send",
                "-t",
                String.valueOf(timeout),
                "-u",
                urgency,
                title,
                body
        };
        rt.exec(cmd);
    }
}