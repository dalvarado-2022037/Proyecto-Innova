package org.douglasalvarado.service;

import org.douglasalvarado.dto.BookDto;
import org.douglasalvarado.model.Book;
import org.douglasalvarado.repository.BookRepository;
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

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Long id = (long) 1;        
        book = Book.builder()
                .id(id)
                .title("El Principito")
                .author("Antoine de Saint-Exupéry")
                .isbn("978-0156012195")
                .available(true)
                .build();

        // Inicializar el DTO
        bookDto = BookDto.builder()
                .id(id)
                .title("El Principito")
                .author("Antoine de Saint-Exupéry")
                .isbn("978-0156012195")
                .available(true)
                .build();
    }

    @Test
    void testCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto createdBook = bookService.createBook(bookDto);

        assertNotNull(createdBook);
        assertEquals(bookDto.getTitle(), createdBook.getTitle());
        assertEquals(bookDto.getAuthor(), createdBook.getAuthor());
        assertEquals(bookDto.getIsbn(), createdBook.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testListBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookDto> books = bookService.listBooks();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(bookDto.getTitle(), books.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testFindByBook_ExistingBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        BookDto foundBook = bookService.findByBook(1);

        assertNotNull(foundBook);
        assertEquals(bookDto.getTitle(), foundBook.getTitle());
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    void testFindByBook_NonExistingBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        BookDto foundBook = bookService.findByBook(1);

        assertNull(foundBook);
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateBook_ExistingBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto updatedBookDto = BookDto.builder()
                .title("Nuevo Título")
                .author("Nuevo Autor")
                .isbn("nuevo-isbn")
                .build();

        BookDto result = bookService.updateBook(updatedBookDto, 1);

        assertNotNull(result);
        assertEquals(updatedBookDto.getTitle(), book.getTitle());
        assertEquals(updatedBookDto.getAuthor(), book.getAuthor());
        assertEquals(updatedBookDto.getIsbn(), book.getIsbn());
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBook_NonExistingBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        BookDto result = bookService.updateBook(bookDto, 1);

        assertNull(result);
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testDeleteBook_ExistingBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(1);

        bookService.deleteBook(1);

        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteBook_NonExistingBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        bookService.deleteBook(1);

        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, never()).deleteById(1);
    }

    @Test
    void testReservarBook_ExistingBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        BookDto result = bookService.reservarBook(1, false);

        assertNotNull(result);
        assertFalse(book.getAvailable());
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    void testReservarBook_NonExistingBook() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        BookDto result = bookService.reservarBook(1, false);

        assertNull(result);
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    void testToModel() {
        Book result = bookService.toModel(bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getId(), result.getId());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getAuthor(), result.getAuthor());
        assertEquals(bookDto.getIsbn(), result.getIsbn());
        assertEquals(bookDto.getAvailable(), result.getAvailable());
    }

    @Test
    void testToDto() {
        BookDto result = bookService.toDto(book);

        assertNotNull(result);
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getAvailable(), result.getAvailable());
    }
}