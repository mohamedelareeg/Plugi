package com.plugi.plugi.core.utilities

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StrictMode
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.plugi.plugi.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URISyntaxException
import java.util.*


object IntentsForActions {

    fun shareTextToAll(activity: Activity, shareBody:String){
        val intent = Intent(Intent.ACTION_SEND)
        /*This will be the actual content you wish you share.*/
        /*This will be the actual content you wish you share.*/
        /*The type of the content is text, obviously.*/
        /*The type of the content is text, obviously.*/intent.type = "text/plain"
        /*Applying information Subject and Body.*/
        /*Applying information Subject and Body.*/intent.putExtra(
            Intent.EXTRA_SUBJECT,
            activity.getString(R.string.reg_code_is)
        )
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        /*Fire!*/
        /*Fire!*/activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.share_using)))
    }
    fun sharePhotoWithText(context: Activity, url: String?, txt: String) {

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val textToShare =
            txt + " " + "   https://play.google.com/store/apps/details?id=" + context.packageName
        url?.let {


            Picasso.get().load(url).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    val i = Intent(Intent.ACTION_SEND)
                    i.type = "image/*"
                    //i.setType("*/*");
                    i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(context, bitmap))
                    i.putExtra(Intent.EXTRA_TEXT, textToShare)
                    i.putExtra(Intent.EXTRA_TITLE, "Share")

                    //                i.putExtra(Intent.EXTRA_STREAM, uri);
                    context.startActivity(Intent.createChooser(i, "Share Image"))
                }

                override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}

                override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
            })
        } ?: run {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            //i.setType("*/*");
            i.putExtra(Intent.EXTRA_TEXT, textToShare)
            i.putExtra(Intent.EXTRA_TITLE, "Share")
            //                i.putExtra(Intent.EXTRA_STREAM, uri);
            context.startActivity(Intent.createChooser(i, "Share"))
        }

    }

    private fun getLocalBitmapUri(activity: Activity, bmp: Bitmap): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(
                activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bmpUri
    }

    fun callNumber(context: Activity, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        context.startActivity(intent)
    }

    fun email(context: Activity, email: String) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email")
        i.putExtra(Intent.EXTRA_TEXT, "body of email")
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT)
                .show()
        }

    }

    fun website(context: Activity, url: String?) {
        if (url != null && !url.isEmpty()) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            context.startActivity(i)
        }
    }

    fun whatsApp(context: Activity, whats: String) {
        var whats = whats
        whats = "" + whats
        if (whats.startsWith("00")) {
            whats = whats.substring(2)
        }
        Log.e("whats", whats)
        try {
            whats = whats.replace(" ", "").replace("+", "")
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(whats) + "@s.whatsapp.net")
            context.startActivity(sendIntent)

        } catch (e: Exception) {
            Toast.makeText(context, R.string.there_error, Toast.LENGTH_SHORT).show()
            Log.e("TAG", "ERROR_OPEN_MESSANGER$e")
        }

    }

    fun facebook(context: Activity, FACEBOOK_URL: String) {
        try {
            context.packageManager.getPackageInfo("com.facebook.katana", 0)
            val versionCode =
                context.packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            val path = "fb://facewebmodal/f?href=$FACEBOOK_URL"
            val x = Intent(Intent.ACTION_VIEW, Uri.parse(path))
            context.startActivity(x)

        } catch (e: Exception) {
            val y = Intent(Intent.ACTION_VIEW, Uri.parse("" + FACEBOOK_URL))
            context.startActivity(y)

        }

    }

    fun twitter(context: Activity, twitter: String) {
        val intent: Intent
        val FullUrl = "https://twitter.com/$twitter"
        try {
            // get the Twitter app if possible
            context.packageManager.getPackageInfo("com.twitter.android", 0)
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(FullUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        } catch (e: Exception) {
            // no Twitter app, revert to browser
            val x = Intent(Intent.ACTION_VIEW, Uri.parse(FullUrl))
            context.startActivity(x)

        }

    }

    fun instgram(context: Activity, insta: String) {
        val intent: Intent
        val FullUrl = "https://www.instagram.com/$insta"

        try {
            // get the Twitter app if possible
            context.packageManager.getPackageInfo("com.instagram.android", 0)
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(FullUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        } catch (e: Exception) {
            // no Twitter app, revert to browser
            val x = Intent(Intent.ACTION_VIEW, Uri.parse(FullUrl))
            context.startActivity(x)

        }

    }

    fun youtube(context: Activity, url: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$url"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.youtube.com/$url")
        )
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }

    }

    fun snapChat(context: Context, snapchatId: String) {
        try {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://snapchat.com/add/$snapchatId"))
            intent.setPackage("com.snapchat.android")
            context.startActivity(intent)
        } catch (e: Exception) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://snapchat.com/add/$snapchatId")
                )
            )
        }

    }
    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String?>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    @Throws(URISyntaxException::class)
    fun getFilePath(context: Context, uriIn: Uri): String? {
        var uri = uriIn
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(
                context.applicationContext,
                uri
            )
        ) {
            if (isExternalStorageDocument(uri)) {
                val docId: String = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                return Environment.getExternalStorageDirectory()
                    .toString() + "/" + split[1]
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                if (!TextUtils.isEmpty(id)) {
                    return if (id.startsWith("raw:")) {
                        id.replaceFirst("raw:".toRegex(), "")
                    } else try {
                        val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            java.lang.Long.valueOf(id)
                        )
                        getDataColumn(context, contentUri, null, null)
                    } catch (e: NumberFormatException) {
                        Log.e(
                            "FileUtils",
                            "Downloads provider returned unexpected uri $uri",
                            e
                        )
                        null
                    }
                }
            } else if (isMediaDocument(uri)) {
                val docId: String = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("image" == type) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                selection = "_id=?"
                selectionArgs = arrayOf(
                    split[1]
                )
            }
        }
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            if (isGooglePhotosUri(uri)) {
                return uri.lastPathSegment
            }
            val projection = arrayOf(
                MediaStore.Images.Media.DATA
            )
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                    .query(uri, projection, selection, selectionArgs, null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    fun getPath(context: Activity?, uri: Uri): String {
        try {

            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context!!.managedQuery(uri, projection, null, null, null)
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } catch (e: java.lang.Exception) {

            return ""
        }

    }

    fun pickImage(activity: Activity, galleryCode: Int) {
        val per = MyPermission.requestPermission(
            activity,
            MyPermission.REQUEST_PERMISSION_STORAGE_READ_INDEX
        )
        if (per == -1) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            activity.startActivityForResult(
                Intent.createChooser(intent, activity.getString(R.string.select_iamge)),
                galleryCode
            )
        }
    }
    fun openCameraImage(activity: Activity, galleryCode: Int) {
        val per = MyPermission.requestPermission(
            activity,
            MyPermission.REQUEST_PERMISSION_STORAGE_READ_INDEX
        )
        if (per == -1) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            activity.startActivityForResult(
                Intent.createChooser(intent, activity.getString(R.string.select_iamge)),
                galleryCode
            )
        }
    }

    fun pickMultiImage(activity: Activity, galleryCode: Int) {
        val per = MyPermission.requestPermission(
            activity,
            MyPermission.REQUEST_PERMISSION_STORAGE_READ_INDEX
        )
        if (per == -1) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "image/*"
            activity.startActivityForResult(
                Intent.createChooser(intent, activity.getString(R.string.select_iamge)),
                galleryCode
            )
        }
    }

    fun pickImage(activity: Fragment, galleryCode: Int) {
        val per = MyPermission.requestPermission(
            activity,
            MyPermission.REQUEST_PERMISSION_STORAGE_READ_INDEX
        )
        if (per == -1) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            activity.startActivityForResult(
                Intent.createChooser(intent, activity.getString(R.string.select_iamge)),
                galleryCode
            )
        }
    }

    fun pickVideo(activity: Activity, galleryCode: Int) {
        val per = MyPermission.requestPermission(
            activity,
            MyPermission.REQUEST_PERMISSION_STORAGE_READ_INDEX
        )
        if (per == -1) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "video/*"
            activity.startActivityForResult(
                Intent.createChooser(intent, activity.getString(R.string.select_iamge)),
                galleryCode
            )
        }
    }

    fun pickSound(activity: Activity, galleryCode: Int) {
        //         Intent intent = new Intent(
        //                Intent.ACTION_PICK,
        //                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        ////        intent.setType("audio/*");
        val per = MyPermission.requestPermission(
            activity,
            MyPermission.REQUEST_PERMISSION_STORAGE_READ_INDEX
        )
        if (per == -1) {
            val intent = Intent()
            intent.type = "audio/*"
            intent.action = Intent.ACTION_GET_CONTENT

            activity.startActivityForResult(
                Intent.createChooser(intent, activity.getString(R.string.select_iamge)),
                galleryCode
            )
        }
    }

    fun pickPdf(activity: Activity, galleryCode: Int) {
        val per = MyPermission.requestPermission(
            activity,
            MyPermission.REQUEST_PERMISSION_STORAGE_READ_INDEX
        )
        if (per == -1) {
            try {
                val mimeTypes = arrayOf(
                    "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                    "application/vnd.ms-powerpoint",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                    "application/vnd.ms-excel",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                    "text/plain",
                    "application/pdf",
                    "application/zip"
                )

                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
                    if (mimeTypes.isNotEmpty()) {
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                    }
                } else {
                    var mimeTypesStr = ""
                    for (mimeType in mimeTypes) {
                        mimeTypesStr += "$mimeType|"
                    }
                    intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
                }
                activity.startActivityForResult(
                    Intent.createChooser(intent, "ChooseFile"),
                    galleryCode
                )
            } catch (e: Exception) {
//                Timber.e(e)
            }
        }
    }

    fun copyText(activity: Activity, text: String) {
        val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("myAdds", text)
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip)
            Toast.makeText(activity, R.string.done, Toast.LENGTH_SHORT).show()
        }
    }

    fun thumbVedio(path: String): Bitmap? {

        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND)
    }

    fun getFileThumb(activity: Activity, path: String): File {
        val bitmap =
            ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND)

        val f = File(activity.cacheDir, Date().time.toString() + "123")
        try {
            f.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return f

    }

    fun retriveVideoFrameFromVideo(videoPath: String): Bitmap? {
        var bitmap: Bitmap? = null
        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        try {
            mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(videoPath, HashMap())
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)
        } catch (e: Exception) {
            e.printStackTrace()
            //            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)"+ e.getMessage());

        } finally {
            mediaMetadataRetriever?.release()
        }
        return bitmap
    }

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    @SuppressWarnings("deprecation")
    fun downloadFile(context: Activity, file: String) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {

            // this will request for permission when user has not granted permission for the app
            ActivityCompat.requestPermissions(
                context,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                1
            )
        } else {
            Log.e("SDSD,", "sdfsdfdsfsd")
            val downloadManager: DownloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager;
            val request = DownloadManager.Request(Uri.parse(file))
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setAllowedOverRoaming(false)
            request.setTitle("Compass")
            request.setDescription("Downloading FIle")
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "Compass" + "_" + System.currentTimeMillis() + ".pdf"
            );
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            downloadManager.enqueue(request)
        }
    }


}