package com.example.cts31301163.shorttermcts;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zucc.cts31301163.R;
import com.example.cts31301163.model.PlanBean;
import com.example.cts31301163.sql.StuDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/7/7.
 */
public class UpdataPlanActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        private Button updata_button = null;
        private Button delete_button = null;
        private Button message_button = null;
        private Button edtbutton = null;
        private Spinner m_spiModel = null; //计划类型
        private String addtime;
        private EditText editTitle=null;
        private EditText editContent=null;
        private EditText editClockTime=null;
        private EditText editMessage=null;
        private String  arrive_year = null;
        private String arrive_month = null;
        private String  arrive_day = null;
        private String  arrive_hour = null;
        private String  arrive_min = null;
        private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private PlanBean pbean=new PlanBean();
        private ImageView ImageViewback=null;


    private int pid;
    private   String strModel_init="学习计划,工作计划,会议安排,私人安排";
    private String[] arrModel =strModel_init.split(",");//转换 数组
    private ArrayList<String> options=new ArrayList<String>();
        @Override
        public void onCreate(Bundle savedInstanceState) {
            options.add("学习计划");
            options.add("工作计划");
            options.add("会议安排");
            options.add("私人安排");

            super.onCreate(savedInstanceState);
            setContentView(R.layout.updataplan_detail);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);


            m_spiModel = (Spinner) findViewById(R.id.spinner);//产品型号
            editTitle=(EditText) findViewById(R.id.editText2);
            editContent=(EditText) findViewById(R.id.editText4);
            editClockTime=(EditText) findViewById(R.id.editText3);
            editMessage=(EditText) findViewById(R.id.messageeditText);
//            editContent.setFocusable(false);//不可编辑
//            editTitle.setFocusable(false);//不可编辑
//            editClockTime.setFocusable(false);//不可编辑



            editClockTime.setTextColor(Color.BLACK);
            editTitle.setTextColor(Color.BLACK);
            editContent.setTextColor(Color.BLACK);
            editMessage.setTextColor(Color.BLACK);

//            m_spiModel.setse(Color.BLACK);

            m_spiModel.setEnabled(false);//不可编辑
            editClockTime.setEnabled(false);//不可编辑
            editTitle.setEnabled(false);//不可编辑
            editContent.setEnabled(false);//不可编辑
            editMessage.setEnabled(false);//不可编辑

            ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this, R.layout.myspinner);
            m_spiModel.setAdapter(spinneradapter);
            updata_button =(Button) findViewById(R.id.updataButton);
            delete_button=(Button) findViewById(R.id.deletebutton);
            message_button =(Button) findViewById(R.id.messagebutton);
            edtbutton =(Button) findViewById(R.id.edtbutton1);
            ImageViewback=(ImageView) findViewById(R.id.imageViewback3);



            Intent intent=getIntent();
             pid=intent.getExtras().getInt("pid");
            System.out.println("updataaaqq------->" + "planidddd："+pid);
//            int pid=Integer.valueOf(Stringid).intValue();
//            int pid=Integer.parseInt(this.getIntent().getStringExtra("pid"));
            pbean=queryPlanByid(pid+"");
//            ArrayAdapter<String> adapter;//数组 配置器 下拉菜单赋值用
////将可选内容与ArrayAdapter连接起来
//            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrModel);
////设置下拉列表的风格
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            m_spiModel.setAdapter(adapter);//将adapter 添加到spinner中
//        m_spiModel.setVisibility(View.VISIBLE);//设置默认值


            message_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(UpdataPlanActivity.this, MainActivity.class);
                    UpdataPlanActivity.this.startActivity(intent);
                    finish();
                }
            });

            ImageViewback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(UpdataPlanActivity.this, MainActivity.class);
                    UpdataPlanActivity.this.startActivity(intent);
                    finish();
                }
            });


            edtbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    m_spiModel.setEnabled(true);//不可编辑
                    editClockTime.setEnabled(true);//不可编辑
                    editTitle.setEnabled(true);//不可编辑
                    editContent.setEnabled(true);//不可编辑
                    editMessage.setEnabled(true);//可编辑
