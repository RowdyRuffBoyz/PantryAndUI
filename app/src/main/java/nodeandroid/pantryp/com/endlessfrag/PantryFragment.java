package nodeandroid.pantryp.com.endlessfrag;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Vector;


public class PantryFragment extends DialogFragment {

    private static Context context;
    private static FileOutputStream outputStream;
    private static FileInputStream inputStream;
    private static Vector<String> pantryItems;
    private static PantryAdapter pantry;
    private static AlertDialog.Builder alertDialogBuilder;
    private static int index = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_pantry, container, false);
        pantryItems = new Vector<String>();

        // Commented out 6:20 PM 4/10
        //final Context context;
        context = getActivity().getApplicationContext();

        final EditText ingredient = (EditText) v.findViewById(R.id.ingredient);

        // items for printing and adding to text file

        // Pantry list
        ArrayList<String> arrayOfIngredients = new ArrayList<String>();
        pantry = new PantryAdapter(v.getContext(), pantryItems);

        final ListView pantryView = (ListView) v.findViewById(R.id.pantryListView);
        pantryView.setAdapter(pantry);
        pantryView.setVisibility(View.GONE);


        final File pantryFile = new File(context.getFilesDir(), "pantrylist.txt");

        // If file exists take whats in the file and fill the vector


            // Grabs list from text file
            /* Need to read each line from file and store in
             * Vector.
             */

        if (pantryFile.exists()) {
            try {
                inputStream = getActivity().openFileInput("pantrylist.txt");
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);

                StringBuilder stringBuilder = new StringBuilder();
                String item;

                while ((item = br.readLine()) != null) {
                    pantryItems.add(item);
                }
                inputStream.close();

                    //ingredient.setText(pantryItems.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
           // ingredient.setText("File Does Not Exist");
        }


        /* GET PREVIOUS SIZE OF VECTOR THEN NEWSIZE FROM ADDING ITEMS
         * INCREMENT FROM THAT POSITION AND USE pantryItems.get(i)
         */




        //debugging
        //ingredient.setText(pantryItems.get(0));

        Button exit = (Button)v.findViewById(R.id.cancelbtn);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    outputStream = getActivity().openFileOutput("pantrylist.txt", Context.MODE_PRIVATE);
                    for (int i = 0; i < pantryItems.size(); i++) {
                        outputStream.write((pantryItems.get(i)+"\n").getBytes());
                        //outputStream.write("\n".getBytes());
                    }
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //pantryFile.delete();
                dismiss();
            }
        });

        final Button viewPantry = (Button)v.findViewById(R.id.view);
        viewPantry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 // If statement for dismiss pantry/view settext
                 if (viewPantry.getText().equals("View Pantry")) {
                     pantryView.setVisibility(View.VISIBLE);
                     viewPantry.setText("Dismiss");
                 }
                 else {
                     pantryView.setVisibility(View.GONE);
                     viewPantry.setText("View Pantry");
                 }

            }
        });

        // Adds ingredient to vector not text file
        Button Add = (Button)v.findViewById(R.id.add);
        Add.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                String txt = ingredient.getText().toString();
                ingredient.setText("");

                pantryItems.add(txt);
                pantryView.setAdapter(pantry);
            }
        });







        return v;
    }


    public class PantryAdapter extends ArrayAdapter<String> {

        public PantryAdapter(Context context, Vector<String> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String item = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.pantry_list_item, parent, false);
            }


            final EditText editName = (EditText) convertView.findViewById(R.id.editName);
            final Button rename = (Button) convertView.findViewById(R.id.rename);
            final Button remove = (Button) convertView.findViewById(R.id.remove);


            final TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            itemName.setText(pantryItems.get(position));

            rename.setOnClickListener(new View.OnClickListener(){

                public void onClick(View v) {
                   if (rename.getText().equals("Rename")) {
                       itemName.setVisibility(View.GONE);
                       editName.setVisibility(View.VISIBLE);
                       rename.setText("Done");
                       remove.setText("Cancel");
                       editName.setSelection(0);

                   }
                   else {
                       // SET pantryItems[i] = editText
                       String oldItem = itemName.getText().toString();
                       String newItem = editName.getText().toString();
                       index = pantryItems.indexOf(oldItem);
                       pantryItems.set(index, newItem);

                       itemName.setVisibility(View.VISIBLE);
                       editName.setVisibility(View.GONE);
                       rename.setText("Rename");
                       pantry.notifyDataSetChanged();

                   }

                }
            });

            // Add search and remove duplicates buttons?

            remove.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if (remove.getText().equals("Cancel")){
                        rename.setText("Rename");
                        remove.setText("Remove");
                        itemName.setVisibility(View.VISIBLE);
                        editName.setText("");
                        editName.setVisibility(View.GONE);

                    }
                    else {

                        String oldItem = itemName.getText().toString();
                        index = pantryItems.indexOf(oldItem);

                       context = getContext();
                       // context = getActivity().getApplicationContext();
                        alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Pantry Item Removal");

                        alertDialogBuilder
                                .setMessage("Do you want to remove this item?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        pantryItems.remove(index);
                                        pantry.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }

                }
            });
            return convertView;
        }
    }

}
