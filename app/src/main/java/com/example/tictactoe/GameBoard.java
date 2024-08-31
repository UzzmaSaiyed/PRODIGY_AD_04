package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameBoard extends AppCompatActivity {

    private TicTacToeBoard tictacboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_board);


        Button playAgain = findViewById(R.id.playagain);
        Button home=findViewById(R.id.home);
        TextView playerTurn = findViewById(R.id.textView5);
        playAgain.setVisibility(View.GONE);
        home.setVisibility(View.GONE);

        String[] playername = getIntent().getStringArrayExtra("Player_Names");

        if(playername != null)
        {
            playerTurn.setText(playername[0] + "'s Turn");
        }

        tictacboard = findViewById(R.id.ticTacToeBoard2);
        tictacboard.setUpGame(playAgain,home,playerTurn,playername);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void playagain(View view)
    {
//        Intent i = new Intent(this,GameBoard.class);
//        startActivity(i);

        tictacboard.resetGame();
        tictacboard.invalidate();

    }

    public void home(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}