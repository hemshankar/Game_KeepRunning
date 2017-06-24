package com.mygdx.game.desktop.com.mygdx.game.desktop.utility;

/**
 * Created by hsahu on 6/24/2017.
 */

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFiles
{
    /*public static void main(String[] args)
    {
        new CopyFiles().copy("jpg", "C:\\Users\\BrunoFurtado\\Pictures", "c:/temp/photos");
    }*/

    private FileTypeOrFolderFilter filter = null;

    public void copy(final String []fileType, String fromPath, String outputPath)
    {
        filter = new FileTypeOrFolderFilter(fileType);
        File currentFolder = new File(fromPath);
        File outputFolder = new File(outputPath);
        scanFolder(fileType, currentFolder, outputFolder);
    }

    private void scanFolder(final String[] fileType, File currentFolder, File outputFolder)
    {
        System.out.println("Scanning folder [" + currentFolder + "]...");
        File[] files = currentFolder.listFiles(filter);
        for (File file : files) {
            if (file.isDirectory()) {
                scanFolder(fileType, file, outputFolder);
            } else {
                copy(file, outputFolder);
            }
        }
    }

    private void copy(File file, File outputFolder)
    {
        try {
            System.out.println("\tCopying [" + file + "] to folder [" + outputFolder + "]...");
            InputStream input = new FileInputStream(file);
            OutputStream out = new FileOutputStream(new File(outputFolder + File.separator + file.getName()));
            byte data[] = new byte[input.available()];
            input.read(data);
            out.write(data);
            out.flush();
            out.close();
            input.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final class FileTypeOrFolderFilter implements FileFilter
    {
        private final String[] fileTypes;

        private FileTypeOrFolderFilter(String []fileTypes)
        {
            this.fileTypes = fileTypes;
        }

        public boolean accept(File pathname)
        {
            //accept directory
            if(pathname.isDirectory())
                return true;

            for(String fileType : fileTypes){
                if(pathname.getName().endsWith("." + fileType)){
                    return true;
                }
            }
            return false;
        }
    }
}