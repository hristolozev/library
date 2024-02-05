package library.operations;

import java.util.ArrayList;

import library.db.Database;
import library.entity.book.Book;
import library.entity.user.User;
import pl.mjaron.etudes.Table;
import pl.mjaron.etudes.table.VerticalAlign;

import static pl.mjaron.etudes.table.RenderContext.col;

public class ViewBooks implements IOOperation{

	@Override
	public void oper(Database database, User user) {
		
	  	ArrayList<Book> books = database.getAllBooks();
//	  	System.out.println("Name\t\tAuthor\t\tPublisher\tCLA\tQty\tPrice"
//	  	+ "\tBrw cps");
//	  	for (Book b : books) {
//	  		System.out.println(b.getName()+"\t\t"+b.getAuthor()+"\t\t"+b.getPublisher()+"\t\t"+
//	  	b.getAdress()+"\t"+b.getQty()+"\t"+b.getPrice()+"\t"+b.getBrwcopies());
//	  	}

		Table.render(books, Book.class)
				.markdown()
				.withColumns(
						col("name","NAME")
								.col("author","AUTHOR")
								.col("publisher", "PUBLISHER")
								.col("adress","ADDRESS")
								.col("qty","QTY")
								.col("price","PRICE")
								.col("brwcopies","BRWCOPIES")
				)
				.withEscaper(null)
				.withAlignedColumnWidths()
				.withLineBreakCRLF()
				.withAlign(VerticalAlign.Left)
				// Where to save the table.
//                .toFile("target/sample.csv")
				// By default, the System.out is used, which can be specified as:
				// .to(System.out)
				.run()
		;
	  	
	  	System.out.println();
	  	user.menu(database, user);
		
	}

}
