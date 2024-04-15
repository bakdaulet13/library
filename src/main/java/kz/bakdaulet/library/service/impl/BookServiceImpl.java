package kz.bakdaulet.library.service.impl;

import kz.bakdaulet.library.model.Book;
import kz.bakdaulet.library.model.User;
import kz.bakdaulet.library.repository.BookRepository;
import kz.bakdaulet.library.repository.UserRepository;
import kz.bakdaulet.library.service.AuthorService;
import kz.bakdaulet.library.service.BookService;
import kz.bakdaulet.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final PublisherService publisherService;
    private final AuthorService authorService;
    private final UserRepository userService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, PublisherService publisherService, AuthorService authorService, UserRepository userService) {
        this.bookRepository = bookRepository;
        this.publisherService = publisherService;
        this.authorService = authorService;
        this.userService = userService;
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> searchBooks(String keyword, Long userId) {
        List<Book> books = bookRepository.searchByNameStartsWith(keyword);
        List<Book> filteredBooks = new ArrayList<>();
        User user = userService.findById(userId).orElseThrow();
        for (Book book : books) {
            if (!user.getBooks().contains(book)) {
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void createBook(Book book) {
        System.out.println(book);
        System.out.println(book.getAuthor());
        System.out.println(book.getPublisher());
        Book newBook = new Book.Builder()
                .name(book.getName())
                .description(book.getDescription())
                .author(authorService.findAuthorById(book.getAuthor().getId()))
                .publisher(publisherService.findPublisherById(book.getPublisher().getId()))
                .build();

        bookRepository.save(newBook);
    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
