package library.operations;


import library.db.Database;
import library.entity.user.User;

public interface IOOperation {
	
	void oper(Database database, User user);

}
