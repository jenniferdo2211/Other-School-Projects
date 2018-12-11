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
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZipUtility {
	public static List<Path> searchByName(Path path, String str) {
		if (!Files.exists(path))
			return null;
		
		List<Path> list = null;
		Predicate<ZipEntry> containString = entry -> entry.getName().contains(str);
		Function<ZipEntry, Path> getPath = zip -> Paths.get(zip.getName());
		
		try {
			ZipFile zip = new ZipFile(path.toFile());
			list = zip.stream()
					.parallel()
					.filter(containString)
					.map(getPath)
					.sequential()
					.collect(Collectors.toList());
			zip.close();
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
		
		List<Path> list = null;

		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(path.toFile());
		} catch (ZipException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		final ZipFile zipFile2 = zipFile;
		Predicate<ZipEntry> containString = entry -> {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(zipFile2.getInputStream(entry)));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.contains(content)) {
						return true;
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		};
		
		Function<ZipEntry, Path> convertToPath = zip -> Paths.get(zip.toString());
		
		/////////////////////////////////////
		try {
			
			list = zipFile2.stream()
					.parallel()
					.filter(entry -> !entry.isDirectory())
					.filter(containString)
					.map(convertToPath)
					.sequential()
					.collect(Collectors.toList());
			zipFile2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
