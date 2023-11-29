package com.xoxoharsh.codershub.util;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
public class FirebaseUtil {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static String currentUserEmail(){
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
    public static boolean isLoggedIn() {
        if(currentUserId()!=null)return true;
        else return false;
    }
    public static DocumentReference currentUserDetails() {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserEmail());
    }
    public static DocumentReference POTD() {
        return FirebaseFirestore.getInstance().collection("questions").document("POTD");
    }
    public static DocumentReference contests() {
        return FirebaseFirestore.getInstance().collection("contests").document("contests");
    }
}
