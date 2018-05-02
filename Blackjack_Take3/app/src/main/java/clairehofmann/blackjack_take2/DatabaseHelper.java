// Blackjack: DatabaseHelper.java
// Reproduced in parts with permission from Zackary Moore, DatabaseHelper.java created on 10/29/17


package clairehofmann.blackjack_take2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import clairehofmann.blackjack_take2.Player;
import java.util.ArrayList;

// Database structure:
//==============================//
//       Player Database        //
//------------------------------//
//  Column:         Data Type:  //
//      email           String  //
//      password        String  //
//      adminCheck      Boolean //
//      firstName       String  //
//      lastName        String  //
//      cash            Double  //
//==============================//

public class DatabaseHelper extends SQLiteOpenHelper
{
    //JOLEEN
    private static final int DATABASE_VERSION = 24;
    private static final String DATABASE_NAME = "playerDB.db";
    private static final String TABLE_ADMIN   = "table_admin";
    private static final String COLUMN_EMAIL       = "email";
    private static final String COLUMN_PASSWORD    = "password";
    private static final String COLUMN_ADMIN_CHECK = "admin_check";
    private static final String COLUMN_FIRSTNAME   = "first_name";
    private static final String COLUMN_LASTNAME    = "last_name";
    private static final String COLUMN_CASH        = "cash";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create database.
    @Override
    public void onCreate(SQLiteDatabase database)
    {
        // Create table.
        database.execSQL("CREATE TABLE " + TABLE_ADMIN + "("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY NOT NULL,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_ADMIN_CHECK + " TEXT,"
                + COLUMN_FIRSTNAME + " TEXT,"
                + COLUMN_LASTNAME + " TEXT,"
                + COLUMN_CASH + " TEXT" +  ");");
    }

    // Drop any existing tables and create new.
    @Override
    public void onUpgrade(SQLiteDatabase database, int olderVersion, int newerVersion) // new == keyword
    {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        onCreate(database);
    }

    public void db_initRows()
    {
        if(db_numberOfRows() == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();

            // Hard-coded (2) admins for testing.

            db.execSQL("INSERT INTO " + TABLE_ADMIN + "(email, password, admin_check, first_name, last_name, cash ) VALUES ('claire847@gmail.com', 'admin2', '1.0', 'Claire', 'Hofmann', '100000000')");
            db.execSQL("INSERT INTO " + TABLE_ADMIN + "(email, password, admin_check, first_name, last_name, cash ) VALUES ('player1@gmail.com', 'player1', '0', 'Player', 'One', '500')");

            db.close();
        }
    }

    private int db_numberOfRows()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRowsAdmin = (int) DatabaseUtils.queryNumEntries(db, TABLE_ADMIN);

