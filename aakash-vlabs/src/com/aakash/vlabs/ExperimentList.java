package com.aakash.vlabs;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
<<<<<<< HEAD
import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Context;
=======
import android.support.v4.app.ListFragment;
import android.content.Intent;
>>>>>>> old-state
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ExperimentList extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = 0;
    ListView listview;
	View view;
	ArrayAdapter<String> adapter;
	private ChooseExperiment thecon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        view = inflater.inflate(R.layout.experiment_list, container, false);
        listview =(ListView)view.findViewById(android.R.id.list);
        
        //adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_list, R.id.desc,JSONdata.Experiments[mCurrentPosition]); 
        //listview.setAdapter(adapter);
        MyAdapter.j = mCurrentPosition;
        adapter = new MyAdapter(getActivity(), JSONdata.ExperimentsHead.get(mCurrentPosition));
        listview.setAdapter(adapter);
        
//        listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				// TODO Auto-generated method stub
//				try {
//					Class ourClass = Class.forName("com.aakash.vlabs.ShowExp");
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				Intent intent = new Intent();
//
////				intent.putExtra("class_no", class_no);
////				intent.putExtra("subject", subject);
////				intent.putExtra("exp_name", exp_name);
////				intent.putExtra("exp_no", exp_no);
//				startActivity(intent);
//				
//			}
//        });
        
<<<<<<< HEAD
        //adapter = new ArrayList<ExperimentDetails>();
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list, JSONdata.Experiments[0]);    
        
        /*ExperimentDetails detail;
        
        detail = new ExperimentDetails();
        detail.setIcon(R.drawable.ic_launcher);
        detail.setDesc("this is");
//        detail.setP1(R.drawable.ic_launcher);
//        detail.setP2(R.drawable.ic_launcher);
//        detail.setP3(R.drawable.ic_launcher);
//        detail.setP4(R.drawable.ic_launcher);
//        detail.setP5(R.drawable.ic_launcher);
        adapter.add(detail);
        /*
        detail = new ExperimentDetails();
        detail.setIcon(R.drawable.ic_launcher);
        detail.setDesc("this is");
        detail.setP1(R.drawable.ic_launcher);
        detail.setP2(R.drawable.ic_launcher);
        detail.setP3(R.drawable.ic_launcher);
        detail.setP4(R.drawable.ic_launcher);
        detail.setP5(R.drawable.ic_launcher);
        adapter.add(detail);
        
        detail = new ExperimentDetails();
        detail.setIcon(R.drawable.ic_launcher);
        detail.setDesc("this is");
        detail.setP1(R.drawable.ic_launcher);
        detail.setP2(R.drawable.ic_launcher);
        detail.setP3(R.drawable.ic_launcher);
        detail.setP4(R.drawable.ic_launcher);
        detail.setP5(R.drawable.ic_launcher);
        adapter.add(detail);
        
        detail = new ExperimentDetails();
        detail.setIcon(R.drawable.ic_launcher);
        detail.setDesc("this is");
        detail.setP1(R.drawable.ic_launcher);
        detail.setP2(R.drawable.ic_launcher);
        detail.setP3(R.drawable.ic_launcher);
        detail.setP4(R.drawable.ic_launcher);
        detail.setP5(R.drawable.ic_launcher);
        adapter.add(detail);*/
        
        listview.setAdapter(adapter);
        		//new CustomAdapter(adapter,thecon));  
=======
>>>>>>> old-state
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
        	
            updateArticleView(args.getInt(ARG_POSITION));
        
        
        } else if (mCurrentPosition != -1) {
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
    	
        //TextView article = (TextView) getActivity().findViewById(R.id.article);
        //article.setText(JSONdata.Experiments[position]);
        mCurrentPosition = position;
<<<<<<< HEAD
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, JSONdata.Experiments[mCurrentPosition]);
        
        /*ExperimentDetails detail;
        
        detail = new ExperimentDetails();
        detail.setIcon(R.drawable.ic_launcher);
        detail.setDesc("this is");
//        detail.setP1(R.drawable.ic_launcher);
//        detail.setP2(R.drawable.ic_launcher);
//        detail.setP3(R.drawable.ic_launcher);
//        detail.setP4(R.drawable.ic_launcher);
//        detail.setP5(R.drawable.ic_launcher);
        adapter.add(detail);
        /*
        detail = new ExperimentDetails();
        detail.setIcon(R.drawable.ic_launcher);
        detail.setDesc("this is");
        detail.setP1(R.drawable.ic_launcher);
        detail.setP2(R.drawable.ic_launcher);
        detail.setP3(R.drawable.ic_launcher);
        detail.setP4(R.drawable.ic_launcher);
        detail.setP5(R.drawable.ic_launcher);
        adapter.add(detail);
        
        detail = new ExperimentDetails();
        detail.setIcon(R.drawable.ic_launcher);
        detail.setDesc("this is");
        detail.setP1(R.drawable.ic_launcher);
        detail.setP2(R.drawable.ic_launcher);
        detail.setP3(R.drawable.ic_launcher);
        detail.setP4(R.drawable.ic_launcher);
        detail.setP5(R.drawable.ic_launcher);
        adapter.add(detail);
        
        detail = new ExperimentDetails();
        detail.setIcon(R.drawable.ic_launcher);
        detail.setDesc("this is");
        detail.setP1(R.drawable.ic_launcher);
        detail.setP2(R.drawable.ic_launcher);
        detail.setP3(R.drawable.ic_launcher);
        detail.setP4(R.drawable.ic_launcher);
        detail.setP5(R.drawable.ic_launcher);
        adapter.add(detail);*/
        
        listview.setAdapter(adapter);
        //new CustomAdapter(adapter,thecon));
=======
        //adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_list, R.id.desc,JSONdata.Experiments[mCurrentPosition]); 
        //listview.setAdapter(adapter);
        MyAdapter.j = mCurrentPosition;
        adapter = new MyAdapter(getActivity(), JSONdata.ExperimentsHead.get(mCurrentPosition));
        listview.setAdapter(adapter);
        
>>>>>>> old-state
        //listview.setBackgroundColor(Color.BLACK);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

	//public void setcon(ChooseExperiment chooseExperiment) {
		// TODO Auto-generated method stub
		//thecon = chooseExperiment;
	//}
}