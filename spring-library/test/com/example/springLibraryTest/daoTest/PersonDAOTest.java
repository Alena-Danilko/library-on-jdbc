package com.example.springLibraryTest.daoTest;

import com.example.springLibrary.dao.PersonDAO;
import com.example.springLibrary.models.Book;
import com.example.springLibrary.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersonDAOTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PersonDAO personDAO;

    private int id;
    private Person person;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        id = 1;
        person = new Person("Test Person", 2000);
    }

    @Test
    public void testIndex(){
        List<Person> people = new ArrayList<>();
        people.add(person);
        people.add(new Person("Test Person2", 2002));

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class))).thenReturn(people);
        List<Person> result = personDAO.index();
        assertEquals(people, result);
    }

    @Test
    public void testShow(){
        when(jdbcTemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyInt())).thenReturn(person);
        Person result = personDAO.show(id);
        assertEquals(person, result);
    }

    @Test
    public void testSave(){
        personDAO.save(person);
        verify(jdbcTemplate).update(eq("INSERT INTO Person (full_name, year_of_birth) VALUES (?,?)"),
                eq("Test Person"), eq(2000));
    }

    @Test
    public void testUpdate(){
        personDAO.update(id, person);
        verify(jdbcTemplate).update(eq("UPDATE Person SET full_name=?, year_of_birth=? WHERE id = ?"),
                eq("Test Person"), eq(2000), eq(id));
    }

    @Test
    public void testDelete(){
        personDAO.delete(id);
        verify(jdbcTemplate).update(eq("DELETE FROM Person WHERE id =?"), eq(id));
    }

    @Test
    public void testGetPersonByFullName(){
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(BeanPropertyRowMapper.class)))
                .thenReturn(Arrays.asList(person));
        Optional<Person> result = personDAO.getPersonByFullName("Test Person");

        assertTrue(result.isPresent());
        assertEquals(person, result.get());
    }

    @Test
    public void testGetBookByPersonId(){
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("Test Book1", "Test Author1", 1999));
        bookList.add(new Book("Test Book2", "Test Author2", 2000));

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), anyInt())).thenReturn(bookList);
        List<Book> result = personDAO.getBookByPersonId(id);

        assertFalse(result.isEmpty());
        assertEquals(bookList, result);
    }
}
