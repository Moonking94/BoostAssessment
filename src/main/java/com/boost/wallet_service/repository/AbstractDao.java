package com.boost.wallet_service.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractDao {

	private static final Logger Log = LoggerFactory.getLogger(AbstractDao.class);
	
	@PersistenceContext
	protected EntityManager em;

	public AbstractDao() {
	}
	
	public void clearEntityManagerCache() {
		em.clear();
	}
	
}
