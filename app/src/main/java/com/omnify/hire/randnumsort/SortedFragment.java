package com.omnify.hire.randnumsort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SortedFragment extends Fragment {
    private static final String ARG_MSORTED_LIST = "mSortedList";
    private static final String ARG_QSORTED_LIST = "qSortedList";

    private ArrayAdapter<Integer> mSortedAdapter;
    private ArrayAdapter<Integer> qSortedAdapter;

    public SortedFragment() {
    }

    public static SortedFragment newInstance(ArrayList<Integer> mSortedList,
                                             ArrayList<Integer> qSortedList) {
        SortedFragment fragment = new SortedFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(ARG_MSORTED_LIST, mSortedList);
        args.putIntegerArrayList(ARG_QSORTED_LIST, qSortedList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArrayList<Integer> mSortedList = getArguments().getIntegerArrayList(ARG_MSORTED_LIST);
            ArrayList<Integer> qSortedList = getArguments().getIntegerArrayList(ARG_QSORTED_LIST);

            mSortedAdapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, mSortedList);
            qSortedAdapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, qSortedList);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sorted, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ListView mSortedListView = view.findViewById(R.id.mSortedListView);
        ListView qSortedListView = view.findViewById(R.id.qSortedListView);

        mSortedListView.setAdapter(mSortedAdapter);
        qSortedListView.setAdapter(qSortedAdapter);
    }
}