        db.close();
        return numRowsAdmin;
    }

    // Delete a player's record.
    public void db_deletePlayer(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ADMIN + " WHERE " + COLUMN_EMAIL + " = '" + email + "'");
        db.close();
    }

    // Finds player in database by email address.
    public Player getPlayerByEmail(String email)
    {
        SQLiteDatabase db = getReadableDatabase();
        Player player = new Player();

        // Search for a player's data using email address as the query.
        String query = "SELECT * FROM " + TABLE_ADMIN + " WHERE " + COLUMN_EMAIL
                + " = '" + email + "';";

        // Get results.
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            if (cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)) != null)
            {
                player.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                player.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                player.setAdminCheck(cursor.getString(cursor.getColumnIndex(COLUMN_ADMIN_CHECK)));
                player.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME)));
                player.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME)));
                player.setCash(cursor.getString(cursor.getColumnIndex(COLUMN_CASH)));
            }
            cursor.moveToNext();
        }

        db.close();     // Close the database.
        cursor.close();
        return player;
    }

    // Save a player's information to the database.
    // This inserts or performs an update operation on a table.
    public void db_appendPlayer(Player player)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> allPlayers = new ArrayList<>();
        String queryResult = null;

        if (player.getEmail() != null)
        {
            String str = "SELECT " + COLUMN_EMAIL + " FROM " + TABLE_ADMIN
                    + " WHERE " + COLUMN_EMAIL + " = ?;";

            db.beginTransaction();
            SQLiteStatement sqLiteStatement = db.compileStatement(str);
            sqLiteStatement.bindString(1, player.getEmail());

            queryResult = sqLiteStatement.simpleQueryForString();
            db.setTransactionSuccessful();
            db.endTransaction();
        }

        // Update if player already exists.
        if (player.getEmail() != null && queryResult != null)
        {

            db.beginTransactionNonExclusive();
            StringBuilder updateQuery = new StringBuilder("UPDATE " + TABLE_ADMIN + " ");

            // Set the player's email address.
            if (player.getEmail() != null)
            {
                if (allPlayers.size() == 0)
                {
                    updateQuery.append( " SET ");
                }
                else
                {
                    updateQuery.append(" , ");
                }
                updateQuery.append(COLUMN_EMAIL + " = ? ");
                allPlayers.add(player.getEmail());
            }

            // Set the player's password.
            if (player.getPassword() != null)
            {
                if (allPlayers.size() == 0)
                {
                    updateQuery.append(" SET ");
                }
                else
                {
                    updateQuery.append(" , ");
                }
                updateQuery.append(COLUMN_PASSWORD + " = ? ");
                allPlayers.add(player.getPassword());
            }

            // Is the player an admin?
            if (player.getAdminCheck() != null)
            {
                if (allPlayers.size() == 0)
                {
                    updateQuery.append(" SET ");
                }
                else
                {
                    updateQuery.append(" , ");
                }
                updateQuery.append(COLUMN_ADMIN_CHECK + " = ? ");
                allPlayers.add(player.getAdminCheck());
            }

            // Set the player's first name.
            if (player.getFirstName() != null)
            {
                if (allPlayers.size() == 0)
                {
                    updateQuery.append(" SET ");
                }
                else
                {
                    updateQuery.append(" , ");
                }
                updateQuery.append(COLUMN_FIRSTNAME + " = ? ");
                allPlayers.add(player.getFirstName());
            }

            // Set the player's last name.
            if (player.getLastName() != null)
            {
                if (allPlayers.size() == 0)
                {
                    updateQuery.append(" SET ");
                }
                else
                {
                    updateQuery.append(" , ");
                }
                updateQuery.append(COLUMN_LASTNAME + " = ? ");
                allPlayers.add(player.getLastName());
            }

            // Set the player's available cash.
            if (player.getCash() != "0")
            {
                if (allPlayers.size() == 0)
                {
                    updateQuery.append(" SET ");
                }
                else
                {
                    updateQuery.append(" , ");
                }
                updateQuery.append(COLUMN_CASH + " = ? ");
                allPlayers.add(player.getCash());
            }

            // Update record based on email address.
            updateQuery.append(" WHERE " + COLUMN_EMAIL + " = ? ;");
            allPlayers.add(player.getEmail());

            SQLiteStatement updateStatement = db.compileStatement(updateQuery.toString());

            // Sets objects.
            for (int i = 0; i < allPlayers.size(); i++)
            {
                Object obj = allPlayers.get(i);

                updateStatement.bindString(i + 1, ((String) obj));
            }

            // Update.
            updateStatement.executeUpdateDelete();
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        else    // Player doesn't exist. Insert player.
        {
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_EMAIL, player.getEmail());
            contentValues.put(COLUMN_PASSWORD, player.getPassword());
            contentValues.put(COLUMN_ADMIN_CHECK, player.getAdminCheck());
            contentValues.put(COLUMN_FIRSTNAME, player.getFirstName());
            contentValues.put(COLUMN_LASTNAME, player.getLastName());
            contentValues.put(COLUMN_CASH, player.getCash());

            db.beginTransactionNonExclusive();
            db.insert(TABLE_ADMIN, null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
        }
        db.close(); // Close the database.
    }

    //CLAIRE
    // Returns all players in database.
    public ArrayList<Player> db_getAllRecords()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorAdmin = db.query(TABLE_ADMIN, null, null, null, null, null, null);
        ArrayList<Player> allPlayers = new ArrayList<Player>();
        Player player;
        if (cursorAdmin.getCount()> 0)
        {
            for (int i = 0; i < cursorAdmin.getCount(); i++)
            {
                cursorAdmin.moveToNext();
                player = new Player();
                player.setEmail(cursorAdmin.getString(0));
                player.setPassword(cursorAdmin.getString(1));
                player.setAdminCheck(cursorAdmin.getString(2));
                player.setFirstName(cursorAdmin.getString(3));
                player.setLastName(cursorAdmin.getString(4));
                player.setCash(cursorAdmin.getString(5));
                allPlayers.add(player);
            }
        }
        cursorAdmin.close();
        db.close();
        return allPlayers;
    }

    // Insert database records.
    public void addPlayers(Player player)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_EMAIL, player.getEmail());
        values.put(COLUMN_PASSWORD, player.getPassword());
        values.put(COLUMN_ADMIN_CHECK, player.getAdminCheck());
        values.put(COLUMN_FIRSTNAME, player.getFirstName());
        values.put(COLUMN_LASTNAME, player.getLastName());
        values.put(COLUMN_CASH, player.getCash());

        db.insert(TABLE_ADMIN, null, values);
    }

    // Joleen
    // Returns true if email address being registered already exist in the database.
    public Boolean checkEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        // Search database for matching email address
        String query = "SELECT * FROM " + TABLE_ADMIN + " WHERE " + COLUMN_EMAIL
                + " = '" + email + "';";

        // Get results.
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            if (cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)).equals(email))
            {
                return true;    // Return true if match is found.
            }
            cursor.moveToNext();
        }
        return false;
    }
}