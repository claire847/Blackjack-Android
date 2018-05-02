package clairehofmann.blackjack_take2;

//CLAIRE
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NonAdminMainScreen extends AppCompatActivity
{
    Button j_nams_logoutBtn;
    Button j_nams_manageBtn;
    Button j_nams_playBtn;
    Intent activity_toLogin;
    Intent activity_toManage;
    Intent activity_toGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_admin_main_screen);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String currentPlayer = extras.getString("currentPlayer");

        j_nams_manageBtn = findViewById(R.id.v_nams_manageBtn);
        j_nams_manageBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity_toManage = new Intent(NonAdminMainScreen.this, NonAdminManageAccount.class);
                activity_toManage.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_toManage);
            }
        });

        j_nams_logoutBtn = findViewById(R.id.v_nams_logoutBtn);
        j_nams_logoutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity_toLogin = new Intent(NonAdminMainScreen.this, LoginActivity.class);
                startActivity(activity_toLogin);
            }
        });

        j_nams_playBtn = findViewById(R.id.v_nams_playBtn);
        j_nams_playBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity_toGame = new Intent(NonAdminMainScreen.this, LoadingActivity.class);
                activity_toGame.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_toGame);
            }
        });
    }
}
