package jennifer.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jennifer.utility.DirectoryUtility;
import org.junit.Assert;

class DirectoryUtilityTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void searchByName() {

		final String sequence = "Generics";
		Path path1 = Paths.get("C:\\Users\\Thu Trang\\eclipse-workspace\\assignment-07\\Lectures\\01-Generics.pptx");
		List<Path> list = DirectoryUtility.searchByName(path1, sequence);

		Assert.assertEquals(path1, list.get(0));
	}

	@Test
	void searchByContent() {
		final String sequence2 = "Heyy";
		Path path2 = Paths.get("input\\input.txt");
		List<Path> list = DirectoryUtility.searchByContent(path2, sequence2);
		Assert.assertEquals(path2, list.get(0));
	}

}