//                    editContent.setFocusable(true);//可编辑
//                    editTitle.setFocusable(true);//可编辑
//                    editClockTime.setFocusable(true);//可编辑

                }
            });

            updata_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    new AlertDialog.Builder(UpdataPlanActivity.this).setTitle("系统提示")//设置对话框标题

                            .setMessage("确认修改吗！")//设置显示的内容

                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮

                                @Override

                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                    // TODO Auto-generated method stub
                                    updatePlan();
                                  ;

                                }

                            }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮


                        @Override

                        public void onClick(DialogInterface dialog, int which) {//响应事件



                        }

                    }).show();//在按键响应事件中显示此对话框


                }
            });
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    new AlertDialog.Builder(UpdataPlanActivity.this).setTitle("系统提示")//设置对话框标题

                            .setMessage("确认删除吗！")//设置显示的内容

                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮

                                @Override

                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                    // TODO Auto-generated method stub
                                    deletePlan();
                                    finish();

                                }

                            }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮


                        @Override

                        public void onClick(DialogInterface dialog, int which) {//响应事件



                        }

                    }).show();//在按键响应事件中显示此对话框







                }
            });




            editClockTime.setOnFocusChangeListener(new android.view.View.
                    OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                        View view = View.inflate(getApplicationContext(), R.layout.date_time_picker, null);
                        final DatePicker datePicker = (DatePicker)view.findViewById(R.id.new_act_date_picker);
                        final TimePicker timePicker = (TimePicker)view.findViewById(R.id.new_act_time_picker);

                        // Init DatePicker
                        int year;


                        int month;
                        int day;

                        // Use the current date as the default date in the picker
                        final Calendar c = Calendar.getInstance();
                        year = c.get(Calendar.YEAR);
                        month = c.get(Calendar.MONTH);
                        day = c.get(Calendar.DAY_OF_MONTH);


                        datePicker.init(year, month, day, null);

                        // Init TimePicker
                        int hour;
                        int minute;

                        // Use the current time as the default values for the picker

                        hour = c.get(Calendar.HOUR_OF_DAY);
                        minute = c.get(Calendar.MINUTE);

                        timePicker.setIs24HourView(true);
                        timePicker.setCurrentHour(hour);
                        timePicker.setCurrentMinute(minute);

                        // Build DateTimeDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdataPlanActivity.this);
                        builder.setView(view);
                        builder.setTitle("请选择闹钟时间");
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrive_year = datePicker.getYear()+"";


                                arrive_month = datePicker.getMonth()+1+"";
                                if((datePicker.getMonth()+1)<10)
                                    arrive_month ="0"+arrive_month;
                                arrive_day = datePicker.getDayOfMonth()+"";
                                if((datePicker.getDayOfMonth()+1)<10)
                                    arrive_day ="0"+arrive_day;
//
                                arrive_hour = timePicker.getCurrentHour()+"";
                                arrive_min = timePicker.getCurrentMinute()+"";

                                editClockTime.setText(arrive_year+"-"+arrive_month+"-"+arrive_day+" "+arrive_hour+":"+arrive_min);
                            }
                        });
                        builder.show();

                    } else {
                        // 此处为失去焦点时的处理内容
                    }
                }
            });









        }

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }





    public PlanBean queryPlanByid(String pid) {

        StuDBHelper dbHelper = new StuDBHelper(UpdataPlanActivity.this,"stu_db",null,2);
//得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        PlanBean planbean=new PlanBean();
        String[] a={pid};

//        Cursor cursor = db.query("plan_table", new String[]{"planId","planTitle","planClockTime","planContent"},pid,null, null, null, null);
        Cursor cursor =  db.rawQuery("select * from plan_table where planId=?",a);

        while(cursor.moveToNext()){


            int id=cursor.getInt(cursor.getColumnIndex("planId"));
            String name = cursor.getString(cursor.getColumnIndex("planTitle"));
            String ClockTime = cursor.getString(cursor.getColumnIndex("planClockTime"));
            String message=cursor.getString(cursor.getColumnIndex("planMessage"));
            planbean.setPlanId(id);
            planbean.setPlanTitle(name);
            planbean.setPlanClockTime(ClockTime);
            planbean.setPlanMessage(message);
            editTitle.setText(name);
            editContent.setText(cursor.getString(cursor.getColumnIndex("planContent")));
            editClockTime.setText(ClockTime);
            editMessage.setText(message);
//            setSpinnerItemSelectedByValue(m_spiModel,cursor.getString(cursor.getColumnIndex("planClockTime")));

            ArrayAdapter<String> adapter;//数组 配置器 下拉菜单赋值用
//将可选内容与ArrayAdapter连接起来
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrModel);
//设置下拉列表的风格
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            m_spiModel.setAdapter(adapter);//将adapter 添加到spinner中

            m_spiModel.setSelection(options.indexOf(cursor.getString(cursor.getColumnIndex("planType"))),true);

            System.out.println("querygggg------->" + "planType："+options.indexOf(cursor.getString(cursor.getColumnIndex("planType")))+" ");
            System.out.println("querygggg------->" + "planType："+cursor.getString(cursor.getColumnIndex("planType"))+" ");
            System.out.println("querygggg------->" + "planContent："+name+" ");
            System.out.println("querygggg------->" + "planTitlea："+name+" ");

        }
