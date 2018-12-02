package rough;

public class Main {

		public static void main(String[] args) {
		    new Thread(new Task("logs/A.log", "com.company.A")).start();
		    new Thread(new Task("logs/B.log", "com.company.B")).start();
		}
}
