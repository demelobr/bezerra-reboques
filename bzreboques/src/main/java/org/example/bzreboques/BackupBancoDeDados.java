package org.example.bzreboques;

import java.io.IOException;

public class BackupBancoDeDados {
    public static int criarBackupDoBancoDeDados(){
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        ProcessBuilder builder = new ProcessBuilder();
        if(isWindows){
            builder.command("src/main/resources/org/example/bzreboques/backup.bat");
        }else{
            builder.command("sh", "-c", "\"" + "src/main/resources/org/example/bzreboques/backup.sh" + "\"");
        }

        try {
            Process process = builder.start();
            return process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
