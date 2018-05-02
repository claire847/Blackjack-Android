// Blackjack: AdminScreen.java

//CLAIRE

package clairehofmann.blackjack_take2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class AdminScreen extends AppCompatActivity
{
    ArrayList<Player> db_listArray;
    ListView j_listView;
    CustomAdapter db_adapter;
    Button j_btn_logout;
    Button j_btn_addUser;
    Button j_adminScreen_playBtn;
    Intent activity_toLogin;
    Intent activity_toAdminEditInfo;
    Intent activity_toAdminAddUser;
    Intent activity_toGame;
    DatabaseHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        db_helper = new DatabaseHelper(this);
        db_listArray = db_helper.db_getAllRecords();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String currentPlayer = extras.getString("currentPlayer");

        db_adapter = new CustomAdapter(this, db_listArray);
        j_listView = findViewById(R.id.v_listView);
        j_listView.setAdapter(db_adapter);
        db_adapter.notifyDataSetChanged();

        j_btn_logout = findViewById(R.id.v_btn_logout);
        j_btn_addUser = findViewById(R.id.v_btn_addUser);
        j_adminScreen_playBtn = findViewById(R.id.v_adminScreen_playBtn);

        //BACK TO LOGIN SCREEN
        j_btn_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity_toLogin = new Intent(AdminScreen.this, LoginActivity.class);
                startActivity(activity_toLogin);
            }
        });

        //SCREEN TO ADD USER
        j_btn_addUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity_toAdminAddUser = new Intent(AdminScreen.this, AdminAddUser.class);
                activity_toAdminAddUser.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_toAdminAddUser);
            }
        });

        //SCREEN TO UPDATE USER INFO
        j_listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                activity_toAdminEditInfo = new Intent(AdminScreen.this, AdminEditInfo.class);
                activity_toAdminEditInfo.putExtra("i", i);
                activity_toAdminEditInfo.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_toAdminEditInfo);
            }
        });

        //DELETES USER
        j_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                db_helper.db_deletePlayer(String.valueOf(db_listArray.get(i).getEmail()));

                db_listArray.remove(i);
                db_adapter.notifyDataSetChanged();

                return false;
            }
        });

        j_adminScreen_playBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity_toGame = new Intent(AdminScreen.this, LoadingActivity.class);
                activity_toGame.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_toGame);
            }
        });
    }
}
