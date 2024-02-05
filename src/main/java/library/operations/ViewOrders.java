package library.operations;

import library.db.Database;
import library.entity.book.Book;
import library.entity.order.Order;
import library.entity.user.User;
import pl.mjaron.etudes.Table;
import pl.mjaron.etudes.table.VerticalAlign;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static pl.mjaron.etudes.table.RenderContext.col;

public class ViewOrders implements IOOperation{

	@Override
	public void oper(Database database, User user) {
		
		System.out.println("\nEnter book name:");
		Scanner s = new Scanner(System.in);
		String bookname = s.nextLine();
		
		int i = database.getBook(bookname);
		if (i>-1) {
			List<Order> allOrders = database
					.getAllOrders()
					.stream()
					.filter((e)->e.getBook().getName().matches(bookname))
					.collect(Collectors.toList());

			Table.render(allOrders, Order.class)
					.markdown()
					.withColumns(
							col("bookName","NAME")
									.col("userName","USER")
									.col("price", "PRICE")
									.col("qty","QTY")
					)
					.withEscaper(null)
					.withAlignedColumnWidths()
					.withLineBreakCRLF()
					.withAlign(VerticalAlign.Left)
					.run()
			;
			System.out.println();
		}else {
			System.out.println("Book doesn't exist!\n");
		}
		user.menu(database, user);
	}

}
