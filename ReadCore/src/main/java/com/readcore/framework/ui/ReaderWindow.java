package com.readcore.framework.ui;

import com.readcore.domain.entities.Book;
import com.readcore.domain.entities.BookContent;
import com.readcore.domain.entities.Bookmark;
import com.readcore.domain.entities.ReadingProgress;
import com.readcore.domain.repositories.BookReaderRepository;
import com.readcore.usecases.*;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * Book reader window
 */
public class ReaderWindow extends JFrame {
    private final Book book;
    private final ReadPageUseCase readPageUseCase;
    private final UpdateReadingProgressUseCase updateProgressUseCase;
    private final GetReadingProgressUseCase getProgressUseCase;
    private final CreateBookmarkUseCase createBookmarkUseCase;
    private final GetBookmarksUseCase getBookmarksUseCase;
    private final BookReaderRepository readerRepository;
    
    private JTextPane contentPane;
    private JLabel pageLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JSlider pageSlider;
    
    private int currentPage = 0;
    private int totalPages = 0;
    
    public ReaderWindow(Book book,
                       ReadPageUseCase readPageUseCase,
                       UpdateReadingProgressUseCase updateProgressUseCase,
                       GetReadingProgressUseCase getProgressUseCase,
                       CreateBookmarkUseCase createBookmarkUseCase,
                       GetBookmarksUseCase getBookmarksUseCase,
                       BookReaderRepository readerRepository) {
        this.book = book;
        this.readPageUseCase = readPageUseCase;
        this.updateProgressUseCase = updateProgressUseCase;
        this.getProgressUseCase = getProgressUseCase;
        this.createBookmarkUseCase = createBookmarkUseCase;
        this.getBookmarksUseCase = getBookmarksUseCase;
        this.readerRepository = readerRepository;
        
        initializeUI();
        loadBook();
    }
    
