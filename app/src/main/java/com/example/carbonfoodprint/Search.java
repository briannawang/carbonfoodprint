package com.example.carbonfoodprint;

import static java.lang.Double.valueOf;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment {

    private RecyclerView mRecyclerView;
    private ElementAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ArrayList<ElementList> elementArrayList = new ArrayList<>();

        List<Foodprint> carbonFoodprints = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.foodprint);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                Foodprint foodprint = new Foodprint();
                foodprint.setCommodity(tokens[0]);
                foodprint.setFootprint(Double.parseDouble(tokens[1]));
                carbonFoodprints.add(foodprint);
                Log.e("token",tokens[0]+"  "+Double.parseDouble(tokens[1]));
                elementArrayList.add(new ElementList(tokens[0],tokens[1]));

                Log.e("myActivity", "Just created:" + foodprint);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file from line" + line, e);
            e.printStackTrace();
        }

//        TextView enter = rootView.findViewById(R.id.enter);
//        @SuppressLint("ResourceType") Menu menu = rootView.findViewById(R.menu.elements_menu);
//        enter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onCreateOptionsMenu(menu);
//            }
//        });


        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ElementAdapter(elementArrayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ElementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                //open pop-up here and get data
                Log.e("clicked","click");

                final Dialog dialog = new Dialog(getContext());
                //We have added a title in the custom layout. So let's disable the default title.
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
                dialog.setCancelable(true);
                //Mention the name of the layout of your custom dialog.
                dialog.setContentView(R.layout.enter_kg_popup);
                dialog.show();

                EditText text = dialog.findViewById(R.id.editText);


                final ImageView cancel = dialog.findViewById(R.id.cancel);
                final TextView okay= dialog.findViewById(R.id.okay);
                 cancel.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         dialog.dismiss();
                     }
                 });
                final Double[] amount = new Double[1];
                 okay.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                        String name = text.getText().toString();

                        amount[0] = Double.parseDouble(name);
                        Log.e("Amount", amount[0].toString());

                        String data = elementArrayList.get(position).getText1().toString()+","+elementArrayList.get(position).getText2().toString()+","+amount[0].toString()+","+amount[0]*valueOf(elementArrayList.get(position).getText2().toString());

                         Bundle commodityResult = new Bundle();
                         commodityResult.putString("commodity_data",data);
                         getParentFragmentManager().setFragmentResult("commodityData",commodityResult);


//                         Bundle commodityResult = new Bundle();
//                         commodityResult.putString("commodity_data",elementArrayList.get(position).getText1().toString());
//                         getParentFragmentManager().setFragmentResult("commodityData",commodityResult);
//
//                         Bundle co2Result = new Bundle();
//                         co2Result.putString("co2_data",elementArrayList.get(position).getText2().toString());
//                         getParentFragmentManager().setFragmentResult("co2Data",co2Result);
//
//                         Bundle amountResult = new Bundle();
//                         amountResult.putString("amount_data",amount[0].toString());
//                         getParentFragmentManager().setFragmentResult("amountData",amountResult);

                        dialog.dismiss();

                     }
                 });





            }
        });
        return rootView;


    }}


//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getActivity().getMenuInflater();
//        inflater.inflate(R.menu.elements_menu,menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//                return false;
//            }
//        });
//        return true;
//    }
//
//}