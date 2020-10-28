package sschr15.qol.code;

import javax.swing.*;
import java.awt.*;

public class PreLaunchWindow implements Runnable {
    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {}

        JFrame frame = new JFrame("Loading Minecraft 1.12.2");
        frame.setSize(400, 60);

        JLabel label = new JLabel("Loading...", SwingConstants.CENTER);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setOrientation(SwingConstants.HORIZONTAL);
        progressBar.setIndeterminate(true);

        frame.add(label);
        frame.add(progressBar);

        Point point = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        point.translate(-200, -30);
        frame.setLocation(point);
        frame.setVisible(true);
        frame.requestFocus();

        while (!Thread.interrupted()) ;

        frame.setVisible(false);
        frame.dispose();
    }
}
