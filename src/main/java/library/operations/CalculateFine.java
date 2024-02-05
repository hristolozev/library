package library.operations;

import java.util.Scanner;

import library.db.Database;
import library.entity.book.Borrowing;
import library.entity.user.User;

public class CalculateFine implements IOOperation{

	@Override
	public void oper(Database database, User user) {
		
		System.out.println("Enter book name: ");
		Scanner s = new Scanner(System.in);
		String bookname = s.nextLine();	
		
		boolean g = true;
		
		for(Borrowing b : database.getBrws()) {
			if(b.getBook().getName().matches(bookname) &&
					b.getUser().getName().matches(user.getName())) {
				System.out.println(b.getDaysLeft());
				if(b.getDaysLeft()<0) {
					System.out.println("You are late!\n" 
							+"You have to pay " +b.getDaysLeft()*50+ "as fine\n");
				}else {
					System.out.println("You don't have to pay fine\n");
				}
				g = false;
				break;
			}
		}
		if(g) {
			System.out.println("You didn't borrow this book!\n");
		}
		user.menu(database, user);
	}

}
