package over.client;

import over.config.Configurator;

import javax.swing.*;
import java.awt.*;

/**
 * <code>ConfigurationPanel</code> class builds a <code>JPanel</code> to enter the configuration data necessary to
 * communicate the chat application with the target server.
 *
 * @author Overload Inc.
 * @version 1.0, 09 Jan 2020
 */
public class ConfigurationPanel {
    private JPanel dataPanel;
    private JTextField txtIP;
    private JTextField txtPort;
    private JTextField txtUser;
    private JLabel lblLogo;
    private JLabel lblUser;
    private JLabel lblIP;
    private JLabel lblPort;
    private JToggleButton tglEdit;

    /**
     * Specifies the default application's port number.
     */
    private final String PORT ="10101";

    /**
     * Specifies the default server <code>IP</code>
     * <p>
     * For example: localhost 127.0.0.1, 10.156.30.207 or any other.
     */
    private final String IP ="127.0.0.1";

    /**
     * Builds a <code>JPanel</code> to enter the target server's communication data.
     * @return the configuration <code>JPanel</code>.
     */
    public JPanel getConfigurationPanel() {
        GridBagConstraints gridBagConstraints;

        dataPanel = new JPanel();
        dataPanel.setName("dataPanel");
        dataPanel.setMaximumSize(new Dimension(350, 450));
        dataPanel.setMinimumSize(new Dimension(350, 450));
        dataPanel.setPreferredSize(new Dimension(350, 450));
        dataPanel.setLayout(new GridBagLayout());

        lblLogo = new JLabel();
        lblLogo.setName("lblLogo");
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setIcon(new ImageIcon(getClass().getResource("/over/res/img/cocorin_login.png")));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;

        dataPanel.add(lblLogo, gridBagConstraints);

        lblUser = new JLabel();
        lblUser.setText(Configurator.getConfigurator().getProperty("lblUser"));
        lblUser.setName("lblUser");

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        dataPanel.add(lblUser, gridBagConstraints);

        txtUser = new JTextField(20);
        txtUser.setText(Configurator.getConfigurator().getProperty("txtUser"));
        txtUser.setName("txtUser");
        txtUser.setMaximumSize(new Dimension(200, 30));
        txtUser.setMinimumSize(new Dimension(200, 30));
        txtUser.setPreferredSize(new Dimension(200, 30));
        txtUser.requestFocusInWindow();
        txtUser.setSelectionStart(0);
        txtUser.setSelectionEnd(txtUser.getText().length());

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;

        dataPanel.add(txtUser, gridBagConstraints);

        tglEdit = new JToggleButton(Configurator.getConfigurator().getProperty("tglEdit01"));
        tglEdit.addItemListener(e -> hideElements());

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        dataPanel.add(tglEdit, gridBagConstraints);

        lblIP = new JLabel();
        lblIP.setText(Configurator.getConfigurator().getProperty("lblIP"));
        lblIP.setName("lblIP");
        lblIP.setVisible(false);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        dataPanel.add(lblIP, gridBagConstraints);

        txtIP = new JTextField(20);
        txtIP.setName("txtIP");
        txtIP.setText(IP);
        txtIP.setVisible(false);
        txtIP.setMaximumSize(new Dimension(200, 30));
        txtIP.setMinimumSize(new Dimension(200, 30));
        txtIP.setPreferredSize(new Dimension(200, 30));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx = 1.0;

        dataPanel.add(txtIP, gridBagConstraints);

        lblPort = new JLabel();
        lblPort.setText(Configurator.getConfigurator().getProperty("lblPort"));
        lblPort.setName("lblPort");
        lblPort.setVisible(false);
        lblPort.setHorizontalAlignment(SwingConstants.CENTER);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        dataPanel.add(lblPort, gridBagConstraints);

        txtPort = new JTextField(20);
        txtPort.setName("txtPort");
        txtPort.setText(PORT);
        txtPort.setVisible(false);
        txtPort.setMaximumSize(new Dimension(200, 30));
        txtPort.setMinimumSize(new Dimension(200, 30));
        txtPort.setPreferredSize(new Dimension(200, 30));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.weightx = 1.0;

        dataPanel.add(txtPort, gridBagConstraints);

        return dataPanel;
    }

    /**
     * Gets the application's configuration data.
     * @return the array with the configuration data.
     */
    public String[] getConfigurationData() {
        String data[] = new String[3];

        int result = JOptionPane.showConfirmDialog(null, getConfigurationPanel(), Configurator.getConfigurator().getProperty("communication"), JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            data[0] = txtIP.getText();
            data[1] = txtPort.getText();
            data[2] = txtUser.getText();
        }
        else {
            data[0] = IP;
            data[1] = PORT;

            System.exit(0);
        }

        return data;
    }

    /**
     * Hides the frame elements depending on the action performed by the configuration <code>JToggleButton</code>.
     */
    public void hideElements() {
        if(tglEdit.isSelected()) {
            tglEdit.setText(Configurator.getConfigurator().getProperty("tglEdit02"));
            lblUser.setVisible(false);
            txtUser.setVisible(false);
            lblIP.setVisible(true);
            txtIP.setVisible(true);
            lblPort.setVisible(true);
            txtPort.setVisible(true);
        }
        else {
            tglEdit.setText(Configurator.getConfigurator().getProperty("tglEdit01"));
            lblUser.setVisible(true);
            txtUser.setVisible(true);
            lblIP.setVisible(false);
            txtIP.setVisible(false);
            lblPort.setVisible(false);
            txtPort.setVisible(false);
        }
    }
}