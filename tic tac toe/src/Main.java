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
        centerPanel.add(welcomeLabel);
        welcomePanel.add(centerPanel, BorderLayout.CENTER);

        playButton = new Button("Play");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] options = {"X", "O"};
                int choice = JOptionPane.showOptionDialog(TicTacToe.this, "Choose your weapon:", "Player Select", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choice == JOptionPane.YES_OPTION) {
                    isXTurn = true;
                    statusLabel.setText("X turn");
                } else {
                    isXTurn = false;
                    statusLabel.setText("O turn");
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
                        JOptionPane.showMessageDialog(this, winner + " wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
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
        Button newGameButton = new Button("New Game");
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
                resetGamePanel();
            }
        });
        gamePanel.add(newGameButton);

        Button backButton = new Button("Main Menu");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "welcome");
                resetGame();
            }
        });
        gamePanel.add(backButton);

        Button exitButton = new Button("Exit");
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

