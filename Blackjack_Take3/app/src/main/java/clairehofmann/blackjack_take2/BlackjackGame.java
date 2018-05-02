package clairehofmann.blackjack_take2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class BlackjackGame extends AppCompatActivity
{
    ArrayList<Cards> deck;
    ArrayList<Cards> playerHand;
    ArrayList<Cards> dealerHand;
    Cards Card;
    Context context;
    DatabaseHelper db_helper;
    ImageButton j_bg_hitBtn;
    ImageButton j_bg_standBtn;
    //ImageButton j_bg_doubleBtn;
    ImageView j_bg_pcard1;
    ImageView j_bg_pcard2;
    ImageView j_bg_pcard3;
    ImageView j_bg_pcard4;
    ImageView j_bg_pcard5;
    ImageView j_bg_pcard6;
    ImageView j_bg_pcard7;
    ImageView j_bg_pcard8;
    ImageView j_bg_dcard1;
    ImageView j_bg_dcard2;
    ImageView j_bg_dcard3;
    ImageView j_bg_dcard4;
    ImageView j_bg_dcard5;
    ImageView j_bg_dcard6;
    ImageView j_bg_dcard7;
    ImageView j_bg_dcard8;
    int playerTotal;
    int dealerTotal;
    int id;
    int i;
    int bet;
    Player temp;
    String currentPlayer;
    TextView j_bg_cashTxtView;
    TextView j_bg_betTxtView;
    TextView j_bg_playerTotalTxtView;
    TextView j_bg_dealerTotalTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack_game);

        db_helper = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        currentPlayer = extras.getString("currentPlayer");
        bet = extras.getInt("bet");

        j_bg_hitBtn = findViewById(R.id.v_bg_hitBtn);
        j_bg_standBtn = findViewById(R.id.v_bg_standBtn);
        //j_bg_doubleBtn = findViewById(R.id.v_bg_doubleBtn);
        j_bg_pcard1 = findViewById(R.id.v_bg_pcard1);
        j_bg_pcard2 = findViewById(R.id.v_bg_pcard2);
        j_bg_pcard3 = findViewById(R.id.v_bg_pcard3);
        j_bg_pcard4 = findViewById(R.id.v_bg_pcard4);
        j_bg_pcard5 = findViewById(R.id.v_bg_pcard5);
        j_bg_pcard6 = findViewById(R.id.v_bg_pcard6);
        j_bg_pcard7 = findViewById(R.id.v_bg_pcard7);
        j_bg_pcard8 = findViewById(R.id.v_bg_pcard8);
        j_bg_dcard1 = findViewById(R.id.v_bg_dcard1);
        j_bg_dcard2 = findViewById(R.id.v_bg_dcard2);
        j_bg_dcard3 = findViewById(R.id.v_bg_dcard3);
        j_bg_dcard4 = findViewById(R.id.v_bg_dcard4);
        j_bg_dcard5 = findViewById(R.id.v_bg_dcard5);
        j_bg_dcard6 = findViewById(R.id.v_bg_dcard6);
        j_bg_dcard7 = findViewById(R.id.v_bg_dcard7);
        j_bg_dcard8 = findViewById(R.id.v_bg_dcard8);
        j_bg_cashTxtView = findViewById(R.id.v_bg_cashTxtView);
        j_bg_betTxtView = findViewById(R.id.v_bg_betTxtView);
        j_bg_playerTotalTxtView = findViewById(R.id.v_bg_playerTotalTxtView);
        j_bg_dealerTotalTxtView = findViewById(R.id.v_bg_dealerTotalTxtView);
        deck = new ArrayList<>();
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();

        temp = db_helper.getPlayerByEmail(currentPlayer);
        j_bg_cashTxtView.setText("$" + temp.getCash());
        j_bg_betTxtView.setText("$" + bet);


        //CREATES DECK OF CARDS
        String suit[] = "hearts,spades,clubs,diamonds".split(",");
        String face[] = "ace,two,three,four,five,six,seven,eight,nine,ten,jack,queen,king".split(",");

        for (int suitnum = 0; suitnum < suit.length; suitnum++)
        {
            for (int facenum = 0; facenum < face.length; facenum++)
            {
                Cards Card = new Cards();
                int points = 0;

                Card.setSuit(suit[suitnum]);
                Card.setFace(face[facenum]);
                Card.setFileName("@drawable/" + face[facenum] + "_" + suit[suitnum]);

                //POINTS OF CARDS
                if(String.valueOf(face[facenum]).matches("two" ))
                {
                    points = 2;
                }

                if(String.valueOf(face[facenum]).matches("three" ))
                {
                    points = 3;
                }

                if(String.valueOf(face[facenum]).matches("four" ))
                {
                    points = 4;
                }

                if(String.valueOf(face[facenum]).matches("five" ))
                {
                    points = 5;
                }

                if(String.valueOf(face[facenum]).matches("six" ))
                {
                    points = 6;
                }

                if(String.valueOf(face[facenum]).matches("seven" ))
                {
                    points = 7;
                }

                if(String.valueOf(face[facenum]).matches("eight" ))
                {
                    points = 8;
                }

                if(String.valueOf(face[facenum]).matches("nine" ))
                {
                    points = 9;
                }

                if(String.valueOf(face[facenum]).matches("ten|jack|queen|king"))
                {
                    points = 10;
                }
                if(String.valueOf(face[facenum]).equals("ace"))
                {
                    points = 11;
                    Card.setAcePoints(1);
                }
                Card.setPoints(points);

                deck.add(Card);
            }
        }

        Collections.shuffle(deck);

        i = 0;
        playerTotal = 0;
        dealerTotal = 0;

        //DEALS 2 CARDS TO BOTH PLAYER AND DEALER
        Card = deck.get(i);
        playerHand.add(Card);
        playerTotal += Card.getPoints();
        context = j_bg_pcard1.getContext();
        id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
        j_bg_pcard1.setImageResource(id);
        i++;

        Card = deck.get(i);
        dealerHand.add(Card);
        dealerTotal += Card.getPoints();
        context = j_bg_dcard1.getContext();
        id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
        j_bg_dcard1.setImageResource(id);
        i++;

        Card = deck.get(i);
        playerHand.add(Card);
        playerTotal += Card.getPoints();
        context = j_bg_pcard2.getContext();
        id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
        j_bg_pcard2.setImageResource(id);
        i++;

        Card = deck.get(i);
        dealerHand.add(Card);

        //IF DEALER GOT BLACKJACK
        if(Integer.valueOf(dealerTotal + Card.getPoints()) == 21)
        {
            dealerTotal += Card.getPoints();
            context = j_bg_dcard2.getContext();
            id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
            j_bg_dcard2.setImageResource(id);
            j_bg_dealerTotalTxtView.setText(String.valueOf(dealerTotal));

            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("DEALER GOT BLACKJACK: YOU LOSE")
                    .setMessage("PLAYER TOTAL: " + playerTotal + "\nDEALER TOTAL: " + dealerTotal + "\nPLAY AGAIN?")
                    .setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int i)
                        {
                            Intent activity_playAgain = new Intent(BlackjackGame.this, BettingScreen.class);
                            activity_playAgain.putExtra("currentPlayer", currentPlayer);
                            startActivity(activity_playAgain);
                        }
                    })
                    .setNegativeButton("QUIT", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            if (Double.valueOf(temp.getAdminCheck()) != 1.0)
                            {
                                Intent activity_quit = new Intent(BlackjackGame.this, NonAdminMainScreen.class);
                                activity_quit.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_quit);
                            }
                            if (Double.valueOf(temp.getAdminCheck()) == 1.0)
                            {
                                Intent activity_quit = new Intent(BlackjackGame.this, AdminScreen.class);
                                activity_quit.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_quit);
                            }
                        }
                    });
            dialog.setCancelable(false);
            setFinishOnTouchOutside(false);
            dialog.show();

        }
        else
        {
            j_bg_dcard2.setImageResource(R.drawable.backcardblue);
        }
        i++;

        j_bg_playerTotalTxtView.setText(String.valueOf(playerTotal));
        j_bg_dealerTotalTxtView.setText(String.valueOf(dealerTotal));

        check(currentPlayer);

        //WHEN A PLAYER HITS
        j_bg_hitBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Card = deck.get(i);
                playerHand.add(Card);
                playerTotal += Card.getPoints();
                j_bg_playerTotalTxtView.setText(String.valueOf(playerTotal));

                //CHECKS FOR EMPTY CARD IMAGEVIEWS
                if(j_bg_pcard3.getDrawable() == null)
                {
                    context = j_bg_pcard3.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    j_bg_pcard3.setImageResource(id);
                    i++;
                }

                else if(j_bg_pcard4.getDrawable() == null)
                {
                    context = j_bg_pcard4.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    j_bg_pcard4.setImageResource(id);
                    i++;
                }

                else if(j_bg_pcard5.getDrawable() == null)
                {
                    context = j_bg_pcard5.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    j_bg_pcard5.setImageResource(id);
                    i++;
                }

                else if(j_bg_pcard6.getDrawable() == null)
                {
                    context = j_bg_pcard6.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    j_bg_pcard6.setImageResource(id);
                    i++;
                }

                else if(j_bg_pcard7.getDrawable() == null)
                {
                    context = j_bg_pcard7.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    j_bg_pcard7.setImageResource(id);
                    i++;
                }

                else if (j_bg_pcard8.getDrawable() == null)
                {
                    context = j_bg_pcard8.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    j_bg_pcard8.setImageResource(id);
                    i++;
                }

               check(currentPlayer);
            }
        });

        //WHEN A PLAYER STANDS
        j_bg_standBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Card = dealerHand.get(1);
                dealerTotal += Card.getPoints();
                j_bg_dealerTotalTxtView.setText(String.valueOf(dealerTotal));
                context = j_bg_dcard2.getContext();
                id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                j_bg_dcard2.setImageResource(id);

                while (checkDealer(currentPlayer))
                {
                    Card = deck.get(i);
                    dealerHand.add(Card);
                    dealerTotal += Card.getPoints();
                    i++;
                }

                if(3 <= dealerHand.size())
                {
                    context = j_bg_dcard3.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(2).getFileName(), "drawable", context.getPackageName());
                    j_bg_dcard3.setImageResource(id);
                    j_bg_dealerTotalTxtView.setText(String.valueOf(dealerTotal));
                }

                if(4 <= dealerHand.size())
                {
                    context = j_bg_dcard4.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(3).getFileName(), "drawable", context.getPackageName());
                    j_bg_dcard4.setImageResource(id);
                    j_bg_dealerTotalTxtView.setText(String.valueOf(dealerTotal));
                }

                if(5 <= dealerHand.size())
                {
                    context = j_bg_dcard5.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(4).getFileName(), "drawable", context.getPackageName());
                    j_bg_dcard5.setImageResource(id);
                    j_bg_dealerTotalTxtView.setText(String.valueOf(dealerTotal));
                }

                if(6 <= dealerHand.size())
                {
                    context = j_bg_dcard6.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(5).getFileName(), "drawable", context.getPackageName());
                    j_bg_dcard6.setImageResource(id);
                    j_bg_dealerTotalTxtView.setText(String.valueOf(dealerTotal));
                }

                if(7 <= dealerHand.size())
                {
                    context = j_bg_dcard7.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(6).getFileName(), "drawable", context.getPackageName());
                    j_bg_dcard7.setImageResource(id);
                    j_bg_dealerTotalTxtView.setText(String.valueOf(dealerTotal));
                }

                if(8 <= dealerHand.size())
                {
                    context = j_bg_dcard8.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(7).getFileName(), "drawable", context.getPackageName());
                    j_bg_dcard8.setImageResource(id);
                    j_bg_dealerTotalTxtView.setText(String.valueOf(dealerTotal));
                }

                checkDealer(currentPlayer);
            }
        });

        db_helper.close();
    }

    //*========================================FUNCTIONS==============================================*
    //CHECKS DEALER'S HAND
    boolean checkDealer(final String currentPlayer)
    {
        //DEALER HAS BUSTED
        if (dealerTotal > 21)
        {
            //CHECKS FOR POINT VALUE OF ACES
            for (int s = 0; s < dealerHand.size(); s++)
            {
                Cards tempCard = dealerHand.get(s);
                if (tempCard.getFace().equals("ace"))
                {
                    tempCard.setPoints(tempCard.getAcePoints());
                    dealerTotal = 0;
                    for (int w = 0; w < dealerHand.size(); w++)
                    {
                        dealerTotal += dealerHand.get(w).getPoints();
                    }

                    j_bg_dealerTotalTxtView.setText(String.valueOf(dealerTotal));
                    return true;
                }
            }

            //HOW MUCH THE PLAYER WINS
            int winnings = (int) (bet * 2);
            int math = Integer.valueOf(temp.getCash()) + winnings;
            temp.setCash(String.valueOf(math));
            db_helper.db_appendPlayer(temp);

            //DIALOG BOX
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("DEALER BUSTED: YOU WIN!")
                    .setMessage("PLAYER TOTAL: " + playerTotal + "\nDEALER TOTAL: " +
                            dealerTotal + "\nWINNINGS = $" + winnings + "\nPLAY AGAIN?")
                    .setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int i)
                        {
                            Intent activity_playAgain = new Intent(BlackjackGame.this, BettingScreen.class);
                            activity_playAgain.putExtra("currentPlayer", currentPlayer);
                            startActivity(activity_playAgain);
                        }
                    })
                    .setNegativeButton("QUIT", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            if (Double.valueOf(temp.getAdminCheck()) != 1.0)
                            {
                                Intent activity_quit = new Intent(BlackjackGame.this, NonAdminMainScreen.class);
                                activity_quit.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_quit);
                            }
                            if (Double.valueOf(temp.getAdminCheck()) == 1.0)
                            {
                                Intent activity_quit = new Intent(BlackjackGame.this, AdminScreen.class);
                                activity_quit.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_quit);
                            }
                        }
                    });
            dialog.setCancelable(false);
            setFinishOnTouchOutside(false);
            dialog.show();
        }

        //CHECKS FOR PUSH
        else if (dealerTotal == 21 && playerTotal == 21)
        {
            //GIVES PLAYER THEIR BET BACK
            int math = Integer.valueOf(temp.getCash()) + bet;
            temp.setCash(String.valueOf(math));
            db_helper.db_appendPlayer(temp);

            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("PUSH: TIE")
                    .setMessage("PLAYER TOTAL: " + playerTotal + "\nDEALER TOTAL: " + dealerTotal + "\nPLAY AGAIN?")
                    .setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int i)
                        {
                            Intent activity_playAgain = new Intent(BlackjackGame.this, BettingScreen.class);
                            activity_playAgain.putExtra("currentPlayer", currentPlayer);
                            startActivity(activity_playAgain);
                        }
                    })
                    .setNegativeButton("QUIT", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            if (Double.valueOf(temp.getAdminCheck()) != 1.0)
                            {
                                Intent activity_quit = new Intent(BlackjackGame.this, NonAdminMainScreen.class);
                                activity_quit.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_quit);
                            }
                            if (Double.valueOf(temp.getAdminCheck()) == 1.0)
                            {
                                Intent activity_quit = new Intent(BlackjackGame.this, AdminScreen.class);
                                activity_quit.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_quit);
                            }
                        }
                    });
            dialog.setCancelable(false);
            setFinishOnTouchOutside(false);
            dialog.show();
        }

        //IF DEALER WILL STOP HITTING
        else if (dealerTotal > 17)
        {
            //COMPARES TOTALS TO SEE WHO WON
                //PLAYER WINS
            if (playerTotal > dealerTotal)
            {
                //PLAYER WINS TWICE THEIR BET
                int winnings = (int) (bet * 2);
                int math = Integer.valueOf(temp.getCash()) + winnings;
                temp.setCash(String.valueOf(math));
                db_helper.db_appendPlayer(temp);

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("YOU WIN!")
                        .setMessage("PLAYER TOTAL: " + playerTotal + "\nDEALER TOTAL: " + dealerTotal + "\nWINNINGS = $" + winnings + "\nPLAY AGAIN?")
                        .setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int i)
                            {
                                Intent activity_playAgain = new Intent(BlackjackGame.this, BettingScreen.class);
                                activity_playAgain.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_playAgain);
                            }
                        })
                        .setNegativeButton("QUIT", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if (Double.valueOf(temp.getAdminCheck()) != 1.0)
                                {
                                    Intent activity_quit = new Intent(BlackjackGame.this, NonAdminMainScreen.class);
                                    activity_quit.putExtra("currentPlayer", currentPlayer);
                                    startActivity(activity_quit);
                                }
                                if (Double.valueOf(temp.getAdminCheck()) == 1.0)
                                {
                                    Intent activity_quit = new Intent(BlackjackGame.this, AdminScreen.class);
                                    activity_quit.putExtra("currentPlayer", currentPlayer);
                                    startActivity(activity_quit);
                                }
                            }
                        });
                dialog.setCancelable(false);
                setFinishOnTouchOutside(false);
                dialog.show();
            }

            //PLAYER LOSES
            else if (dealerTotal > playerTotal)
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("YOU LOSE")
                        .setMessage("PLAYER TOTAL: " + playerTotal + "\nDEALER TOTAL: " + dealerTotal + "\nPLAY AGAIN?")
                        .setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int i)
                            {
                                Intent activity_playAgain = new Intent(BlackjackGame.this, BettingScreen.class);
                                activity_playAgain.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_playAgain);
                            }
                        })
                        .setNegativeButton("QUIT", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if (Double.valueOf(temp.getAdminCheck()) != 1.0)
                                {
                                    Intent activity_quit = new Intent(BlackjackGame.this, NonAdminMainScreen.class);
                                    activity_quit.putExtra("currentPlayer", currentPlayer);
                                    startActivity(activity_quit);
                                }
                                if (Double.valueOf(temp.getAdminCheck()) == 1.0)
                                {
                                    Intent activity_quit = new Intent(BlackjackGame.this, AdminScreen.class);
                                    activity_quit.putExtra("currentPlayer", currentPlayer);
                                    startActivity(activity_quit);
                                }
                            }
                        });
                dialog.setCancelable(false);
                setFinishOnTouchOutside(false);
                dialog.show();
            }
        }

        //IF DEALER NEEDS TO KEEP HITTING
        else if (dealerTotal < 17)
        {
            return true;
        }

        return false;
    }

    //CHECKS PLAYER HAND AFTER EACH HIT
    void check(final String currentPlayer)
    {
        //IF DEALER BUSTS
        if (playerTotal > 21)
        {
            //CHECK FOR ACES AND CHANGES VALUE IF SO
            for (int s = 0; s < playerHand.size(); s++)
            {
                Cards tempCard = playerHand.get(s);
                if (tempCard.getFace().equals("ace"))
                {
                    tempCard.setPoints(tempCard.getAcePoints());
                    playerTotal = 0;
                    for (int w = 0; w < playerHand.size(); w++)
                    {
                         playerTotal += playerHand.get(w).getPoints();
                    }

                    j_bg_playerTotalTxtView.setText(String.valueOf(playerTotal));

                    if (s == (playerHand.size() - 1))
                    {
                        return;
                    }
                }
            }

            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("BUST: YOU LOSE")
                    .setMessage("PLAYER TOTAL: " + playerTotal + "\nPLAY AGAIN?")
                    .setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int i)
                        {
                            Intent activity_playAgain = new Intent(BlackjackGame.this, BettingScreen.class);
                            activity_playAgain.putExtra("currentPlayer", currentPlayer);
                            startActivity(activity_playAgain);
                        }
                    })
                    .setNegativeButton("QUIT", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            if (Double.valueOf(temp.getAdminCheck()) != 1.0)
                            {
                                Intent activity_quit = new Intent(BlackjackGame.this, NonAdminMainScreen.class);
                                activity_quit.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_quit);
                            }
                            if (Double.valueOf(temp.getAdminCheck()) == 1.0)
                            {
                                Intent activity_quit = new Intent(BlackjackGame.this, AdminScreen.class);
                                activity_quit.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_quit);
                            }
                        }
                    });
            dialog.setCancelable(false);
            setFinishOnTouchOutside(false);
            dialog.show();
        }

        //IF PLAYER GETS TO 21
        if (playerTotal == 21)
        {
            //WIN 2.5 TIMES THEIR BET
            int winnings = (int) (bet * 2.5);
            int math = Integer.valueOf(temp.getCash()) + winnings;
            temp.setCash(String.valueOf(math));
            db_helper.db_appendPlayer(temp);

            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("YOU WIN!")
                    .setMessage("PLAYER TOTAL: " + playerTotal + "\nDEALER TOTAL: " + dealerTotal + "\nWINNINGS = $" + winnings + "\nPLAY AGAIN?")
                    .setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int i)
                        {
                            Intent activity_playAgain = new Intent(BlackjackGame.this, BettingScreen.class);
                            activity_playAgain.putExtra("currentPlayer", currentPlayer);
                            startActivity(activity_playAgain);
                        }
                    })
                    .setNegativeButton("QUIT", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            if (Double.valueOf(temp.getAdminCheck()) != 1.0)
                            {
                                Intent activity_quit = new Intent(BlackjackGame.this, NonAdminMainScreen.class);
                                activity_quit.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_quit);
                            }
                            if (Double.valueOf(temp.getAdminCheck()) == 1.0)
                            {
                                Intent activity_quit = new Intent(BlackjackGame.this, AdminScreen.class);
                                activity_quit.putExtra("currentPlayer", currentPlayer);
                                startActivity(activity_quit);
                            }
                        }
                    });
            dialog.setCancelable(false);
            setFinishOnTouchOutside(false);
            dialog.show();
        }
    }
}
