// ILINE S. GUTIERREZ - CCB
//  Tic Tac Toe is fun!

import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

class TicTacToe extends Frame implements ActionListener {
    private Panel cardPanel;
    private CardLayout cardLayout;
    private Button playButton;
    private Button[][] buttons;
    private Panel gamePanel;
    private Label statusLabel;
    private boolean isXTurn;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Create welcome panel
        Panel welcomePanel = new Panel(new BorderLayout());
        Panel centerPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        Label welcomeLabel = new Label("Welcome to Tic Tac Toe");

        // font
        Font font =new Font("Arial", Font.BOLD, 16);
        // Set the Font
        welcomeLabel.setFont(font);
        centerPanel.add(welcomeLabel);
        welcomePanel.add(centerPanel, BorderLayout.CENTER);

        playButton = new Button("Play");
        Font buttonFont = new Font("Arial", Font.BOLD, 12); // Get the current font and make it bold
        playButton.setFont(buttonFont); // Set the bold font for the button

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] options = {"X", "O"};
                int choice = JOptionPane.showOptionDialog(TicTacToe.this, "Choose your weapon:", "Player Select", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choice == JOptionPane.YES_OPTION) {
                    isXTurn = true;
                    statusLabel.setText(" X ");
                } else {
                    isXTurn = false;
                    statusLabel.setText(" O ");
                }
                cardLayout.show(cardPanel, "game");
                resetGamePanel();
            }
        });

        Panel playButtonPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        playButtonPanel.add(playButton);
        welcomePanel.add(playButtonPanel, BorderLayout.SOUTH);


        // Create Tic Tac Toe game panel
        gamePanel = new Panel(new GridLayout(4, 3));
        buttons = new Button[3][3];
        statusLabel = new Label("");
        gamePanel.add(statusLabel);

        // Create card layout to switch between panels
        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);
        cardPanel.add(welcomePanel, "welcome");
        cardPanel.add(gamePanel, "game");

        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Button clickedButton = (Button) e.getSource();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j] == clickedButton && clickedButton.getLabel().isEmpty()) {
                    if (isXTurn) {
                        clickedButton.setLabel("X");
                        statusLabel.setText("O turn");
                    } else {
                        clickedButton.setLabel("O");
                        statusLabel.setText("X turn");
                    }
                    isXTurn = !isXTurn;

                    if (checkWinner()) {
                        String winner = isXTurn ? "O" : "X";
                        JOptionPane.showMessageDialog(this, winner + " wins!", "The Winner Is!!!", JOptionPane.INFORMATION_MESSAGE);
                        showEndGameOptions();
                        disableButtons();
                    } else if (isBoardFull()) {
                        JOptionPane.showMessageDialog(this, "It's a draw!", "Draw", JOptionPane.INFORMATION_MESSAGE);
                        showEndGameOptions();
                    }
                    return;
                }
            }
        }
    }

    private void resetGamePanel() {
        gamePanel.removeAll();
        buttons = new Button[3][3];
        isXTurn = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button("");
                buttons[i][j].addActionListener(this);
                gamePanel.add(buttons[i][j]);
            }
        }

        // Add an additional row with the "Main Menu" button
        Panel buttonPanel = new Panel(new FlowLayout());
        Button backButton = new Button("Main Menu");
        //font
        Font buttonFont = new Font( "Arial", Font.BOLD, 12);
        backButton.setFont(buttonFont);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "welcome");
                resetGame();
            }
        });
        buttonPanel.add(backButton);
        gamePanel.add(buttonPanel);

        statusLabel.setText("X turn");
        gamePanel.add(statusLabel);
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private boolean checkWinner() {
        String[][] board = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = buttons[i][j].getLabel();
            }
        }

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].isEmpty()) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j].equals(board[1][j]) && board[1][j].equals(board[2][j]) && !board[0][j].isEmpty()) {
                return true;
            }
        }

        // Check diagonals
        if ((board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].isEmpty()) ||
                (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].isEmpty())) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getLabel().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void showEndGameOptions() {
        gamePanel.removeAll();

        // Define the font you want to use
        Font buttonFont = new Font("Arial", Font.BOLD, 14); // Example: Arial, Bold, Size 14

        // New Game Button
        Button newGameButton = new Button("New Game");
        newGameButton.setFont(buttonFont); // Set font for the button
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
                resetGamePanel();
            }
        });
        gamePanel.add(newGameButton);

        // Back Button
        Button backButton = new Button("Main Menu");
        backButton.setFont(buttonFont); // Set font for the button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "welcome");
                resetGame();
            }
        });
        gamePanel.add(backButton);

        // Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setFont(buttonFont); // Set font for the button
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gamePanel.add(exitButton);

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setLabel("");
                buttons[i][j].setEnabled(true);
            }
        }
        playButton.setEnabled(true);
        statusLabel.setText("");
    }

    public static void main(String[] args) {

        new TicTacToe();
    }
}

