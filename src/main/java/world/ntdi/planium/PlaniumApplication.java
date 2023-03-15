package world.ntdi.planium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class PlaniumApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaniumApplication.class, args);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			File dir = new File("src/main/resources/static/cache");
			File[] directoryListing = dir.listFiles();
			if (directoryListing != null) {
				for (File child : directoryListing) {
					child.delete();
				}
			}
		}));
	}

}
