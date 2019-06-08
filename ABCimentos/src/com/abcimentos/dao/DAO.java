package com.abcimentos.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.abcimentos.factory.ConnectionFactory;


public abstract class DAO<T> {
	
	private Connection conn = null;

	public Connection getConnection() {
		if (conn == null)
			conn = ConnectionFactory.getInstance();
		return conn;
	}
	
	public void closeConnection() {
		try {
			getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}
	
	public abstract boolean create(T obj);
	public abstract boolean update(T obj);
	public abstract boolean delete(int id);
	
	public abstract T findById(int id);
	public abstract List<T> findAll();

}
