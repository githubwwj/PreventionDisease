package com.prevention.disease.orm;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.prevention.disease.bean.Attach;
import com.prevention.disease.bean.Column;
import com.prevention.disease.bean.Item;
import com.prevention.disease.bean.PreventionDisease;
import com.prevention.disease.tool.LogUtil;

public class DAOHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DATABASE_NAME = "prevention_disease";
	private static final int DATABASE_VERSON = 8;
	private Dao<PreventionDisease, Integer> preventionDiseaseDao;
	private Dao<Column, Integer> columnDao;
	private Dao<Item, Integer> itemDao;
	private Dao<Attach, Integer> attachDao;
	
	public DAOHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSON);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			LogUtil.i("db","-------- daohelper oncreate---------");
			TableUtils.createTable(connectionSource, PreventionDisease.class);
			TableUtils.createTable(connectionSource, Column.class);
			TableUtils.createTable(connectionSource, Item.class);
			TableUtils.createTable(connectionSource, Attach.class);
		} catch (SQLException e) {
			LogUtil.i("db", "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			if (newVersion > oldVersion) {
				LogUtil.i("db", "onUpgrade");
				TableUtils.dropTable(connectionSource, PreventionDisease.class, true);
				TableUtils.dropTable(connectionSource, Column.class, true);
				TableUtils.dropTable(connectionSource, Item.class, true);
				TableUtils.dropTable(connectionSource, Attach.class, true);
				onCreate(database, connectionSource);
			}
		} catch (SQLException e) {
			LogUtil.i("db", "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {
		super.close();
		preventionDiseaseDao = null;
		columnDao = null;
		itemDao = null;
		attachDao = null;
	}

	public boolean deleteDatabase(Context context) {
		LogUtil.i("db","------delete database------");

		boolean b = context.deleteDatabase(DATABASE_NAME);

		LogUtil.i("db","---------- 00 " + b + " 00 -----------");
		return b;
	}

	public Dao<PreventionDisease, Integer> getPreventionDiseaseDao() {
		if (preventionDiseaseDao == null) {
			try {
				preventionDiseaseDao = getDao(PreventionDisease.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return preventionDiseaseDao;
	}

	public Dao<Column, Integer> getColumnDao() {
		if (columnDao == null) {
			try {
				columnDao = getDao(Column.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return columnDao;
	}

	public Dao<Item, Integer> getItemDao() {
		if (itemDao == null) {
			try {
				itemDao = getDao(Item.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemDao;
	}

	public Dao<Attach, Integer> getAttachDao() {
		if (attachDao == null) {
			try {
				attachDao = getDao(Attach.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return attachDao;
	}

}
