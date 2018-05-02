package clairehofmann.blackjack_take2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BettingScreen extends AppCompatActivity
{
    DatabaseHelper db_helper;
    ImageButton j_bs_dealBtn;
    ImageButton j_bs_exitBtn;
    ImageButton j_bs_allInBtn;
    ImageButton j_bs_resetBtn;
    ImageButton j_bs_fiveHundredBtn;
    ImageButton j_bs_oneHundredBtn;
    ImageButton j_bs_tenBtn;
    Intent activity_toGame;
    Intent activity_back;
    int bet;
    TextView j_bs_currentPlayer;
    TextView j_bs_cashTextView;
    TextView j_bs_betTxtView;
    Toast emptyFieldToast;
    Player temp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting_screen);

        db_helper = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String currentPlayer = extras.getString("currentPlayer");

        j_bs_dealBtn = findViewById(R.id.v_bs_dealBtn);
        j_bs_exitBtn = findViewById(R.id.v_bs_exitBtn);
        j_bs_allInBtn = findViewById(R.id.v_bs_allInBtn);
        j_bs_resetBtn = findViewById(R.id.v_bs_resetBtn);
        j_bs_fiveHundredBtn = findViewById(R.id.v_bs_fiveHundredBtn);
        j_bs_oneHundredBtn = findViewById(R.id.v_bs_oneHundredBtn);
        j_bs_tenBtn = findViewById(R.id.v_bs_tenBtn);
        j_bs_currentPlayer = findViewById(R.id.v_bs_currentPlayer);
        j_bs_cashTextView = findViewById(R.id.v_bs_cashTxtView);
        j_bs_betTxtView = findViewById(R.id.v_bs_betTxtView);
        temp = db_helper.getPlayerByEmail(String.valueOf(currentPlayer));
        bet = 0;

        j_bs_currentPlayer.setText(String.valueOf(currentPlayer));
        j_bs_cashTextView.setText("$" + temp.getCash());
        j_bs_betTxtView.setText("$" + bet);

        j_bs_fiveHundredBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int total = (bet + 500);

                if(total > Integer.valueOf(temp.getCash()))
                {
                    emptyFieldToast = Toast.makeText(getApplicationContext(), "Exceeds your total", Toast.LENGTH_LONG);
                    emptyFieldToast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                    emptyFieldToast.show();
                }
                else
                {
                    bet += 500;
                    int math = Integer.valueOf(temp.getCash()) - 500;
                    temp.setCash(String.valueOf(math));
                    j_bs_betTxtView.setText("$" + bet);
                    j_bs_cashTextView.setText("$" + temp.getCash());
                }
            }
        });

        j_bs_oneHundredBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int total = (bet + 100);

                if(total > Integer.valueOf(temp.getCash()))
                {
                    emptyFieldToast = Toast.makeText(getApplicationContext(), "Exceeds your total", Toast.LENGTH_LONG);
                    emptyFieldToast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                    emptyFieldToast.show();
                }
                else
                {
                    bet += 100;
                    int math = Integer.valueOf(temp.getCash()) - 100;
                    temp.setCash(String.valueOf(math));
                    j_bs_betTxtView.setText("$" + bet);
                    j_bs_cashTextView.setText("$" + temp.getCash());
                }
            }
        });

        j_bs_tenBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int total = (bet + 10);

                if(total > Integer.valueOf(temp.getCash()))
                {
                    emptyFieldToast = Toast.makeText(getApplicationContext(), "Exceeds your total", Toast.LENGTH_LONG);
                    emptyFieldToast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                    emptyFieldToast.show();
                }
                else
                {
                    bet += 10;
                    int math = Integer.valueOf(temp.getCash()) - 10;
                    temp.setCash(String.valueOf(math));
                    j_bs_betTxtView.setText("$" + bet);
                    j_bs_cashTextView.setText("$" + temp.getCash());
                }
            }
        });

        j_bs_resetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int math = Integer.valueOf(temp.getCash()) + bet;
                temp.setCash(String.valueOf(math));
                bet = 0;
                j_bs_betTxtView.setText("$" + bet);
                j_bs_cashTextView.setText("$" + temp.getCash());
            }
        });

        j_bs_allInBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                bet += Integer.valueOf(temp.getCash());
                temp.setCash(String.valueOf(0));
                j_bs_betTxtView.setText("$" + bet);
                j_bs_cashTextView.setText("$" + temp.getCash());
            }
        });

        j_bs_exitBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity_back = new Intent(BettingScreen.this, NonAdminMainScreen.class);
                activity_back.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_back);
            }
        });

        j_bs_dealBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (bet==0)
                {
                    emptyFieldToast = Toast.makeText(getApplicationContext(), "Place your bet", Toast.LENGTH_LONG);
                    emptyFieldToast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                    emptyFieldToast.show();
                }
                else
                {
                    db_helper.db_appendPlayer(temp);
                    activity_toGame = new Intent(BettingScreen.this, BlackjackGame.class);
                    activity_toGame.putExtra("currentPlayer", currentPlayer);
                    activity_toGame.putExtra("bet", bet);
                    startActivity(activity_toGame);
                }
            }
        });
    }
}
