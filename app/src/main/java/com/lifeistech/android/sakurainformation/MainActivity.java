package com.lifeistech.android.sakurainformation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SakuraAdapter adapter;
    ListView listView;

    static final String APPLICATION_KEY= "421d09fe3957ca790282da7ee12a8ea511a21997e8f2ee35534991a9e038309f";
    static final String CLIENT_KEY= "8689baffb6e79aeb07a3ed7fd5db93386d18bbc5a774689adccaa1b53642b6c5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初期化
        NCMB.initialize(this,APPLICATION_KEY,CLIENT_KEY);
        NCMBQuery<NCMBObject> query= new NCMBQuery<>("SakuraClass");

        //dataというフィールドがsakuraとなっているデータを検索する条件を設定
        query.whereEqualTo("data","sakura");

        try {
            //データストアからデータを検索
            List<NCMBObject> NCMBObjects = query.find();
            adapter= new SakuraAdapter(this,R.layout.custom_list_layout, NCMBObjects);
            listView= (ListView)findViewById(R.id.listView);
            listView.setAdapter(adapter);
        } catch (NCMBException error) {
            //検索失敗時の処理
            NCMBError(error);
        }

    }

    //エラー処理
    public void NCMBError(NCMBException error) {

        StringBuilder sb=new StringBuilder("【Failure】\n");
        if (error.getCode() !=null) {
            sb.append("StatusCode : ").append(error.getCode()).append("\n");
        }
        if (error.getMessage() != null) {
            sb.append("Message : ").append(error.getMessage()).append("\n");
        }
        Log.e("error",sb.toString());
    }


}
