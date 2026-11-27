package com.omnify.hire.randnumsort;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GeneratorFragment.OnSortedListener
{
    private GeneratorFragment generatorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Apps targeting Android 15+ are drawn edge-to-edge; keep the content
        // (notably the bottom buttons) clear of the system bars.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container),
                (v, insets) -> {
                    Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars()
                            | WindowInsetsCompat.Type.displayCutout());
                    v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                    return WindowInsetsCompat.CONSUMED;
                });

        if (savedInstanceState != null) {
            return;
        }

        generatorFragment = new GeneratorFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, generatorFragment).commit();
        getSupportActionBar().setTitle(R.string.gen_frag_title);
    }

    @Override
    public void onSorted(ArrayList<Integer> mSortedList, ArrayList<Integer> qSortedList) {
        if (!isFinishing() && !isDestroyed()) {
            SortedFragment sortedFragment = SortedFragment.newInstance(mSortedList, qSortedList);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, sortedFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.sort_frag_title);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (generatorFragment == null) {
            generatorFragment = new GeneratorFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, generatorFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.gen_frag_title);
        return true;
    }
}
