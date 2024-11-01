package org.douglasalvarado.controller;

import org.douglasalvarado.dto.BookDto;
import org.douglasalvarado.interfaces.BookService;
import org.douglasalvarado.service.BookDatabaseServiceSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    @Mock
    private BookDatabaseServiceSelector serviceSelector;

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(serviceSelector.getBookService()).thenReturn(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    // Test para crear un libro exitosamente
    @Test
    void createBookSuccessfully() throws Exception {
        BookDto bookDto = new BookDto((long) 1, "Title", "Author", "ISBN", true);
        when(serviceSelector.getBookService().createBook(any(BookDto.class))).thenReturn(bookDto);

        mockMvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Title\", \"author\": \"Author\", \"isbn\": \"ISBN\", \"available\": true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"));
    }

    // Test para obtener un libro por ID exitosamente
    @Test
    void getBookByIdSuccessfully() throws Exception {
        BookDto bookDto = new BookDto((long)1, "Title", "Author", "ISBN", true);
        when(serviceSelector.getBookService().findByBook(1)).thenReturn(bookDto);

        mockMvc.perform(get("/book/find-by/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"));
    }

    // Test para manejar cuando un libro no es encontrado
    @Test
    void getBookByIdNotFound() throws Exception {
        when(serviceSelector.getBookService().findByBook(anyInt())).thenReturn(null);

        mockMvc.perform(get("/book/find-by/1"))
                .andExpect(status().isNotFound());
    }

    // Test para obtener todos los libros
    @Test
    void getAllBooksSuccessfully() throws Exception {
        List<BookDto> books = List.of(new BookDto((long)1, "Title1", "Author1", "ISBN1", true));
        when(serviceSelector.getBookService().listBooks()).thenReturn(books);

        mockMvc.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title1"));
    }

    // Test para actualizar un libro exitosamente
    @Test
    void updateBookSuccessfully() throws Exception {
        BookDto updatedBook = new BookDto((long)1, "Updated Title", "Updated Author", "ISBN", true);
        when(serviceSelector.getBookService().updateBook(any(BookDto.class), anyInt())).thenReturn(updatedBook);

        mockMvc.perform(put("/book/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Updated Title\", \"author\": \"Updated Author\", \"isbn\": \"ISBN\", \"available\": true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    // Test para manejar cuando un libro a actualizar no es encontrado
    @Test
    void updateBookNotFound() throws Exception {
        when(serviceSelector.getBookService().updateBook(any(BookDto.class), anyInt())).thenReturn(null);

        mockMvc.perform(put("/book/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Updated Title\", \"author\": \"Updated Author\", \"isbn\": \"ISBN\", \"available\": true}"))
                .andExpect(status().isNotFound());
    }

    // Test para eliminar un libro exitosamente
    @Test
    void deleteBookSuccessfully() throws Exception {
        when(serviceSelector.getBookService()).thenReturn(bookService);
    
        doNothing().when(bookService).deleteBook(1);
    
        mockMvc.perform(delete("/book/delete/1"))
                .andExpect(status().isOk());
    }
    

    // Test para reservar un libro exitosamente
    @Test
    void reservarBookSuccessfully() throws Exception {
        BookDto reservedBook = new BookDto((long)1, "Title", "Author", "ISBN", false);
        when(serviceSelector.getBookService().reservarBook(1, false)).thenReturn(reservedBook);

        mockMvc.perform(post("/book/1/reserve")
                .param("reserva", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false));
    }

    // Test para manejar cuando un libro a reservar no es encontrado
    @Test
    void reservarBookNotFound() throws Exception {
        when(serviceSelector.getBookService().reservarBook(anyInt(), anyBoolean())).thenReturn(null);

        mockMvc.perform(post("/book/1/reserve")
                .param("reserva", "false"))
                .andExpect(status().isNotFound());
    }
}
