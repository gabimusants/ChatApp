package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.chatapp.adapters.UsersAdapter
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.chatapp.R;
import com.example.chatapp.listeners.UserListener;
import com.example.chatapp.utilities.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.chatapp.models.User;
import java.util.ArrayList;
import java.util.List;


public class UsersActivity extends AppCompatActivity implements UserListener {

    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(v -> onBackPressed());

    }
    private void getUsers() {
        loading(isloading true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get().addOnCompleteListener(task -> {
                    loading(isloading:false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (currentUserId.equals(queryDocumentSnapshot :task.getId())){
                                continue;
                            }
                            User user = new User();
                            user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            users.add(user);
                        }
                        if (users.size() > 0) {
                            UsersAdapter usersAdapter = new UsersAdapter(users, this);
                            binding.usersRecyclerView.setAdapter(usersAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showErrorMessage();
                        }
                    } else {
                        showErrorMessage();
                    }
                });
    }

        private void showErrorMessage() {
            binding.textErrorMessage.setText(String.format("%s", "No user available"));
            binding.textErrorMessage.setVisibility(View.VISIBLE);
        }

        private void loading(Boolean isLoading){
            if(isLoading){
                binding.progressBar.setVisibility(View.VISIBLE);

            }else {
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        }
        @Override
        public void onUserClicked(User user){
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            intent.putExtra(Constants.KEY_USER, user);
            startActivity(intent);
            finish();
        }
    }
