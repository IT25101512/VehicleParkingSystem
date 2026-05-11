package com.parking.util;

import java.io.*;
import java.util.*;

public class FileHandler {

    private static String getBasePath(javax.servlet.ServletContext ctx) {
        if (ctx != null) {
            return ctx.getRealPath("/") + "data" + File.separator;
        }
        return "data" + File.separator;
    }

    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            try { file.getParentFile().mkdirs(); file.createNewFile(); } catch (IOException ignored) {}
            return lines;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) lines.add(line.trim());
            }
        } catch (IOException e) { e.printStackTrace(); }
        return lines;
    }

    public static void writeLines(String filePath, List<String> lines) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static boolean appendLine(String filePath, String line) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
        return false;
    }

    public static String generateId(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    public static String getDataPath(javax.servlet.ServletContext ctx, String filename) {
        return getBasePath(ctx) + filename;
    }
}
