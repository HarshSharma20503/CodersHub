package com.xoxoharsh.codershub;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.xoxoharsh.codershub.model.Contest;
import com.xoxoharsh.codershub.util.FirebaseUtil;
import com.xoxoharsh.codershub.util.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MainActivity extends AppCompatActivity {
    CodeforcesProfile codeforcesProfile;
    LeetcodeProfile leetcodeProfile;
    GfgProfile gfgProfile;
    PotdFragment potdFragment;
    ContestsFragment contestsFragment;
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    ImageButton menuBtn;
    Map<String, Object> geeksforgeekMap,leetcodeMap,codeforcesMap,geeksforgeekPotdMap,leetcodePotdMap,codeforcesPotdMap;
    List<String> menuList;
    List<Contest> contestList;
    String platform;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("CodersHub_Errors","Entered MainActivity");
        frameLayout = findViewById(R.id.main_frame_layout);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        menuBtn = findViewById(R.id.menu_btn);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        codeforcesProfile = new CodeforcesProfile();
        leetcodeProfile = new LeetcodeProfile();
        gfgProfile = new GfgProfile();
        potdFragment = new PotdFragment();
        contestsFragment = new ContestsFragment();
        menuList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            Map<String, Object> userData = (HashMap<String, Object>) intent.getSerializableExtra("Data");
            if (userData != null) {
                for (Map.Entry<String, Object> entry : userData.entrySet()) {
                    String outerKey = entry.getKey();
                    Object outerValue = entry.getValue();
                    platform = outerKey;
                    menuList.add(outerKey);
                    if ("codeforces".equals(outerKey)) {
                        codeforcesMap = (Map<String, Object>) outerValue;
                    } else if ("leetcode".equals(outerKey)) {
                        leetcodeMap = (Map<String, Object>) outerValue;
                    } else if ("gfg".equals(outerKey)) {
                        geeksforgeekMap = (Map<String, Object>) outerValue;
                    }
                }
                Log.d("CodersHub_Errors","Recieved all the profiles data");
                Toast.makeText(this, "Data recieved", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.d("CodersHub_Errors","Data not recieved");
                Toast.makeText(this, "Data not recieved", Toast.LENGTH_SHORT).show();
            }
        }
        getPotdData();
        getContestData();
        menuBtn.setOnClickListener(v -> showMenu());
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.menu_profile) {
                Bundle bundle = new Bundle();
                Log.d("CodersHub_Errors","Entered bottomNaviagtionView");
                switch (platform) {
                    case "codeforces":
                        Log.d("CodersHub_Errors", "Entered bottomNaviagtionView Codeforces");
                        bundle.putSerializable("userData", (Serializable) codeforcesMap);
                        codeforcesProfile.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, codeforcesProfile).commit();
                        break;
                    case "leetcode":
                        Log.d("CodersHub_Errors", "Entered bottomNaviagtionView Leetcode");
                        bundle.putSerializable("userData", (Serializable) leetcodeMap);
                        leetcodeProfile.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, leetcodeProfile).commit();
                        break;
                    case "gfg":
                        Log.d("CodersHub_Errors", "Entered bottomNaviagtionView Geeksforgeek");
                        bundle.putSerializable("userData", (Serializable) geeksforgeekMap);
                        gfgProfile.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, gfgProfile).commit();
                        break;
                }
            }
            if(item.getItemId() == R.id.menu_potd) {
                Bundle bundle = new Bundle();
                Log.d("Harsh Error","Platform value is "+platform);
                switch (platform) {
                    case "leetcode":
                        bundle.putSerializable("userData", (Serializable) leetcodePotdMap);
                        break;
                    case "codeforces":
                        bundle.putSerializable("userData", (Serializable) codeforcesPotdMap);
                        break;
                    case "gfg":
                        Log.d("Harsh Error", "putting GeeksforgeekPotdMap");
                        bundle.putSerializable("userData", (Serializable) geeksforgeekPotdMap);
                        break;
                }
                bundle.putString("Platform",platform);
                potdFragment.setArguments(bundle);
                Log.d("Harsh Error","Sending data");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,potdFragment).commit();
            }
            if(item.getItemId() == R.id.menu_contest) {
                contestsFragment.setContestList(contestList);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,contestsFragment).commit();
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
        swipeRefreshLayout.setDistanceToTriggerSync(400);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            boolean isAtTop = frameLayout.getScrollY() == 0;
            if (isAtTop) {
                Log.d("CodersHub_Errors", "Entered OnRefresh Function");
                Utility.updateProfile();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(MainActivity.this, "Login Again to see the changes", Toast.LENGTH_LONG).show();
                startActivity(intent1);
                finish();
            }
            swipeRefreshLayout.setRefreshing(false);
        });
    }
    void showMenu() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, menuBtn);
        popupMenu.getMenu().add("Codeforces");
        popupMenu.getMenu().add("Leetcode");
        popupMenu.getMenu().add("Geeksforgeek");
        popupMenu.getMenu().add("Settings");
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);
            String fragment;
            if (currentFragment instanceof CodeforcesProfile || currentFragment instanceof LeetcodeProfile || currentFragment instanceof GfgProfile){
                fragment = "Profile";
            } else if (currentFragment instanceof PotdFragment) {
                fragment = "POTD";
            } else if (currentFragment instanceof ContestsFragment) {
                fragment = "Contests";
            } else {
                fragment = "";
            }
            if(menuItem.getTitle() == "Codeforces"){
                if(menuList.contains("codeforces")) {
                    platform = "codeforces";
                    if(fragment.equals("Profile")) {
                        codeforcesProfile = new CodeforcesProfile();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) codeforcesMap);
                        bundle.putString("Platform",platform);
                        codeforcesProfile.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,codeforcesProfile).commit();
                    } else if(fragment.equals("POTD")) {
                        potdFragment = new PotdFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) codeforcesPotdMap);
                        bundle.putString("Platform",platform);
                        potdFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,potdFragment).commit();
                    } else {
                        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
                    }
                } else {
                    Toast.makeText(this, "Please add the Codeforces handle from settings", Toast.LENGTH_SHORT).show();
                }
            }
            else if(menuItem.getTitle() == "Leetcode"){
                if(menuList.contains("leetcode")) {
                    platform = "leetcode";
                    if(fragment.equals("Profile")) {
                        leetcodeProfile = new LeetcodeProfile();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) leetcodeMap);
                        bundle.putString("Platform",platform);
                        leetcodeProfile.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,leetcodeProfile).commit();
                    } else if(fragment.equals("POTD")) {
                        potdFragment = new PotdFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) leetcodePotdMap);
                        bundle.putString("Platform",platform);
                        potdFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,potdFragment).commit();
                    } else {
                        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
                    }
                } else {
                    Toast.makeText(this, "Please add the Leetcode handle from settings", Toast.LENGTH_SHORT).show();
                }
            } else if(menuItem.getTitle() == "Geeksforgeek"){
                if(menuList.contains("gfg")) {
                    platform = "gfg";
                    if(fragment.equals("Profile")) {
                        gfgProfile = new GfgProfile();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) geeksforgeekMap);
                        bundle.putString("Platform",platform);
                        gfgProfile.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,gfgProfile).commit();
                    } else if(fragment.equals("POTD")) {
                        potdFragment = new PotdFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) geeksforgeekPotdMap);
                        bundle.putString("Platform",platform);
                        potdFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,potdFragment).commit();
                    } else {
                        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
                    }
                } else {
                    Toast.makeText(this, "Please add the Geeksforgeek handle from settings", Toast.LENGTH_SHORT).show();
                }
            }
            else if(menuItem.getTitle() == "Settings"){
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                if(geeksforgeekMap != null) {
                    intent.putExtra("gfgHandle",(String)geeksforgeekMap.get("Handle"));
                }
                if(leetcodeMap != null) {
                    intent.putExtra("leetcodeHandle",(String)leetcodeMap.get("Handle"));
                }
                if(codeforcesMap != null){
                    intent.putExtra("codeforcesHandle",(String)codeforcesMap.get("Handle"));
                }
                startActivity(intent);
            } else if (menuItem.getTitle() == "Logout") {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }
    public void getPotdData() {
        Log.d("CodersHub_Errors", "Fetching Potd data");
        DocumentReference userRef = FirebaseUtil.POTD();
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> userData = document.getData();
                    if(userData.containsKey("Codeforces"))
                        codeforcesPotdMap = (Map<String,Object>)userData.get("Codeforces");
                    if(userData.containsKey("LeetCode"))
                        leetcodePotdMap = (Map<String,Object>)userData.get("LeetCode");
                    if(userData.containsKey("GeeksForGeeks"))
                        geeksforgeekPotdMap = (Map<String,Object>)userData.get("GeeksForGeeks");
                    Log.d("CodersHub_Errors", "Fetching Potd data succesfull");
                } else {
                    Toast.makeText(MainActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                    Log.d("CodersHub_Errors", "User data not found.");
                }
            } else {
                Toast.makeText(MainActivity.this, "Error fetching user potd data.", Toast.LENGTH_SHORT).show();
                Log.d("CodersHub_Errors", "Error fetching user potd data.");
            }
        });
    }
    public void getContestData() {
        Log.d("CodersHub_Errors", "Fetching contests data");
        DocumentReference userRef = FirebaseUtil.contests();
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Map<String, Object> userData = document.getData();
                    Log.d("CodersHub_Errors", "Got contests data");
                    contestList = new ArrayList<>();
                    for(Map.Entry<String,Object>entry: userData.entrySet()){
                        Map<String, Object>contestData = (Map<String, Object>)entry.getValue();
                        String title = (String)contestData.get("Name");
                        String platform = (String)contestData.get("Platform");
                        String date = (String)contestData.get("Date");
                        String time = (String)contestData.get("Time");
                        Contest contest = new Contest(title,platform,date,time);
                        contestList.add(contest);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Error fetching user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}