package com.example.cts31301163.shorttermcts;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.zucc.cts31301163.R;
import com.example.cts31301163.model.PlanBean;
import com.example.cts31301163.sql.StuDBHelper;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //
    String[] from = {"name", "time"};              //这里是ListView显示内容每一列的列名
    int[] to = {R.id.plan_name};   //这里是ListView显示每一列对应的list_item中控件的id

    String[] userName = {};
    String[] userId = {};

    ArrayList<HashMap<String, String>> list = null;
    HashMap<String, String> map = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    ArrayList<PlanBean> planlist= null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        reflashlistview();
//http://www.cnblogs.com/JohnTsai/p/4715454.html
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        MyFragmentPagerAdapter adapter1 = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        viewPager.setAdapter(adapter1);

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddDetailActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });





    }











    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //搜索栏输入关键字查找相应事件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);



        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String queryText) {

                ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
                MyFragmentPagerAdapter adapter1 = new MyFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this);
                viewPager.setAdapter(adapter1);

                //TabLayout
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
                tabLayout.setupWithViewPager(viewPager);

                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                tabLayout.setTabMode(TabLayout.MODE_FIXED);


                System.out.println("SearchView------->" +queryText );
                queryByTitle(queryText);//sqllite查找相应事件

                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String queryText) {

                return true;
            }


});


    return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }











    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






        public ArrayList<PlanBean> queryAllPlan() {
            ArrayList<PlanBean> p=new ArrayList<PlanBean>();
            StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,2);
//得到一个可写的数据库
            SQLiteDatabase db =dbHelper.getReadableDatabase();

            Cursor cursor = db.query("plan_table", new String[]{"planId","planTitle","planClockTime"},null,null, null, null, null);
            while(cursor.moveToNext()){
                PlanBean planbean=new PlanBean();
                int id=cursor.getInt(cursor.getColumnIndex("planId"));
                String name = cursor.getString(cursor.getColumnIndex("planTitle"));
                String ClockTime = cursor.getString(cursor.getColumnIndex("planClockTime"));
                planbean.setPlanId(id);
                planbean.setPlanTitle(name);
                planbean.setPlanClockTime(ClockTime);


                System.out.println("query------->" + "planTitlea："+name+" ");
                p.add(planbean);
            }
//关闭数据库
            db.close();
            return p;
        }




    public ArrayList<PlanBean> queryByTitle(String ptitle) {
        ArrayList<PlanBean> p=new ArrayList<PlanBean>();
        StuDBHelper dbHelper = new StuDBHelper(MainActivity.this,"stu_db",null,2);
//得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String[] a={"%"+ptitle+"%"};
        Cursor cursor =  db.rawQuery("select * from plan_table where planTitle like ?",a);
        ListView  lv = (ListView) findViewById(R.id.list);
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/
        while(cursor.moveToNext()){

            PlanBean planbean=new PlanBean();
            int id=cursor.getInt(cursor.getColumnIndex("planId"));
            String name = cursor.getString(cursor.getColumnIndex("planTitle"));
            String ClockTime = cursor.getString(cursor.getColumnIndex("planClockTime"));
            planbean.setPlanId(id);
            planbean.setPlanTitle(name);
            planbean.setPlanClockTime(ClockTime);


            HashMap<String, Object> hmap = new HashMap<String, Object>();
            hmap.put("plan_name", cursor.getString(cursor.getColumnIndex("planTitle")));
            hmap.put("ItemText",cursor.getString(cursor.getColumnIndex("planClockTime")));
            listItem.add(hmap);



            System.out.println("query------->" + "planTitlea："+name+" ");
            p.add(planbean);
        }
//关闭数据库
        db.close();

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,//需要绑定的数据
                R.layout.list_item,//每一行的布局//动态数组中的数据源的键对应到定义布局的View中
                new String[] {"plan_name", "ItemText"},
                new int[] {R.id.plan_name,R.id.ItemText}
        );

        lv.setAdapter(mSimpleAdapter);//为ListView绑定适配器
        return p;
    }



//    public void reflashlistview() {
//        ListView  lv = (ListView) findViewById(R.id.list);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
//
//        planlist=queryAllPlan();
//
//
//
//
//        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();/*在数组中存放数据*/
//        for(int i=0;i<planlist.size();i++)
//        {
//            HashMap<String, Object> hmap = new HashMap<String, Object>();
//            hmap.put("plan_name", planlist.get(i).getPlanTitle());
//            hmap.put("ItemText", planlist.get(i).getPlanClockTime());
//            listItem.add(hmap);
//        }
//
//
//        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,//需要绑定的数据
//                R.layout.list_item,//每一行的布局//动态数组中的数据源的键对应到定义布局的View中
//                new String[] {"plan_name", "ItemText"},
//                new int[] {R.id.plan_name,R.id.ItemText}
//        );
//
//        lv.setAdapter(mSimpleAdapter);//为ListView绑定适配器
//    }


}
