package me.tyler.encoding;

import javax.swing.*;
import java.io.IOException;

public class EncodingExecutor {

    public static void main(String[] args) throws IOException {
        new EncodingExecutor();
    }

    public EncodingExecutor() throws IOException {
        String readPath = this.getPath();

        new Encoding(readPath);
    }

    public String getPath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setApproveButtonText("Open");
        int r = chooser.showSaveDialog(null);

        if(r == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }

        System.exit(0);
        return null;
    }
}
