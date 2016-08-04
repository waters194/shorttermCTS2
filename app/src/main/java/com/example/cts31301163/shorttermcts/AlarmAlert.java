package com.example.cts31301163.shorttermcts;

/**
 * Created by Administrator on 2016/7/9.
 */
  import android.app.Activity;
          import android.app.AlertDialog;
          import android.content.DialogInterface;
          import android.os.Bundle;

public class AlarmAlert extends Activity
  {
           @Override
  protected void onCreate(Bundle savedInstanceState)
           {
             super.onCreate(savedInstanceState); /* 跳出的闹铃警示 */
             new AlertDialog.Builder(AlarmAlert.this)
                 .setTitle("闹钟响了!!").setMessage("您的日程安排到时间了!!").setPositiveButton(
                     "关掉他", new DialogInterface.OnClickListener()
                     {
                       public void onClick(DialogInterface dialog,
                           int whichButton)
                       { /* 关闭Activity */
                         finish();
                       }
                     }).show();
           }

  }
