package library.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import library.entity.book.Book;
import library.entity.book.Borrowing;
import library.entity.order.Order;
import library.entity.user.Admin;
import library.entity.user.NormalUser;
import library.entity.user.User;

public class Database {

	private final ArrayList<User> users = new ArrayList<User>();
	private final ArrayList<String> usernames = new ArrayList<String>();
	private final ArrayList<Book> books = new ArrayList<Book>();
	private final ArrayList<String> booknames = new ArrayList<String>();
	private final ArrayList<Order> orders = new ArrayList<Order>();
	private final ArrayList<Borrowing> borrowings = new ArrayList<Borrowing>();
	
	private final File userFile = new File("src/main/resources/data/Users.txt");
	private final File booksFile = new File("src/main/resources/data/Books.txt");
	private final File ordersFile = new File("src/main/resources/data/Orders.txt");
	private final File borrowingsFile = new File("src/main/resources/data/Borrowings.txt");
	private final File folder = new File("src/main/resources/data");
	
	public Database() {
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (!userFile.exists()) {
			try {
				userFile.createNewFile();
			}catch (Exception e) {}
		}
		if(!booksFile.exists()) {
			try {
				booksFile.createNewFile();
			}catch (Exception e) {}
		}
		if(!ordersFile.exists()) {
			try {
				ordersFile.createNewFile();
			}catch (Exception e) {}
		}
		if(!borrowingsFile.exists()) {
			try {
				borrowingsFile.createNewFile();
			}catch (Exception e) {}
		}
		getUsers();
		getBooks();
		getOrders();
		getBorrowings();
	}
	
	public void AddUser(User s) {
		users.add(s);
		usernames.add(s.getName());
		saveUsers();
	}
	
	public int login(String phonenumber, String email) {
		int n = -1;
		for(User s : users) {
			if (s.getPhoneNumber().matches(phonenumber) && s.getEmail().matches(email)) {
				n = users.indexOf(s);
				break;
			}
		}
		return n;
	}
	public User getUser(int n) {
		return users.get(n);
	}
	
	public void AddBook(Book book) {
		books.add(book);
		booknames.add(book.getName());
		saveBooks();
	}
	
