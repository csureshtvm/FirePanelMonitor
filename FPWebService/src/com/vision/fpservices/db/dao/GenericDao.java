package com.vision.fpservices.db.dao;

import org.springframework.transaction.annotation.Transactional;

public interface GenericDao<E,ID> {

	public E findById(ID id, boolean lock);
	
	public E findById(ID id);
	
	public E makePersistent(E entity);
}
