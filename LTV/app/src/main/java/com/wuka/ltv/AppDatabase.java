package com.wuka.ltv;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wuka.ltv.bean.Channel;
import com.wuka.ltv.dao.ChannelDao;

@Database(entities = {Channel.class}, version = 10, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

	private static volatile AppDatabase instance;

	public static synchronized AppDatabase getInstance() {
		if (instance == null) instance = create(App.get());
		return instance;
	}

	private static AppDatabase create(Context context) {
		return Room.databaseBuilder(context, AppDatabase.class, "bear").allowMainThreadQueries().fallbackToDestructiveMigration().build();
	}

	public abstract ChannelDao getDao();
}
