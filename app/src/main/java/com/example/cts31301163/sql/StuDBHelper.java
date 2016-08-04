package com.example.cts31301163.sql;

/**
 * Created by Administrator on 2016/7/4.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cts31301163.model.PlanBean;
import com.example.cts31301163.shorttermcts.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StuDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "TestSQLite";
    public static final int VERSION = 1;

    //必须要有构造函数
    public StuDBHelper(Context context, String name, CursorFactory factory,
                       int version) {
        super(context, name, factory, version);
    }

    // 当第一次创建数据库的时候，调用该方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table plan_table(planId  INTEGER PRIMARY KEY autoincrement,planTitle varchar(50),planType varchar(50),planContent varchar(255)," +
                "planAddTime timestamp (50)," +
                "planClockTime timestamp (50)," +
                "planMessage varchar(255))";
//输出创建数据库的日志信息
        Log.i(TAG, "create Database------------->");
//execSQL函数用于执行SQL语句
        db.execSQL(sql);
    }

    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//输出更新数据库的日志信息
        db.execSQL("INSERT INTO plan_table VALUES (planContent, planTitle, planType)", new Object[]{"aa", "bb","vv"});
        Log.i(TAG, "update Database------------->");
    }



    public void queryByTitle(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArrayList<PlanBean> p=new ArrayList<PlanBean>();


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

    }


    public ArrayList<PlanBean> queryAllPlan(SQLiteDatabase db) {
        ArrayList<PlanBean> p=new ArrayList<PlanBean>();


        Cursor cursor = db.query("plan_table", new String[]{"planId","planTitle","planClockTime"},null,null, null, null, null);
        while(cursor.moveToNext()){
            PlanBean planbean=new PlanBean();
            int id=cursor.getInt(cursor.getColumnIndex("planId"));
            String name = cursor.getString(cursor.getColumnIndex("planTitle"));
            String ClockTime = cursor.getString(cursor.getColumnIndex("planClockTime"));
            planbean.setPlanId(id);
            planbean.setPlanTitle(name);
            planbean.setPlanClockTime(ClockTime);


            System.out.println("queryall------->" + "planTitlea："+name+" ");
            p.add(planbean);
        }
//关闭数据库
        db.close();
        return p;
    }


    public ArrayList<PlanBean> queryTodayPlan(SQLiteDatabase db) {
        ArrayList<PlanBean> p=new ArrayList<PlanBean>();

        System.out.println("querytoday------->");

        String addtime;
         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        addtime=df.format(new Date());
        System.out.println("querytoday--addtime----->"+addtime);
        String starttime=addtime+" 00:00";
        String endtime=addtime+" 24:00";
        Cursor cursor =  db.rawQuery(" select * from plan_table where planClockTime >= ? and planClockTime < ?",new String[] {addtime,endtime});
        while(cursor.moveToNext()){
            PlanBean planbean=new PlanBean();
            int id=cursor.getInt(cursor.getColumnIndex("planId"));
            String name = cursor.getString(cursor.getColumnIndex("planTitle"));
            String ClockTime = cursor.getString(cursor.getColumnIndex("planClockTime"));
            planbean.setPlanId(id);
            planbean.setPlanTitle(name);
            planbean.setPlanClockTime(ClockTime);


            System.out.println("querytoday------->" + "planTitlea："+name+" ");
            p.add(planbean);
        }
//关闭数据库
        db.close();
        return p;
    }



    public ArrayList<PlanBean> queryOutPlan(SQLiteDatabase db) {
        ArrayList<PlanBean> p=new ArrayList<PlanBean>();
        System.out.println("queryout------->" );
        String addtime;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        addtime=df.format(new Date());
        System.out.println("addtimeaddtime------->" +addtime+" ");
        Cursor cursor =  db.rawQuery(" select * from plan_table where planClockTime < ?",new String[] {addtime});
        while(cursor.moveToNext()){
            PlanBean planbean=new PlanBean();
            int id=cursor.getInt(cursor.getColumnIndex("planId"));
            String name = cursor.getString(cursor.getColumnIndex("planTitle"));
            String ClockTime = cursor.getString(cursor.getColumnIndex("planClockTime"));
            planbean.setPlanId(id);
            planbean.setPlanTitle(name);
            planbean.setPlanClockTime(ClockTime);


            System.out.println("queryout------->" + "planTitlea："+name+" ");
            p.add(planbean);
        }
//关闭数据库
        db.close();
        return p;
    }







}