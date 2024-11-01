package org.douglasalvarado.service.postgres;

import org.douglasalvarado.dto.BookDto;
import org.douglasalvarado.interfaces.BookService;
import org.douglasalvarado.model.postgres.BookPostgres;
import org.douglasalvarado.repository.postgres.BookPostgresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServicePostgresImpl implements BookService {
    private final BookPostgresRepository bookRepository;

    public BookServicePostgresImpl(BookPostgresRepository bookRepository) {
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
        BookPostgres book = bookRepository.findById(id).orElse(null);
        return book != null ? toDto(book) : null;
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Integer id) {
        BookPostgres bookExist = bookRepository.findById(id).orElse(null);
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
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto reservarBook(Integer id, boolean reserva) {
        BookPostgres bookExist = bookRepository.findById(id).orElse(null);
        if (bookExist != null) {
            bookExist.setAvailable(reserva);
            return toDto(bookExist);
        }
        return null;
    }

    private BookPostgres toModel(BookDto bookDto) {
        return BookPostgres.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .available(bookDto.getAvailable())
                .build();
    }

    private BookDto toDto(BookPostgres book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .available(book.getAvailable())
                .build();
    }
}
