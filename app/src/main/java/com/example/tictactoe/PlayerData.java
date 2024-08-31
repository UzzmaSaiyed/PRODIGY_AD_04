package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PlayerData extends AppCompatActivity {

    private EditText player1;
    private EditText player2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.player_data);

        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void goongameboard(View view)
    {
        String player1Name = player1.getText().toString();
        if(player1Name.equals(""))
        {
            player1Name = "Player 1";
        }


        String player2Name = player2.getText().toString();
        if(player2Name.equals(""))
        {
            player2Name ="Player 2";
        }
        Intent i = new Intent(this,GameBoard.class);
        i.putExtra("Player_Names", new String[] {player1Name,player2Name});
        startActivity(i);
    }
}