package com.aakash.vlabs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

public class ChooseExperiment extends FragmentActivity 
        implements SubjectList.OnHeadlineSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.subject_list);
    }

    public void onArticleSelected(int position) {
       
        ExperimentList articleFrag = (ExperimentList)
                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        if (articleFrag != null) {

            articleFrag.updateArticleView(position);

        }
    }
}