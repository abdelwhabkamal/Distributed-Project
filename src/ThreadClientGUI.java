import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.time.Instant;

public class ThreadClientGUI extends JFrame implements ActionListener {

    private JTextField fileTextField;
    private JTextArea outputTextArea;

    public ThreadClientGUI() {
        setTitle("ThreadClient GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Create UI elements
        JLabel fileLabel = new JLabel("Select File:");
        JButton fileButton = new JButton("Browse");
        fileTextField = new JTextField();
        JLabel outputLabel = new JLabel("Output:");
        outputTextArea = new JTextArea(10, 30);
        JButton analyzeButton = new JButton("Analyze");

        // Set UI element properties
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        // Set event handlers
        fileButton.addActionListener(this);
        analyzeButton.addActionListener(this);

        // Create layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(fileLabel, BorderLayout.WEST);
        filePanel.add(fileTextField, BorderLayout.CENTER);
        filePanel.add(fileButton, BorderLayout.EAST);

        panel.add(filePanel, BorderLayout.NORTH);
        panel.add(outputLabel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(analyzeButton, BorderLayout.SOUTH);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ThreadClientGUI().setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Browse")) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileTextField.setText(selectedFile.getAbsolutePath());
            }
        } else if (e.getActionCommand().equals("Analyze")) {
            String filePath = fileTextField.getText();
            analyzeFile(filePath);
        }
    }

    private void analyzeFile(String filePath) {
        try {
            Instant startTime = Instant.now();
            outputTextArea.setText("Start Time : " + startTime + "\n");

            String data = new String(Files.readAllBytes(Paths.get(filePath)));

            Registry registry = LocateRegistry.getRegistry();
            ProjectInterface stub = (ProjectInterface) registry.lookup("obj");

            Thread longestWordThread = new Thread(() -> {
                try {
                    outputTextArea.append("Longest Word Is : " + stub.longest(data) + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread shortestWordThread = new Thread(() -> {
                try {
                    outputTextArea.append("Shortest Word Is : " + stub.shortest(data) + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread countThread = new Thread(() -> {
                try {
                    outputTextArea.append("Number Of Letters Is : " + stub.Count(data) + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread repeatedWordsThread = new Thread(() -> {
                try {
                    outputTextArea.append("The Repeated Words are  : " + stub.repeatedwords(data) + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread repetitionThread = new Thread(() -> {
                try {
                    outputTextArea.append("The Repetition Number Of Each Word Is : " + stub.Repeat(data) + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            longestWordThread.start();
            shortestWordThread.start();
            countThread.start();
            repeatedWordsThread.start();
            repetitionThread.start();

            // Wait for all threads to finish
            try {
                longestWordThread.join();
                shortestWordThread.join();
                countThread.join();
                repeatedWordsThread.join();
                repetitionThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Instant endTime = Instant.now();
            outputTextArea.append("Total Threaded Time Is : " + Duration.between(startTime, endTime).toMillis() + "ms\n");

        } catch (Exception ex) {
            outputTextArea.setText("An error occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
