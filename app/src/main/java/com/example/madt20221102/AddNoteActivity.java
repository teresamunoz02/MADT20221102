package com.example.madt20221102;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {

    EditText edNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.edNote = findViewById(R.id.edNote);

    }


    public void onBtnSaveAndCloseClick(View view) {
        String t = this.edNote.getText().toString();
        System.out.println(t);
        if (t.isEmpty()){
            Context context = getApplicationContext();
            CharSequence text = "Warning: Empty Note!";
            int duration = Toast.LENGTH_LONG;

            Toast.makeText(getApplicationContext(), R.string.msg_updated_clicked, Toast.LENGTH_LONG).show();

        }else {


            String noteToAdd = this.edNote.getText().toString();
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);

            SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);
            Set<String> newSet = new HashSet<>();
            if (savedSet != null) {
                newSet.addAll(savedSet);
            }
            newSet.add(noteToAdd);

            editor.putString(Constants.NOTE_KEY, noteToAdd);
            editor.putString(Constants.NOTE_KEY_DATE, formattedDate);
            editor.putStringSet(Constants.NOTES_ARRAY_KEY, newSet);
            editor.apply();


            finish();
        }
    }
}
