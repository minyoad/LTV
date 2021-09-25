package com.wuka.ltv.utils;

import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.wuka.ltv.App;
import com.wuka.ltv.databinding.DialogSettingBinding;
import com.wuka.ltv.impl.SeekBarListener;
import com.wuka.ltv.ui.PlayerActivity;

public class Notify {

	private Toast mToast;

	private static class Loader {
		static volatile Notify INSTANCE = new Notify();
	}

	private static Notify getInstance() {
		return Loader.INSTANCE;
	}

	public static void show(int resId) {
		show(Utils.getString(resId));
	}

	public static void show(String text) {
		getInstance().makeText(text);
	}

	private void makeText(String message) {
		if (mToast != null) mToast.cancel();
		mToast = Toast.makeText(App.get(), message, Toast.LENGTH_LONG);
		mToast.show();
	}

	public static void showDialog(PlayerActivity context, int visible) {
		DialogSettingBinding binding = DialogSettingBinding.inflate(LayoutInflater.from(context));
		new AlertDialog.Builder(context).setView(binding.getRoot()).show();
		binding.control.setVisibility(visible);
		binding.delay.setProgress(Prefers.getDelay());
		binding.size.setProgress(Prefers.getSize());
		binding.boot.setChecked(Prefers.isBoot());
		binding.full.setChecked(Prefers.isFull());
		binding.pad.setChecked(Prefers.isPad());
		binding.pip.setChecked(Prefers.isPip());
		binding.rev.setChecked(Prefers.isRev());
		binding.boot.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> Prefers.putBoot(isChecked));
		binding.rev.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> Prefers.putRev(isChecked));
		binding.pip.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> Prefers.putPip(isChecked));
		binding.full.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
			Prefers.putFull(isChecked);
			context.setScaleType();
		});
		binding.pad.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
			Prefers.putPad(isChecked);
			context.setKeypad();
		});
		binding.size.setOnSeekBarChangeListener(new SeekBarListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				context.onSizeChange(progress);
			}
		});
		binding.delay.setOnSeekBarChangeListener(new SeekBarListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				Prefers.putDelay(progress);
			}
		});
	}
}
