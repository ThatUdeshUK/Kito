package com.anagramsoftware.kito.ui.identify

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anagramsoftware.kito.R
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Button
import com.anagramsoftware.kito.extentions.navigateToPredictionDetail
import java.io.File
import java.io.IOException


/**
 * Created by udesh on 2/10/18.
 */

class IdentifyFragment: Fragment() {

    private var mFilePhotoTaken: File? = null
    private var mUriPhotoTaken: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_identify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionOpenCamera = view.findViewById<FloatingActionButton>(R.id.action_open_camera)
        actionOpenCamera.setOnClickListener{
            Log.d(TAG, "actionOpenCamera")
            takePhoto()
        }

        val actionOpenGallery = view.findViewById<Button>(R.id.action_open_gallery)
        actionOpenGallery.setOnClickListener{
            selectFromGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult $requestCode ${mFilePhotoTaken?.path}")
        when (requestCode) {
            REQUEST_TAKE_PHOTO -> if (resultCode == RESULT_OK) {
                navigateToPredictionDetail(imageUri = mUriPhotoTaken)
            }
            REQUEST_SELECT_IMAGE_IN_ALBUM -> if (resultCode == RESULT_OK) {
                val imageUri: Uri? = if (data == null || data.data == null) {
                    mUriPhotoTaken
                } else {
                    data.data
                }
                navigateToPredictionDetail(imageUri = imageUri)
            }
        }
    }


    private fun takePhoto() {
        Log.d(TAG, "takePhoto")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity?.packageManager) != null) {
            // Save the photo taken to a temporary file.
            val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            try {
                mFilePhotoTaken = File.createTempFile(
                        "IMG_", /* prefix */
                        ".jpg", /* suffix */
                        storageDir      /* directory */
                )

                // Create the File where the photo should go
                // Continue only if the File was successfully created
                if (mFilePhotoTaken != null) {
                    mUriPhotoTaken = FileProvider.getUriForFile(context!!,
                            "com.anagramsoftware.kito.fileprovider",
                            mFilePhotoTaken!!)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken)

                    // Finally start camera activity
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO)
                }
            } catch (e: IOException) {

            }
        }
    }

    private fun selectFromGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, REQUEST_SELECT_IMAGE_IN_ALBUM)
    }

    companion object {
        private const val TAG = "IdentityFragment"

        private const val REQUEST_TAKE_PHOTO = 0
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 1

        fun create(): IdentifyFragment = IdentifyFragment()
    }

}