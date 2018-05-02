// Blackjack: AdminEditInfo.java

//CLAIRE

package clairehofmann.blackjack_take2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminEditInfo extends AppCompatActivity
{
    ArrayList<Player> db_listArray;
    Button j_aei_backBtn;
    Button j_aei_updateBtn;
    Intent activity_toAdminScreen;
    Intent activity_toAdminScreen2;
    TextView j_aei_emailTxtView;
    EditText j_aei_passEditText;
    EditText j_aei_fNameEditText;
    EditText j_aei_lNameEditText;
    EditText j_aei_adminEditText;
    EditText j_aei_cashEditText;
    DatabaseHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_info);

        db_helper = new DatabaseHelper(this);
        db_listArray = db_helper.db_getAllRecords();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String currentPlayer = extras.getString("currentPlayer");
        final int i = extras.getInt("i");

        j_aei_backBtn = findViewById(R.id.v_aei_cancelBtn);

        //BACK TO PREVIOUS SCREEN WITHOUT UPDATING
        j_aei_backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity_toAdminScreen2 = new Intent(AdminEditInfo.this, AdminScreen.class);
                activity_toAdminScreen2.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_toAdminScreen2);

            }
        });

        j_aei_updateBtn = findViewById(R.id.v_aei_updateBtn);

        //UPDATES USER AND GOES BACK TO PREVIOUS SCREEN
        j_aei_updateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Player temp = new Player();
                temp = db_helper.getPlayerByEmail(String.valueOf(j_aei_emailTxtView.getText()));

                temp.setPassword(String.valueOf(j_aei_passEditText.getText()));
                temp.setFirstName(String.valueOf(j_aei_fNameEditText.getText()));
                temp.setLastName(String.valueOf(j_aei_lNameEditText.getText()));
                temp.setAdminCheck(String.valueOf(j_aei_adminEditText.getText()));
                temp.setCash(String.valueOf(j_aei_cashEditText.getText()));
                db_helper.db_appendPlayer(temp);

                activity_toAdminScreen = new Intent(AdminEditInfo.this, AdminScreen.class);
                activity_toAdminScreen.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_toAdminScreen);
            }
        });

        j_aei_emailTxtView = findViewById(R.id.v_aei_emailTxtView);
        j_aei_emailTxtView.setText(String.valueOf(db_listArray.get(i).getEmail()));

        j_aei_passEditText = findViewById(R.id.v_aei_passEditText);
        j_aei_passEditText.setText(String.valueOf(db_listArray.get(i).getPassword()));

        j_aei_fNameEditText = findViewById(R.id.v_aei_fNameEditText);
        j_aei_fNameEditText.setText(String.valueOf(db_listArray.get(i).getFirstName()));

        j_aei_lNameEditText = findViewById(R.id.v_aei_lNameEditText);
        j_aei_lNameEditText.setText(String.valueOf(db_listArray.get(i).getLastName()));

        j_aei_adminEditText = findViewById(R.id.v_aei_adminEditText);
        j_aei_adminEditText.setText(String.valueOf(db_listArray.get(i).getAdminCheck()));

        j_aei_cashEditText = findViewById(R.id.v_aei_cashEditText);
        j_aei_cashEditText.setText(String.valueOf(db_listArray.get(i).getCash()));
    }
}
