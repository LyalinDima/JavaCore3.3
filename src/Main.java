import com.sun.source.tree.WhileLoopTree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final String SAVE_PATH = "E:\\Games\\savegames\\";

    public static void main(String[] args) {
        openZip(SAVE_PATH + "zip.zip", SAVE_PATH);
        File saveFolder = new File(SAVE_PATH);
        GameProgress gameProgress = openProgress(SAVE_PATH + "save1.dat");
        System.out.println(gameProgress);
    }

    private static void openZip(String zipPath, String extractPath) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry zipEntry;
            String name;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                name = zipEntry.getName();
                FileOutputStream fileOutputStream = new FileOutputStream(extractPath + "\\" + name);
                for (int i = zipInputStream.read(); i != -1; i = zipInputStream.read()) {
                    fileOutputStream.write(i);
                }
                fileOutputStream.flush();
            }
            zipInputStream.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static GameProgress openProgress(String filePath) {
        GameProgress gameProgress = null;
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            gameProgress = (GameProgress) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameProgress;
    }
}
