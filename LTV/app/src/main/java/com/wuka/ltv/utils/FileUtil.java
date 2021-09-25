package com.wuka.ltv.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.wuka.ltv.App;
import com.wuka.ltv.BuildConfig;
import com.wuka.ltv.R;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.net.URLConnection;

public class FileUtil {

	private static final String TAG = FileUtil.class.getSimpleName();

	private static File getCachePath() {
		return App.get().getExternalCacheDir();
	}

	public static File getCacheFile(String fileName) {
		return new File(getCachePath(), fileName);
	}

	public static void clearDir(File dir) {
		if (dir == null) return;
		if (dir.isDirectory()) for (File file : dir.listFiles()) clearDir(file);
		if (dir.delete()) Log.d(TAG, dir.getPath() + " File Deleted");
	}

	private static String getMimeType(String fileName) {
		String mimeType = URLConnection.guessContentTypeFromName(fileName);
		return TextUtils.isEmpty(mimeType) ? "*/*" : mimeType;
	}

	private static Uri getShareUri(File file) {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.N ? Uri.fromFile(file) : FileProvider.getUriForFile(App.get(), App.get().getPackageName() + ".provider", file);
	}

	public static void checkUpdate(long version) {
		if (version > BuildConfig.VERSION_CODE) {
			Notify.show(R.string.app_update);
			startDownload();
		} else {
			clearDir(getCachePath());
		}
	}

	private static void startDownload() {
		StorageReference ref = FirebaseStorage.getInstance().getReference();
		ref.listAll().addOnSuccessListener(listResult -> {
			File apkFile = getCacheFile(listResult.getItems().get(0).getName());
			ref.child(apkFile.getName()).getFile(apkFile).addOnSuccessListener((FileDownloadTask.TaskSnapshot taskSnapshot) -> openFile(apkFile));
		});
	}

	private static void openFile(File file) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		intent.setDataAndType(getShareUri(file), FileUtil.getMimeType(file.getName()));
		App.get().startActivity(intent);
	}
}
