package com.mycompany.passwordmanager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.mycompany.passwordmanager.Entry;
import com.mycompany.passwordmanager.JsonManager;
import com.mycompany.passwordmanager.LoginGUI;
import com.mycompany.passwordmanager.RegisterGUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import static java.util.Map.entry;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import table.TableActionCellEditor;
import table.TableActionCellRender;
import table.TableActionEvent;


public class MainGUI extends javax.swing.JFrame {
    private Account account;
    private JsonManager jsonManager = new JsonManager();

    
    
    public MainGUI(Account account) {
        this.account = account ;
        initComponents();
        adjustMenuAlignment();
        
        // logo application
        Image icon = new ImageIcon(this.getClass().getResource("/images/logoIcon.png")).getImage();
        this.setIconImage(icon);


        boolean logged = account != null;
        LoginMenu.setEnabled(!logged);
        LogoutMenu.setEnabled(logged);
        LoginMenu2.setEnabled(!logged);
        SaveButton2.setEnabled(logged);
        EntryListMenu.setEnabled(logged);
        NewEntryMenu.setEnabled(logged);
        RegisterMenu.setEnabled(!logged);
        LogoutMenu2.setEnabled(logged);
        MainPanel.setEnabledAt(1, logged);
        MainPanel.setEnabledAt(2, logged);
        
        if (logged) {
            // Ottieni le entries associate all'utente loggato
            List<Entry> entries = jsonManager.GetEntryListFromJSON(null, null, account);

            // Mostra le entries nella tabella
            displayEntries(entries);

            // Sostituisce la parola "Login" con il nome dell'account loggato
            LoginMenu.setText(account.username);

            // Carica l'icona per l'utente loggato (modifica il percorso dell'immagine in base alla tua struttura di progetto)
            ImageIcon userIcon = new ImageIcon("C:\\Users\\Lorenzo\\OneDrive\\Desktop\\Documenti\\GitHub\\PasswordManager\\src\\main\\resources\\images\\user.png");

            // Imposta l'icona su LoginMenu
            LoginMenu.setIcon(userIcon);
        } else {
            // Se non è loggato, imposta un'icona predefinita o nessuna icona
            LoginMenu.setText("Login");

            // Usa ImageIcon per l'icona del login, non LoginIcon
            ImageIcon loginIcon = new ImageIcon("C:\\Users\\Lorenzo\\OneDrive\\Desktop\\Documenti\\GitHub\\PasswordManager\\src\\main\\resources\\images\\Login.png");

            // Imposta l'icona su LoginMenu
            LoginMenu.setIcon(loginIcon);
        }
    }
    
