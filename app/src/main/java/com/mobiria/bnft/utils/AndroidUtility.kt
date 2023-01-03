package com.mobiria.bnft.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.res.ColorStateList
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.ClipboardManager
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.mobiria.bnft.R
import com.mobiria.bnft.base.BaseActivity
import java.io.File

class AndroidUtility {
    companion object {
        fun isAndroid5(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        }

        // Un Selected
        fun setImageTintUnCheck(view: ImageView, mActivity: BaseActivity){
            ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(
                ContextCompat.getColor(mActivity, R.color.unselected)))
        }
        fun textColorUnselected(textView: TextView, mActivity: BaseActivity) {
            textView.setTextColor(mActivity?.resources.getColor(R.color.unselected))
        }

        // Selected Color
        fun setImageTintChecked(view: ImageView, mActivity: BaseActivity){
            ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(
                ContextCompat.getColor(mActivity, R.color.white)))
        }

        fun textColorWhite(textView: TextView, mActivity: BaseActivity) {
            textView.setTextColor(mActivity?.resources.getColor(R.color.white))
        }



        fun setClipboard(context: BaseActivity, text: String) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.text = text
            } else {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = ClipData.newPlainText("Copied", text)
                context.myToast("Copied")
                clipboard.setPrimaryClip(clip)
            }
        }



        fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
            var cursor: Cursor? = null
            try {
                if (contentUri.path != null && File(contentUri.path).exists()) {
                    return contentUri.path
                }
                if (contentUri.lastPathSegment != null && File(contentUri.lastPathSegment).exists()) {
                    return contentUri.lastPathSegment
                }
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(contentUri, null, null, null, null)
                val column_index = cursor!!.getColumnIndex(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                if (column_index == -1) {
                    try {
                        val path: String? = AndroidUtility.getPhoneGalleryImages(
                            context, cursor.getString(
                                cursor.getColumnIndex("document_id")
                            ).substring("image:".length)
                        )
                        if (path != null) {
                            return path
                        }
                    } catch (e: java.lang.Exception) {
                    }
                }
                return cursor.getString(column_index)
            } catch (e: java.lang.Exception) {
                Log.e("ex", "", e)
            } finally {
                cursor?.close()
            }
            return null
        }

        @SuppressLint("Range")
        fun getPhoneGalleryImages(context: Context, imageId: String): String? {
            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                MediaStore.Images.Thumbnails._ID + "=?",
                arrayOf(imageId),
                null,
                null
            )
            var path: String? = null
            if (cursor != null && cursor.moveToFirst()) {
                path = cursor.getString(
                    cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA)
                )
            }
            cursor?.close()
            return path
        }

    }
}