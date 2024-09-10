package com.example.sample.Utils

import android.app.DatePickerDialog
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    val DATE_PREFIX = "-"

    fun getMonthByNumber(monthnum:Int):String {
        val c = Calendar.getInstance()
        val month_date = SimpleDateFormat("MMMM")
        c[Calendar.MONTH] = monthnum-1
        return month_date.format(c.time)
    }

    fun getDateFromTwoDateRange(activity: FragmentActivity, textView: EditText, daysCount: Int, startDate: Long?= null) {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            activity,
            { view, year, monthOfYear, dayOfMonth ->
                textView.setText(
                    convertDateFormat(
                        dayOfMonth,
                        monthOfYear,
                        year,
                        DATE_PREFIX
                    )
                )
            },
            mYear,
            mMonth,
            mDay
        )
        if (startDate != null) {
            datePickerDialog.datePicker.minDate = startDate
        }
        else {
            datePickerDialog.datePicker.minDate = c.timeInMillis
            val date = Calendar.getInstance()
            date.add(Calendar.DATE, daysCount)
            datePickerDialog.datePicker.maxDate = date.timeInMillis
        }
        datePickerDialog.show()
    }

    fun convertDateFormat(
        dayOfMonth: Int,
        monthOfYear: Int,
        year: Int,
        format: String
    ): String {

        val month = monthOfYear + 1
        var day = "0"
        var monthStr = "0"
        day = if (dayOfMonth < 9) {
            day + "" + dayOfMonth
        } else {
            dayOfMonth.toString()
        }

        monthStr = if (month < 9) {
            monthStr + "" + month
        } else {
            month.toString()
        }
        //Log.d("agesss", "$day/$monthStr/$year")
        return "$day$format$monthStr$format$year"
    }

    fun plusOrMinusOneDay(textView: EditText, countText: EditText, daysCount: Int, format: String, minus: String?= null) : String {
        val timeMills = convertDateToMilles(textView.text.toString(), format)
        val date = Calendar.getInstance()
        date.timeInMillis = timeMills
        var count = countText.text.toString().toInt()
        if (minus != null) {
            count -= 1
            date.add(Calendar.DATE, -daysCount)
        }
        else {
            count += 1
            date.add(Calendar.DATE, daysCount)
        }
        countText.setText(count.toString())
        return convertMillesToDate(date.timeInMillis, format)
    }

    fun convertStringToDate(dtStart: String, formatStr: String): Date {
        val format = SimpleDateFormat(formatStr, Locale.ENGLISH)
        var date: Date? = null
        try {

            date = format.parse(dtStart)
            println(date)
            return date
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date!!
    }

    fun convertMillesToDate(milles: Long, dateFormat: String): String {
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val dateString = simpleDateFormat.format(milles)
        return String.format("%s", dateString)
    }

    fun convertDateToMilles(input: String?, input_foramt: String?): Long {
        //String givenDateString = "Tue Apr 23 16:08:28 GMT+05:30 2013";
        val sdf = SimpleDateFormat(input_foramt, Locale.ENGLISH)
        try {
            val mDate = sdf.parse(input)
            val timeInMilliseconds = mDate.time
            //println("Date in milli :: $timeInMilliseconds")
            return timeInMilliseconds
        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return 0
    }

    fun convertDate(input: String, inputFormat: String, outputFormat: String): String {
        var spf = SimpleDateFormat(inputFormat)
        val newDate = spf.parse(input)
        spf = SimpleDateFormat(outputFormat)
        return spf.format(newDate)
    }
}