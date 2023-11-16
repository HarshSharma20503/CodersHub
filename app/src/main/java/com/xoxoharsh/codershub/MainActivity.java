package com.xoxoharsh.codershub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.xoxoharsh.codershub.util.FirebaseUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ProfileFragment profileFragment;
    PotdFragment potdFragment;
    ContestsFragment contestsFragment;
    BottomNavigationView bottomNavigationView;
    ImageButton menuBtn;
    Map<String, Object> geeksforgeekMap,leetcodeMap,codeforcesMap;
    Map<String, Object> geeksforgeekPotdMap,leetcodePotdMap,codeforcesPotdMap;
    List<String> menuList;


    String platform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuBtn = findViewById(R.id.menu_btn);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        profileFragment = new ProfileFragment();
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
                    if ("Codeforces".equals(outerKey)) {
                        codeforcesMap = (Map<String, Object>) outerValue;
                    } else if ("Leetcode".equals(outerKey)) {
                        leetcodeMap = (Map<String, Object>) outerValue;
                    } else if ("Geeksforgeek".equals(outerKey)) {
                        geeksforgeekMap = (Map<String, Object>) outerValue;
                    }
                }
                Toast.makeText(this, "Data recieved", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Data not recieved", Toast.LENGTH_SHORT).show();
            }
        }

        // to get POTD and contests data
        getData();

        menuBtn.setOnClickListener(v -> showMenu());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.menu_profile) {
                Bundle bundle = new Bundle();
                if(platform.equals("Codeforces")) {
                    bundle.putSerializable("userData", (Serializable) codeforcesMap);
                }
                else if(platform.equals("Leetcode")) {
                    bundle.putSerializable("userData", (Serializable) leetcodeMap);
                }
                else if(platform.equals("Geeksforgeek")) {
                    bundle.putSerializable("userData", (Serializable) geeksforgeekMap);
                }
                bundle.putString("Platform",platform);
                profileFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
            }
            if(item.getItemId() == R.id.menu_potd) {
                Bundle bundle = new Bundle();
                Log.d("Harsh Error","Platform value is "+platform);
                if(platform.equals("Leetcode")) {
                    bundle.putSerializable("userData", (Serializable) leetcodePotdMap);
                }
                else if(platform.equals("Codeforces")) {

                    bundle.putSerializable("userData", (Serializable) codeforcesPotdMap);
                }
                else if(platform.equals("Geeksforgeek")) {
                    Log.d("Harsh Error","putting GeeksforgeekPotdMap");
                    bundle.putSerializable("userData", (Serializable) geeksforgeekPotdMap);
                }
                bundle.putString("Platform",platform);
                potdFragment.setArguments(bundle);
                Log.d("Harsh Error","Sending data");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,potdFragment).commit();
            }
            if(item.getItemId() == R.id.menu_contest)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,contestsFragment).commit();
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);
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
            if (currentFragment instanceof ProfileFragment) {
                fragment = "Profile";
            } else if (currentFragment instanceof PotdFragment) {
                fragment = "POTD";
            } else if (currentFragment instanceof ContestsFragment) {
                fragment = "Contests";
            }
            else {
                fragment = "";
            }
            if(menuItem.getTitle() == "Codeforces"){
                if(menuList.contains("Codeforces"))
                {
                    platform = "Codeforces";
                    if(fragment.equals("Profile")) {
                        profileFragment = new ProfileFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) codeforcesMap);
                        bundle.putString("Platform",platform);
                        profileFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
                    }
                    else if(fragment.equals("POTD"))
                    {
                        potdFragment = new PotdFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) codeforcesPotdMap);
                        bundle.putString("Platform",platform);
                        potdFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,potdFragment).commit();
                    }
                    else {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) codeforcesMap);
                        bundle.putString("Platform",platform);
                        profileFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
                    }
                }
                else
                {
                    Toast.makeText(this, "Please add the Codeforces handle from settings", Toast.LENGTH_SHORT).show();
                }
            }
            else if(menuItem.getTitle() == "Leetcode"){
                if(menuList.contains("Leetcode"))
                {
                    platform = "Leetcode";
                    if(fragment.equals("Profile")) {
                        profileFragment = new ProfileFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) leetcodeMap);
                        bundle.putString("Platform",platform);
                        profileFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
                    }
                    else if(fragment.equals("POTD"))
                    {
                        potdFragment = new PotdFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) leetcodePotdMap);
                        bundle.putString("Platform",platform);
                        potdFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,potdFragment).commit();
                    }
                    else {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) leetcodeMap);
                        bundle.putString("Platform",platform);
                        profileFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
                    }
                }
                else
                {
                    Toast.makeText(this, "Please add the Leetcode handle from settings", Toast.LENGTH_SHORT).show();
                }
            }
            else if(menuItem.getTitle() == "Geeksforgeek"){
                if(menuList.contains("Geeksforgeek"))
                {
                    platform = "Geeksforgeek";
                    if(fragment.equals("Profile")) {
                        profileFragment = new ProfileFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) geeksforgeekMap);
                        bundle.putString("Platform",platform);
                        profileFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
                    }
                    else if(fragment.equals("POTD"))
                    {
                        potdFragment = new PotdFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) geeksforgeekPotdMap);
                        bundle.putString("Platform",platform);
                        potdFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,potdFragment).commit();
                    }
                    else {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", (Serializable) geeksforgeekMap);
                        bundle.putString("Platform",platform);
                        profileFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
                    }
                }
                else
                {
                    Toast.makeText(this, "Please add the Geeksforgeek handle from settings", Toast.LENGTH_SHORT).show();
                }
            }
            else if(menuItem.getTitle() == "Settings"){
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            }
            else if (menuItem.getTitle() == "Logout") {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    public void getData()
    {
        DocumentReference userRef = FirebaseUtil.POTD();

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Convert the document to a Map
                    Map<String, Object> userData = document.getData();
                    if(userData.containsKey("Codeforces"))
                        codeforcesPotdMap = (Map<String,Object>)userData.get("Codeforces");
                    if(userData.containsKey("Leetcode"))
                        leetcodePotdMap = (Map<String,Object>)userData.get("Leetcode");
                    if(userData.containsKey("Geeksforgeek"))
                        geeksforgeekPotdMap = (Map<String,Object>)userData.get("Geeksforgeek");

                } else {
                    Toast.makeText(MainActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Error fetching user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}