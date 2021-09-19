package com.example.carbonfoodprint;

import static java.lang.Double.valueOf;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Total#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Total extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Total() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Total.
     */
    // TODO: Rename and change types and number of parameters
    public static Total newInstance(String param1, String param2) {
        Total fragment = new Total();
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
        View rootview= inflater.inflate(R.layout.fragment_total, container, false);

        ArrayList<TotalItem> totalItemArrayList = new ArrayList<>();

        mRecyclerView = rootview.findViewById(R.id.recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new TotalItemsAdapter(totalItemArrayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        int initial_cap = 400;
        List<Double> array= new ArrayList<>(initial_cap);

        getParentFragmentManager().setFragmentResultListener("commodityData", this, new FragmentResultListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String data =result.getString("commodity_data");
                Log.e("Fragment transfer commodity", data);
                String [] tokens = null;
                tokens = data.split(",");
                Double number = valueOf(tokens[3]);
                Log.e("number", number.toString());
                array.add(number);
                totalItemArrayList.add(new TotalItem(tokens[0],tokens[1],tokens[2],tokens[3]));



            }
        });


        TextView textView=rootview.findViewById(R.id.totalValue);
        TextView textView2=rootview.findViewById(R.id.totalValue2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sizeArray = totalItemArrayList.size();
                double totalVal=0.0;
                for(int i=0;i<sizeArray;i++){
                    totalVal+=valueOf(totalItemArrayList.get(i).getmText4());
                }
                if (totalVal>3.05){
                    final Dialog dialog = new Dialog(getContext());
                    //We have added a title in the custom layout. So let's disable the default title.
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
                    dialog.setCancelable(true);
                    //Mention the name of the layout of your custom dialog.
                    dialog.setContentView(R.layout.warning);
                    dialog.show();
                    ImageView cancel=dialog.findViewById(R.id.cancel2);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                }
                String.format("%.3g%n", totalVal); //rounding
                textView2.setText(String.valueOf(totalVal));
                Log.e("arr", String.valueOf(totalVal));

            }
        });
        if (!totalItemArrayList.isEmpty()){
        Log.e("arr", String.valueOf(totalItemArrayList.get(0)));}

//        final String[] data2 = new String[1];
//        getParentFragmentManager().setFragmentResultListener("co2Data", this, new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
//                data2[0] =result.getString("co2_data");
//                Log.e("Fragment transfer co2", data2[0]);
//            }
//        });
//
//        final String[] data3 = new String[1];
//        getParentFragmentManager().setFragmentResultListener("amountData", this, new FragmentResultListener() {
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
//                 data3[0] =result.getString("amount_data");
//                Log.e("Fragment transfer amount", data3[0]);
//            }
//        });
        //Log.e("check",data1[0].toString()+"  "+data2[0].toString()+"  "+data3[0].toString());
        //totalItemArrayList.add(data1.toString(),data2,data3,);
        return rootview;
    }

}