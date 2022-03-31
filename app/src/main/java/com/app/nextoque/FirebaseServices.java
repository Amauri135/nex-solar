package com.app.nextoque;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseServices {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    Context context;

    public FirebaseServices() {
        firebaseDatabase  =  FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

}
