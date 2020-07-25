package com.omar.myapps.Azkar.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.myapps.Azkar.DataBaseHelper;
import com.omar.myapps.Azkar.MyRecyclerViewAdapter;
import com.omar.myapps.Azkar.R;
import com.omar.myapps.Azkar.Utils;

import java.util.List;

public class ListFragment extends Fragment {

    private List<String> prayList;
    private List<String> favOrNotList;

    MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

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

        //if not open any list, initialy open allah names list;
        if (Utils.Openedlist == null) {
            Utils.Openedlist = "ALLAH_NAMES_TABLE";
            new Utils(getContext()).getDateFrom(getContext(), DataBaseHelper.ALLAH_NAMES_TABLE);
        }
        prayList = Utils.getArrayList();
        favOrNotList = Utils.getFAVorNotList();

        recyclerView = root.findViewById(R.id.recyclerView);
// data to populate the RecyclerView with

        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(getContext(), prayList, favOrNotList, this);
        recyclerView.setAdapter(adapter);

        LinearLayout listFragmentLayout = root.findViewById(R.id.listFragmentLayout);
        if (Utils.Day_Night_Mode.equals("day")) {
            listFragmentLayout.setBackgroundColor(getResources().getColor(R.color.day_background));
        } else {
            listFragmentLayout.setBackgroundColor(getResources().getColor(R.color.night_background));
        }

        return root;
    }


    public interface OnDataPass {
        public void onDataPass(String data);
    }

    public void passData(String data) {
        dataPasser.onDataPass(data);
    }


}
