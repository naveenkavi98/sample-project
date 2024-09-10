package com.example.sample.Utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.sample.R
import com.example.sample.ui.selectImage.Media
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.abs


object Utils {
    fun toRequestBody(data: String): RequestBody {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), data)
    }

    fun toMultipartBodyPart(imageFile: File, key: String): MultipartBody.Part {
        val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),
            imageFile
        )
        return MultipartBody.Part.createFormData(key, imageFile.name, requestFile)
    }

    fun <S> navigateActivity(fromActivity: FragmentActivity, toActivity: Class<S>) {
        fromActivity.startActivity(Intent(fromActivity, toActivity))
        fromActivity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }

    fun hideKeyboard(context: FragmentActivity, view: View) {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideSoftKeyboard(context: FragmentActivity) {
        try {
            val inputMethodManager =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (context.currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(
                    context.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showSoftKeyboard(activity: Activity, editText: View) {
        try {

            if (editText.requestFocus()) {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showSoftKey(view: View, appCompatActivity: FragmentActivity) {
        if (view.requestFocus()) {
            val imm =
                appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun openYoutubeLink(activity: FragmentActivity, url: String) {
        val splitUrl = url.split("=")
        val youtubeID = splitUrl[1]
        Log.e( "openYoutubeLink: ", youtubeID)
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID))
        try {
            activity.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            activity.startActivity(intentBrowser)
        }

    }

    fun glideImage(context: FragmentActivity, imageUrl: String, imageView: ImageView)  {
        Glide.with(context)
            .load(imageUrl)
            .into(imageView)
    }

    fun capitalize(capString: String): String {
        val capBuffer = StringBuffer();
        val capMatcher: Matcher =
            Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(
                capBuffer,
                capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase()
            );
        }
        return capMatcher.appendTail(capBuffer).toString();
    }

    fun changeBackground(position: Int, view: View, activity: Context) {
        if (position % 2 == 0) {
            view.setBackgroundColor(activity.getColor(R.color.white))
        } else {
            view.setBackgroundColor(activity.getColor(R.color.black))
        }
    }

    fun showHidePassword(isPasswordVisible: Boolean, editText: EditText, imageView: ImageView) {
        if (isPasswordVisible) {
            editText.transformationMethod = PasswordTransformationMethod()
            imageView.setImageResource(R.drawable.eye)
        } else {
            editText.transformationMethod = null
            imageView.setImageResource(R.drawable.eye_cross)
        }
        editText.setSelection(editText.text.length)
    }

    fun requestPermission(context: FragmentActivity, permissions: Array<String>, permissionCode: Int) {
        ActivityCompat.requestPermissions(
            context, permissions,
            permissionCode
        )
    }

    fun isLocationEnabled(context: FragmentActivity): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    fun getRealPathFromURI(context: Context, imageUri: Uri): String? {
        var cursor: Cursor? = null
        try {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(imageUri, projection, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    fun timeDifference(context: Context, time: String): String {
        val simpleDateFormat = SimpleDateFormat(AppConstants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH)
        val date1 = simpleDateFormat.parse(time)?.time
        val date2 = System.currentTimeMillis()
        val duration = abs(date2 - date1!!)
        val days = TimeUnit.MILLISECONDS.toDays(duration)
        val hours = TimeUnit.MILLISECONDS.toHours(duration)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration)

        if (days > 1) {
            return days.toString() + String.format("%s", "d")
        }
        else if (days > 0) {
            return days.toString() + String.format("%s", "d")
        }
        else if (hours > 1) {
            return hours.toString() + String.format("%s", "h")
        }
        else if (hours > 0) {
            return hours.toString() + String.format("%s", "h")
        }
        else if (minutes > 1) {
            return minutes.toString() + String.format("%s", "m")
        } else if (minutes > 0) {
            return minutes.toString() + String.format("%s", "m")
        } else if (seconds < 60) {
            return "now"
        } else {
            return "mo"
        }
    }

    fun timeDifferenceFullForm(context: Context, time: String): String {
        val simpleDateFormat = SimpleDateFormat(AppConstants.YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH)
        val date1 = simpleDateFormat.parse(time)?.time
        val date2 = System.currentTimeMillis()
        val duration = abs(date2 - date1!!)
        val days = TimeUnit.MILLISECONDS.toDays(duration)
        val hours = TimeUnit.MILLISECONDS.toHours(duration)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration)

        if (days > 1) {
            return days.toString() + String.format("%s", "days ago")
        }
        else if (days > 0) {
            return days.toString() + String.format("%s", "day ago")
        }
        else if (hours > 1) {
            return hours.toString() + String.format("%s", "hours ago")
        }
        else if (hours > 0) {
            return hours.toString() + String.format("%s", "hour ago")
        }
        else if (minutes > 1) {
            return minutes.toString() + String.format("%s", "months ago")
        } else if (minutes > 0) {
            return minutes.toString() + String.format("%s", "month ago")
        } else if (seconds < 60) {
            return "now"
        } else {
            return "year"
        }
    }

    fun createImageFile(context: FragmentActivity): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        var destination =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .toString() + "/"
        destination += timeStamp
        return File(destination)
    }

    fun createTempImageFile(context: FragmentActivity): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DCIM)
        return File.createTempFile("img_${timeStamp}_", ".jpg", storageDir)
    }

    fun getBitmapFromViewUsingCanvas(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun createViewToBitmap(context: FragmentActivity, view: View): File {
        val imageFile: File = createTempImageFile(context)
        val bitmap = getBitmapFromViewUsingCanvas(view)
        val os = FileOutputStream(imageFile)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.flush();
        os.close();
        return imageFile
    }

    fun changeVectorDrawableColors(
        context: Context,
        draw: Drawable?,
        view: View,
        color: Int
    ) {
        val porterDuffColorFilter = PorterDuffColorFilter(
            color,
            PorterDuff.Mode.SRC_ATOP
        )
        draw!!.colorFilter = porterDuffColorFilter
        view.background = draw

    }

    fun compressAndSetImage(context: FragmentActivity, filePath: String): File {
        val bitmap = BitmapFactory.decodeFile(filePath)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        val compressedBitmap = BitmapFactory.decodeStream(ByteArrayInputStream(outputStream.toByteArray()))
        val imageFile: File = createTempImageFile(context)
        val os = FileOutputStream(imageFile)
        compressedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.flush()
        os.close()
        return imageFile
    }

    fun setMultiView(context: FragmentActivity, uriList: ArrayList<Media>): GridLayoutManager {
        val layoutManager : GridLayoutManager
        when (uriList.size) {
            1 ->{
                layoutManager = GridLayoutManager(context, 1)
            }
            2 ->{
                layoutManager = GridLayoutManager(context, 2)
            }
            3 ->{
                layoutManager = GridLayoutManager(context, 3)
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {
                            0 -> 2
                            1 -> 1
                            else -> 3
                        }
                    }
                }
            }
            else -> {
                layoutManager = GridLayoutManager(context, 3)
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (position) {
                            0 -> 3
                            else -> 1
                        }
                    }
                }
            }
        }
        return layoutManager
    }

    fun getCurrentDate(fromat: String): String {
        val simpleDateFormat = SimpleDateFormat(fromat)
        return simpleDateFormat.format(Date())
    }

    fun convertMillsToDate(mills: Long, format: String): CharSequence? {
        val formatter = SimpleDateFormat(format)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = mills
        return  formatter.format(calendar.time)
    }


    fun saveLogin(context: FragmentActivity, phone: String, password: String, userData: String) {
        SharedPreferenceManager.saveBooleanPreferences(context, AppConstants.LOGIN_FLAG, true)
        val credentials =  Credentials.basic(phone, password)
        SharedPreferenceManager.saveStringPreferences(context, AppConstants.AUTH_TOKEN, credentials)
        SharedPreferenceManager.saveStringPreferences(context, AppConstants.USER_DATA, userData)
    }

    fun logOut(context: FragmentActivity, loginActivity: FragmentActivity) {
        SharedPreferenceManager.clearPreference(context)
        context.finish()
        val intent = Intent(context, loginActivity::class.java)
        context.startActivity(intent)
    }

    fun getAddress(context: Context, lat: Double, lng: Double): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>? = geocoder.getFromLocation(lat, lng, 1)
        val obj: Address = addresses!![0]
        var add: String = obj.getAddressLine(0)
        add = """
            $add
            ${obj.countryName}
            """.trimIndent()
        add = """
            $add
            ${obj.countryCode}
            """.trimIndent()
        add = """
            $add
            ${obj.adminArea}
            """.trimIndent()
        add = """
            $add
            ${obj.postalCode}
            """.trimIndent()
        add = """
            $add
            ${obj.subAdminArea}
            """.trimIndent()
        add = """
            $add
            ${obj.locality}
            """.trimIndent()
        add = """
            $add
            ${obj.subThoroughfare}
            """.trimIndent()
        Log.v("IGAwert", "Address$add")
        return obj
    }

    fun getOutputDirectory(context: Context, type: String): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            when (type) {
                AppConstants.IMAGE -> {
                    File(it, context.resources.getString(R.string.app_name)  + "/Image").apply {
                        mkdirs()
                    }
                }
                AppConstants.VIDEO -> {
                    File(it, context.resources.getString(R.string.app_name)  + "/Video").apply {
                        mkdirs()
                    }
                }
                AppConstants.AUDIO -> {
                    File(it, context.resources.getString(R.string.app_name)  + "/Audio").apply {
                        mkdirs()
                    }
                }
                AppConstants.AUDIO -> {
                    File(it, context.resources.getString(R.string.app_name)  + "/Download").apply {
                        mkdirs()
                    }
                }
                else -> {
                    File(it, context.resources.getString(R.string.app_name)).apply {
                        mkdirs()
                    }
                }
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir
    }

    fun getMimeType(type: String): String {
        var mime = ""
        when (type) {
            AppConstants.IMAGE -> {
                mime = "image/*"
            }
            AppConstants.VIDEO -> {
                mime = "video/*"
            }
            AppConstants.AUDIO -> {
                mime = "audio/*"
            }
        }
        return mime
    }

    fun setLanguage(context: FragmentActivity) {
        var language: String = SharedPreferenceManager.getStringPreferences(context, AppConstants.LANGUAGE)
        if (language.isEmpty()) {
            language = "en"
        }
        NumberFormat.getInstance(Locale.forLanguageTag(language))
        val myLocale = Locale(language)
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
    }

    fun createFolder(context: FragmentActivity) {
        var directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + context.getString(R.string.app_name)
        if (!File(directory).exists()) {
            File(directory).mkdir()
        }
    }
    fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun getSelectedLanguage(context: Context): String {
        return SharedPreferenceManager.getStringPreferences(context, AppConstants.LANGUAGE).toString()
    }

    fun openChrome(context: FragmentActivity, url: String) {
        val builder = CustomTabsIntent.Builder()
        val params = CustomTabColorSchemeParams.Builder()
        //params.setToolbarColor(ContextCompat.getColor(this@DashboardActivity, R.color.colorPrimary))
        builder.setDefaultColorSchemeParams(params.build())
        builder.setShowTitle(true)
        builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)
        builder.setInstantAppsEnabled(true)
        //  builder.setStartAnimations(this, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
        //  builder.setExitAnimations(this, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
        val customBuilder = builder.build()
        customBuilder.intent.setPackage(AppConstants.CHROME_PACKAGE)
        customBuilder.launchUrl(context, Uri.parse(url))

        /*if (isPackageInstalled(AppConstants.CHROME_PACKGE)) {
            // if chrome is available use chrome custom tabs

        } else {
            // if not available use WebView to launch the url
            Toast.makeText(this@DashboardActivity,"no", Toast.LENGTH_SHORT).show()
        }*/
    }

    fun openIntent(context: FragmentActivity, uri: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(uri)
        )
        val chooseIntent = Intent.createChooser(intent, "Choose from below")
        context.startActivity(chooseIntent)
    }

    fun getHighlightedText(context: Context, data: String, highlightText: String): Spannable? {
        val spanString =
            Spannable.Factory.getInstance().newSpannable(data)
        val startPos: Int = data.indexOf(highlightText, ignoreCase = true)
        val endPos: Int = startPos + highlightText.length
        if (startPos != -1) {
            spanString.setSpan(
                ForegroundColorSpan(context.getColor(R.color.primary_color)),
                startPos,
                endPos,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spanString
    }

    fun getHighlightStringWithClick(
        context: FragmentActivity,
        textView: TextView,
        text: String,
        clickableStrings: MutableList<String>
    ) {
        val fullText = Html.fromHtml(text)
        // Create a SpannableStringBuilder from the full text
        val spannableStringBuilder = SpannableStringBuilder(fullText)

        // Set movement method to make the TextView clickable
        textView.movementMethod = LinkMovementMethod.getInstance()
        val green = ForegroundColorSpan(Color.GREEN)

        // Iterate through each clickable string and apply ClickableSpan to it
        clickableStrings.forEach { clickableString ->
            // Find the start index of the clickable string
            var startIndex = fullText.indexOf(clickableString)

            // Add ClickableSpan to all occurrences of the string in the text
            while (startIndex != -1) {
                val endIndex = startIndex + clickableString.length

                // Apply ClickableSpan to the clickable string
                spannableStringBuilder.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            // Handle click action here
                            // For example, you can open a new activity or perform any other action
                            // You can differentiate between different clickable strings using `clickableString`
                            // For demonstration, we'll just show a toast message
                            // Replace this with your desired action
                        }
                    },
                    startIndex,
                    endIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                /*spannableStringBuilder.setSpan(
                    ForegroundColorSpan(context.getColor(R.color.toolBar)),
                    startIndex,
                    endIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )*/
                // Find the next occurrence of the clickable string
                startIndex = fullText.indexOf(clickableString, endIndex)
            }
        }

        // Set the SpannableStringBuilder to the TextView
        textView.text = spannableStringBuilder

    }

    fun checkDataVisibility (
        context: FragmentActivity,
        textView: TextView,
        valueTextView: TextView,
        text: String,
        clickableStrings: MutableList<String>
    ) {
        if (text.isNullOrEmpty()) {
            textView.visibility = View.GONE
            valueTextView.visibility = View.GONE
        }
        else {
            textView.visibility = View.VISIBLE
            valueTextView.visibility = View.VISIBLE
            getHighlightStringWithClick(context, valueTextView, text, clickableStrings)
        }
    }

    fun isLatLngValid(latitude: Double, longitude: Double): Boolean {
        return latitude in -90.0..90.0 && longitude in -180.0..180.0
    }

    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun getDistance(
        startLat: Double,
        startLang: Double,
        endLat: Double,
        endLang: Double
    ): Float {
        val locStart = Location("")
        locStart.latitude = startLat
        locStart.longitude = startLang
        val locEnd = Location("")
        locEnd.latitude = endLat
        locEnd.longitude = endLang
        return locStart.distanceTo(locEnd)
    }

    fun findNearestMarkerPosition(markerList: ArrayList<Marker>, latitude: Double, longitude: Double): Int {
        val EARTH_RADIUS = 6371
        var nearestDistance = EARTH_RADIUS
        var nearestPosition = markerList.size + 1
        repeat(markerList.size) {
            val distance = Utils.getDistance(latitude, longitude,
                markerList[it].position.latitude, markerList[it].position.longitude
            )
            if (distance < nearestDistance) {
                nearestPosition = it
                nearestDistance = distance.toInt()
            }
        }
        return nearestPosition
    }

    fun roundOffDecimal(data: String, format: String): Double {
        var number = 0.0
        if (data.isNotEmpty()) {
            number = data.toDouble()
        }
        //format  example "#.##"
        val df = DecimalFormat(format)
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }

    private fun ViewPager.autoScroll(interval: Long) {

        val handler = Handler()
        var scrollPosition = 0
        val runnable = object : Runnable {
            override fun run() {
                val count = adapter?.count ?: 0
                setCurrentItem(scrollPosition++ % count, true)
                handler.postDelayed(this, interval)
            }
        }
        addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                scrollPosition = position + 1
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })
        handler.post(runnable)
    }

    fun marquee(farmerName: TextView) {
        farmerName.setSingleLine()
        farmerName.ellipsize = TextUtils.TruncateAt.MARQUEE
        farmerName.isSelected = true
        farmerName.marqueeRepeatLimit = -1
    }

    fun showDatePicker(context: FragmentActivity, textView: TextView) {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(context,
            R.style.DatePickerTheme, { view, year, month, dayOfMonth ->
                val d = dayOfMonth.toString()
                var m = (month + 1).toString()
                if (m.length == 1) {
                    m = "0$m"
                }
                val y = year.toString()
                textView.text = String.format("%s/%s/%s", d, m, y)
            }, year, month, dayOfMonth)
        dialog.datePicker.minDate = calendar.timeInMillis
        dialog.show()
    }

    fun initDatePicker(listener: com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener,daysavailable: ArrayList<String>, daysblocked: ArrayList<String>) {
        val now = Calendar.getInstance()
        val dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
            listener,
            now[Calendar.YEAR],
            now[Calendar.MONTH],
            now[Calendar.DAY_OF_MONTH]
        )
        val calendar = Calendar.getInstance()
        dpd.minDate = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 90)
        dpd.maxDate = calendar
        dpd.setStyle(R.style.DatePickerTheme, R.style.DatePickerTheme)
        val tuesday: List<String> = getTuesdaysFromCalendar(calendar)
        daysblocked.addAll(tuesday)
        daysavailable.forEach {
            daysblocked.remove(it)
        }
        val blockedDates: MutableList<Calendar> = ArrayList()
        daysblocked.forEach {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            sdf.parse(it)?.let { date->
                val cal = dateToCalendar(date)
                blockedDates.add(cal)
            }
        }
        val blockedDatesCalendar = blockedDates.toTypedArray<Calendar>()
        dpd.setDisabledDays(blockedDatesCalendar)
    }

    private fun getTuesdaysFromCalendar(calendar: Calendar): List<String> {
        val tuesdays = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateCalendar = Calendar.getInstance()
        //c.set(Calendar.DAY_OF_MONTH, 1)
        while (dateCalendar.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)+ 1) {
            if (dateCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                tuesdays.add(dateFormat.format(dateCalendar.time))
            }
            dateCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return tuesdays
    }

    private fun dateToCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance()
        calendar.setTime(date)
        return calendar
    }

    fun jsonToHashMap(jsonString: String): HashMap<String, Any> {
        val gson = Gson()
        val type = object : TypeToken<HashMap<String, Any>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    fun isSpecificTimeEarlier(hour: Int, minute: Int): Boolean {
        val currentTime = Calendar.getInstance()
        val timeToMatch = Calendar.getInstance()
        timeToMatch[Calendar.HOUR_OF_DAY] = hour
        timeToMatch[Calendar.MINUTE] = minute
        when {
            currentTime == timeToMatch -> {
                Log.e( "initDatePicker: ", "==")
            }
            currentTime < timeToMatch -> {
                Log.e( "initDatePicker: ", "<")
            }
            currentTime > timeToMatch -> {
                Log.e( "initDatePicker: ", ">")
                return true
            }
        }
        return false
    }

}