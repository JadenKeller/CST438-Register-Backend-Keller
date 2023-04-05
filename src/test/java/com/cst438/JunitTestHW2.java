package com.cst438;

import com.cst438.controller.StudentController;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {StudentController.class})
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
class JunitTestHW2 {

    final String TEST_STUDENT_EMAIL = "test@csumb.edu";

    final String TEST_STUDENT_NAME  = "test";

    @MockBean
    GradebookService gradebookService;

    @MockBean
    StudentRepository studentRepository;

    @Autowired
    MockMvc mvc;

    @Test
    void addStudentTest() throws Exception{
        Student student = new Student();
        student.setEmail(TEST_STUDENT_EMAIL);
        student.setName(TEST_STUDENT_NAME);
        student.setStatusCode(0);
        student.setStudent_id(1);

        given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);
        given(studentRepository.save(any(Student.class))).willReturn(student);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders
                    .post("/student?email=" +  TEST_STUDENT_EMAIL + "&name=" + TEST_STUDENT_NAME))
                .andReturn().getResponse();


        assertEquals(200, response.getStatus());

        //verify(studentRepository).save(any(Student.class));

        verify(studentRepository, times(1)).findByEmail(TEST_STUDENT_EMAIL);
    }

    @Test
    void changeStatusTest() throws Exception{
        Student student = new Student();
        student.setEmail(TEST_STUDENT_EMAIL);
        student.setName(TEST_STUDENT_NAME);
        student.setStatusCode(0);
        student.setStudent_id(1);

        given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(student);


        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/student/hold?email=" +  TEST_STUDENT_EMAIL + "&code=1"))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());

        assertEquals(1, studentRepository.findByEmail(TEST_STUDENT_EMAIL).getStatusCode());
    }
}
