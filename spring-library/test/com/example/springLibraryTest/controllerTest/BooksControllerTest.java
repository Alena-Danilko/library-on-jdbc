package com.example.springLibraryTest.controllerTest;

import com.example.springLibrary.controller.BooksController;
import com.example.springLibrary.dao.BookDAO;
import com.example.springLibrary.dao.PersonDAO;
import com.example.springLibrary.models.Book;
import com.example.springLibrary.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BooksControllerTest {
    @Mock
    private BookDAO bookDAO;

    @Mock
    private PersonDAO personDAO;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private BooksController booksController;

    private int id;
    private Book book;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        id = 1;
        book = new Book("The Master and Margarita", "Bulgakov Mikhail", 1967);
    }

    @Test
    public void testIndex(){
        List<Book> books = new ArrayList<>();
        when(bookDAO.index()).thenReturn(books);

        String result = booksController.index(model);
        assertEquals("books/index", result);
        verify(bookDAO).index();
    }

    @Test
    public void testShow(){
        Person person = new Person("Test Person", 2000);
        when(bookDAO.show(id)).thenReturn(book);
        when(bookDAO.getBookOwner(id)).thenReturn(Optional.of(person));

        String result = booksController.show(id, model, person);
        assertEquals("books/show", result);
        verify(model).addAttribute("owner", person);

        List<Person> people = new ArrayList<>();
        when(bookDAO.getBookOwner(id)).thenReturn(Optional.empty());

        result = booksController.show(id, model, new Person());
        assertEquals("books/show", result);
        verify(model).addAttribute(eq("people"), anyList());
    }

    @Test
    public void testNewBook(){
        String result = booksController.newBook(book);
        assertEquals("books/new", result);
    }

    @Test
    public void testCreate(){
        when(bindingResult.hasErrors()).thenReturn(false);
        String result = booksController.create(book, bindingResult);
        assertEquals("redirect:/books", result);

        when(bindingResult.hasErrors()).thenReturn(true);
        result = booksController.create(book, bindingResult);
        assertEquals("books/new", result);
    }

    @Test
    public void testEdit(){
        when(bookDAO.show(id)).thenReturn(book);

        String result = booksController.edit(model, id);
        assertEquals("books/edit", result);
    }

    @Test
    public void testUpdate(){
        when(bindingResult.hasErrors()).thenReturn(false);
        String result = booksController.update(book, id, bindingResult);
        assertEquals("redirect:/books", result);

        when(bindingResult.hasErrors()).thenReturn(true);
        result = booksController.update(book, id, bindingResult);
        assertEquals("books/edit", result);
    }

    @Test
    public void testDelete(){
        String result = booksController.delete(id);
        assertEquals("redirect:/books", result);
    }

    @Test
    public void testRelease(){
        String result = booksController.release(id);
        assertEquals("redirect:/books/" +id, result);
    }

    @Test
    public void testAssign(){
        Person person = new Person("Test Person", 2000);
        String result = booksController.assign(id, person);
        assertEquals("redirect:/books/"+id, result);
    }
}
