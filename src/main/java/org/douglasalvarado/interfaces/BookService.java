package org.douglasalvarado.interfaces;

import org.douglasalvarado.dto.BookDto;
import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);
    List<BookDto> listBooks();
    BookDto findByBook(Integer id);
    BookDto updateBook(BookDto bookDto, Integer id);
    void deleteBook(Integer id);
    BookDto reservarBook(Integer id, boolean reserva);
}
