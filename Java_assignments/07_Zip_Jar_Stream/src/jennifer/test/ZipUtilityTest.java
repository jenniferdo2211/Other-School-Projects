package jennifer.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jennifer.utility.ZipUtility;

class ZipUtilityTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void searchByName() {
		final String sequence = "Generics";
		Path path1 = Paths.get("C:\\Users\\s17774\\eclipse-workspace\\assignment-07\\Lectures.zip");
		List<Path> list = ZipUtility.searchByName(path1, sequence);

		Assert.assertEquals("Lectures\\01-Generics.pptx", list.get(0).toString());
	}
	
	@Test
	void searchByContent() {
		final String sequence2 = "Ohho";
		Path path2 = Paths.get("C:\\Users\\s17774\\eclipse-workspace\\assignment-07\\Lectures.zip");
		List<Path> list = ZipUtility.searchByContent(path2, sequence2);
		
		Assert.assertEquals("Lectures\\input\\input.txt", list.get(0).toString());
		
	}

}
