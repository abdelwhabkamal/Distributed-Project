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

public class ClientGUI extends JFrame implements ActionListener {

    private JTextField fileTextField;
    private JTextArea outputTextArea;

    public ClientGUI() {
        setTitle("Client GUI");
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
                new ClientGUI().setVisible(true);
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

            String data = new String(Files.readAllBytes(Paths.get(filePath)));

            // Getting the registry
            Registry registry = LocateRegistry.getRegistry();

            // Looking up the registry for the remote object
            ProjectInterface stub = (ProjectInterface) registry.lookup("obj");

            outputTextArea.setText("");
            outputTextArea.append("Longest Word Is : " + stub.longest(data) + "\n");
            outputTextArea.append("----------------------------\n");
            outputTextArea.append("Shortest Word Is : " + stub.shortest(data) + "\n");
            outputTextArea.append("----------------------------\n");
            outputTextArea.append("Number Of Letters Is : " + stub.Count(data) + "\n");
            outputTextArea.append("----------------------------\n");
            outputTextArea.append("The Repeated Words are  : " + stub.repeatedwords(data) + "\n");
            outputTextArea.append("----------------------------\n");
            outputTextArea.append("The Repetition Number Of Each Word Is : " + stub.Repeat(data) + "\n");
            outputTextArea.append("----------------------------\n");

            Instant endTime = Instant.now();
            outputTextArea.append("Total Non-Threaded Time Is : " + Duration.between(startTime, endTime).toMillis() + "ms\n");
            outputTextArea.append("----------------------------\n");

        } catch (Exception ex) {
            outputTextArea.setText("An error occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