    public static void OpenExceptionMessage(String errorMessage, String stackTrace) {
        Object[] options = {"Close", "Copy to clipboard", "Report the Problem"};

        if (errorMessage == null ) {
            errorMessage = "\n\n";
        }
        stackTrace = errorMessage + stackTrace;
        String stackTraceMessage = "Please report this error, either with an image of the screen or by copying the following error text (it is appreciable to provide a description of the operations performed before the error): \n" +  stackTrace;

        int choice;

        // Keep displaying the dialog until the "Close" option (index 0) is chosen
        do {
            
            System.out.println(stackTraceMessage.length());
            
            if (stackTraceMessage.length() > 1500) {
                stackTraceMessage = stackTraceMessage.substring(0, 1500) + "...";     
            }
                                
            // Display the option dialog
            choice = JOptionPane.showOptionDialog(
                null,
                stackTraceMessage,                   // The detailed message or stack trace
                "Error...",                          // The error message/title
                JOptionPane.DEFAULT_OPTION,          // Option type (default option type)
                JOptionPane.ERROR_MESSAGE,           // Message type (error message icon)
                null,                                // Icon (null means default icon)
                options,                             // The options for the buttons
                options[0]                           // The default option (Close)
            );
            
            if (choice == 1) {
                StringSelection selection = new StringSelection(stackTrace);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
                JOptionPane.showMessageDialog(null, "Error text has been copied to the clipboard.");
            } else if (choice == 2) {
                try {
                    String reportUrl = "https://github.com/LorenzoBertinelli/Password-Generator/issues";

                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.BROWSE)) {
                            desktop.browse(new URI(reportUrl));
                        }
                    }
                } catch (IOException | URISyntaxException e) {
                    JOptionPane.showMessageDialog(null, "Failed to open the web page. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } while (choice == 1 || choice == 2);
    }
    
    private void adjustMenuAlignment() {
        // Create a horizontal glue
        Box.Filler horizontalGlue = new Box.Filler(
            new java.awt.Dimension(0, 0), 
            new java.awt.Dimension(0, 0), 
            new java.awt.Dimension(Integer.MAX_VALUE, 0)
        );
        
        // Add the glue to the menu bar before the Help menu
        jMenuBar1.add(horizontalGlue, 3); // Insert at the correct position
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SavePasswordButton = new javax.swing.JButton();
        SeePasswordsButton = new javax.swing.JToggleButton();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        MainPanel = new javax.swing.JTabbedPane();
        PasswordGeneratorPanel = new javax.swing.JPanel();
        OutputPassword = new javax.swing.JTextField();
        GeneratePasswordButton = new javax.swing.JButton();
        Symbol = new javax.swing.JCheckBox();
        Uppercase = new javax.swing.JCheckBox();
        LowercaseLetters = new javax.swing.JCheckBox();
        Numbers = new javax.swing.JCheckBox();
        PasswordSize = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        SaveButton2 = new javax.swing.JToggleButton();
        SecurityPassword = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        NewEntryPanel = new javax.swing.JPanel();
        Password = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Note = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        CancelButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        SaveButton = new javax.swing.JButton();
        AccountName = new javax.swing.JTextField();
        Email = new javax.swing.JTextField();
        SecurityPassword2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        ConfirmPassword = new javax.swing.JPasswordField();
        EntryListPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PasswordSearchButton = new javax.swing.JButton();
        AccountSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        PasswordTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        EntryListMenu = new javax.swing.JMenu();
        PasswordGeneratorMenu = new javax.swing.JMenuItem();
        NewEntryMenu = new javax.swing.JMenuItem();
        EntryListMenu2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        LoginMenu2 = new javax.swing.JMenuItem();
        LogoutMenu2 = new javax.swing.JMenuItem();
        RegisterMenu = new javax.swing.JMenuItem();
        CreditsMenu = new javax.swing.JMenu();
        MyGitHub = new javax.swing.JMenuItem();
        MyFacebook = new javax.swing.JMenuItem();
        LoginMenu = new javax.swing.JMenu();
        LogoutMenu = new javax.swing.JMenu();

        SavePasswordButton.setText("Save a password");
        SavePasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SavePasswordButtonActionPerformed(evt);
            }
        });

        SeePasswordsButton.setText("See my password");
        SeePasswordsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeePasswordsButtonActionPerformed(evt);
            }
        });

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PasswordGeneratorPanel.setBackground(new java.awt.Color(51, 51, 51));
        PasswordGeneratorPanel.setForeground(new java.awt.Color(51, 51, 51));

        OutputPassword.setBackground(new java.awt.Color(102, 102, 102));
        OutputPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OutputPasswordActionPerformed(evt);
            }
        });
        OutputPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                GeneratePasswordUpdate(evt);
            }
        });

        GeneratePasswordButton.setBackground(new java.awt.Color(0, 102, 204));
        GeneratePasswordButton.setText("Generate Password");
        GeneratePasswordButton.setMaximumSize(new java.awt.Dimension(50, 27));
        GeneratePasswordButton.setMinimumSize(new java.awt.Dimension(50, 27));
        GeneratePasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GeneratePasswordButtonActionPerformed(evt);
            }
        });

        Symbol.setText(" Special characters");

        Uppercase.setText("Uppercase");

        LowercaseLetters.setText("Lowercase letters");

        Numbers.setText("Numbers");

        PasswordSize.setToolTipText("Password size");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Choose options to generate your password:");

        SaveButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/file.png"))); // NOI18N
        SaveButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButton2ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel8.setText("Password generator");

        javax.swing.GroupLayout PasswordGeneratorPanelLayout = new javax.swing.GroupLayout(PasswordGeneratorPanel);
        PasswordGeneratorPanel.setLayout(PasswordGeneratorPanelLayout);
        PasswordGeneratorPanelLayout.setHorizontalGroup(
            PasswordGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PasswordGeneratorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PasswordGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PasswordGeneratorPanelLayout.createSequentialGroup()
                        .addGroup(PasswordGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Symbol)
                            .addComponent(LowercaseLetters))
                        .addGap(102, 102, 102)
                        .addGroup(PasswordGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Numbers, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PasswordGeneratorPanelLayout.createSequentialGroup()
                                .addComponent(OutputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(SaveButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Uppercase, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PasswordGeneratorPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PasswordGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SecurityPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GeneratePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(252, 252, 252))
            .addGroup(PasswordGeneratorPanelLayout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addGroup(PasswordGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PasswordGeneratorPanelLayout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(PasswordSize, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(265, 265, 265))
        );
        PasswordGeneratorPanelLayout.setVerticalGroup(
            PasswordGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PasswordGeneratorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(51, 51, 51)
                .addComponent(jLabel7)
                .addGap(48, 48, 48)
                .addGroup(PasswordGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Symbol)
                    .addComponent(Uppercase))
                .addGap(5, 5, 5)
                .addComponent(PasswordSize, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(PasswordGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Numbers)
                    .addComponent(LowercaseLetters))
                .addGap(69, 69, 69)
                .addGroup(PasswordGeneratorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OutputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SaveButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(GeneratePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SecurityPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );

        MainPanel.addTab("Password generator", PasswordGeneratorPanel);

        NewEntryPanel.setBackground(new java.awt.Color(51, 51, 51));

        Password.setToolTipText("To find out how secure your password is, press enter");
        Password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordActionPerformed(evt);
            }
        });
        Password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordUpdate(evt);
            }
        });

        jLabel2.setText("Account name");

        jLabel3.setText("Email");

        jLabel4.setText("Password");

        Note.setColumns(20);
        Note.setRows(5);
        jScrollPane2.setViewportView(Note);

        jLabel5.setText("Note");

        CancelButton.setBackground(new java.awt.Color(255, 0, 0));
        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("Save entry");

        SaveButton.setBackground(new java.awt.Color(0, 102, 255));
        SaveButton.setText("Save");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        AccountName.setToolTipText("Required field");

        Email.setToolTipText("Required field");
        Email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailActionPerformed(evt);
            }
        });

        jLabel9.setText("Confirm password");

        ConfirmPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NewEntryPanelLayout = new javax.swing.GroupLayout(NewEntryPanel);
        NewEntryPanel.setLayout(NewEntryPanelLayout);
        NewEntryPanelLayout.setHorizontalGroup(
            NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewEntryPanelLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addGroup(NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NewEntryPanelLayout.createSequentialGroup()
                        .addGroup(NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AccountName, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SecurityPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(NewEntryPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addGroup(NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 168, Short.MAX_VALUE))))
            .addGroup(NewEntryPanelLayout.createSequentialGroup()
                .addGap(325, 325, 325)
                .addComponent(CancelButton)
                .addGap(18, 18, 18)
                .addComponent(SaveButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        NewEntryPanelLayout.setVerticalGroup(
            NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewEntryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(41, 41, 41)
                .addGroup(NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AccountName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(17, 17, 17)
                .addGroup(NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SecurityPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGroup(NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NewEntryPanelLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NewEntryPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(NewEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelButton)
                    .addComponent(SaveButton))
                .addGap(60, 60, 60))
        );

        MainPanel.addTab("New entry", NewEntryPanel);

        EntryListPanel.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Password list");

        PasswordSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LenteIngradimento.png"))); // NOI18N
        PasswordSearchButton.setToolTipText("Search password");
        PasswordSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordSearchButtonActionPerformed(evt);
            }
        });

        AccountSearch.setToolTipText("Enter Account name");
        AccountSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AccountSearchActionPerformed(evt);
            }
        });
        AccountSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                EntrySearch(evt);
            }
        });

        PasswordTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Account Name", "Email", "Password", "Note", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PasswordTable.setRowHeight(40);
        PasswordTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane1.setViewportView(PasswordTable);

        javax.swing.GroupLayout EntryListPanelLayout = new javax.swing.GroupLayout(EntryListPanel);
        EntryListPanel.setLayout(EntryListPanelLayout);
        EntryListPanelLayout.setHorizontalGroup(
            EntryListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EntryListPanelLayout.createSequentialGroup()
                .addContainerGap(287, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(115, 115, 115)
                .addComponent(AccountSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PasswordSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
        );
        EntryListPanelLayout.setVerticalGroup(
            EntryListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EntryListPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(EntryListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(EntryListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(AccountSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(PasswordSearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addContainerGap())
        );

        MainPanel.addTab("Entry list", EntryListPanel);

        EntryListMenu.setText("Operations");

        PasswordGeneratorMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        PasswordGeneratorMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PasswordGenerator.png"))); // NOI18N
        PasswordGeneratorMenu.setText("Password generator");
        PasswordGeneratorMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordGeneratorMenuActionPerformed(evt);
            }
        });
        EntryListMenu.add(PasswordGeneratorMenu);

        NewEntryMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        NewEntryMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add-security.png"))); // NOI18N
        NewEntryMenu.setText("New entry");
        NewEntryMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewEntryMenuActionPerformed(evt);
            }
        });
        EntryListMenu.add(NewEntryMenu);

        EntryListMenu2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        EntryListMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/list.png"))); // NOI18N
        EntryListMenu2.setText("Entry list");
        EntryListMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EntryListMenu2ActionPerformed(evt);
            }
        });
        EntryListMenu.add(EntryListMenu2);

        jMenuBar1.add(EntryListMenu);

        jMenu2.setText("Account");

        LoginMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Login.png"))); // NOI18N
        LoginMenu2.setText("Login");
        LoginMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginMenu2ActionPerformed(evt);
            }
        });
        jMenu2.add(LoginMenu2);

        LogoutMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logout.png"))); // NOI18N
        LogoutMenu2.setText("Logout");
        LogoutMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutMenu2ActionPerformed(evt);
            }
        });
        jMenu2.add(LogoutMenu2);

        RegisterMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/UserRegister.png"))); // NOI18N
        RegisterMenu.setText("Register");
        RegisterMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterMenuActionPerformed(evt);
            }
        });
        jMenu2.add(RegisterMenu);

        jMenuBar1.add(jMenu2);

        CreditsMenu.setText("Credits");

        MyGitHub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/github.png"))); // NOI18N
        MyGitHub.setText("My GitHub");
        MyGitHub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MyGitHubActionPerformed(evt);
            }
        });
        CreditsMenu.add(MyGitHub);

        MyFacebook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/facebook.png"))); // NOI18N
        MyFacebook.setText("My Facebook");
        MyFacebook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MyFacebookActionPerformed(evt);
            }
        });
        CreditsMenu.add(MyFacebook);

        jMenuBar1.add(CreditsMenu);

        LoginMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Login.png"))); // NOI18N
        LoginMenu.setText("Login");
        LoginMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LoginClick(evt);
            }
        });
        LoginMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginMenuActionPerformed(evt);
            }
        });
        jMenuBar1.add(LoginMenu);

        LogoutMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logout.png"))); // NOI18N
        LogoutMenu.setText("Logout");
        LogoutMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutClick(evt);
            }
        });
        LogoutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutMenuActionPerformed(evt);
            }
        });
        jMenuBar1.add(LogoutMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainPanel))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loginGUI (){
        LoginGUI loginFrame = new LoginGUI();
        loginFrame.setVisible(true);

        dispose();
    }
    private void SavePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SavePasswordButtonActionPerformed
        jsonManager.WritePasswordToJSON(AccountName.getText(), Email.getText(), Password.getText(), Note.getText(), account.username, this);
        
        JOptionPane.showMessageDialog(this, "Password saved successfully!", "Password saved", JOptionPane.OK_OPTION);
        
        dispose(); // Chiude il frame
        
        displayEntries(null);
    }//GEN-LAST:event_SavePasswordButtonActionPerformed

    private void SeePasswordsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeePasswordsButtonActionPerformed
        
    }//GEN-LAST:event_SeePasswordsButtonActionPerformed

    private void AccountSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AccountSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AccountSearchActionPerformed

    private void PasswordSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordSearchButtonActionPerformed
        String accountSearch = AccountSearch.getText().trim();  // Ottieni il testo cercato
        List<Entry> filteredEntries = jsonManager.GetEntryListFromJSON(accountSearch, account.username, account); // Filtra i risultati
        displayEntries(filteredEntries);  // Mostra i risultati filtrati
    }//GEN-LAST:event_PasswordSearchButtonActionPerformed

    private void EmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailActionPerformed

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
       // Controlla se la password e la password di conferma coincidono
        if (!Password.getText().equals(ConfirmPassword.getText())) {
            // Se non coincidono, mostra un messaggio di errore
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please check the parameters again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Esce dal metodo, impedendo di salvare la nuova entry
        }

        // Se le password coincidono, procede a salvare la nuova entry
        jsonManager.WritePasswordToJSON(AccountName.getText(), Email.getText(), Password.getText(), Note.getText(), account.username, this);        
        JOptionPane.showMessageDialog(this, "Password saved successfully!", "Password saved", JOptionPane.OK_OPTION);

        // Aggiorna la tabella con le entry
        if (isUserLoggedIn()) {
        List<Entry> updatedEntries = jsonManager.GetEntryListFromJSON(null, account.username, account);
            displayEntries(updatedEntries);
        }

        // Ripulisce i campi di input
        AccountName.setText("");
        Email.setText("");
        Password.setText("");
        ConfirmPassword.setText("");
        Note.setText("");
    }//GEN-LAST:event_SaveButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        dispose();  // Chiude il frame
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void SaveButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButton2ActionPerformed
        MainPanel.setSelectedComponent(NewEntryPanel);      
        Password.setText(OutputPassword.getText());
    }//GEN-LAST:event_SaveButton2ActionPerformed

    private void GeneratePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GeneratePasswordButtonActionPerformed
        Integer passwordSize = (Integer) PasswordSize.getValue();

        boolean useLower = LowercaseLetters.isSelected();
        boolean useUpper = Uppercase.isSelected();
        boolean useNumbers = Numbers.isSelected();
        boolean useSymbols = Symbol.isSelected();

        String generatedPassword = generatePassword(passwordSize, useLower, useUpper, useNumbers, useSymbols);

        if (generatedPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select at least one option to generate your password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        OutputPassword.setText(generatedPassword);
        
        // Aggiornare la JLabel con la forza calcolata
        CalculateSecurityPassword(OutputPassword, SecurityPassword);
    }//GEN-LAST:event_GeneratePasswordButtonActionPerformed

    private void OutputPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OutputPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OutputPasswordActionPerformed

    private void LoginMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginMenuActionPerformed
        loginGUI();
    }//GEN-LAST:event_LoginMenuActionPerformed

    private void EntryListMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EntryListMenu2ActionPerformed
       MainPanel.setSelectedComponent(EntryListPanel);
    }//GEN-LAST:event_EntryListMenu2ActionPerformed

    private void NewEntryMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewEntryMenuActionPerformed
        MainPanel.setSelectedComponent(NewEntryPanel);
    }//GEN-LAST:event_NewEntryMenuActionPerformed

    private void PasswordGeneratorMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordGeneratorMenuActionPerformed
        MainPanel.setSelectedComponent(PasswordGeneratorPanel);
    }//GEN-LAST:event_PasswordGeneratorMenuActionPerformed

    private void LogoutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutMenuActionPerformed
        loginGUI();
    }//GEN-LAST:event_LogoutMenuActionPerformed

    private void LoginMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginMenu2ActionPerformed
        loginGUI();
    }//GEN-LAST:event_LoginMenu2ActionPerformed

    private void LogoutMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutMenu2ActionPerformed
        loginGUI();
    }//GEN-LAST:event_LogoutMenu2ActionPerformed

    private void RegisterMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterMenuActionPerformed
        RegisterGUI registerFrame = new RegisterGUI();
        registerFrame.setVisible(true);

        dispose();
    }//GEN-LAST:event_RegisterMenuActionPerformed

    private void MyGitHubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MyGitHubActionPerformed
        // URL del sito web da aprire
        String url = "https://github.com/LorenzoBertinelli";
                
        // Tentativo di aprire il sito web nel browser predefinito
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to open the web page. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else {
            System.err.println("Desktop not supported. Unable to open website.");
        }
    }//GEN-LAST:event_MyGitHubActionPerformed

    private void MyFacebookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MyFacebookActionPerformed
        // URL del sito web da aprire
        String url = "https://www.facebook.com/profile.php?id=100073820334511&locale=it_IT";
                
        // Tentativo di aprire il sito web nel browser predefinito
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to open the web page. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            System.err.println("Desktop not supported. Unable to open website.");
        }
    }//GEN-LAST:event_MyFacebookActionPerformed

    private void LoginClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoginClick
        loginGUI();
    }//GEN-LAST:event_LoginClick

    private void LogoutClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutClick
        loginGUI();
    }//GEN-LAST:event_LogoutClick

    private void GeneratePasswordUpdate(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GeneratePasswordUpdate
        if(OutputPassword.getText().isEmpty()){
            SecurityPassword.setText(" ");
        }
        else{
            // Aggiorna la JLabel con la forza calcolata
            CalculateSecurityPassword(OutputPassword, SecurityPassword);
        }
    }//GEN-LAST:event_GeneratePasswordUpdate

    private void EntrySearch(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EntrySearch
        String accountSearch = AccountSearch.getText().trim();
        List<Entry> filteredEntries = jsonManager.GetEntryListFromJSON(accountSearch, null, account);
        displayEntries(filteredEntries);
    }//GEN-LAST:event_EntrySearch

    private void PasswordUpdate(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordUpdate
        if(Password.getText().isEmpty()){
            SecurityPassword2.setText(" ");
        }
        else{
            // Aggiorna la JLabel con la forza calcolata
            CalculateSecurityPassword(Password, SecurityPassword2);
        }
    }//GEN-LAST:event_PasswordUpdate

    private void PasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordActionPerformed

        if(Password.getText().isEmpty()){
            SecurityPassword2.setText(" ");
        }
        else{
            // Aggiorna la JLabel con la forza calcolata
            CalculateSecurityPassword(Password, SecurityPassword);
        }
    }//GEN-LAST:event_PasswordActionPerformed

    private void ConfirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConfirmPasswordActionPerformed

    private void displayEntries(List<Entry> entries) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Account Name", "Password", "Email", "Note", "Actions"}, 0);
        PasswordTable.setModel(model);
        
        if(entries==null){
            return;
        }
        
        // Inizia il ciclo da 1 per saltare la prima riga
        for (int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);

            // Aggiungi righe se necessario
            if (i >= model.getRowCount()) {
                model.addRow(new Object[]{"", "", "", "", ""});
            }

            // Imposta i valori per ogni cella
            model.setValueAt(entry.getAccountName(), i, 0);  // Usa i per l'indice della tabella
            model.setValueAt("•••••••••••••", i, 1);         // Password nascosta
            model.setValueAt(entry.getEmail(), i, 2);
            model.setValueAt(entry.getNote(), i, 3);
            model.setValueAt("Actions", i, 4);               // Placeholder per le azioni
        }

        // Renderer personalizzato per centrare il testo nelle celle
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);  // Imposta l'allineamento orizzontale al centro

        // Applica il renderer personalizzato a tutte le colonne
        for (int i = 0; i < PasswordTable.getColumnCount(); i++) {
            PasswordTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Configura gli eventi e il renderer della tabella per la colonna delle azioni
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                EntryDeteilsGUI entryDeteilsFrame = new EntryDeteilsGUI(entries.get(row), account, model);
                entryDeteilsFrame.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                if (PasswordTable.isEditing()) {
                    PasswordTable.getCellEditor().stopCellEditing();
                }
                int response = JOptionPane.showOptionDialog(null, "Are you sure to delete this entry?", "Confirmation request", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (response == JOptionPane.OK_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) PasswordTable.getModel();
                    model.removeRow(row);
                    entries.remove(row);
                    jsonManager.saveEntriesToJson(entries, account);
                }
            }

            @Override
            public void onCopy(int row) {
                // Copia la password negli appunti
                StringSelection stringSelection = new StringSelection(entries.get(row).getPassword());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                System.out.println("Password copiata negli appunti: " + entries.get(row).getPassword());
            }
        };

        // Imposta il renderer e l'editor per la colonna delle azioni
        PasswordTable.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        PasswordTable.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));
    }


    private boolean isUserLoggedIn() {
        return account.username != null && !account.username.isEmpty();
    }

    private String generatePassword(int length, boolean useLower, boolean useUpper, boolean useNumbers, boolean useSymbols) {
        StringBuilder password = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        
        List<String> charCategories = new ArrayList<>();
        if (useLower) charCategories.add("abcdefghijklmnopqrstuvwxyz");
        if (useUpper) charCategories.add("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if (useNumbers) charCategories.add("0123456789");
        if (useSymbols) charCategories.add("?!<>-*[]{}/");

        if (charCategories.isEmpty()) {
            return "";
        }

        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }
        
        return new String(password);
    }
    
    private void CalculateSecurityPassword(JTextField passwordInput, JLabel securityOutput) {
    Integer passwordSize = (Integer) PasswordSize.getValue();

    // Calcolare la forza della password generata
    int strength = calculatePasswordStrength(passwordInput.getText());
    String strengthText = getStrengthText(strength); // Funzione per ottenere il testo descrittivo della forza
    
    // Aggiornare la JLabel con la forza calcolata
    securityOutput.setText("Password Strength: " + strengthText);

    // Cambiare il colore della JLabel in base alla forza della password
    switch (strengthText) {
        case "Very Weak":
        case "Weak":
            securityOutput.setForeground(Color.RED);
            break;
        case "Medium":
            securityOutput.setForeground(Color.ORANGE);
            break;
        case "Strong":
            securityOutput.setForeground(Color.YELLOW);
            break;
        default: // "Unknown"
            securityOutput.setForeground(Color.GREEN);
            break;
    }

    // Aggiorna l'interfaccia utente
    validate();
    repaint();
}

    private int calculatePasswordStrength(String password) {
        int length = password.length();
        if (length < 4) {
            return 0; // Password molto debole
        } else if (length < 8) {
            return 1; // Password debole
        } else if (length < 12) {
            return 2; // Password media
        }  else if (length < 16) {
            return 3; // Password forte
        } else {
            return 4; // Password molto forte
        }
    }

    private String getStrengthText(int strength) {
        switch (strength) {
            case 0:
                return "Very Weak";
            case 1:
                return "Weak";
            case 2:
                return "Medium";
            case 3: 
                return "Strong";
            default:
                return "Unknown";
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AccountName;
    private javax.swing.JTextField AccountSearch;
    private javax.swing.JButton CancelButton;
    private javax.swing.JPasswordField ConfirmPassword;
    private javax.swing.JMenu CreditsMenu;
    private javax.swing.JTextField Email;
    private javax.swing.JMenu EntryListMenu;
    private javax.swing.JMenuItem EntryListMenu2;
    private javax.swing.JPanel EntryListPanel;
    private javax.swing.JButton GeneratePasswordButton;
    private javax.swing.JMenu LoginMenu;
    private javax.swing.JMenuItem LoginMenu2;
    private javax.swing.JMenu LogoutMenu;
    private javax.swing.JMenuItem LogoutMenu2;
    private javax.swing.JCheckBox LowercaseLetters;
    private javax.swing.JTabbedPane MainPanel;
    private javax.swing.JMenuItem MyFacebook;
    private javax.swing.JMenuItem MyGitHub;
    private javax.swing.JMenuItem NewEntryMenu;
    private javax.swing.JPanel NewEntryPanel;
    private javax.swing.JTextArea Note;
    private javax.swing.JCheckBox Numbers;
    private javax.swing.JTextField OutputPassword;
    private javax.swing.JTextField Password;
    private javax.swing.JMenuItem PasswordGeneratorMenu;
    private javax.swing.JPanel PasswordGeneratorPanel;
    private javax.swing.JButton PasswordSearchButton;
    private javax.swing.JSpinner PasswordSize;
    private javax.swing.JTable PasswordTable;
    private javax.swing.JMenuItem RegisterMenu;
    private javax.swing.JButton SaveButton;
    private javax.swing.JToggleButton SaveButton2;
    private javax.swing.JButton SavePasswordButton;
    private javax.swing.JLabel SecurityPassword;
    private javax.swing.JLabel SecurityPassword2;
    private javax.swing.JToggleButton SeePasswordsButton;
    private javax.swing.JCheckBox Symbol;
    private javax.swing.JCheckBox Uppercase;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}