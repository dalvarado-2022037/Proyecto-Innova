package org.douglasalvarado.service.mongo;

import org.douglasalvarado.dto.BookDto;
import org.douglasalvarado.model.mongo.BookMongo;
import org.douglasalvarado.repository.mongo.BookMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceMongoImplTest {

    @Mock
    private BookMongoRepository bookRepository;

    @InjectMocks
    private BookServiceMongoImpl bookService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para crear un libro exitosamente
    @Test
    void createBookSuccessfully() {
        BookMongo book = new BookMongo("1", "Title", "Author", "ISBN", true);
        BookDto bookDto = new BookDto((long) 1, "Title", "Author", "ISBN", true);
        when(bookRepository.save(any(BookMongo.class))).thenReturn(book);

        BookDto result = bookService.createBook(bookDto);

        assertNotNull(result);
        assertEquals("Title", result.getTitle());
    }

    // Test para listar todos los libros
    @Test
    void listAllBooks() {
        List<BookMongo> books = List.of(new BookMongo("1", "Title1", "Author1", "ISBN1", true));
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> result = bookService.listBooks();

        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    // Test para encontrar un libro por ID exitosamente
    @Test
    void findBookByIdSuccessfully() {
        BookMongo book = new BookMongo("1", "Title", "Author", "ISBN", true);
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        BookDto result = bookService.findByBook(1);

        assertNotNull(result);
        assertEquals("Title", result.getTitle());
    }

    // Test para manejar cuando un libro no es encontrado por ID
    @Test
    void findBookByIdNotFound() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        BookDto result = bookService.findByBook(1);

        assertNull(result);
    }

    // Test para actualizar un libro exitosamente
    @Test
    void updateBookSuccessfully() {
        BookMongo book = new BookMongo("1", "Title", "Author", "ISBN", true);
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));
        when(bookRepository.save(any(BookMongo.class))).thenReturn(book);

        BookDto bookDto = new BookDto((long) 1, "Updated Title", "Author", "ISBN", true);
        BookDto result = bookService.updateBook(bookDto, 1);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    // Test para manejar cuando un libro a actualizar no es encontrado
    @Test
    void updateBookNotFound() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        BookDto result = bookService.updateBook(new BookDto(), 1);

        assertNull(result);
    }

    // Test para eliminar un libro exitosamente
    @Test
    void deleteBookSuccessfully() {
        doNothing().when(bookRepository).deleteById("1");

        bookService.deleteBook(1);

        verify(bookRepository, times(1)).deleteById("1");
    }

    // Test para reservar un libro exitosamente
    @Test
    void reservarBookSuccessfully() {
        BookMongo book = new BookMongo("1", "Title", "Author", "ISBN", true);
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        BookDto result = bookService.reservarBook(1, false);

        assertNotNull(result);
        assertFalse(result.getAvailable());
    }

    // Test para manejar cuando un libro a reservar no es encontrado
    @Test
    void reservarBookNotFound() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        BookDto result = bookService.reservarBook(1, false);

        assertNull(result);
    }
}
