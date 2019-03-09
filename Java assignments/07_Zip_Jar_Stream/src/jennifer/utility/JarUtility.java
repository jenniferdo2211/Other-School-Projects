package jennifer.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class JarUtility {
	public static List<Path> searchByName(Path path, String str) {
		if (!Files.exists(path))
			return null;

		List<Path> list = null;
		Predicate<JarEntry> containString = entry -> entry.getName().contains(str);
		Function<JarEntry, Path> getPath = jar -> Paths.get(jar.getName());

		try {
			JarFile jar = new JarFile(path.toFile());
			list = jar.stream().filter(containString).map(getPath).collect(Collectors.toList());
			jar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static List<Path> searchByContent(Path path, String content) {
		if (!Files.exists(path)) {
			System.err.println("Zip File does not exist");
			return null;
		}

		JarFile jarFile = null;
		try {
			jarFile = new JarFile(path.toFile());
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		List<Path> list = null;
		
		final JarFile jarFile2 = jarFile;
		Predicate<JarEntry> containString = entry -> {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(jarFile2.getInputStream(entry)));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.contains(content)) {
						br.close();
						return true;
					}
				}
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		};
		
		
		Function<JarEntry, Path> convertToPath = jar -> Paths.get(jar.toString());

		/////////////////////////////////////
		try {

			list = jarFile.stream()
					.parallel()
					.filter(entry -> !entry.isDirectory())
					.filter(containString)
					.map(convertToPath)
					.sequential()
					.collect(Collectors.toList());
			jarFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

}
