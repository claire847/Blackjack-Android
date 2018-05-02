// Joleen Powers (and Claire Hofmann)
// Blackjack: LoginActivity.java

// Joleen

package clairehofmann.blackjack_take2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity
{
    //JOLEEN
    ArrayList<Player>   db_listArray;
    Button              j_loginBtn;
    Button              j_newPlayerBtn;
    DatabaseHelper      db_helper;
    EditText            j_email_editTxt;
    EditText            j_password_editTxt;
    Intent              j_activity_toRegisterNewUserScreen;

    //CLAIRE
    Intent activity_toAdminScreen;
    Intent activity_toNonAdmin;
    TextView j_errorTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //JOLEEN
        j_loginBtn = findViewById(R.id.v_loginBtn);
        j_newPlayerBtn = findViewById(R.id.v_newPlayerBtn);
        j_email_editTxt = findViewById(R.id.v_email_editTxt);
        j_password_editTxt = findViewById(R.id.v_password_editTxt);

        db_helper = new DatabaseHelper(this);
        db_listArray = db_helper.db_getAllRecords();

        db_helper.db_initRows();
        db_listArray = db_helper.db_getAllRecords();

        //CLAIRE
        j_errorTxtView = findViewById(R.id.v_errorTxtView);

        j_loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Player temp;
                temp = db_helper.getPlayerByEmail(String.valueOf(j_email_editTxt.getText()));

                //IF USERNAME AND PASSWORD MATCH
                if((String.valueOf(j_password_editTxt.getText())).equals(String.valueOf(temp.getPassword())))
                {
                    //IF USER IS ADMIN
                    if (String.valueOf(temp.getAdminCheck()).equals("1.0"))
                    {
                        activity_toAdminScreen = new Intent(LoginActivity.this, AdminScreen.class);
                        activity_toAdminScreen.putExtra("currentPlayer", String.valueOf(j_email_editTxt.getText()));
                        startActivity(activity_toAdminScreen);
                    }
                    //IF USER IS NOT ADMIN
                    else
                    {
                        activity_toNonAdmin = new Intent(LoginActivity.this, NonAdminMainScreen.class);
                        activity_toNonAdmin.putExtra("currentPlayer", String.valueOf(j_email_editTxt.getText()));
                        startActivity(activity_toNonAdmin);
                    }

                }
                //IF USERNAME AND PASSWORD DON'T MATCH
                else
                {
                    //Toast.makeText(LoginActivity.this, "The username or password you entered is incorrect. Please try again.", Toast.LENGTH_LONG).show();
                    j_errorTxtView.setText("The username or password you entered is incorrect. Please try again.");
                }
            }
        });

        j_newPlayerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                j_activity_toRegisterNewUserScreen = new Intent(LoginActivity.this, NewPlayerScreen.class);
                startActivity(j_activity_toRegisterNewUserScreen);
            }
        });

    }
}
