package telran.company;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"telran"})
public class CompanyApplApplication {

	private static final Object SHUTDOWN = "shutdown";

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(CompanyApplApplication.class,  args);
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("To stop server type " + SHUTDOWN);
			String line = scanner.nextLine();
			if (line.equals(SHUTDOWN)) {
				break;
			}
		}
		ctx.close();
	}

}
