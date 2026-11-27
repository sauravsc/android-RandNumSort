package com.omnify.hire.randnumsort;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;

public class GeneratorFragment extends Fragment {
    private OnSortedListener listener;

    public static final String LIST_PARAM = "com.omnify.hire.randnumsort.extra.LIST";

    private static final Integer LIST_COUNT = 40;
    private static final Integer RAND_MIN = 10;
    private static final Integer RAND_MAX = 99;

    private ArrayAdapter<Integer> arrayAdapter;
    private ArrayList<Integer> qSortedList;
    private ArrayList<Integer> mSortedList;

    private final BroadcastReceiver qSortBroadcastReceiver = new QSortBroadcastReceiver();
    private final BroadcastReceiver mSortBroadcastReceiver = new MSortBroadcastReceiver();

    public GeneratorFragment() {}

    private void generateRandomNumbers() {
        arrayAdapter.clear();
        for (int i = 0; i < LIST_COUNT; i++) {
            arrayAdapter.add(10 + (int) (Math.random()*(RAND_MAX - RAND_MIN)));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSortedListener) {
            listener = (OnSortedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_generator, container, false);

        if (arrayAdapter == null) {
            arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.list_item,
                    new ArrayList<>());
            generateRandomNumbers();
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ListView listView = view.findViewById(R.id.unsortedListView);
        listView.setAdapter(arrayAdapter);

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(requireContext());
        broadcastManager.registerReceiver(qSortBroadcastReceiver,
                new IntentFilter(QuickSortService.BROADCAST_ACTION_SORTED));
        broadcastManager.registerReceiver(mSortBroadcastReceiver,
                new IntentFilter(MergeSortService.BROADCAST_ACTION_SORTED));

        view.findViewById(R.id.gen_button).setOnClickListener(this::genButtonClicked);
        view.findViewById(R.id.sort_button).setOnClickListener(this::sortButtonClicked);
    }

    @Override
    public void onDestroyView() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(requireContext());
        broadcastManager.unregisterReceiver(qSortBroadcastReceiver);
        broadcastManager.unregisterReceiver(mSortBroadcastReceiver);
        super.onDestroyView();
    }

    public void genButtonClicked(View view) {
        generateRandomNumbers();
    }

    public void sortButtonClicked(View view) {
        mSortedList = null;
        qSortedList = null;

        ArrayList<Integer> list = new ArrayList<>(arrayAdapter.getCount());
        for (int i = 0; i < arrayAdapter.getCount(); i++) {
            list.add(arrayAdapter.getItem(i));
        }

        Context context = requireContext();

        Intent qSortIntent = new Intent(context, QuickSortService.class);
        qSortIntent.setAction(QuickSortService.ACTION_SORT);
        qSortIntent.putIntegerArrayListExtra(LIST_PARAM, list);
        context.startService(qSortIntent);

        Intent mSortIntent = new Intent(context, MergeSortService.class);
        mSortIntent.setAction(MergeSortService.ACTION_SORT);
        mSortIntent.putIntegerArrayListExtra(LIST_PARAM, list);
        context.startService(mSortIntent);
    }

    private class QSortBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            qSortedList = intent
                    .getIntegerArrayListExtra(QuickSortService.SORTED_LIST_PARAM);
            if (listener != null && mSortedList != null){
                listener.onSorted(mSortedList, qSortedList);
            }
        }
    }

    private class MSortBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mSortedList = intent
                    .getIntegerArrayListExtra(MergeSortService.SORTED_LIST_PARAM);
            if (listener != null && qSortedList != null){
                listener.onSorted(mSortedList, qSortedList);
            }
        }
    }

    public interface OnSortedListener {
        void onSorted(ArrayList<Integer> mSortedList, ArrayList<Integer> qSortedList);
    }
}
