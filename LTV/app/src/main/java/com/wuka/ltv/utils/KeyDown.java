package com.wuka.ltv.utils;

import android.os.Handler;
import android.view.KeyEvent;

import com.wuka.ltv.impl.KeyDownImpl;

public class KeyDown {

	private final KeyDownImpl mKeyDown;
	private final StringBuilder mText;
	private final Handler mHandler;
	private boolean mPress;

	public KeyDown(KeyDownImpl keyDown) {
		this.mKeyDown = keyDown;
		this.mHandler = new Handler();
		this.mText = new StringBuilder();
	}

	public void onKeyDown(int keyCode) {
		if (mText.length() >= 4) return;
		mText.append(getNumber(keyCode));
		mHandler.removeCallbacks(mRunnable);
		mHandler.postDelayed(mRunnable, getDelay());
		mKeyDown.onShow(mText.toString());
	}

	public boolean onKeyDown(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN && Utils.isUpKey(event)) {
			mKeyDown.onKeyVertical(Prefers.isRev());
		} else if (event.getAction() == KeyEvent.ACTION_DOWN && Utils.isDownKey(event)) {
			mKeyDown.onKeyVertical(!Prefers.isRev());
		} else if (event.getAction() == KeyEvent.ACTION_UP && Utils.isLeftKey(event)) {
			mKeyDown.onKeyLeft();
		} else if (event.getAction() == KeyEvent.ACTION_UP && Utils.isRightKey(event)) {
			mKeyDown.onKeyRight();
		} else if (event.getAction() == KeyEvent.ACTION_UP && Utils.isBackKey(event)) {
			mKeyDown.onKeyBack();
		} else if (event.getAction() == KeyEvent.ACTION_UP && Utils.isMenuKey(event)) {
			mKeyDown.onLongPress();
		} else if (event.getAction() == KeyEvent.ACTION_UP && Utils.isDigitKey(event)) {
			onKeyDown(event.getKeyCode());
		} else if (Utils.isEnterKey(event)) {
			checkPress(event);
		}
		return true;
	}

	private void checkPress(KeyEvent event) {
		if (event.isLongPress()) {
			mPress = true;
			mKeyDown.onLongPress();
		} else if (event.getAction() == KeyEvent.ACTION_UP) {
			if (mPress) mPress = false;
			else mKeyDown.onKeyCenter();
		}
	}

	private int getDelay() {
		return Prefers.getDelay() * 500 + 500;
	}

	private int getNumber(int keyCode) {
		return keyCode >= 144 ? keyCode - 144 : keyCode - 7;
	}

	private final Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			mKeyDown.onFind(mText.toString());
			mText.setLength(0);
		}
	};
}
