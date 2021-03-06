package com.example.projectbeacon.Database.Firebase.Rule;

import android.util.Log;

import com.example.projectbeacon.Model.Rule.CanSleepRule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;

public class CanSleepRuleFB {
    private String TAG = "TEST";
    private FirebaseFirestore db;

    private void initFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    public CanSleepRuleFB(){}

    private void uploadCanSleepRule(){
        initFirestore();
        String str1[] = {"bedroom","T1","T2","T3","T8","T9"};
        String str2[] = {"bedroom","T4","T5","T6","T7"};
        String str3[] = {"toilet",""};
        String str4[] = {"kitchen",""};
        String str5[] = {"living room","T9","T1","T2","T4","T6","T7","T8"};
        String str6[] = {"living room","T3","T5"};
        String str7[] = {"working room",""};

        Log.d(TAG,"in function");
        CanSleepRule csr = new CanSleepRule();
        int cnt = 1;
//        CanSleepRule csr = new CanSleepRule("csr"+cnt, str1[0], str1[1], true);
//        db.collection("canSleepRule").document().set(csr);


        for(int i=1; i<str1.length; i++){
            csr.setCs_rule("csr"+cnt);
            csr.setTime_id(str1[i]);
            csr.setRoom_type(str1[0]);
            csr.setCanSleep(true);
            Log.d(TAG,"in loop 1 for "+cnt);
            db.collection("canSleepRules").document(csr.getCs_rule()).set(csr);
            cnt++;
        }
        for(int i=1; i<str2.length; i++){
            csr.setCs_rule("csr"+cnt);
            csr.setTime_id(str2[i]);
            csr.setRoom_type(str2[0]);
            csr.setCanSleep(false);
            Log.d(TAG,"in loop 2 for "+cnt);
            db.collection("canSleepRules").document(csr.getCs_rule()).set(csr);
            cnt++;
        }
        for(int i=1; i<str3.length; i++){
            csr.setCs_rule("csr"+cnt);
            csr.setTime_id(str3[i]);
            csr.setRoom_type(str3[0]);
            csr.setCanSleep(false);
            Log.d(TAG,"in loop 3 for "+cnt);
            db.collection("canSleepRules").document(csr.getCs_rule()).set(csr);
            cnt++;
        }
        for(int i=1; i<str4.length; i++){
            csr.setCs_rule("csr"+cnt);
            csr.setTime_id(str4[i]);
            csr.setRoom_type(str4[0]);
            csr.setCanSleep(false);
            Log.d(TAG,"in loop 4 for "+cnt);
            db.collection("canSleepRules").document(csr.getCs_rule()).set(csr);
            cnt++;
        }
        for(int i=1; i<str5.length; i++){
            csr.setCs_rule("csr"+cnt);
            csr.setTime_id(str5[i]);
            csr.setRoom_type(str5[0]);
            csr.setCanSleep(false);
            Log.d(TAG,"in loop 5 for "+cnt);
            db.collection("canSleepRules").document(csr.getCs_rule()).set(csr);
            cnt++;
        }
        for(int i=1; i<str6.length; i++){
            csr.setCs_rule("csr"+cnt);
            csr.setTime_id(str6[i]);
            csr.setRoom_type(str6[0]);
            csr.setCanSleep(true);
            Log.d(TAG,"in loop 6 for "+cnt);
            db.collection("canSleepRules").document(csr.getCs_rule()).set(csr);
            cnt++;
        }
        for(int i=1; i<str7.length; i++){
            csr.setCs_rule("csr"+cnt);
            csr.setTime_id(str7[i]);
            csr.setRoom_type(str7[0]);
            csr.setCanSleep(false);
            Log.d(TAG,"in loop 7 for "+cnt);
            db.collection("canSleepRules").document(csr.getCs_rule()).set(csr);
            cnt++;
        }
    }

    public boolean getCanSleep(final String roomType, String timeID){
//        room_type;
//        time_id;
        final boolean[] canSleep = {false};
        db.collection("canSleepRules")
                .whereEqualTo("time_id", timeID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                CanSleepRule c = document.toObject(CanSleepRule.class);
                                if(c.getRoom_type().equalsIgnoreCase(roomType)){
                                    canSleep[0] = c.isCanSleep() == 1? true:false;
                                    Log.d(TAG, c.getCs_rule());
                                }
                            }
                        }
                    }
                });
        return canSleep[0];
    }
}
