package clairehofmann.blackjack_take2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends AppCompatActivity
{
    ProgressBar j_progressBar;
    TextView j_loadingTxtView;
    int progressStatus = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String currentPlayer = extras.getString("currentPlayer");

        j_progressBar = findViewById(R.id.v_progressBar);
        j_loadingTxtView = findViewById(R.id.v_loadingTxtView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100)
                {
                    progressStatus++;
                    android.os.SystemClock.sleep(25);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            j_progressBar.setProgress(progressStatus);
                            j_loadingTxtView.setText("Loading... " + progressStatus + "%");
                        }
                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent2 = new Intent(LoadingActivity.this, BettingScreen.class);
                        intent2.putExtra("currentPlayer", currentPlayer);
                        startActivity(intent2);
                        finish();
                    }
                });
            }
        }).start();
    }
}
