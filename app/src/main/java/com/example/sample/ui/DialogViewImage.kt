package com.example.sample.ui

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ScaleGestureDetector
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.sample.R
import com.example.sample.databinding.DialogViewImageBinding

class DialogViewImage(val imagePath: String): DialogFragment() {

    lateinit var binding: DialogViewImageBinding
    lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext(), R.style.CustomAlertDialog)
        //dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogViewImageBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        binding.txtClose.bringToFront()
        Glide.with(this)
            .load(imagePath)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Image loading failed
                    // Handle the failure here
                    return false // Return false if you want Glide to handle error placeholder
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Handler(Looper.getMainLooper()) .postDelayed({
                        val bitmap: Bitmap = binding.imageView.drawable.toBitmap()
                        Palette.from(bitmap).generate { palette ->
                            val dominantColor = palette?.dominantSwatch?.rgb ?: Color.TRANSPARENT
                            binding.root.setBackgroundColor(dominantColor)
                        }
                        //binding.imageView.visibility = View.VISIBLE
                    }, 0)
                    return false // Return false if you want Glide to set the image into the ImageView
                }
            })
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.imageView)
        //scaleGestureDetector = ScaleGestureDetector(requireContext(), ScaleListener())
        binding.txtClose.setOnClickListener {
            dialog.dismiss()
        }

        return dialog
    }

    /*override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(motionEvent)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = scaleFactor.coerceAtLeast(1.0f) // Prevent zooming out beyond original size
            scaleFactor = scaleFactor.coerceAtMost(10.0f)
            //scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            binding.imageView.scaleX = scaleFactor
            binding.imageView.scaleY = scaleFactor
            return true
        }
    }*/

}