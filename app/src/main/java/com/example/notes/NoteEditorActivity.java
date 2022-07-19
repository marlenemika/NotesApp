package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteID;
    Gson gson = new Gson();

    // balken oben: support action bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID", 0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(MainActivity.notes.get(noteID).title);
        setContentView(R.layout.activity_note_editor);
        setCardColour();

        EditText editText = (EditText) findViewById(R.id.editText);

        editText.setText(MainActivity.notes.get(noteID).content);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.get(noteID).content = s.toString();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.tanay.thunderbird.deathnote", Context.MODE_PRIVATE);
                ArrayList<String> tmp = new ArrayList<>();
                for (Note note : MainActivity.notes) {
                    tmp.add(gson.toJson(note));
                }
                HashSet<String> set = new HashSet<String>(tmp);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setCardColour() {
        CardView cardView = findViewById(R.id.cardView);
        switch(MainActivity.notes.get(noteID).colour) {
            case BLUE:
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_blue));
                break;
            case GREEN:
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_green));
                break;
            case ORANGE:
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_orange));
                break;
            case PINK:
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_pink));
                break;
            case GREY:
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_grey));
                break;
            case PURPLE:
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_purple));
                break;
            case YELLOW:
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_yellow));
                break;
        }
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.tanay.thunderbird.deathnote", Context.MODE_PRIVATE);
        ArrayList<String> tmp = new ArrayList<>();
        for (Note note : MainActivity.notes) {
            tmp.add(gson.toJson(note));
        }
        HashSet<String> set = new HashSet<>(tmp);
        sharedPreferences.edit().putStringSet("notes", set).apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.note_settings:
                openBottomSheet();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openBottomSheet() {
        // inflate bottomsheet layout
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottomsheet_layout, null);


        TextView noteTitle = bottomSheetView.findViewById(R.id.textView);
        noteTitle.setText(MainActivity.notes.get(noteID).title);

        Button editTitle = bottomSheetView.findViewById(R.id.btn_edit_title);
        Button deleteNote =  bottomSheetView.findViewById(R.id.delete_note);
        Button shareWFriend = bottomSheetView.findViewById(R.id.share_friends);

        ImageView clr_blue = bottomSheetView.findViewById(R.id.blue);
        ImageView clr_green = bottomSheetView.findViewById(R.id.green);
        ImageView clr_orange = bottomSheetView.findViewById(R.id.orange);
        ImageView clr_pink = bottomSheetView.findViewById(R.id.pink);
        ImageView clr_grey = bottomSheetView.findViewById(R.id.grey);
        ImageView clr_purple = bottomSheetView.findViewById(R.id.purple);
        ImageView clr_yellow = bottomSheetView.findViewById(R.id.yellow);

        clr_blue.setOnClickListener(v -> {
            MainActivity.notes.get(noteID).colour = Colour.BLUE;
            setCardColour();
        });

        clr_green.setOnClickListener(v -> {
            MainActivity.notes.get(noteID).colour = Colour.GREEN;
            setCardColour();
        });

        clr_orange.setOnClickListener(v -> {
            MainActivity.notes.get(noteID).colour = Colour.ORANGE;
            setCardColour();
        });

        clr_pink.setOnClickListener(v -> {
            MainActivity.notes.get(noteID).colour = Colour.PINK;
            setCardColour();
        });

        clr_grey.setOnClickListener(v -> {
            MainActivity.notes.get(noteID).colour = Colour.GREY;
            setCardColour();
        });

        clr_purple.setOnClickListener(v -> {
            MainActivity.notes.get(noteID).colour = Colour.PURPLE;
            setCardColour();
        });

        clr_yellow.setOnClickListener(v -> {
            MainActivity.notes.get(noteID).colour = Colour.YELLOW;
            setCardColour();
        });

        editTitle.setOnClickListener(v -> {
            View dialogView = getLayoutInflater().inflate(R.layout.enter_title_layout, null);
            new MaterialAlertDialogBuilder(NoteEditorActivity.this)
                    .setTitle("Title of note")
                    .setView(dialogView)
                    .setPositiveButton("Accept", (dialog, which) -> {
                        EditText input = dialogView.findViewById(R.id.noteTitle);
                        MainActivity.notes.get(noteID).title = input.getText().toString();
                        getSupportActionBar().setTitle(input.getText().toString());
                        noteTitle.setText(input.getText().toString());
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.tanay.thunderbird.deathnote", Context.MODE_PRIVATE);
                        ArrayList<String> tmp = new ArrayList<>();
                        for (Note note : MainActivity.notes) {
                            tmp.add(gson.toJson(note));
                        }
                        HashSet<String> set = new HashSet<>(tmp);
                        sharedPreferences.edit().putStringSet("notes", set).apply();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {

                    })
                    .show();
        });

        // to remove the selected note once "Yes" is pressed
        deleteNote.setOnClickListener(v -> new MaterialAlertDialogBuilder(NoteEditorActivity.this)                   // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete?")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    MainActivity.notes.remove(noteID);
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.tanay.thunderbird.deathnote", Context.MODE_PRIVATE);
                    ArrayList<String> tmp = new ArrayList<>();
                    for (Note note : MainActivity.notes) {
                        tmp.add(gson.toJson(note));
                    }
                    HashSet<String> set = new HashSet<>(tmp);
                    sharedPreferences.edit().putStringSet("notes", set).apply();
                    NoteEditorActivity.this.finish();
                })
                .setNegativeButton("No", null)
                .show());

        shareWFriend.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, MainActivity.notes.get(noteID).content);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        // open bottom sheet
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(bottomSheetView);
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }
}