package com.example.palliativecareapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.palliativecareapp.screens.signup.MysignupPart3Activity
import java.util.*

class SignupPart2 : AppCompatActivity() {
    lateinit var birthday : TextView
    lateinit var date : String
    lateinit var datePickerDialog : DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_part2)
        supportActionBar?.hide()
        var userId = intent.getIntExtra("id", 2)
        var userFirstName = intent.getStringExtra("firstName")
        var userMiddleName = intent.getStringExtra("middleName")
        var userLastName = intent.getStringExtra("lastName")
        var address = findViewById<EditText>(R.id.signupAddress)
        var phone = findViewById<EditText>(R.id.signupPhone)
        birthday = findViewById<TextView>(R.id.signupBirthday)
        var nextBtn = findViewById<Button>(R.id.nextButton2)

        //birthday.setText(getTodaysDate())
        date = getTodaysDate()
        initDatePicker()

        nextBtn.setOnClickListener {
            if (address.text.toString().isEmpty()) {
                address.error = "الرجاء تعبئة هذا الحقل"
                address.requestFocus()
            }else if (phone.text.toString().isEmpty()) {
                phone.error = "الرجاء تعبئة هذا الحقل"
                phone.requestFocus()
            }else {
                val i = Intent(this, SignupPart3::class.java)
                i.putExtra("id",userId)
                i.putExtra("firstName", userFirstName.toString())
                i.putExtra("middleName", userMiddleName.toString())
                i.putExtra("lastName", userLastName.toString())
                i.putExtra("address", address.text.toString())
                i.putExtra("phone", phone.text.toString())
                i.putExtra("birthday",date)
                startActivity(i)
            }
        }
    }

    private fun initDatePicker() {
        var dateSetListener = DatePickerDialog.OnDateSetListener(){ datePicker: DatePicker, year: Int, month: Int, day: Int ->
            val month = month + 1
            date = makeDateString(day, month, year)
            birthday.setText(date)
        }
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        val style: Int = AlertDialog.THEME_HOLO_LIGHT
         datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return "$day ${getMonthFormat(month).toString()} $year"
    }

    private fun getMonthFormat(month: Int): String? {
        if (month == 1) return "يناير"
        if (month == 2) return "فبراير"
        if (month == 3) return "مارس"
        if (month == 4) return "أبريل"
        if (month == 5) return "مايو"
        if (month == 6) return "يونيو"
        if (month == 7) return "يوليو"
        if (month == 8) return "اغسطس"
        if (month == 9) return "سبتمبر"
        if (month == 10) return "اكتوبر"
        if (month == 11) return "نوفمبر"
        if (month == 12) return "ديسمبر"
        else return "يناير"
        //default should never happen
    }

    private fun getTodaysDate(): String {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        var month = cal[Calendar.MONTH]
        month += 1
        val day = cal[Calendar.DAY_OF_MONTH]
        return makeDateString(day, month, year)
    }

    fun openDatePicker(view: View?) {
        datePickerDialog.show()
    }

}