package com.example.sample.ui.selectImage

import android.Manifest
import android.app.AlertDialog.*
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.sample.R
import com.example.sample.Utils.AppConstants
import com.example.sample.databinding.BottomSheetSelectImageBinding

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectImageBottomSheetDialogFragment(val mediaList: ArrayList<Media>, val multiSelection: Boolean) : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetSelectImageBinding
    lateinit var galleryList: ArrayList<Media>
    private var onItemClicked: OnItemClicked? = null
    private var startPosition = 0
    val READ_MEDIA_IMAGES = Manifest.permission.READ_MEDIA_IMAGES
    val READ_MEDIA_VISUAL_USER_SELECTED = Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
    val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetSelectImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            && (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED)
        ) {
            // Full access on Android 13 (API level 33) or higher
            askPermissionCheck(true)
        }
        else if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
            ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) == PackageManager.PERMISSION_GRANTED
        ) {
            // Partial access on Android 14 (API level 34) or higher
            askPermissionCheck(true)
        }
        else if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            // Full access up to Android 12 (API level 32)
            askPermissionCheck(true)
        } else {
            askPermissionCheck(false)
        }
    }

    fun initView() {
        isCancelable =false
        val parent = view?.parent as View
        val behavior = BottomSheetBehavior.from(parent)
        val layoutParams = parent.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        parent.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
        binding.btnAdd.setOnClickListener {
            val selectedGalleryList = galleryList.filter { it.selected }
            var finalGalleryList = ArrayList<Media>()
            if (startPosition == 1){
                finalGalleryList.add(mediaList[0])
            }
            finalGalleryList.addAll(selectedGalleryList.sortedBy { it.itemCount })
            onItemClicked?.itemMultiImageClicked(ArrayList(finalGalleryList))
            dismiss()
        }
        binding.btnAllowAccess.setOnClickListener {
            requestPermission()
        }
        binding.txtClose.setOnClickListener {
            dismiss()
        }
    }

    fun askPermissionCheck(allowed: Boolean) {
        if (allowed) {
            binding.lnrPhotoPermission.visibility = View.GONE
            binding.rcvImages.visibility = View.VISIBLE
            binding.btnAdd.visibility = View.VISIBLE
            setImageList()
        }
        else {
            binding.lnrPhotoPermission.visibility = View.VISIBLE
            binding.rcvImages.visibility = View.GONE
            binding.btnAdd.visibility = View.GONE
        }
    }
    fun setImageList() {
        if (multiSelection){
            binding.btnAdd.visibility = View.VISIBLE
        }
        else {
            binding.btnAdd.visibility = View.GONE
        }
        startPosition = 0
        mediaList.forEach { mediaListItem ->
            if (!mediaListItem.isFromGallery) {
                startPosition = 1
            }
        }
        galleryList = getAllImages()
        if (mediaList.isNotEmpty()){
            mediaList.forEach { mediaListItem->
                if (mediaListItem.isFromGallery) {
                    galleryList.forEach { galleryListItem->
                        if (mediaListItem.path == galleryListItem.path) {
                            galleryListItem.selected = mediaListItem.selected
                            galleryListItem.itemCount = mediaListItem.itemCount
                        }
                        galleryListItem.selectedCount = mediaListItem.selectedCount
                    }
                }
            }
        }

        val selectImageAdapter = SelectImageAdapter(galleryList, multiSelection)
        selectImageAdapter.setOnItemClicked(object : SelectImageAdapter.OnItemClicked{
            override fun itemClicked(data: Media, position: Int) {
                if (multiSelection){
                    if (data.selectedCount < AppConstants.IMAGE_SIZE_TO_SELECT || data.selected) {
                        for (i in 0 until galleryList.size) {
                            if (i == position) {
                                if (data.selected) {
                                    galleryList[position].selected = false
                                    for (j in 0 until galleryList.size) {
                                        if (galleryList[j].selected) {
                                            if (galleryList[j].itemCount > data.itemCount) {
                                                galleryList[j].itemCount -= 1
                                            }
                                        }
                                        galleryList[j].selectedCount -= 1
                                    }
                                }
                                else {
                                    galleryList[position].selected = true
                                    galleryList[position].itemCount =  galleryList[position].selectedCount + 1
                                    galleryList.forEach {
                                        it.selectedCount = galleryList[position].itemCount
                                    }
                                }
                            }
                        }
                        selectImageAdapter.notifyDataSetChanged()
                    }
                    else {
                        Toast.makeText(requireContext(), "Only ${AppConstants.IMAGE_SIZE_TO_SELECT} images allowed to select`", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    onItemClicked?.itemSingleImageClicked(data)
                }
            }
        })
        binding.rcvImages.adapter = selectImageAdapter
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(READ_MEDIA_IMAGES)
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissionLauncher.launch(READ_MEDIA_VISUAL_USER_SELECTED)
        }
        else {
            permissionLauncher.launch(READ_EXTERNAL_STORAGE)
        }
    }

    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted){
            askPermissionCheck(true)
            setImageList()
        }else{
            dismiss()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                showPermissionDialog("Photos And Videos")
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                showPermissionDialog("Photos And Videos")
            }
            else {
                showPermissionDialog("Storage")
            }
        }
    }

    private fun getAllImages() : ArrayList<Media> {
        val galleryList = ArrayList<Media>()

        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media._ID)

        val cursor = requireActivity().contentResolver.query(allImageUri, projection, null, null,"${MediaStore.Images.Media.DATE_ADDED} DESC")
        try {
            cursor!!.moveToFirst()
            do {
                val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                galleryList.add(Media( path, name, 0, "image/jpeg", false, 0, startPosition, true))
            }
            while (cursor.moveToNext())
        }
        catch (e: Exception){
            Log.e( "getGalleryImages: ", e.printStackTrace().toString())
        }
        return galleryList
    }

    fun setOnItemClicked(listener: OnItemClicked) {
        onItemClicked = listener
    }

    interface OnItemClicked {
        fun itemMultiImageClicked(finalMediaList: ArrayList<Media>)
        fun itemSingleImageClicked(finalMedia: Media)
    }

    private fun showPermissionDialog(type: String) {
        val dialog = Dialog(requireContext(), R.style.CustomAlertDialog)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window?.setLayout(width, height)
        dialog.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(resources.getColor(R.color.white, null)), 50, 0, 50, 0))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_permission)

        val txtDialogTitle = dialog.findViewById<TextView>(R.id.txt_dialog_title)
        val txtDialogDescription = dialog.findViewById<TextView>(R.id.txt_dialog_description)
        val txtCloseApp = dialog.findViewById<TextView>(R.id.txt_close_app)
        val txtOk = dialog.findViewById<TextView>(R.id.txt_ok)
        txtDialogTitle.text =  String.format("Permission Needed", type)
        txtDialogDescription.text =  String.format("To access this feature we need permission please allow %s access to app from app settings", type)
        txtCloseApp.text = "Cloes"
        txtCloseApp.setOnClickListener {
            dialog.dismiss()
        }
        txtOk.setOnClickListener {
            dialog.dismiss()
            val intent = Intent()
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireActivity().packageName, null)
            intent.setData(uri)
            startActivity(intent)
        }

        dialog.show()
    }

}

data class Media(
    var path: String,
    var name: String,
    var size: Long,
    var mimeType: String,
    var selected: Boolean,
    var itemCount: Int,
    var selectedCount: Int,
    var isFromGallery: Boolean
)
