package over.client;

import over.config.Configurator;
import over.controller.format.FontEditor;
import over.controller.listener.ButtonListener;
import over.controller.listener.FrameListener;
import over.controller.listener.TextListener;
import over.controller.renderer.ListRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * <code>Chat</code> class implements a chat window to communicate a set of connected users vía <code>sockets</code>.
 *
 * @author Overload Inc.
 * @version 1.0, 09 Jan 2020
 */
public class Chat extends JFrame {
    private JSplitPane splitPane;
    private JMenuItem aboutOption;
    private JMenuItem exitOption;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenuBar menuBar;
    private JButton btnSend;
    private JPanel mainPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JScrollPane scrollConsole;
    private JScrollPane scrollList;
    private JScrollPane scrollMessage;
    private JTextPane txtConsole;
    private JTextPane txtMessage;
    private JLabel lblSession;
    private JLabel lblOverload;
    private JList<String> userList;
    private DefaultListModel<String> model;
    private FontEditor fontEditor;

    /**
     * Instance for the client which communicates with the server.
     */
    private final Client client;

    /**
     * Class constructor.
     */
    public Chat() {
        initComponents();

        String portData[] = getPortData();
        String portIP = portData[0];
        String portNumber = portData[1];
        String portName = portData[2];

        client = new Client(this, portIP, Integer.valueOf(portNumber), portName);
    }

    /**
     * Initializes the chat components.
     */
    private void initComponents() {
        splitPane = new JSplitPane();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        exitOption = new JMenuItem();
        helpMenu = new JMenu();
        aboutOption = new JMenuItem();
        btnSend = new JButton();
        northPanel = new JPanel();
        southPanel = new JPanel();
        mainPanel = new JPanel();
        scrollList = new JScrollPane();
        scrollMessage = new JScrollPane();
        txtMessage = new JTextPane();
        lblSession = new JLabel();
        lblOverload = new JLabel();
        model = new DefaultListModel<>();
        userList = new JList(model);
        fontEditor = new FontEditor();

        GridBagConstraints gridBagConstraints;

        setTitle(Configurator.getConfigurator().getProperty("clientName"));
        setName("frmChat");
        setIconImage(FrameListener.getFrameListener().getIcon().getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setPreferredSize(new Dimension(800, 600));

        addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.acceptDisconnection();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                client.acceptDisconnection();
            }

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        menuBar.setName("menuBar");

        fileMenu.setMnemonic('F');
        fileMenu.setText(Configurator.getConfigurator().getProperty("fileMenu"));
        fileMenu.setName("fileMenu");

        exitOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        exitOption.setMnemonic('E');
        exitOption.setText(Configurator.getConfigurator().getProperty("exitOption"));
        exitOption.setName("exitOption");
        exitOption.addActionListener(e -> client.acceptDisconnection());

        fileMenu.add(exitOption);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('H');
        helpMenu.setText(Configurator.getConfigurator().getProperty("helpMenu"));
        helpMenu.setName("helpMenu");

        aboutOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        aboutOption.setMnemonic('A');
        aboutOption.setText(Configurator.getConfigurator().getProperty("aboutOption"));
        aboutOption.setName("aboutOption");
        aboutOption.addActionListener(e -> new About().setVisible(true));

        helpMenu.add(aboutOption);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        mainPanel.setName("mainPanel");
        mainPanel.setLayout(new GridBagLayout());

        txtConsole = new JTextPane();
        txtConsole.setName("txtConsole");
        txtConsole.setEditable(false);

        scrollConsole = new JScrollPane();
        scrollConsole.setName("scrollConsole");
        scrollConsole.setViewportView(txtConsole);

        splitPane.setDividerLocation(600);
        splitPane.setDividerSize(5);
        splitPane.setName("splitPane");
        splitPane.setOneTouchExpandable(true);
        splitPane.setLeftComponent(txtConsole);

        userList.setName("userList");
        userList.setPreferredSize(new Dimension(126, 150));
        userList.setVisibleRowCount(15);
        userList.setCellRenderer(new ListRenderer());

        scrollList.setName("scrollList");
        scrollList.setViewportView(userList);

        splitPane.setRightComponent(scrollList);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.ipady = 199;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);

        mainPanel.add(splitPane, gridBagConstraints);

        txtMessage.setName("txtMessage");
        txtMessage.addKeyListener(new TextListener(this));

