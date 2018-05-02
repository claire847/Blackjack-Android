package clairehofmann.blackjack_take2;

//CLAIRE
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class NonAdminManageAccount extends AppCompatActivity
{
    ArrayList<Player> db_listArray;
    Button j_nama_cancelBtn;
    Button j_nama_updateBtn;
    Intent activity_toNonAdminScreen;
    Intent activity_toNonAdminScreen2;
    TextView j_nama_emailTxtView;
    EditText j_nama_passEditText;
    EditText j_nama_fNameEditText;
    EditText j_nama_lNameEditText;
    DatabaseHelper db_helper;
    Player temp;
    String currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_admin_manage_account);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        currentPlayer = extras.getString("currentPlayer");

        db_helper = new DatabaseHelper(this);
        db_listArray = db_helper.db_getAllRecords();

        temp = db_helper.getPlayerByEmail(String.valueOf(currentPlayer));

        j_nama_cancelBtn = findViewById(R.id.v_nama_cancelBtn);
        j_nama_cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                activity_toNonAdminScreen = new Intent(NonAdminManageAccount.this, NonAdminMainScreen.class);
                activity_toNonAdminScreen.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_toNonAdminScreen);
            }
        });

        j_nama_updateBtn = findViewById(R.id.v_nama_updateBtn);
        j_nama_updateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                temp.setPassword(String.valueOf(j_nama_passEditText.getText()));
                temp.setFirstName(String.valueOf(j_nama_fNameEditText.getText()));
                temp.setLastName(String.valueOf(j_nama_lNameEditText.getText()));
                db_helper.db_appendPlayer(temp);

                activity_toNonAdminScreen2 = new Intent(NonAdminManageAccount.this, NonAdminMainScreen.class);
                activity_toNonAdminScreen2.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_toNonAdminScreen2);
            }
        });

        j_nama_emailTxtView = findViewById(R.id.v_nama_emailTxtView);
        j_nama_emailTxtView.setText(String.valueOf(temp.getEmail()));

        j_nama_passEditText = findViewById(R.id.v_nama_passwordEditTxt);
        j_nama_passEditText.setText(String.valueOf(temp.getPassword()));

        j_nama_fNameEditText = findViewById(R.id.v_nama_fNameEditTxt);
        j_nama_fNameEditText.setText(String.valueOf(temp.getFirstName()));

        j_nama_lNameEditText = findViewById(R.id.v_nama_lNameEditTxt);
        j_nama_lNameEditText.setText(String.valueOf(temp.getLastName()));
    }
}
