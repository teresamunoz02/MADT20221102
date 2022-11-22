package com.example.madt20221102;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DeleteNoteActivity extends AppCompatActivity {

    ArrayList<String> listNoteItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);
        this.listView = findViewById(R.id.lvNotes);
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.listNoteItems);
        this.listView.setAdapter(adapter);

    }
        @Override
        protected void onStart() {
            super.onStart();

            SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);
            Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);

            if(savedSet != null) {
                this.listNoteItems.clear();
                this.listNoteItems.addAll(savedSet);
                this.adapter.notifyDataSetChanged();


        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemToDelete = i;

                new AlertDialog.Builder(DeleteNoteActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Deleting this note")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listNoteItems.remove(itemToDelete);
                                adapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.NOTES_FILE, Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(listNoteItems);
                                sharedPreferences.edit().putStringSet("notes", null).apply();
                            }
                        }).setNegativeButton("NO", null).show();
                return true;
            }
        });

    }
}
