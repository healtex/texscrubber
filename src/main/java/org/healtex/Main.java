package org.healtex;



import org.healtex.gui.StartWindow;

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartWindow().setVisible(true);
            }
        });        

    }
}