	private void getUsers() {
		String text1 = "";
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(userFile));
			String s1;
			while ((s1 = br1.readLine()) != null) {
				text1 = text1 + s1;
			}
			br1.close();
		}catch(Exception e) {
			System.err.println(e.toString());
		}
		if(!text1.matches("") || !text1.isEmpty()) {
			String[] a1 = text1.split("<NewUser/>");
			for (String s : a1) {
				String[]a2 = s.split("<N/>");
				if (a2[3].matches("Admin")) {
					User user = new Admin(a2[0], a2[1], a2[2]);
					users.add(user);
					usernames.add(user.getName());
				}else {
					User user = new NormalUser(a2[0], a2[1], a2[2]);
					users.add(user);
					usernames.add(user.getName());
				}
			}
		}
	}
	
	private void saveUsers() {
		String text1 = "";
		for(User user : users) {
			text1 = text1 + user.toString() + "<NewUser/>\n";
		}
		try {
			PrintWriter pw = new PrintWriter(userFile);
			pw.print(text1);
			pw.close();
		}catch(Exception e) {
			System.err.println(e.toString());
		}
	}
	
	private void saveBooks() {
		String text1 = "";
		for(Book book : books) {
			text1 = text1 + book.writeToFile() + "<NewBook/>\n";
		}
		try {
			PrintWriter pw = new PrintWriter(booksFile);
			pw.print(text1);
			pw.close();
		}catch(Exception e) {
			System.err.println(e.toString());
		}
	}
	
	private void getBooks() {
		String text1 = "";
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(booksFile));
			String s1;
			while ((s1 = br1.readLine()) != null) {
				text1 = text1 + s1;
			}
			br1.close();
		}catch(Exception e) {
			System.err.println(e.toString());
		}
		if(!text1.matches("") || !text1.isEmpty()) {
			String[] a1 = text1.split("<NewBook/>");
			for (String s : a1) {
				Book book = parseBook(s);
				books.add(book);
				booknames.add(book.getName());
			}
		}
	}
	
	public Book parseBook(String s) {
		String[] a = s.split("<N/>");
		Book book = new Book();
		book.setName(a[0]);
		book.setAuthor(a[1]);
		book.setPublisher(a[2]);
		book.setAdress(a[3]);
		book.setQty(Integer.parseInt(a[4]));
		book.setPrice(Double.parseDouble(a[5]));
		book.setBrwcopies(Integer.parseInt(a[6]));
		return book;
	}
	
	public ArrayList<Book> getAllBooks() {
		return books;
	}
	
	public int getBook(String bookname) {
		int i = -1;
		for (Book book : books) {
			if (book.getName().matches(bookname)) {
				i = books.indexOf(book);
			}
		}
		return i;
	}
	
	public Book getBook(int i) {
		return books.get(i);
	}
	
	public void deleteBook(int i) {
		books.remove(i);
		booknames.remove(i);
		saveBooks();
	}
	
	public void deleteAllData() {
		if (userFile.exists()) {
			try {
				userFile.delete();
			}catch (Exception e) {}
		}
		if(booksFile.exists()) {
			try {
				booksFile.delete();
			}catch (Exception e) {}
		}
		if(ordersFile.exists()) {
			try {
				ordersFile.delete();
			}catch (Exception e) {}
		}
		if(borrowingsFile.exists()) {
			try {
				borrowingsFile.delete();
			}catch (Exception e) {}
		}
	}
	
	public void addOrder(Order order, Book book, int bookindex) {
		orders.add(order);
		books.set(bookindex, book);
		saveOrders();
		saveBooks();
	}
	
	private void saveOrders() {
		String text1 = "";
		for(Order order : orders) {
			text1 = text1 + order.toString2() + "<NewOrder/>\n";
		}
		try {
			PrintWriter pw = new PrintWriter(ordersFile);
			pw.print(text1);
			pw.close();
		}catch(Exception e) {
			System.err.println(e.toString());
		}
	}
	
	private void getOrders() {
		String text1 = "";
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(ordersFile));
			String s1;
			while ((s1 = br1.readLine()) != null) {
				text1 = text1 + s1;
			}
			br1.close();
		}catch(Exception e) {
			System.err.println(e.toString());
		}
		if(!text1.matches("") || !text1.isEmpty()) {
			String[] a1 = text1.split("<NewOrder/>");
			for (String s : a1) {
				Order order = parseOrder(s);
				orders.add(order);
			}
		}
	}
	
	public boolean userExists(String name) {
		boolean f = false;
		for (User user : users) {
			if(user.getName().toLowerCase().matches(name.toLowerCase())) {
				f=true;
				break;
			}
		}
		return f;
	}
	
	private User getUserByName(String name) {
		User u = new NormalUser("");
		for (User user : users) {
			if(user.getName().matches(name)) {
				u = user;
				break;
				
			}
		}
		return u;
	}
	
	private Order parseOrder(String s) {
		String[]a = s.split("<N/>");
		Order order = new Order(books.get(getBook(a[0])), getUserByName(a[1]),
				Double.parseDouble(a[2]), Integer.parseInt(a[3]));
		return order;
	}
	
	public ArrayList<Order> getAllOrders() {
		return orders;
	}
	
	private void saveBorrowings() {
		String text1 = "";
		for(Borrowing borrowing : borrowings) {
			text1 = text1 + borrowing.toString2() + "<NewBorrowing/>\n";
		}
		try {
			PrintWriter pw = new PrintWriter(borrowingsFile);
			pw.print(text1);
			pw.close();
		}catch(Exception e) {
			System.err.println(e.toString());
		}
	}
	
	private void getBorrowings() {
		String text1 = "";
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(borrowingsFile));
			String s1;
			while ((s1 = br1.readLine()) != null) {
				text1 = text1 + s1;
			}
			br1.close();
		}catch(Exception e) {
			System.err.println(e.toString());
		}
		if(!text1.matches("") || !text1.isEmpty()) {
			String[] a1 = text1.split("<NewBorrowing/>");
			for (String s : a1) {
				Borrowing borrowing = parseBorrowing(s);
				borrowings.add(borrowing);
			}
		}	
	}
	
	private Borrowing parseBorrowing(String s) {
		String[] a = s.split("<N/>");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate start = LocalDate.parse(a[0], formatter);
		LocalDate finish = LocalDate.parse(a[1], formatter);
		Book book = getBook(getBook(a[3]));
		User user = getUserByName(a[4]);
		Borrowing brw = new Borrowing(start, finish, book, user);
		return brw;
	}
	
	public void borrowBook(Borrowing brw, Book book, int bookindex) {
		borrowings.add(brw);
		books.set(bookindex, book);
		saveBorrowings();
		saveBooks();
	}
	
	public ArrayList<Borrowing> getBrws(){
		return borrowings;
	}
	
	public void returnBook(Borrowing b, Book book, int bookindex) {
		borrowings.remove(b);
		books.set(bookindex, book);
		saveBorrowings();
		saveBooks();
	}
	
}
