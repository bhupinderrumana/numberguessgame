import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField pinField;

    public LoginFrame() {
        setTitle("Number Guessing Game ");
        setSize(626, 417);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // === Load Background Image ===
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/download.jpg"));
        JLabel backgroundLabel = new JLabel(bgIcon);
        backgroundLabel.setLayout(new GridBagLayout()); // allows centered layout
        setContentPane(backgroundLabel);

        // Colors and Fonts
        Color primaryColor = new Color(255, 255, 255);
        Font labelFont = new Font("Times New Roman", Font.BOLD, 16);

        // Labels and Fields
        JLabel titleLabel = new JLabel("Welcome to Number Guessing Game");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD|Font.ITALIC, 36));
        titleLabel.setForeground(primaryColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel userLabel = new JLabel("User ID:");
        userLabel.setFont(labelFont);
        userLabel.setForeground(Color.WHITE);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(labelFont);
        pinLabel.setForeground(Color.WHITE);

        userField = new JTextField(15);
        pinField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Cancel");

        // Actions
        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String pin = new String(pinField.getPassword());

            if (user.equals("oasis") && pin.equals("1302")) {
                dispose(); // close login
                new NumberGuessingGame(); // open dashboard
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        // Layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

// User ID Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userLabel, gbc);

// User ID TextField
        gbc.gridx = 1;
        formPanel.add(userField, gbc);

// PIN Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(pinLabel, gbc);

// PIN TextField
        gbc.gridx = 1;
        formPanel.add(pinField, gbc);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }


    public static void main(String[] args) {
        new LoginFrame();
    }
}