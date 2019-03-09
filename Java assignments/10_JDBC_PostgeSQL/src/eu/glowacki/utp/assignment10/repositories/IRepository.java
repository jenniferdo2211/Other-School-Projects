package eu.glowacki.utp.assignment10.repositories;

import java.sql.Connection;

import eu.glowacki.utp.assignment10.dtos.DTOBase;

public interface IRepository<TDTO extends DTOBase> {

	Connection getConnection();

	void add(TDTO dto);

	void update(TDTO dto);
	
	void addOrUpdate(TDTO dto);

	void delete(TDTO dto);

	TDTO findById(int id);

	void beginTransaction();

	void commitTransaction();

	void rollbackTransaction();
	
	int getCount(int id);
	
	boolean exists(TDTO dto);
}