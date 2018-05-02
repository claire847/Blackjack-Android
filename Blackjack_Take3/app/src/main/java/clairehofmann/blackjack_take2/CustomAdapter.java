// Blackjack: CustomerAdapter.java

//CLAIRE

package clairehofmann.blackjack_take2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Claire'sPC on 11/7/2017.
 */

public class CustomAdapter extends BaseAdapter
{
    //CLAIRE
    private ArrayList<Player> db_listArray;
    private Context context;
    TextView j_nameTxtView;
    TextView j_coinTxtView;

    public CustomAdapter (Context context, ArrayList<Player> db_listArray)
    {
        this.context = context;
        this.db_listArray = db_listArray;
    }

    @Override
    public int getCount()
    {
        return db_listArray.size();
    }

    @Override
    public Object getItem(int i)
    {
        return db_listArray.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if(view == null)
        {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.layout, null);
        }

        j_coinTxtView = (TextView) view.findViewById(R.id.v_coinTxtView);
        j_coinTxtView.setText(String.valueOf(db_listArray.get(i).getCash()) + " coins");

        j_nameTxtView = (TextView) view.findViewById(R.id.v_nameTxtView);
        j_nameTxtView.setText(String.valueOf(db_listArray.get(i).getEmail()));

        return view;
    }
}
