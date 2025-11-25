package com.readcore.framework.ui;

import com.readcore.domain.entities.Book;
import com.readcore.domain.entities.BookContent;
import com.readcore.domain.entities.Bookmark;
import com.readcore.domain.repositories.BookReaderRepository;
import com.readcore.usecases.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * Main window for the ReadCore application
 */
public class MainWindow extends JFrame {
    private final GetAllBooksUseCase getAllBooksUseCase;
    private final AddBookUseCase addBookUseCase;
    private final RemoveBookUseCase removeBookUseCase;
    private final SearchBooksUseCase searchBooksUseCase;
    
    private JList<Book> bookList;
    private DefaultListModel<Book> bookListModel;
    private JTextField searchField;
    
    public MainWindow(GetAllBooksUseCase getAllBooksUseCase,
                     AddBookUseCase addBookUseCase,
                     RemoveBookUseCase removeBookUseCase,
                     SearchBooksUseCase searchBooksUseCase,
                     OpenBookUseCase openBookUseCase,
                     ReadPageUseCase readPageUseCase,
                     UpdateReadingProgressUseCase updateProgressUseCase,
                     GetReadingProgressUseCase getProgressUseCase,
                     CreateBookmarkUseCase createBookmarkUseCase,
                     GetBookmarksUseCase getBookmarksUseCase,
                     BookReaderRepository readerRepository) {
        this.getAllBooksUseCase = getAllBooksUseCase;
        this.addBookUseCase = addBookUseCase;
        this.removeBookUseCase = removeBookUseCase;
        this.searchBooksUseCase = searchBooksUseCase;
        
        initializeUI();
        loadBooks();
        
        // Store use cases for reader window
        setReaderUseCases(openBookUseCase, readPageUseCase, updateProgressUseCase,
                         getProgressUseCase, createBookmarkUseCase, getBookmarksUseCase,
                         readerRepository);
    }
    
    private OpenBookUseCase openBookUseCase;
    private ReadPageUseCase readPageUseCase;
    private UpdateReadingProgressUseCase updateProgressUseCase;
    private GetReadingProgressUseCase getProgressUseCase;
    private CreateBookmarkUseCase createBookmarkUseCase;
    private GetBookmarksUseCase getBookmarksUseCase;
    private BookReaderRepository readerRepository;
    
    private void setReaderUseCases(OpenBookUseCase openBookUseCase,
                                   ReadPageUseCase readPageUseCase,
                                   UpdateReadingProgressUseCase updateProgressUseCase,
                                   GetReadingProgressUseCase getProgressUseCase,
                                   CreateBookmarkUseCase createBookmarkUseCase,
                                   GetBookmarksUseCase getBookmarksUseCase,
                                   BookReaderRepository readerRepository) {
        this.openBookUseCase = openBookUseCase;
        this.readPageUseCase = readPageUseCase;
        this.updateProgressUseCase = updateProgressUseCase;
        this.getProgressUseCase = getProgressUseCase;
        this.createBookmarkUseCase = createBookmarkUseCase;
        this.getBookmarksUseCase = getBookmarksUseCase;
        this.readerRepository = readerRepository;
    }
    
    private void initializeUI() {
        setTitle("ReadCore - Leitor de Livros Digitais");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        // Menu bar
        createMenuBar();
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel with search
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        searchField.addActionListener(e -> performSearch());
        JButton searchButton = new JButton("Buscar");
        searchButton.addActionListener(e -> performSearch());
        
        topPanel.add(new JLabel("Buscar: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);
        
        // Book list
        bookListModel = new DefaultListModel<>();
        bookList = new JList<>(bookListModel);
        bookList.setCellRenderer(new BookListRenderer());
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openSelectedBook();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(bookList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Biblioteca"));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Adicionar Livro");
        JButton removeButton = new JButton("Remover Livro");
        JButton openButton = new JButton("Abrir Livro");
        
        addButton.addActionListener(e -> addBook());
        removeButton.addActionListener(e -> removeBook());
        openButton.addActionListener(e -> openSelectedBook());
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(openButton);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem addItem = new JMenuItem("Adicionar Livro");
        JMenuItem exitItem = new JMenuItem("Sair");
        
        addItem.addActionListener(e -> addBook());
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(addItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem aboutItem = new JMenuItem("Sobre");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void loadBooks() {
        bookListModel.clear();
        List<Book> books = getAllBooksUseCase.execute();
        for (Book book : books) {
            bookListModel.addElement(book);
        }
    }
    
    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            loadBooks();
            return;
        }
        
        bookListModel.clear();
        List<Book> results = searchBooksUseCase.executeByTitle(query);
        for (Book book : results) {
            bookListModel.addElement(book);
        }
    }
    
    private void addBook() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Livros (*.pdf, *.epub, *.txt, *.mobi)", "pdf", "epub", "txt", "mobi"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                Book book = addBookUseCase.execute(fileChooser.getSelectedFile().getAbsolutePath());
                bookListModel.addElement(book);
                JOptionPane.showMessageDialog(this, "Livro adicionado com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar livro: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void removeBook() {
        Book selected = bookList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um livro para remover.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja remover '" + selected.getTitle() + "'?",
                "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            removeBookUseCase.execute(selected.getId());
            bookListModel.removeElement(selected);
        }
    }
    
    private void openSelectedBook() {
        Book selected = bookList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um livro para abrir.");
            return;
        }
        
        ReaderWindow reader = new ReaderWindow(selected, readPageUseCase,
                updateProgressUseCase, getProgressUseCase,
                createBookmarkUseCase, getBookmarksUseCase, readerRepository);
        reader.setVisible(true);
    }
    
    private void showAbout() {
        JOptionPane.showMessageDialog(this,
                "ReadCore v1.0\n\n" +
                "Um leitor de livros digitais limpo e offline\n" +
                "Construído com Clean Architecture\n\n" +
                "Suporta: PDF, EPUB, TXT, MOBI",
                "Sobre o ReadCore", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Custom renderer for book list items
     */
    private static class BookListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                     int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Book) {
                Book book = (Book) value;
                setText("<html><b>" + book.getTitle() + "</b><br>" +
                       "<i>" + book.getAuthor() + "</i> - " + book.getFormat() + "</html>");
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            }
            
            return this;
        }
    }
}
