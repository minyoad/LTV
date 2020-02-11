package com.fongmi.android.ltv.bean;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fongmi.android.ltv.utils.Prefers;
import com.google.firebase.database.DataSnapshot;

import java.util.Locale;

@Entity
public class Channel extends Bean {

	@NonNull
	@PrimaryKey
	private String number;
	private String name;
	private String logo;
	private String url;
	private boolean token;
	private boolean hidden;
	private boolean dynamic;

	public static Channel create(String number) {
		return new Channel(String.format(Locale.getDefault(), "%03d", Integer.valueOf(number)));
	}

	public static Channel create(DataSnapshot data) {
		return data.getValue(Channel.class);
	}

	public Channel() {
		this("");
	}

	public Channel(@NonNull String number) {
		this.number = number;
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return TextUtils.isEmpty(url) ? "" : url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isToken() {
		return token;
	}

	public void setToken(boolean token) {
		this.token = token;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public int getTextSize() {
		return Prefers.getSize() * 2 + 14;
	}

	public String getAgent() {
		return isToken() ? "VasCreativePlayer/20.19.0520 (Linux;Android 5.1.1) ExoPlayerLib/2.0.0" : "(Windows NT 10.0; Win64; x64) PotPlayer/1.7.13963";
	}

	public Channel get() {
		Channel item = new Channel();
		item.setNumber(getNumber());
		item.setName(getName());
		item.setLogo(getLogo());
		item.setUrl(getUrl());
		item.setToken(isToken());
		item.setHidden(isHidden());
		item.setDynamic(isDynamic());
		return item;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Channel)) return false;
		Channel it = (Channel) obj;
		return getNumber().equals(it.getNumber());
	}
}
