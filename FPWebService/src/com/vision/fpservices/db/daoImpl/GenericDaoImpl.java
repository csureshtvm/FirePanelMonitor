package com.vision.fpservices.db.daoImpl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vision.fpservices.db.dao.GenericDao;

@SuppressWarnings( { "unchecked", "serial" })
public abstract class GenericDaoImpl<E, ID extends Serializable> extends
		HibernateTemplate implements GenericDao<E, ID> {
	
	protected Class<E> entityClass;
	
	@Transactional(readOnly=true)
	public E findById(ID id, boolean lock){
		return (E)super.get(entityClass, id);
	}
	
	@Transactional(readOnly=true)
	public E findById(ID id){
		return findById(id, false);
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Transactional(readOnly=false )
	public E makePersistent(E entity){
		this.checkWriteOperationAllowed(getSessionFactory().getCurrentSession());
		this.saveOrUpdate(entity);
		return entity;
	}
	

}
