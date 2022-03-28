package com.example.alammanagerwithnotification

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity(),  TimePickerDialog.OnTimeSetListener {
    private lateinit var mButton_TimePicker : Button
    private lateinit var mTimePickerFragment: DialogFragment
    private lateinit var mTextView: TextView
    private lateinit var mButton_Cancel : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextView = findViewById(R.id.textView)

        mButton_TimePicker = findViewById(R.id.button_timepicker)
        mButton_TimePicker.setOnClickListener {
            mTimePickerFragment = TimePickerFragment()
            mTimePickerFragment.show(supportFragmentManager, "time picker")
        }
        mButton_Cancel = findViewById(R.id.button_cancel)
        mButton_Cancel.setOnClickListener { cancelAlarm() }
    }


    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        //알다시피 객체를 만들어야지 사용할 수 있는거지...그런데 내가 직접 클래스를 가지고 객체를 생성할 수도 있지만 보다시피..
        //현재 일어나고 있는 것들에대해서 즉 Context로부터 객체를 생성할 수도 있고.. 또..
        //내가 원하는 클래스의 정적 멤버를 통해서도 객체를 생성할 수 있다.. 그렇다면 왜 정적 멤버를 통해서 객체를 생성하도록 했을까??
        // 여기서 생성된 객체는 같은걸까? 그래서 정적함수로 객체를 생성하는 걸까?
        val c : Calendar = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, p1)
        c.set(Calendar.MINUTE, p2)
        c.set(Calendar.SECOND, 0)

        updateTimeText(c);
        startAlarm(c);
    }

    private fun updateTimeText(c: Calendar) {
        val timeText = "Alarm set for : ${DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)}"
        mTextView.text = timeText
    }
    private fun startAlarm(c: Calendar) {
        val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent : Intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent : PendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)

    }
    private fun cancelAlarm() {
        val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent : Intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent : PendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
        alarmManager.cancel(pendingIntent)
        mTextView.setText("Alarm Canceled")
    }
}