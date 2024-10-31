package org.douglasalvarado.controller;

import org.douglasalvarado.dto.BookDto;
import org.douglasalvarado.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    public BookControllerTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    // Crear un libro correctamente
    @Test
    void testCreateBook() throws Exception {
        BookDto bookDto = new BookDto();
        when(bookService.createBook(any(BookDto.class))).thenReturn(bookDto);

        mockMvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\": \"Nuevo Libro\", \"autor\": \"Autor Ejemplo\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Crear un libro con error de servidor
    @Test
    void testCreateBookServerError() throws Exception {
        when(bookService.createBook(any(BookDto.class)))
                .thenThrow(new RuntimeException("Error al crear el libro"));

        mockMvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\": \"Nuevo Libro\", \"autor\": \"Autor Ejemplo\"}"))
                .andExpect(status().isInternalServerError());
    }

    // Obtener un libro por ID correctamente
    @Test
    void testGetBook() throws Exception {
        BookDto bookDto = new BookDto();
        when(bookService.findByBook(1)).thenReturn(bookDto);

        mockMvc.perform(get("/book/find-by/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Obtener un libro por ID Not Found
    @Test
    void testGetBookNotFound() throws Exception {
        when(bookService.findByBook(1)).thenReturn(null);

        mockMvc.perform(get("/book/find-by/1"))
                .andExpect(status().isNotFound());
    }

    // Obtener todos los libros correctamente
    @Test
    void testGetAllBooks() throws Exception {
        List<BookDto> books = Arrays.asList(new BookDto(), new BookDto());
        when(bookService.listBooks()).thenReturn(books);

        mockMvc.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Actualizar un libro correctamente
    @Test
    void testUpdateBook() throws Exception {
        BookDto updatedBook = new BookDto();
        when(bookService.updateBook(any(BookDto.class), eq(1))).thenReturn(updatedBook);

        mockMvc.perform(put("/book/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\": \"Libro Actualizado\", \"autor\": \"Autor Ejemplo\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Actualizar un libro Not Found
    @Test
    void testUpdateBookNotFound() throws Exception {
        when(bookService.updateBook(any(BookDto.class), eq(1))).thenReturn(null);

        mockMvc.perform(put("/book/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\": \"Libro Actualizado\", \"autor\": \"Autor Ejemplo\"}"))
                .andExpect(status().isNotFound());
    }

    // Actualizar un libro con error de servidor
    @Test
    void testUpdateBookServerError() throws Exception {
        when(bookService.updateBook(any(BookDto.class), eq(1)))
                .thenThrow(new RuntimeException("Error al actualizar el libro"));

        mockMvc.perform(put("/book/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\": \"Libro Actualizado\", \"autor\": \"Autor Ejemplo\"}"))
                .andExpect(status().isInternalServerError());
    }

    // Eliminar un libro correctamente
    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/book/delete/1"))
                .andExpect(status().isOk());
    }

    // Eliminar un libro con error de servidor
    @Test
    void testDeleteBookServerError() throws Exception {
        doThrow(new RuntimeException("Error al eliminar el libro")).when(bookService).deleteBook(1);

        mockMvc.perform(delete("/book/delete/1"))
                .andExpect(status().isInternalServerError());
    }
}
