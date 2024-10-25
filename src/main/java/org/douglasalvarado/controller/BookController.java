package org.douglasalvarado.controller;

import org.douglasalvarado.dto.BookDto;
import org.douglasalvarado.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        try {
            BookDto createdBook = bookService.createBook(bookDto);
            return ResponseEntity.ok(createdBook);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/find-by/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Integer id) {
        Optional<BookDto> book = Optional.ofNullable(bookService.findByBook(id));
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.listBooks();
        return ResponseEntity.ok(books);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Integer id, @RequestBody BookDto bookDto) {
        try {
            BookDto updatedBook = bookService.updateBook(bookDto, id);
            return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.status(404).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

