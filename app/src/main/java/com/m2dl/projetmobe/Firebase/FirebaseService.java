package com.m2dl.projetmobe.Firebase;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.m2dl.projetmobe.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;

public class FirebaseService {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String SCORES_KEY = "scores";
    private static final String SCORES_ID_KEY = "scoresId";


    public static void publishScore(String pseudo, String score){
        Log.i("FirebaseService","publish score");
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
        DatabaseReference picturesReference = database.getReference(SCORES_KEY);
        picturesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<HashMap<String, String>> value = (ArrayList<HashMap<String, String>>) dataSnapshot.getValue();
                MainActivity.callbackImagesFirebase(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("on cancelled", databaseError.getMessage());
            }
        });
    }
}