//关闭数据库
        db.close();
        return planbean;
    }




    /**
     * 根据值, 设置spinner默认选中:

     */
//    public  void setSpinnerItemSelectedByValue(Spinner spinner,String value){
//        SpinnerAdapter apsAdapter= m_spiModel.getAdapter(); //得到SpinnerAdapter对象
//        int k= apsAdapter.getCount();
//        System.out.println("querygggg------->" + "plankkk："+k+" ");
//        for(int i=0;i<k;i++){
//            if(value.equals(m_spiModel.getItem(i).toString())){
//                m_spiModel.setSelection(i,true);// 默认选中项
//                break;
//            }
//        }
//    }


    public  void updatePlan(){

        StuDBHelper dbHelper = new StuDBHelper(UpdataPlanActivity.this,"stu_db",null,2);
        //得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        String name=editTitle.getText().toString();
        String content=editContent.getText().toString();
        String ClockTime=editClockTime.getText().toString();
        String planType= m_spiModel.getSelectedItem().toString();

        String sql;
        String edtmessg=editMessage.getText().toString();
        if(edtmessg.equals("")) {
             sql = "update [plan_table] set planTitle = '" + name + "',planType='" + planType +
                    "',planContent='" + content + "',planClockTime='" + ClockTime + "' " +
                    "where planId='" + pid + "'";//修改的SQL语句
        }
        else {
             sql = "update [plan_table] set planTitle = '" + name + "',planType='" + planType +
                    "',planContent='" + content + "',planClockTime='" + ClockTime + "' " +
                    " , planMessage='"+edtmessg+"' where planId='" + pid + "'";//修改的SQL语句
        }


        db.execSQL(sql);//执行修改



        Calendar calendar= Calendar.getInstance();
        //设置闹钟提醒
        calendar.setTimeInMillis(System.currentTimeMillis());
        //设置日历的小时和分钟
        calendar.set(Calendar.YEAR, Integer.valueOf(arrive_year).intValue());
        calendar.set(Calendar.MONTH, Integer.valueOf(arrive_month).intValue()-1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(arrive_day).intValue());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(arrive_hour).intValue());
        calendar.set(Calendar.MINUTE, Integer.valueOf(arrive_min).intValue());
        //将秒和毫秒设置为0
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //建立Intent和PendingIntent来调用闹钟管理器
        Intent pendingIntent = new Intent(UpdataPlanActivity.this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(UpdataPlanActivity.this, (int)calendar.getTimeInMillis(), pendingIntent, 0);
//            Alarmid++;
        //获取闹钟管理器
//            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//            //设置闹钟
//            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10*1000, pendingIntent);
        Toast.makeText(UpdataPlanActivity.this, "设置闹钟的时间为："+arrive_year+"-"+arrive_month+"-"+arrive_day+" "+arrive_hour+":"+arrive_min, Toast.LENGTH_SHORT).show();








        Intent intent = new Intent();
        intent.setClass(UpdataPlanActivity.this, MainActivity.class);
        UpdataPlanActivity.this.startActivity(intent);
    }


    public  void deletePlan(){

        StuDBHelper dbHelper = new StuDBHelper(UpdataPlanActivity.this,"stu_db",null,2);
        //得到一个可写的数据库
        SQLiteDatabase db =dbHelper.getReadableDatabase();

        String sql = "delete from  plan_table "+
                "where planId='"+pid+"'";//修改的SQL语句
        db.execSQL(sql);//执行修改
        Intent intent = new Intent();
        intent.setClass(UpdataPlanActivity.this, MainActivity.class);
        UpdataPlanActivity.this.startActivity(intent);
    }




}
