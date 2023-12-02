package com.example.chatapp.activities;

import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.listeners.UserListener;

import java.util.prefs.PreferenceChangeEvent;

public class UserActivity extends AppCompatActivity implements UserListener {
    private ActivityUserBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();

    }

    private void setListeners(){binding.imageBack.setOnClickListener(v -> onBackPressed());}

    private void getUsers(){
        loading(isLoading true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task ->){
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if(task.isSucessful() && task.getResult() != null){
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot )
                    }
        }
    }
}
