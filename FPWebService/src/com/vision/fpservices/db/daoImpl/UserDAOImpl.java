package com.vision.fpservices.db.daoImpl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.vision.fpservices.db.dao.AddressDAO;
import com.vision.fpservices.db.dao.UserDAO;
import com.vision.fpservices.db.model.AddressDetails;
import com.vision.fpservices.db.model.User;
import com.vision.fpservices.dto.UserDTO;
import com.vision.fpservices.util.HibernateUtil;
@Repository("UserDAO")
public class UserDAOImpl extends GenericDaoImpl<User, Integer> implements UserDAO  {
	
	
	Logger log = Logger.getLogger("UserDAO");
	
	public UserDTO getLoginUserDetails(String userName){
		Session session = getSessionFactory().getCurrentSession();
		SQLQuery query=session.createSQLQuery("SELECT u.user_name as userName,u.password as password,u.first_name as firstName,u.last_name as lastName, " +
				" u.user_role as userRole, u.customer_id as customerId, u.user_status as userStatus " +
				"FROM users u WHERE u.user_name=:arg_user_name");
		
		//query.setParameter("arg_user_status", "ACTIVE");
		query.setParameter("arg_user_name", userName);
		query.setMaxResults(1);
		
		query.addScalar("userName").addScalar("firstName").addScalar("lastName").
		addScalar("userRole").addScalar("customerId",StandardBasicTypes.INTEGER).addScalar("password").addScalar("userStatus");
			
		
		query.setResultTransformer(Transformers.aliasToBean(UserDTO.class));
		List<UserDTO> userList = query.list();
		System.out.println("Result Size---------"+userList.size());
		
		return userList.size()>0?userList.get(0):null;
		
		
	}
	public String createNewUser(User user) {
		log.info("Going to save User");
		Session session = getSessionFactory().getCurrentSession();
		String userName = "";
		userName = (String) session.save(user);
		log.info("User saved with userName=" + userName);
		return userName;
	}
	
	public User loadUserDetails(String userName) {
		log.info("Going to load User with ID --->"+userName);
		Session session = getSessionFactory().getCurrentSession();
		 Transaction tx1 = session.beginTransaction();
		 User user = (User) session.get(User.class,
				 userName);
		return user;

	}
	public boolean updateUser(User user) {
		log.info("Going to save User");
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(user);
		log.info("User saved with userName=" + user.getUserName());

		return true;
	}


}
