package com.fongmi.android.tv.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fongmi.android.tv.utils.Prefers;

@Entity
public class Channel {

	@NonNull
	@PrimaryKey
	private String number;
	private String name;
	private String url;
	private boolean token;
	@Ignore private String real;
	@Ignore private boolean select;

	public Channel() {
	}

	public Channel(@NonNull String number) {
		this.number = number;
	}

	public static Channel create(String number) {
		return new Channel(number);
	}

	@NonNull
	public String getNumber() {
		return number;
	}

	public void setNumber(@NonNull String number) {
		this.number = number;
	}

	public String getName() {
		return TextUtils.isEmpty(name) ? "" : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return TextUtils.isEmpty(url) ? "" : url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReal() {
		return TextUtils.isEmpty(real) ? "" : real;
	}

	public void setReal(String real) {
		this.real = real;
	}

	public boolean isToken() {
		return token;
	}

	public void setToken(boolean token) {
		this.token = token;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public void deselect() {
		setSelect(false);
	}

	public void select() {
		setSelect(true);
	}

	public boolean hasUrl() {
		return getReal().length() > 0;
	}

	public int getTextSize() {
		return Prefers.getSize() * 2 + 16;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Channel)) return false;
		Channel it = (Channel) obj;
		return getNumber().equals(it.getNumber());
	}
}
