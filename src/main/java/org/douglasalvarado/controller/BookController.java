package org.douglasalvarado.controller;

import org.douglasalvarado.dto.BookDto;
import org.douglasalvarado.service.BookDatabaseServiceSelector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookDatabaseServiceSelector serviceSelector;

    @PostMapping("/create")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        try {
            BookDto createdBook = serviceSelector.getBookService().createBook(bookDto);
            return ResponseEntity.ok(createdBook);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/find-by/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Integer id) {
        BookDto book = serviceSelector.getBookService().findByBook(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.status(404).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = serviceSelector.getBookService().listBooks();
        return ResponseEntity.ok(books);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Integer id, @RequestBody BookDto bookDto) {
        try {
            BookDto updatedBook = serviceSelector.getBookService().updateBook(bookDto, id);
            return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.status(404).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        try {
            serviceSelector.getBookService().deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/{id}/reserve")
    public ResponseEntity<BookDto> reservarBook(@PathVariable Integer id, @RequestParam boolean reserva) {
        try {
            BookDto reservedBook = serviceSelector.getBookService().reservarBook(id, reserva);
            return reservedBook != null ? ResponseEntity.ok(reservedBook) : ResponseEntity.status(404).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
