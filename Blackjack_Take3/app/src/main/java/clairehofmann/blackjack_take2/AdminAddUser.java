// Blackjack: AdminAddUser.java

//CLAIRE

package clairehofmann.blackjack_take2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AdminAddUser extends AppCompatActivity
{
    Button j_aau_cancelBtn;
    Button j_aau_addBtn;
    EditText j_aau_emailEditText;
    EditText j_aau_passEditText;
    EditText j_aau_fNameEditText;
    EditText j_aau_lNameEditText;
    EditText j_aau_adminEditText;
    EditText j_aau_cashEditText;
    DatabaseHelper db_helper;
    Toast   emptyFieldToast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_user);

        db_helper = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String currentPlayer = extras.getString("currentPlayer");

        j_aau_cancelBtn = findViewById(R.id.v_aei_cancelBtn);
        j_aau_addBtn = findViewById(R.id.v_aei_updateBtn);

        j_aau_emailEditText = findViewById(R.id.v_newPlayerReg_emailEditText);
        j_aau_passEditText = findViewById(R.id.v_aei_passEditText);
        j_aau_fNameEditText = findViewById(R.id.v_aei_fNameEditText);
        j_aau_lNameEditText = findViewById(R.id.v_aei_lNameEditText);
        j_aau_adminEditText = findViewById(R.id.v_aei_adminEditText);
        j_aau_cashEditText = findViewById(R.id.v_aei_cashEditText);
        emptyFieldToast = Toast.makeText(getApplicationContext(), "One or more required fields is empty. Please try again.", Toast.LENGTH_LONG);
        emptyFieldToast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);


        //BACK TO PREVIOUS SCREEN WITHOUT ADDING NEW PLAYER
        j_aau_cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent activity_toAdminScreen = new Intent(AdminAddUser.this, AdminScreen.class);
                activity_toAdminScreen.putExtra("currentPlayer", currentPlayer);
                startActivity(activity_toAdminScreen);
            }
        });

        //ADDS NEW PLAYER AND GOES BACK
        // Joleen: Added the ability to check admin registrations against the database in order to
        // prevent registering multiple players to the same email address (primary key).
        j_aau_addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // All fields require input.
                if (!j_aau_emailEditText.getText().toString().trim().isEmpty() &&
                        !j_aau_emailEditText.getText().toString().trim().equals("null"))
                {
                    if (!j_aau_passEditText.getText().toString().trim().isEmpty() &&
                            !j_aau_passEditText.getText().toString().trim().equals("null"))
                    {

                        if (!j_aau_fNameEditText.getText().toString().trim().isEmpty() &&
                                !j_aau_lNameEditText.getText().toString().trim().equals("null"))
                        {
                            if (!j_aau_lNameEditText.getText().toString().trim().isEmpty() &&
                                    !j_aau_lNameEditText.getText().toString().trim().equals("null"))
                            {
                                if (!j_aau_adminEditText.getText().toString().trim().isEmpty() &&
                                        !j_aau_adminEditText.getText().toString().trim().equals("null"))
                                {
                                    if (!j_aau_cashEditText.getText().toString().trim().isEmpty() &&
                                            !j_aau_cashEditText.getText().toString().trim().equals("null"))
                                    {
                                        // Proceed with registration.
                                        Player temp = new Player();

                                        temp.setEmail(String.valueOf(j_aau_emailEditText.getText()));
                                        temp.setPassword(String.valueOf(j_aau_passEditText.getText()));
                                        temp.setFirstName(String.valueOf(j_aau_fNameEditText.getText()));
                                        temp.setLastName(String.valueOf(j_aau_lNameEditText.getText()));
                                        temp.setAdminCheck(String.valueOf(j_aau_adminEditText.getText()));
                                        temp.setCash(String.valueOf(j_aau_cashEditText.getText()));

                                        // Check email address against registered players in the database.
                                        if (!db_helper.checkEmail(temp.getEmail()))
                                        {
                                            // If the email address is not previously registered, proceed and return to admin screen.
                                            db_helper.addPlayers(temp);
                                            Intent activity_toAdminScreen2 = new Intent(AdminAddUser.this, AdminScreen.class);
                                            activity_toAdminScreen2.putExtra("currentPlayer", currentPlayer);
                                            startActivity(activity_toAdminScreen2);
                                        }
                                        else
                                        {
                                            // If the email address is previously registered, display an error message.
                                            Toast toast2 = Toast.makeText(getApplicationContext(), "The email address you entered already exists. Please try again.", Toast.LENGTH_LONG);
                                            toast2.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                                            toast2.show();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    emptyFieldToast.show();
                }
            }
        });
    }
}
