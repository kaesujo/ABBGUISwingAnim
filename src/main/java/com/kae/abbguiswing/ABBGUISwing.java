package com.kae.abbguiswing;

import javax.swing.SwingUtilities;

public class ABBGUISwing {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ZoomableBinarySearchTreeGUI();
            }
        });
    }
}
