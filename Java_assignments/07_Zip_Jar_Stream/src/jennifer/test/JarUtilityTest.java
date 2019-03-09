package jennifer.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jennifer.utility.JarUtility;

class JarUtilityTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void searchByName() {
		final String sequence = "Hello";
		Path path1 = Paths.get("C:\\Users\\s17774\\eclipse-workspace\\assignment-07\\MyProject.jar");
		List<Path> list = JarUtility.searchByName(path1, sequence);

		Assert.assertEquals("firstpacket\\HelloWorld.class", list.get(0).toString());
	}

	@Test
	void searchByContent() {
		final String sequence2 = "new";
		Path path2 = Paths.get("C:\\Users\\s17774\\eclipse-workspace\\assignment-07\\MyProject.jar");
		List<Path> list = JarUtility.searchByContent(path2, sequence2);
		
		Assert.assertEquals("firstpacket\\HelloWorld.class", list.get(0).toString());
		
	}
}
