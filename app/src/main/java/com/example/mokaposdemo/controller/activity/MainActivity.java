package com.example.mokaposdemo.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.mokaposdemo.R;
import com.example.mokaposdemo.controller.fragments.DiscountListFragment;
import com.example.mokaposdemo.controller.fragments.ItemListFragment;
import com.example.mokaposdemo.controller.fragments.LibraryFragment;
import com.example.mokaposdemo.controller.fragments.ShoppingCartFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(R.id.container_1, LibraryFragment.get());
        loadFragment(R.id.container_2, ShoppingCartFragment.get());
    }

    private void loadFragment(int container, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void openDiscountScreen() {
        loadFragment(R.id.container_1, DiscountListFragment.get());
    }

    public void openItemsScreen() {
        loadFragment(R.id.container_1, ItemListFragment.get());
    }

    @Override
    public void onBackPressed() {

        Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.container_1);
        if (currentFrag != null) {
            if (currentFrag.getClass().equals(LibraryFragment.class)) {
                finish();
                return;
            }
        }

        super.onBackPressed();
    }
}
