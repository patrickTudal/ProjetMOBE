package com.m2dl.projetmobe.Firebase;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;

public class FirebaseService {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String SCORES_KEY = "scores";
    private static final String SCORES_ID_KEY = "scoresId";


    public static void publishScore(String score, String pseudo){
        autoIncrementId( score, pseudo);
    }

    private static void autoIncrementId(final String score, final String pseudo) {
        final DatabaseReference incrementId = database.getReference(SCORES_ID_KEY);
        incrementId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long id = (long) dataSnapshot.getValue();
                incrementId.setValue(++id);
                sendHighScoreDAO(id, score, pseudo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("on cancelled", databaseError.getMessage());
            }
        });
    }

    private static void sendHighScoreDAO(long id, String score, String pseudo) {
        DatabaseReference pictureRef = database.getReference(SCORES_KEY + "/" + id);
        HighScoreDAO highScoreDAO = new HighScoreDAO(String.valueOf(id), score, pseudo);
        pictureRef.setValue(highScoreDAO);
    }

    public static void getAllScores(){
       /* DatabaseReference myRef = database.getReference("scores");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("FireBaseService", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FirebaseService", "Failed to read value.", error.toException());
            }
        });*/
        DatabaseReference picturesReference = database.getReference(SCORES_KEY);
        picturesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String,String>> value = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                //MapActivity.callbackImagesFirebase(value, context, gMap, tokenId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("on cancelled", databaseError.getMessage());
            }
        });
    }
}
