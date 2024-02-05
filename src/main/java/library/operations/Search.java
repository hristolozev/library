package library.operations;

import java.util.Scanner;

import library.db.Database;
import library.entity.user.User;

public class Search implements IOOperation{

	@Override
	public void oper(Database database, User user) {
		
		System.out.println("\nEnter book name:");
		Scanner s = new Scanner(System.in);
		String name = s.nextLine();
		int i = database.getBook(name);
		if (i>-1) {
			System.out.println("\n"+database.getBook(i).toString()+"\n");
		}else {
			System.out.println("Book doesn't exist!\n");
		}
		user.menu(database, user);
	}

}
