package org.douglasalvarado.service.mongo;

import org.douglasalvarado.dto.BookDto;
import org.douglasalvarado.model.mongo.BookMongo;
import org.douglasalvarado.repository.mongo.BookMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceMongoImpl implements org.douglasalvarado.interfaces.BookService {
    private final BookMongoRepository bookRepository;

    public BookServiceMongoImpl(BookMongoRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        return toDto(bookRepository.save(toModel(bookDto)));
    }

    @Override
    public List<BookDto> listBooks() {
        return bookRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public BookDto findByBook(Integer id) {
        String idString = id.toString();
        BookMongo book = bookRepository.findById(idString).orElse(null);
        return book != null ? toDto(book) : null;
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Integer id) {
        String idString = id.toString();
        BookMongo bookExist = bookRepository.findById(idString).orElse(null);
        if (bookExist != null) {
            bookExist.setAuthor(bookDto.getAuthor());
            bookExist.setTitle(bookDto.getTitle());
            bookExist.setIsbn(bookDto.getIsbn());
            return toDto(bookRepository.save(bookExist));
        }
        return null;
    }

    @Override
    public void deleteBook(Integer id) {
        String idString = id.toString();
        bookRepository.deleteById(idString);
    }

    @Override
    public BookDto reservarBook(Integer id, boolean reserva) {
        String idString = id.toString();
        BookMongo bookExist = bookRepository.findById(idString).orElse(null);
        if (bookExist != null) {
            bookExist.setAvailable(reserva);
            return toDto(bookExist);
        }
        return null;
    }

    private BookMongo toModel(BookDto bookDto) {
        return BookMongo.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .available(bookDto.getAvailable())
                .build();
    }

    private BookDto toDto(BookMongo book) {
        return BookDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .available(book.getAvailable())
                .build();
    }
}
