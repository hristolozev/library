package library.operations;

import java.util.Scanner;

import library.db.Database;
import library.entity.user.User;

public class DeleteBook implements IOOperation{

	@Override
	public void oper(Database database, User user) {
		
		Scanner s = new Scanner(System.in);
		System.out.println("Enter book name: ");
		String bookname = s.nextLine();
		
		int i = database.getBook(bookname);
		if (i>-1) {
			database.deleteBook(i);
			System.out.println("Book deleted successfully!\n");
		}else {
			System.out.println("Book doesn't exist!\n");
		}
		user.menu(database, user);
	}

}
