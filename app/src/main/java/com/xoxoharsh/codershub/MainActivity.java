package com.xoxoharsh.codershub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ProfileFragment profileFragment;
    PotdFragment potdFragment;
    ContestsFragment contestsFragment;
    BottomNavigationView bottomNavigationView;
    ImageButton menuBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuBtn = findViewById(R.id.menu_btn);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        profileFragment = new ProfileFragment();
        potdFragment = new PotdFragment();
        contestsFragment = new ContestsFragment();

        menuBtn.setOnClickListener(v -> showMenu());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.menu_profile)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
            }
            if(item.getItemId() == R.id.menu_potd)
            {
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
            if(menuItem.getTitle() == "Codeforces"){
                Toast.makeText(this, "Codeforces", Toast.LENGTH_SHORT).show();
            }
            else if(menuItem.getTitle() == "Leetcode"){
                Toast.makeText(this, "Leetcode", Toast.LENGTH_SHORT).show();
            }
            else if(menuItem.getTitle() == "Geeksforgeek"){
                Toast.makeText(this, "Geeksforgeek", Toast.LENGTH_SHORT).show();
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
}