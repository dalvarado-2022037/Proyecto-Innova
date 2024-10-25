package org.douglasalvarado.service;

import java.util.List;

import org.douglasalvarado.dto.BookDto;
import org.douglasalvarado.model.Book;
import org.douglasalvarado.repository.BookRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookDto createBook(BookDto bookDto){
        return toDto(bookRepository.save(toModel(bookDto)));
    }

    public List<BookDto> listBooks(){
        return bookRepository.findAll().stream()
            .map(this::toDto).toList();
    }

    public BookDto findByBook(Integer id){
        return toDto(bookRepository.findById(id).orElse(null));
    }

    public BookDto updateBook(BookDto bookDto, Integer id){
        Book bookExist = bookRepository.findById(id).orElse(null);
        if (bookExist != null) {
            bookExist.setAuthor(bookDto.getAuthor());
            bookExist.setTitle(bookDto.getTitle());
            bookExist.setIsbn(bookDto.getIsbn());
            return toDto(bookRepository.save(bookExist));
        }
        return null;
    }

    public void deleteBook(Integer id){
        Book bookExist = bookRepository.findById(id).orElse(null);
        if(bookExist != null){
            bookRepository.deleteById(id);
        }
    }

    public BookDto reservarBook(Integer id, boolean reserva){
        Book bookExist = bookRepository.findById(id).orElse(null);
        if (bookExist != null) {
            bookExist.setAvailable(reserva);
        }
        return toDto(bookExist);
    }

    public Book toModel(BookDto bookDto){
        return Book.builder()
            .id(bookDto.getId())
            .title(bookDto.getTitle())
            .author(bookDto.getAuthor())
            .isbn(bookDto.getIsbn())
            .available(bookDto.getAvailable())
            .build();
    }

    public BookDto toDto(Book book){
        return BookDto.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .isbn(book.getIsbn())
            .available(book.getAvailable())
            .build();
    }
}
