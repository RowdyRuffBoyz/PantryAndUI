package nodeandroid.pantryp.com.endlessfrag;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class MainActivity extends ActionBarActivity{

    String[] values = {" ", "Pantry", "Favorites", "Search", "Menu Planner", "Community"};

    //String[] Subjects = {"C1", "C2", "C3", "C4", "C5", "C6"};

    int[] images = { R.mipmap.message,
            R.mipmap.pantry,
            R.mipmap.favs,
            R.mipmap.search,
            R.mipmap.calendar,
            R.mipmap.share
    };

    //PantryPlusMessage dlg0;
    PantryFragment dlg1;
    FavoritesFragment dlg2;
    SearchFragment dlg3;
    CalendarFragment dlg4;
    CommunityFragment dlg5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spn = (Spinner)findViewById(R.id.PantryPlus);
        spn.setAdapter(new CustomAdapter(this, R.layout.spinner, values));

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (position == 0){



               }
               if (position == 1){

                   dlg1 = new PantryFragment();
                   dlg1.show(getFragmentManager(), "Pantry");
                   //favoritesFrag favorites = new favoritesFrag();
                   //fragTransaction.replace(android.R.id.content, favorites);
                   //fragTransaction.commit();
               }
               if (position == 2){

                   dlg2 = new FavoritesFragment();
                   dlg2.show(getFragmentManager(), "Favorites");
                   //searchFrag search = new searchFrag();
                   //fragTransaction.replace(android.R.id.content, search);
                   //fragTransaction.commit();
               }
               if (position == 3){
                   dlg3 = new SearchFragment();
                   dlg3.show(getFragmentManager(), "Search");
               }
               if (position == 4){

               }
                if (position == 5){

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    public class CustomAdapter extends ArrayAdapter<String>{
        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        @Override
        public View getDropDownView(int posi, View cnvtView, ViewGroup prnt) {

            return getCustomView(posi, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();

            View mySpinner = inflater.inflate(R.layout.spinner, parent, false);

            TextView txt = (TextView)mySpinner.findViewById(R.id.textView);

            txt.setText(values[position]);

            //TextView subSpn = (TextView)mySpinner.findViewById(R.id.textView2);

            //subSpn.setText(Subjects[position]);

            ImageView img = (ImageView)mySpinner.findViewById(R.id.image);

            img.setImageResource(images[position]);

            return mySpinner;

        }

        public CustomAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
