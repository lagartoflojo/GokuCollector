package com.plegnic.goku.database;

import java.util.List;

public interface Dao<T> {
	long save(T type);

	void update(T type);

	void delete(T type);

	T find(long id);

	List<T> findAll();

}
