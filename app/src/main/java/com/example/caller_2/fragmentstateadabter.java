package com.example.caller_2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class fragmentstateadabter extends FragmentStateAdapter {
    public fragmentstateadabter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                return new fragment_caller();
            case 0:
                return new fragment_cards();
        }

        return new fragment_cards();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
