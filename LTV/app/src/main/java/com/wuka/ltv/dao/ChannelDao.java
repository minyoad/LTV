package com.wuka.ltv.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.wuka.ltv.bean.Channel;

import java.util.List;

@Dao
public interface ChannelDao {

	@Query("SELECT * FROM channel ORDER BY number")
	List<Channel> getKeep();

	@Query("SELECT COUNT(number) FROM channel WHERE number = :number")
	int getCount(String number);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(Channel item);

	@Delete
	void delete(Channel item);
}