package com.example.springLibraryTest.daoTest;

import com.example.springLibrary.dao.BookDAO;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookDAOTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDAO bookDAO;

    private int id;
    private Book book;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        id = 1;
        book = new Book("Test Book1", "Test Author1", 1999);
    }

    @Test
    public void testIndex(){
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(new Book("Test Book2", "Test Author2", 2000));

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class))).thenReturn(books);
        List<Book> result = bookDAO.index();
        assertEquals(books, result);
    }

    @Test
    public void testShow(){
        when(jdbcTemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), anyInt())).thenReturn(book);
        Book result = bookDAO.show(id);
        assertEquals(book, result);
    }

    @Test
    public void testSave(){
        bookDAO.save(book);
        verify(jdbcTemplate).update(eq("INSERT INTO Book (title, author, year_of_publication) VALUES (?,?,?)"),
                eq("Test Book1"), eq("Test Author1"), eq(1999));
    }

    @Test
    public void testUpdate(){
        bookDAO.update(id, book);
        verify(jdbcTemplate).update(eq("UPDATE Book SET title=?, author=?, year_of_publication=? WHERE id = ?"),
                eq("Test Book1"), eq("Test Author1"), eq(1999), eq(id));
    }

    @Test
    public void testDelete(){
        bookDAO.delete(id);
        verify(jdbcTemplate).update(eq("DELETE FROM Book WHERE id =?"), eq(id));
    }
     @Test
    public void testGetBookOwner(){
        Person person = new Person("Test Person", 2000);
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(BeanPropertyRowMapper.class))).thenReturn(Arrays.asList(person));
        Optional<Person> result = bookDAO.getBookOwner(id);

        assertTrue(result.isPresent());
        assertEquals(person, result.get());
     }

    @Test
    public void testRelease(){
        bookDAO.release(id);
        verify(jdbcTemplate).update(eq("UPDATE Book SET person_id=NULL WHERE id=?"), eq(id));
    }

    @Test
    public void testAssign(){
        Person person = new Person("Test Person", 2000);
        person.setId(3);
        bookDAO.assign(id, person);
        verify(jdbcTemplate).update(eq("UPDATE Book SET person_id=? WHERE id=?"), eq(3), eq(id));
    }
}
