package com.example.sample.Utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.sample.R
import com.google.android.material.snackbar.Snackbar

object MessageUtils {
    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun showCustomSnackBarWithCustomToast(view: View, message: String, context: FragmentActivity) {
        if (isKeyboardVisible(context)) {
            showCustomToast(context, message)
        } else {
            showCustomSnackBar(context, view, message)
        }
    }

    @SuppressLint("RestrictedApi")
    fun showCustomSnackBar(context: FragmentActivity, view: View, message: String) {
        val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
        val customSnackView: View =
            context.layoutInflater.inflate(R.layout.custom_snackbar_view, null)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val txtSnackMessage = customSnackView.findViewById<TextView>(R.id.txt_snack_message)
        txtSnackMessage.text = message
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setPadding(0, 0, 0, 0)
        snackBarLayout.addView(customSnackView, 0)
        snackBar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
            }
        })
        snackBar.show()
    }

    fun showCustomToast(context: FragmentActivity, message: String) {
        val inflater: LayoutInflater = context.layoutInflater
        val layout: View = inflater.inflate(R.layout.custom_snackbar_view, null)
        val toastTextView = layout.findViewById<TextView>(R.id.txt_snack_message)
        toastTextView.text = message
        val toast = Toast(context)
        toast.setView(layout)
        toast.show()
    }

    fun showDialog(activity: FragmentActivity?): Dialog? {
        val dialog = Dialog(activity!!)
        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val progressBar = ProgressBar(activity)
            progressBar.indeterminateDrawable = ContextCompat.getDrawable(activity, R.drawable.my_progress_indeterminate)
            ///dialog.getWindow().setWindowAnimations(R.style.grow);
            dialog.setContentView(progressBar)
            dialog.window!!.setBackgroundDrawableResource(R.color.trans)
            dialog.setCancelable(false)
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dialog
    }

    fun dismissDialog(dialog: Dialog?): Dialog? {
        try {
            if (dialog != null) {
                if (dialog.isShowing) {
                    dialog.dismiss()
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return dialog
    }

    fun isKeyboardVisible(context: FragmentActivity): Boolean {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.isAcceptingText()
    }

}