// Joleen Powers (and Claire Hofmann)
// Blackjack: NewPlayerScreen.java

package clairehofmann.blackjack_take2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPlayerScreen extends AppCompatActivity
{
    Button          j_newPlayerReg_cancelBtn;
    Button          j_newPlayerReg_registerBtn;
    DatabaseHelper  db_helper;
    EditText        j_newPlayerReg_emailEditText;
    EditText        j_newPlayerReg_passEditText;
    EditText        j_newPlayerReg_confirmPassEditText;
    EditText        j_newPlayerReg_fNameEditText;
    EditText        j_newPlayerReg_lNameEditText;
    Intent          activity_returnToMainLogin;
    Toast           emptyFieldToast;
    Toast           passwordsDontMatchToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_player_registration);

        db_helper = new DatabaseHelper(this);

        j_newPlayerReg_cancelBtn = findViewById(R.id.v_aei_cancelBtn);
        j_newPlayerReg_registerBtn = findViewById(R.id.v_aei_updateBtn);
        j_newPlayerReg_emailEditText = findViewById(R.id.v_newPlayerReg_emailEditText);
        j_newPlayerReg_passEditText = findViewById(R.id.v_aei_passEditText);
        j_newPlayerReg_confirmPassEditText = findViewById(R.id.v_newPlayerReg_confirmPassEditText);
        j_newPlayerReg_fNameEditText = findViewById(R.id.v_aei_fNameEditText);
        j_newPlayerReg_lNameEditText = findViewById(R.id.v_aei_lNameEditText);
        emptyFieldToast = Toast.makeText(getApplicationContext(), "One or more required fields is empty. Please try again.", Toast.LENGTH_LONG);
        emptyFieldToast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
        passwordsDontMatchToast = Toast.makeText(getApplicationContext(), "The passwords you entered do not match. Please try again.", Toast.LENGTH_LONG);
        passwordsDontMatchToast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);


        // User clicks cancel, returns to main login page.
        j_newPlayerReg_cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity_returnToMainLogin = new Intent(NewPlayerScreen.this, LoginActivity.class);
                startActivity(activity_returnToMainLogin);
            }
        });

        // Register new player.
        j_newPlayerReg_registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Verify that the password entries match.
                if (String.valueOf(j_newPlayerReg_passEditText.getText()).equals(String.valueOf(j_newPlayerReg_confirmPassEditText.getText())))
                {
                    // All fields require input.
                    if (!j_newPlayerReg_emailEditText.getText().toString().trim().isEmpty() &&
                            !j_newPlayerReg_emailEditText.getText().toString().trim().equals("null"))
                    {
                        if (!j_newPlayerReg_passEditText.getText().toString().trim().isEmpty() &&
                                !j_newPlayerReg_passEditText.getText().toString().trim().equals("null"))
                        {
                            if (!j_newPlayerReg_confirmPassEditText.getText().toString().trim().isEmpty() &&
                                    !j_newPlayerReg_confirmPassEditText.getText().toString().trim().equals("null"))
                            {
                                if (!j_newPlayerReg_fNameEditText.getText().toString().trim().isEmpty() &&
                                        !j_newPlayerReg_fNameEditText.getText().toString().trim().equals("null"))
                                {
                                    if (!j_newPlayerReg_lNameEditText.getText().toString().trim().isEmpty() &&
                                            !j_newPlayerReg_lNameEditText.getText().toString().trim().equals("null"))
                                    {
                                        // Proceed with registration.
                                        Player newPlayer = new Player();

                                        newPlayer.setEmail(String.valueOf(j_newPlayerReg_emailEditText.getText()));
                                        newPlayer.setPassword(String.valueOf(j_newPlayerReg_passEditText.getText()));
                                        newPlayer.setFirstName(String.valueOf(j_newPlayerReg_fNameEditText.getText()));
                                        newPlayer.setLastName(String.valueOf(j_newPlayerReg_lNameEditText.getText()));
                                        newPlayer.setAdminCheck("0.0");
                                        newPlayer.setCash("500");

                                        // Check email address against registered players in the database.
                                        if (!db_helper.checkEmail(newPlayer.getEmail()))
                                        {
                                            // If the email address is not previously registered, proceed and return to login.
                                            db_helper.addPlayers(newPlayer);
                                            Intent activity_toLogin = new Intent(NewPlayerScreen.this, LoginActivity.class);
                                            startActivity(activity_toLogin);
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
                    else
                    {
                        emptyFieldToast.show();
                    }
                }
                else if (!String.valueOf(j_newPlayerReg_passEditText.getText()).equals(String.valueOf(j_newPlayerReg_confirmPassEditText.getText())))
                {
                    passwordsDontMatchToast.show();
                }
            }
        });
    }
}
