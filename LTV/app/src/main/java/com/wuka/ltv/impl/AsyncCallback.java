package com.wuka.ltv.impl;

import androidx.annotation.NonNull;

import com.wuka.ltv.bean.Config;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public abstract class AsyncCallback implements ValueEventListener {

	public void onResponse(String result) {
	}

	public void onResponse(Config config) {
	}

	public void onFail() {
	}

	@Override
	public void onDataChange(@NonNull DataSnapshot data) {
		onResponse(Config.get(data));
	}

	@Override
	public void onCancelled(@NonNull DatabaseError data) {
	}
}
