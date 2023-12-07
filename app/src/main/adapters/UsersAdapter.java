package com.example.chatapp.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.listeners.UserListener;
import com.example.chatapp.models.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

 /* class UserViewHolder extends
    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }*/

    private final List<User> users;

    private final UserListener userListener;


    public UsersAdapter(List<User> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(parent,
                false
        );
        return new UserViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    /* public UsersAdapter(List<User> users, UserListener userListener) {
                this.users = users;
                this.userListener = userListener;
            }*/
    class UserViewHolder extends  RecyclerView.ViewHolder{
        ItemContainweUserBinding binding;

        UserViewHolder(ItemContainerUserBinding itemContainerUserBinding){
            super(itemContainerUserBinding.getRoot());
            biding = itemContainerUserBinding;

        }

        void setUserData(User user){
            binding.textName.setText(user.name);
            binding.textEmail.setText(User.email);
            binding.imageProfile.setImageBitmap(getUserImage(user.image));
            binding.getRoot().setOnClickListener(v-> userListener.onUserClicked(user));

        }
    }








}
