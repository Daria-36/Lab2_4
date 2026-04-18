import gui.MissionAnalyzerGUI;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MissionAnalyzerGUI gui = new MissionAnalyzerGUI();
            gui.setVisible(true);
        });
    }
}