package com.example.cts31301163.shorttermcts;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/6.
 */
public class AddDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button add_button = null;
    private Button can_button1 = null;
    static   int  Alarmid=0;
    private Spinner m_spiModel = null; //型号
    private String addtime;
    private EditText editTitle=null;
    private EditText editContent=null;
    private EditText editClockTime=null;
    private ImageView ImageViewback=null;
    private String  arrive_year = null;
    private String arrive_month = null;
    private String  arrive_day = null;
    private String  arrive_hour = null;
    private String  arrive_min = null;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String[] colorList = {"#7067E2","#FF618F","#B674D2","#00C2EB"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addplan_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        m_spiModel = (Spinner) findViewById(R.id.spinner);//产品型号
        editTitle=(EditText) findViewById(R.id.editTexttitle);
        editContent=(EditText) findViewById(R.id.editText4);
        editClockTime=(EditText) findViewById(R.id.editTexttime);
//        editClockTime.clearFocus();

        String strModel_init="学习计划,工作计划,会议安排,私人安排";
        String[] arrModel =strModel_init.split(",");//转换 数组

        ArrayAdapter<String> adapter;//数组 配置器 下拉菜单赋值用
//将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrModel);
//设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m_spiModel.setAdapter(adapter);//将adapter 添加到spinner中
//        m_spiModel.setVisibility(View.VISIBLE);//设置默认值


        add_button=(Button) findViewById(R.id.addbutton);
        can_button1=(Button) findViewById(R.id.can_button1);
        ImageViewback=(ImageView) findViewById(R.id.imageView2);
        add_button.setOnClickListener(new addListener());
//
//
//        add_button.setBackgroundColor(Color.parseColor(colorList[3]));
//        can_button1.setBackgroundColor(Color.parseColor(colorList[3]));
//        add_button.setTextColor(Color.parseColor("#FFFFFF"));
//        can_button1.setTextColor(Color.parseColor("#FFFFFF"));


        can_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddDetailActivity.this, MainActivity.class);
                AddDetailActivity.this.startActivity(intent);
                finish();
            }
        });


        ImageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddDetailActivity.this, MainActivity.class);
                AddDetailActivity.this.startActivity(intent);
                finish();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddDetailActivity.this);
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
                        Calendar c = Calendar.getInstance();
                       int nowyear = c.get(Calendar.YEAR);
                        int nowmonth = c.get(Calendar.MONTH);
                        int nowday = c.get(Calendar.DAY_OF_MONTH);
                        int iintyear=Integer.valueOf(arrive_year).intValue();
                        int iintmonth=Integer.valueOf(arrive_month).intValue();
                        int iintday=Integer.valueOf(arrive_day).intValue();


                        if(iintyear>nowyear){ //设置年大于当前年，直接设置，不用判断下面的

                        }else if(nowyear == iintyear){   //设置年等于当前年，则向下开始判断月
                            if(iintmonth > nowmonth){ //设置月等于当前月，直接设置，不用判断下面的

                            }else if(iintmonth == nowmonth){     //设置月等于当前月，则向下开始判断日
                                if(iintday > nowday){          //设置日大于当前月，直接设置，不用判断下面的

                                }else if(iintday == nowday){  //设置日等于当前日，则向下开始判断时

                                }else{     //设置日小于当前日，提示重新设置
                                    Toast.makeText(AddDetailActivity.this, "当前日不能小于今日，请重新设置", Toast.LENGTH_SHORT).show();

                                }
                            }else{         //设置月小于当前月，提示重新设置
                                Toast.makeText(AddDetailActivity.this, "当前月不能小于今月，请重新设置", Toast.LENGTH_SHORT).show();

                            }
                        }else{             //设置年小于当前年，提示重新设置

                            Toast.makeText(AddDetailActivity.this, "当前年不能小于今年，请重新设置", Toast.LENGTH_SHORT).show();
                        }


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


    class addListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            String Content=editContent.getText().toString();
            String Title=editTitle.getText().toString();
            String ClockTime=editClockTime.getText().toString();
            if(Content.equals("")||Title.equals("")||ClockTime.equals("")) {
                dialog();
                return;
            }
            StuDBHelper dbHelper = new StuDBHelper(AddDetailActivity.this,"stu_db",null,2);
            SQLiteDatabase db =dbHelper.getReadableDatabase();
            PlanBean planBean = new PlanBean();
//往ContentValues对象存放数据，键-值对模式
            ContentValues cv = new ContentValues();

            cv.put("planContent", editContent.getText().toString());
            cv.put("planTitle",editTitle.getText().toString());
            cv.put("planType",m_spiModel.getSelectedItem().toString());
           //设置日期格式
            addtime=df.format(new Date());
            System.out.println(addtime);// new Date()为获取当前系统时间
            cv.put("planAddTime", addtime);
            cv.put("planClockTime",editClockTime.getText().toString());
//调用insert方法，将数据插入数据库
            db.insert("plan_table", null, cv);

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
            Intent pendingIntent = new Intent(AddDetailActivity.this, AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(AddDetailActivity.this, (int)calendar.getTimeInMillis(), pendingIntent, 0);
//            Alarmid++;
            //获取闹钟管理器
//            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//            //设置闹钟
//            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10*1000, pendingIntent);
            Toast.makeText(AddDetailActivity.this, "设置闹钟的时间为："+arrive_year+"-"+arrive_month+"-"+arrive_day+" "+arrive_hour+":"+arrive_min, Toast.LENGTH_SHORT).show();


            Intent intent1 = new Intent();
            intent1.setClass(AddDetailActivity.this, MainActivity.class);
            AddDetailActivity.this.startActivity(intent1);

        }





        protected void dialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddDetailActivity.this);
            builder.setMessage("请填写完整！");

            builder.setTitle("提示");

            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });



            builder.create().show();
        }
    }


















}