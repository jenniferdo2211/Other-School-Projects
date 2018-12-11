package test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import generator.StudentGenerator;
import generator.TeacherGenerator;
import group.Department;
import group.StudentGroup;
import group.Subject;
import org.junit.Assert;
import person.Student;
import person.Teacher;

class TestData {
	Set<Student> studentList;
	Set<StudentGroup> groupList;
	Set<Department> departmentList;
	Set<Subject> subjectList;
	Set<Teacher> teacherList;
	
	@BeforeEach
	void setUp() throws Exception {
		studentList = new HashSet<>();
		groupList = new HashSet<>();
		departmentList = new HashSet<>();
		subjectList = new HashSet<>();
		teacherList = new HashSet<>();
	}

	@Test
	void test() {
		Random rand = new Random();
		// list of students and list of groups of students
		for (int i = 0; i < 100; i++) {
			studentList.add(StudentGenerator.next());
		}
		Assert.assertEquals(100, studentList.size());
		
		
		int counter = 0;
		Iterator<Student> iter = studentList.iterator();
		for (int i = 0; i < 12; i++) {
			String name = "Group " + (i+1);
			Set<Student> list = new HashSet<>();
			int x = rand.nextInt(10 - 8 + 1) + 8;
			if (counter == 4) {
				x = 8;
			}
			for (int j = 0; j < x; j++) {
				if (iter.hasNext())
					list.add(iter.next());
			}
			if (x > 8) {
				counter = counter + (x - 8);
			}
			groupList.add(new StudentGroup(name, list));
		}
		
		// WEIRD
		boolean nonEmpty = true;
		for (StudentGroup st : groupList) {
			if (st.isEmpty()) {
				nonEmpty = false;
				System.out.println(st.getStudentGroup());
				break;
			}
		}
		Assert.assertTrue(nonEmpty);
		
		// list of teachers and list of departments
		for (int i = 0; i < 10; i++) {
			teacherList.add(TeacherGenerator.next());
		}
		Assert.assertEquals(10, teacherList.size());
		
		counter = 0;
		Iterator<Teacher> iter2 = teacherList.iterator();
		for (int i = 0; i < 3; i++) {
			String name = "Department " + (i+1);
			Set<Teacher> list = new HashSet<>();
			
			for (int j = 0; j < 10/3 + 1; j++) {
				if (iter2.hasNext())
					list.add(iter2.next());
			}
			departmentList.add(new Department(name, list));
		}
		
		nonEmpty = true;
		for (Department dept : departmentList) {
			if (dept.isEmpty()) {
				nonEmpty = false;
				break;
			}
		}
		Assert.assertTrue(nonEmpty);
		
		// list of subjects
		Iterator<Department> iter3 = departmentList.iterator();
		Iterator<StudentGroup> iter4 = groupList.iterator();
		Department dept = null;
		counter = 0;
		
		for (int i = 0; i < 10; i++) {
			String name = "Subject " + (i+1);
			if (i % 3 == 0 && iter3.hasNext()) {
				dept = iter3.next();
			}
			Teacher lecturer = dept.getLecturer();
			
			Set<StudentGroup> set = new HashSet<>();
			
			int x = rand.nextInt(2);
			set.add(iter4.next());
			if (x == 1 && counter < 2 && iter4.hasNext()) {
				set.add(iter4.next());
				counter++;
			}
			subjectList.add(new Subject(name, dept, lecturer, set));
		}
		
		nonEmpty = true;
		for (Subject s: subjectList) {
			if (s.isEmpty()) {
				nonEmpty = false;
				break;
			}
		}
		Assert.assertTrue(nonEmpty);
		
	}

}
