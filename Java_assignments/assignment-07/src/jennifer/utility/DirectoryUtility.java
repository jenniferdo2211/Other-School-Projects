package jennifer.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DirectoryUtility {

	public static List<Path> searchByName(Path path, String str) {
		if (!Files.exists(path)) {
			System.err.println("File does not exist");
			return null;
		}
		
		List<Path> list = null;
		Predicate<Path> containString = t -> t.getFileName().toString().contains(str);
		
		if (Files.isRegularFile(path) && containString.test(path)) {
			list = new ArrayList<>();
			list.add(path);
			return list;
		}
		
		try {
			list = Files.walk(path)
								.parallel()
								.filter(containString)
								.sequential()
								.collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static List<Path> searchByContent(Path path, String content) {
		if (!Files.exists(path)) {
			System.err.println("File does not exist");
			return null;
		}
		
		List<Path> list = null;
		Predicate<Path> containString = f -> {
			try {
				return Files.lines(f).filter(s -> s.contains(content)).findFirst().isPresent();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		};
		
		try {
			list = Files.walk(path)
					.parallel()
					.filter(Files::isRegularFile)
					.filter(containString)
					.sequential()
					.collect(Collectors.toList());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
}
