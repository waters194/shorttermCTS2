package com.example.cts31301163.shorttermcts;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.cts31301163.model.PlanBean;
import com.example.cts31301163.sql.StuDBHelper;
import com.example.zucc.cts31301163.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/7/15.
 */
public class PageFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private int mPage;
    ArrayList<PlanBean> planlist= null;
    ArrayList<HashMap<String, String>> list = null;
    HashMap<String, String> map = null;


    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        System.out.println( "tab.getPosicccpage"+page);
        args.putInt(ARGS_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);

        System.out.println( "tab.getPosiccc"+mPage);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item,container,false);
        ListView  lv = (ListView) view.findViewById(R.id.list);

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();/*在数组中存放数据*/
        StuDBHelper dbHelper = new StuDBHelper(getContext(),"stu_db",null,2);
//得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        if(mPage==1)
            planlist=dbHelper.queryAllPlan(db);
        else if(mPage==2)
            planlist=dbHelper.queryTodayPlan(db);
        else if(mPage==3)
            planlist=dbHelper.queryOutPlan(db);
        else
            planlist=dbHelper.queryAllPlan(db);


        for(int i=0;i<planlist.size();i++)
        {
            HashMap<String, Object> hmap = new HashMap<String, Object>();
            hmap.put("plan_name", planlist.get(i).getPlanTitle());
            hmap.put("ItemText", planlist.get(i).getPlanClockTime());
            listItem.add(hmap);
        }

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(getContext(),listItem,R.layout.list_item, new String[] {"plan_name", "ItemText"},
    new int[] {R.id.plan_name,R.id.ItemText});

    lv.setAdapter(mSimpleAdapter);//为ListView绑定适配器
        System.out.println( "tab.getPositbbbb"+mPage);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("select------->" + "selectedPosition："+position+" ");
                Intent intent = new Intent();
                System.out.println("query------->" + "planidddd："+planlist.get(position).getPlanId()+" ");
                intent.putExtra("pid",planlist.get(position).getPlanId() );
                intent.setClass(getContext(), UpdataPlanActivity.class);
                getContext().startActivity(intent);

            }
        });


        return view;
    }










}