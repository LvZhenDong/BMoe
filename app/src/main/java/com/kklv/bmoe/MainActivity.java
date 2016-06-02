package com.kklv.bmoe;

import android.app.Activity;
import android.os.Bundle;

import com.kklv.bmoe.data.DataHelper;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataHelper dataHelper=DataHelper.getInstance(this);
        dataHelper.getAllCamps();
        dataHelper.getCampRank("Fate/stay night [UBW]");
        dataHelper.getRoleIntradayCount("Saber","16-01-03");
    }

}
