package com.prevention.disease.orm;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.prevention.disease.bean.Attach;
import com.prevention.disease.bean.Column;
import com.prevention.disease.bean.Item;
import com.prevention.disease.bean.PreventionDisease;
import com.prevention.disease.tool.LogUtil;

public class DAOManager {
	public DAOHelper helper;
	public Dao<PreventionDisease, Integer> preventionDiseaseDao;
	public Dao<Column, Integer> columnDao;
	public Dao<Item, Integer> itemDao;
	public Dao<Attach, Integer> attachDao;

	public static DAOManager manager = null;

	public DAOManager(Context context) {
		helper = OpenHelperManager.getHelper(context, DAOHelper.class);
		preventionDiseaseDao = helper.getPreventionDiseaseDao();
		columnDao = helper.getColumnDao();
		itemDao = helper.getItemDao();
		attachDao = helper.getAttachDao();
	}

	public static DAOManager getInstance(Context context) {
		if (manager == null) {
			manager = new DAOManager(context);
		}
		return manager;
	}

	public void close() {
		if (helper != null) {
			helper.close();
		}
		OpenHelperManager.releaseHelper();
		helper = null;
	}

	public void deleteDataBase(Context context) {
		helper.deleteDatabase(context);
		close();
	}

	/*************************************** 以下为PreventionDisease操作 ******************************************/

	public void addPreventionDisease(PreventionDisease preventionDisease) throws SQLException {
		if (preventionDisease != null) {
			LogUtil.i("db","--------create user : " + preventionDisease.getName()+ "-----");
			preventionDiseaseDao.create(preventionDisease);
		}
	}

	public int deletePreventionDisease(PreventionDisease preventionDisease) throws SQLException {
		if (preventionDisease != null) {
			return  preventionDiseaseDao.deleteById(preventionDisease.getId());
		}
		return 0;
	}

	public void updatePreventionDisease(PreventionDisease preventionDisease) throws SQLException {
		if (preventionDisease != null) {
			preventionDiseaseDao.update(preventionDisease);
		}
	}

	public PreventionDisease queryPreventionDiseaseById(int id) throws SQLException {
		List<PreventionDisease> preventionDiseases = preventionDiseaseDao.
				queryBuilder().where().eq("id", id).query();
		PreventionDisease preventionDisease = null;
		if (preventionDiseases!=null&&preventionDiseases.size() > 0) {
			preventionDisease = preventionDiseases.get(0);
		}
		return preventionDisease;
	}

	public List<PreventionDisease> queryAllPreventionDisease() throws SQLException {
		List<PreventionDisease> list = null;
		list = preventionDiseaseDao.queryForAll();
		return list;
	}

	/*************************************** 以下为Column操作 ******************************************/

	public void addColumn(Column column) throws SQLException {
		if (column != null) {
			columnDao.create(column);
		}
	}

	public void deleteColumn(Column column) throws SQLException {
		if (column != null) {
			columnDao.delete(column);
		}
	}

	public void updateColumn(Column column) throws SQLException {
		if (column != null) {
			columnDao.update(column);
		}
	}

	public Column querycolumnById(int id) throws SQLException {
		Column column = null;
		column = columnDao.queryForId(id);
		return column;
	}

	public List<Column> queryAllColumn() throws SQLException {
		List<Column> list = null;
		list = columnDao.queryForAll();
		return list;
	}



	/*************************************** 以下为Item操作 ******************************************/

	public void addItem(Item item) throws SQLException {
		if (item != null) {
			itemDao.create(item);
		}
	}

	public void deleteItem(Item item) throws SQLException {
		if (item != null) {
			itemDao.delete(item);
		}
	}

	public void updateItem(Item item) throws SQLException {
		if (item != null) {
			itemDao.update(item);
		}
	}

	public Item queryItemById(int id) throws SQLException {
		Item item = null;
		item = itemDao.queryForId(id);

		return item;
	}

	public List<Item> queryAllItem() throws SQLException {
		List<Item> list = null;
		list = itemDao.queryForAll();
		return list;
	}

	public void deleteAllItem() throws SQLException {
		List<Item> list = itemDao.queryForAll();
		for (Item Item : list) {
			itemDao.delete(Item);
		}
	}

	/*************************************** 以下为Attach操作 ******************************************/

	public void addAttach(Attach attach) throws SQLException {
		if (attach != null) {
			attachDao.create(attach);
		}
	}

	public void delete(Attach attach) throws SQLException {
		if (attach != null) {
			attachDao.delete(attach);
		}
	}

	public void updateAttach(Attach attach) throws SQLException {
		if (attach != null) {
			attachDao.update(attach);
		}
	}

	public List<Attach> queryAllattach() throws SQLException {
		List<Attach> list = null;
		list = attachDao.queryForAll();
		return list;
	}

	public List<Attach> queryAllAttachById(int id) throws SQLException {
		List<Attach> list = attachDao.queryBuilder().where().eq("id", id).query();
		return list;
	}
	
	public List<Attach> queryAllAttachByItemId(int itemId) throws SQLException {
		List<Attach> list = attachDao.queryBuilder().where().eq("id",itemId).query();
		return list;
	}

	public void deleteAllAttach() throws SQLException {
		List<Attach> list = attachDao.queryForAll();
		for (Attach Attach : list) {
			attachDao.delete(Attach);
		}
	}

}