        scrollMessage.setName("scrollMessage");
        scrollMessage.setViewportView(txtMessage);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);

        mainPanel.add(scrollMessage, gridBagConstraints);

        btnSend.setName("btnSend");
        btnSend.setToolTipText(Configurator.getConfigurator().getProperty("btnSend"));
        btnSend.setIcon(new ImageIcon(getClass().getResource("/over/res/img/close_mail_01.png")));
        btnSend.addActionListener(e -> sendMessage(e));
        btnSend.addMouseListener(new ButtonListener(btnSend));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new Insets(10, 0, 10, 10);

        mainPanel.add(btnSend, gridBagConstraints);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        northPanel.setName("northPanel");
        northPanel.setLayout(new BorderLayout());

        lblSession.setHorizontalAlignment(SwingConstants.CENTER);
        lblSession.setHorizontalTextPosition(SwingConstants.CENTER);
        lblSession.setMinimumSize(new Dimension(100, 25));
        lblSession.setName("lblSession");
        lblSession.setPreferredSize(new Dimension(100, 25));

        northPanel.add(lblSession, BorderLayout.CENTER);

        getContentPane().add(northPanel, BorderLayout.NORTH);

        southPanel.setName("southPanel");
        southPanel.setLayout(new BorderLayout());

        lblOverload.setName("lblOverload");
        lblOverload.setText(Configurator.getConfigurator().getProperty("lblOverload"));
        lblOverload.setHorizontalAlignment(SwingConstants.CENTER);
        lblOverload.setHorizontalTextPosition(SwingConstants.CENTER);
        lblOverload.setMinimumSize(new Dimension(100, 25));
        lblOverload.setPreferredSize(new Dimension(100, 25));

        southPanel.add(lblOverload, BorderLayout.CENTER);

        getContentPane().add(southPanel, BorderLayout.SOUTH);

        pack();

        setLocationRelativeTo(null);
    }

    /**
     * Sends a message to a specific connected client.
     */
    public void sendMessage() {
        if(userList.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(null, Configurator.getConfigurator().getProperty("sendMessage"));

            return;
        }

        String receiver = userList.getSelectedValue();
        String message = txtMessage.getText();

        client.sendMessage(receiver, message);

        fontEditor.setBold(txtConsole, client.getClientId() + " -> " + receiver + ": ");
        fontEditor.setSimple(txtConsole,message + "\n");

        txtMessage.setText("");
    }

    /**
     * Sends a message every time the user performs a click over the <code>Send</code> button or presses the
     * <code>Enter</code> key.
     * @param event the event.
     */
    public void sendMessage(Object event) {
        if(event instanceof KeyEvent && (((KeyEvent)event).getKeyCode() == 10))
            sendMessage();
        else if(event instanceof ActionEvent)
            sendMessage();
    }

    /**
     * Adds a new connected user to the list.
     * @param user the connected user.
     */
    public void addConnectedUser(String user) {
        model.addElement(user);
    }

    /**
     * Adds a new message to the console.
     * @param transmitter the user who sends the message.
     * @param message the current message.
     */
    public void addMessage(String transmitter, String message) {
        fontEditor.setBold(txtConsole, transmitter + ": ");
        fontEditor.setSimple(txtConsole, message + "\n");
    }

    /**
     * Adds the title for the window session.
     * @param id the <code>Id</code> for the current session.
     */
    public void initSession(String id) {
        lblSession.setText(Configurator.getConfigurator().getProperty("welcome") + " " + id);
    }

    /**
     * Builds a new window to enter the host <code>IP</code>, port number, and the client's name.
     * @return the array which contains the server <code>IP</code>, connection <code>PORT</code> and the client's user name.
     */
    private String[] getPortData() {
        return new ConfigurationPanel().getConfigurationData();
    }

    /**
     * Removes a connected user of the list whenever the session is finished.
     * @param id the user <code>Id</code> to remove from the list.
     */
    void removeUser(String id) {
        for (int i = 0; i < model.getSize(); i++) {
            if(model.getElementAt(i).equals(id)){
                model.remove(i);
                return;
            }
        }
    }

    /**
     * Starts the applications
     * @param args the initial input parameters for the application.
     */
    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            Configurator.getConfigurator().initConfigurator();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> new Chat().setVisible(true));
    }
}