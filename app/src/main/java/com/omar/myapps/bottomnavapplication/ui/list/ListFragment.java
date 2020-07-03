package com.omar.myapps.bottomnavapplication.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.myapps.bottomnavapplication.MyRecyclerViewAdapter;
import com.omar.myapps.bottomnavapplication.R;
import com.omar.myapps.bottomnavapplication.Utils;

import java.util.List;

public class ListFragment extends Fragment {

    private List<String> prayList;
    private List<String> favOrNotList;


    MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    Utils utils;

    View root;


    OnDataPass dataPasser;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_list, container, false);

        //utils = new Utils(getContext());
        prayList = Utils.getArrayList();
        favOrNotList = Utils.getFAVorNotList();

        recyclerView = root.findViewById(R.id.recyclerView);
// data to populate the RecyclerView with

        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), prayList, favOrNotList, this);
        recyclerView.setAdapter(adapter);
        return root;
    }


    public interface OnDataPass {
        public void onDataPass(String data);
    }

    public void passData(String data) {
        dataPasser.onDataPass(data);
    }


}
