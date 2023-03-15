package world.ntdi.planium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class PlaniumApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(PlaniumApplication.class, args);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			File dir = new File("image/cache");
			File[] directoryListing = dir.listFiles();
			if (directoryListing != null) {
				for (File child : directoryListing) {
					child.delete();
				}
			}
		}));
	}

}