    private void initializeUI() {
        setTitle(book.getTitle() + " - " + book.getAuthor());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        
        // Menu bar
        createMenuBar();
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Content pane
        contentPane = new JTextPane();
        contentPane.setEditable(false);
        contentPane.setContentType("text/html");
        contentPane.setEditorKit(new HTMLEditorKit());
        contentPane.setFont(new Font("Serif", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        // Navigation panel
        JPanel navPanel = new JPanel(new BorderLayout(5, 5));
        
        // Page controls
        JPanel pageControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prevButton = new JButton("← Anterior");
        nextButton = new JButton("Próxima →");
        pageLabel = new JLabel("Página 0 de 0");
        
        prevButton.addActionListener(e -> previousPage());
        nextButton.addActionListener(e -> nextPage());
        
        pageControlPanel.add(prevButton);
        pageControlPanel.add(pageLabel);
        pageControlPanel.add(nextButton);
        
        // Page slider
        pageSlider = new JSlider(0, 100, 0);
        pageSlider.addChangeListener(e -> {
            if (!pageSlider.getValueIsAdjusting()) {
                goToPage(pageSlider.getValue());
            }
        });
        
        navPanel.add(pageControlPanel, BorderLayout.NORTH);
        navPanel.add(pageSlider, BorderLayout.CENTER);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(navPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu navigationMenu = new JMenu("Navegação");
        JMenuItem goToPageItem = new JMenuItem("Ir para Página...");
        JMenuItem bookmarksItem = new JMenuItem("Ver Marcadores");
        
        goToPageItem.addActionListener(e -> showGoToPageDialog());
        bookmarksItem.addActionListener(e -> showBookmarks());
        
        navigationMenu.add(goToPageItem);
        navigationMenu.add(bookmarksItem);
        
        JMenu bookmarkMenu = new JMenu("Marcador");
        JMenuItem addBookmarkItem = new JMenuItem("Adicionar Marcador");
        addBookmarkItem.addActionListener(e -> addBookmark());
        bookmarkMenu.add(addBookmarkItem);
        
        JMenu viewMenu = new JMenu("Visualizar");
        JMenuItem increaseFontItem = new JMenuItem("Aumentar Fonte");
        JMenuItem decreaseFontItem = new JMenuItem("Diminuir Fonte");
        
        increaseFontItem.addActionListener(e -> changeFontSize(2));
        decreaseFontItem.addActionListener(e -> changeFontSize(-2));
        
        viewMenu.add(increaseFontItem);
        viewMenu.add(decreaseFontItem);
        
        menuBar.add(navigationMenu);
        menuBar.add(bookmarkMenu);
        menuBar.add(viewMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void loadBook() {
        // Open the book file
        if (!readerRepository.openBook(book.getFilePath())) {
            contentPane.setText("<html><body style='padding: 20px;'>" +
                    "<h2>Erro ao carregar livro</h2>" +
                    "<p>Não foi possível ler o arquivo do livro.</p>" +
                    "</body></html>");
            return;
        }
        
        totalPages = readerRepository.getTotalPages();
        
        // Load saved progress
        Optional<ReadingProgress> progress = getProgressUseCase.execute(book.getId());
        if (progress.isPresent()) {
            currentPage = progress.get().getCurrentPage();
        }
        
        if (totalPages > 0) {
            pageSlider.setMaximum(totalPages - 1);
            displayPage(currentPage);
        } else {
            contentPane.setText("<html><body style='padding: 20px;'>" +
                    "<h2>Erro ao carregar livro</h2>" +
                    "<p>Não foi possível ler o arquivo do livro.</p>" +
                    "</body></html>");
        }
    }
    
    private void displayPage(int page) {
        try {
            Optional<BookContent> content = readPageUseCase.execute(page);
            if (content.isPresent()) {
                BookContent bookContent = content.get();
                String displayText;
                
                if (bookContent.getContentType() == BookContent.ContentType.HTML) {
                    displayText = bookContent.getContent();
                } else {
                    displayText = "<html><body style='padding: 20px; font-family: serif;'>" +
                            "<pre style='white-space: pre-wrap; font-family: serif; font-size: 14px;'>" +
                            escapeHtml(bookContent.getContent()) +
                            "</pre></body></html>";
                }
                
                contentPane.setText(displayText);
                contentPane.setCaretPosition(0);
                
                currentPage = page;
                updateNavigationControls();
                updateProgress();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler página: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;");
    }
    
    private void updateNavigationControls() {
        pageLabel.setText("Página " + (currentPage + 1) + " de " + totalPages);
        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled(currentPage < totalPages - 1);
        pageSlider.setValue(currentPage);
    }
    
    private void updateProgress() {
        updateProgressUseCase.execute(book.getId(), currentPage, totalPages);
    }
    
    private void previousPage() {
        if (currentPage > 0) {
            displayPage(currentPage - 1);
        }
    }
    
    private void nextPage() {
        if (currentPage < totalPages - 1) {
            displayPage(currentPage + 1);
        }
    }
    
    private void goToPage(int page) {
        if (page >= 0 && page < totalPages && page != currentPage) {
            displayPage(page);
        }
    }
    
    private void showGoToPageDialog() {
        String input = JOptionPane.showInputDialog(this,
                "Digite o número da página (1-" + totalPages + "):",
                "Ir para Página",
                JOptionPane.QUESTION_MESSAGE);
        
        if (input != null) {
            try {
                int page = Integer.parseInt(input) - 1;
                if (page >= 0 && page < totalPages) {
                    goToPage(page);
                } else {
                    JOptionPane.showMessageDialog(this, "Número de página inválido.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um número válido.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void addBookmark() {
        String note = JOptionPane.showInputDialog(this,
                "Digite uma nota para este marcador (opcional):",
                "Adicionar Marcador",
                JOptionPane.QUESTION_MESSAGE);
        
        if (note != null) {
            createBookmarkUseCase.execute(book.getId(), currentPage, note);
            JOptionPane.showMessageDialog(this, "Marcador adicionado!");
        }
    }
    
    private void showBookmarks() {
        List<Bookmark> bookmarks = getBookmarksUseCase.execute(book.getId());
        
        if (bookmarks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum marcador encontrado.");
            return;
        }
        
        DefaultListModel<Bookmark> model = new DefaultListModel<>();
        for (Bookmark bookmark : bookmarks) {
            model.addElement(bookmark);
        }
        
        JList<Bookmark> bookmarkList = new JList<>(model);
        bookmarkList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                         int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Bookmark) {
                    Bookmark b = (Bookmark) value;
                    setText("Página " + (b.getPageNumber() + 1) +
                            (b.getNote() != null && !b.getNote().isEmpty() ? ": " + b.getNote() : ""));
                }
                return this;
            }
        });
        
        bookmarkList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Bookmark selected = bookmarkList.getSelectedValue();
                    if (selected != null) {
                        goToPage(selected.getPageNumber());
                        SwingUtilities.getWindowAncestor(bookmarkList).dispose();
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(bookmarkList);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Marcadores",
                JOptionPane.PLAIN_MESSAGE);
    }
    
    private void changeFontSize(int delta) {
        Font currentFont = contentPane.getFont();
        int newSize = Math.max(8, Math.min(32, currentFont.getSize() + delta));
        contentPane.setFont(new Font(currentFont.getFamily(), currentFont.getStyle(), newSize));
        displayPage(currentPage); // Refresh display
    }
}
