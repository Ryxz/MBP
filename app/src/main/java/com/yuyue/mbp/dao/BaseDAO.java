package com.yuyue.mbp.dao;

import android.database.Cursor;
import android.util.Log;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.CursorUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.yuyue.mbp.entity.BPMeasurement;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseDAO<T extends BPMeasurement> {

	DbUtils db;
	String TAG;
    Class<? extends BPMeasurement> entityClazz;

    @SuppressWarnings("unchecked")
	protected BaseDAO(DbUtils db) {
		this.db = db;
        this.entityClazz = (Class<? extends BPMeasurement>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            db.createTableIfNotExist(entityClazz);
        } catch (DbException e) {
            Log.e(BaseDAO.class.getSimpleName(), "create table " + entityClazz.getSimpleName() + " failed!");
            e.printStackTrace();
        }
    }

	public boolean saveOrUpdate(T t) {
		boolean flag = false;
//        if (TextUtils.isEmpty(t.getId())) {
//            t.setId(UUID.randomUUID().toString());
//        }
        try {
			db.saveOrUpdate(t);
			flag = true;
		} catch (DbException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

    public boolean save(T t) {
        boolean flag = false;
//        t.setId(UUID.randomUUID().toString());

        try {
            db.save(t);
            flag = true;
        } catch (DbException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }

    public boolean saveOrUpdateList(List<T> list) {
        boolean flag = false;
        for (T t : list) {
            flag = saveOrUpdate(t);
        }
        return flag;
    }

    public T saveOrUpdateWithEntity(T t) {
        if (saveOrUpdate(t)) {
            return t;
        }
        return null;
    }

    public T getById(String id) {
        Selector selector = Selector.from(entityClazz).where(T.ID, "=", id);
        T t = null;
        try {
            t = db.findFirst(selector);
        } catch (DbException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return t;
    }

    protected List<T> getList(Selector selector) {

        List<T> data = null;
        try {
            data = db.findAll(selector);
        } catch (DbException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return data == null ? Collections.<T> emptyList() : data;
    }

    @SuppressWarnings("unchecked")
    protected List getList(String sql) {
        long seq = CursorUtils.FindCacheSequence.getSeq();
        List data = null;
        Cursor cursor;
        try {
            cursor = db.execQuery(sql);
            if (cursor != null) {
                data = new ArrayList();
                while (cursor.moveToNext()) {
                    data.add(CursorUtils.getEntity(db, cursor, entityClazz, seq));
                }
            }
        } catch (DbException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return data == null ? Collections.emptyList() : data;
    }


    protected Selector getSelector() {
        return Selector.from(entityClazz);
    }

}
