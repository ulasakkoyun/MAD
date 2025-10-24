package msku.ceng.madlab.week5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static final String PLAYER_1 = "X";
    static final String PLAYER_2 = "O";

    boolean player1_Turn = true;
    byte[][] board = new byte[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            TableRow tableRow = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) tableRow.getChildAt(j);
                button.setOnClickListener(new CellListener(i, j));
            }
        }
    }

    // A helper method to disable all buttons on the board when the game ends
    private void disableBoard() {
        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) row.getChildAt(j);
                button.setEnabled(false);
            }
        }
    }

    public boolean isValidMove(int row, int column) {
        return board[row][column] == 0;
    }

    public int gameEnded(int row, int col) {
        int symbol = board[row][col];
        boolean win;

        // Check Row
        win = true;
        for (int i = 0; i < 3; i++) {
            if (board[row][i] != symbol) {
                win = false;
                break;
            }
        }
        if (win) return symbol;

        // Check Column
        win = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][col] != symbol) {
                win = false;
                break;
            }
        }
        if (win) return symbol;

        // Check Diagonals
        if (row == col) { // Main diagonal
            win = true;
            for (int i = 0; i < 3; i++) {
                if (board[i][i] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) return symbol;
        }
        if (row + col == 2) { // Anti-diagonal
            win = true;
            for (int i = 0; i < 3; i++) {
                if (board[i][2 - i] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) return symbol;
        }

        // Check for Draw
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return -1; // Game is not over yet
                }
            }
        }

        return 0; // It's a draw
    }

    class CellListener implements View.OnClickListener {
        int row, column;

        public CellListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void onClick(View v) {
            if (!isValidMove(row, column)) {
                Toast.makeText(MainActivity.this, "Cell is already occupied", Toast.LENGTH_SHORT).show();
                return;
            }

            Button button = (Button) v;
            button.setTextSize(40); // Set text size for visibility

            // --- FIX 2: Added color logic to onClick ---
            if (player1_Turn) {
                button.setText(PLAYER_1);
                button.setTextColor(getColor(R.color.player_x_color)); // Set color for Player X
                board[row][column] = 1;
            } else {
                button.setText(PLAYER_2);
                button.setTextColor(getColor(R.color.player_o_color)); // Set color for Player O
                board[row][column] = 2;
            }
            button.setEnabled(false);

            int gameState = gameEnded(row, column);

            if (gameState == -1) { // Game continues
                player1_Turn = !player1_Turn;
            } else if (gameState == 0) { // Draw
                Toast.makeText(MainActivity.this, "Game is a DRAW", Toast.LENGTH_LONG).show();
                disableBoard();
            } else if (gameState == 1) { // Player 1 wins
                Toast.makeText(MainActivity.this, "Player 1 WINS!", Toast.LENGTH_LONG).show();
                disableBoard();
            } else { // Player 2 wins
                Toast.makeText(MainActivity.this, "Player 2 WINS!", Toast.LENGTH_LONG).show();
                disableBoard();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("player1Turn", player1_Turn);
        byte[] boardSingle = new byte[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardSingle[i * 3 + j] = board[i][j];
            }
        }
        outState.putByteArray("board", boardSingle);
    }

    // --- FIX 1: Removed the duplicate method ---
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        player1_Turn = savedInstanceState.getBoolean("player1Turn");
        byte[] boardSingle = savedInstanceState.getByteArray("board");
        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = boardSingle[i];
        }

        // Restore the UI (button text, state, and color) from the board array
        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) row.getChildAt(j);
                button.setTextSize(40); // Also set the text size here

                if (board[i][j] == 1) {
                    button.setText(PLAYER_1);
                    button.setTextColor(getColor(R.color.player_x_color)); // Restore color for X
                    button.setEnabled(false);
                } else if (board[i][j] == 2) {
                    button.setText(PLAYER_2);
                    button.setTextColor(getColor(R.color.player_o_color)); // Restore color for O
                    button.setEnabled(false);
                }
            }
        }
    }
}
