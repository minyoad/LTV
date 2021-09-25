package com.wuka.ltv.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Rational;
import android.view.KeyEvent;
import android.view.View;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.wuka.ltv.App;
import com.wuka.ltv.BuildConfig;
import com.google.android.exoplayer2.util.Util;
import com.google.common.net.HttpHeaders;

public class Utils {

	private static DisplayMetrics getDisplayMetrics() {
		return App.get().getResources().getDisplayMetrics();
	}

	public static String getString(int resId) {
		return App.get().getString(resId);
	}

	public static int dp2px(int dpValue) {
		return Math.round(dpValue * getDisplayMetrics().density);
	}

	public static boolean hasEvent(KeyEvent event) {
		return isArrowKey(event) || isBackKey(event) || isMenuKey(event) || isDigitKey(event) || event.isLongPress();
	}

	private static boolean isArrowKey(KeyEvent event) {
		return isEnterKey(event) || isUpKey(event) || isDownKey(event) || isLeftKey(event) || isRightKey(event);
	}

	static boolean isBackKey(KeyEvent event) {
		return event.getKeyCode() == KeyEvent.KEYCODE_BACK;
	}

	static boolean isMenuKey(KeyEvent event) {
		return event.getKeyCode() == KeyEvent.KEYCODE_MENU;
	}

	public static boolean isDigitKey(KeyEvent event) {
		return event.getKeyCode() >= KeyEvent.KEYCODE_0 && event.getKeyCode() <= KeyEvent.KEYCODE_9 || event.getKeyCode() >= KeyEvent.KEYCODE_NUMPAD_0 && event.getKeyCode() <= KeyEvent.KEYCODE_NUMPAD_9;
	}

	static boolean isEnterKey(KeyEvent event) {
		return event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_SPACE || event.getKeyCode() == KeyEvent.KEYCODE_NUMPAD_ENTER;
	}

	static boolean isUpKey(KeyEvent event) {
		return event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode() == KeyEvent.KEYCODE_CHANNEL_UP || event.getKeyCode() == KeyEvent.KEYCODE_PAGE_UP || event.getKeyCode() == KeyEvent.KEYCODE_MEDIA_PREVIOUS;
	}

	static boolean isDownKey(KeyEvent event) {
		return event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN || event.getKeyCode() == KeyEvent.KEYCODE_CHANNEL_DOWN || event.getKeyCode() == KeyEvent.KEYCODE_PAGE_DOWN || event.getKeyCode() == KeyEvent.KEYCODE_MEDIA_NEXT;
	}

	static boolean isLeftKey(KeyEvent event) {
		return event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT;
	}

	static boolean isRightKey(KeyEvent event) {
		return event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT;
	}

	public static void showViews(View... views) {
		for (View view : views) showView(view);
	}

	public static void hideViews(View... views) {
		for (View view : views) hideView(view);
	}

	public static void showView(View view) {
		view.animate().alpha(1).setDuration(250).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				view.setVisibility(View.VISIBLE);
			}
		}).start();
	}

	public static void hideView(View view) {
		view.animate().alpha(0).setDuration(250).setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.GONE);
			}
		}).start();
	}

	public static void hideSystemUI(Activity activity) {
		int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		activity.getWindow().getDecorView().setSystemUiVisibility(flags);
	}

	public static boolean hasPIP() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && App.get().getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE);
	}

	public static void enterPIP(Activity activity) {
		try {
			if (!Prefers.isPip()) return;
			if (!hasPIP() || activity.isInPictureInPictureMode()) return;
			PictureInPictureParams.Builder builder = new PictureInPictureParams.Builder();
			builder.setAspectRatio(new Rational(16, 9)).build();
			activity.enterPictureInPictureMode(builder.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static GlideUrl getImageUrl(String url) {
		return new GlideUrl(Token.getImg(url), new LazyHeaders.Builder().addHeader(HttpHeaders.USER_AGENT, getUserAgent()).build());
	}

	public static String getUUID() {
		return Settings.Secure.getString(App.get().getContentResolver(), Settings.Secure.ANDROID_ID);
	}

	static String getDevice() {
		String model = Build.MODEL;
		String manufacturer = Build.MANUFACTURER;
		if (model.startsWith(manufacturer)) return model;
		else return manufacturer + " " + model;
	}

	static String getVersion() {
		return BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ", " + Build.VERSION.RELEASE + ")";
	}

	public static String getUserAgent() {
		return Util.getUserAgent(App.get(), App.get().getPackageName());
	}
}
