package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.bezahlung;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;

import de.uni_hamburg.informatik.swt.se2.kino.fachwerte.Geld;

import java.awt.SystemColor;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;

public class BezahlWerkzeugUI
{
    private JDialog _dialog;
    private JTextField _textField;
    private JButton _ok;
    private JButton _abbrechen;
    private JTextPane _zuZahlenderPreis;
    private JTextPane _rueckzugebenderPreis;
    private Box _verticalBox;
    private Box _horizontalBox;
    private Box _verticalBox_1;

    public BezahlWerkzeugUI(Geld preis)
    {
        _dialog = new JDialog();
        _dialog.setTitle("Bezahlung");
        _dialog.setModal(true);
        _dialog.setResizable(false);
        _dialog.setAlwaysOnTop(true);
        _dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _dialog.getContentPane()
            .setLayout(new BorderLayout(0, 0));

        _verticalBox_1 = Box.createVerticalBox();
        _dialog.getContentPane()
            .add(_verticalBox_1, BorderLayout.NORTH);

        _verticalBox = Box.createVerticalBox();
        _verticalBox_1.add(_verticalBox);

        _zuZahlenderPreis = new JTextPane();
        _zuZahlenderPreis.setEditable(false);
        _verticalBox.add(_zuZahlenderPreis);
        _zuZahlenderPreis.setBackground(SystemColor.window);
        _zuZahlenderPreis.setText("Zu bezahlen: " + preis);

        _textField = new JTextField();
        _verticalBox.add(_textField);
        _textField.setColumns(5);

        _rueckzugebenderPreis = new JTextPane();
        _rueckzugebenderPreis.setEditable(false);
        _verticalBox.add(_rueckzugebenderPreis);
        _rueckzugebenderPreis.setBackground(SystemColor.window);
        _rueckzugebenderPreis.setText("Betrag nicht ausreichend");

        _horizontalBox = Box.createHorizontalBox();
        _verticalBox_1.add(_horizontalBox);

        _ok = new JButton("OK");
        _horizontalBox.add(_ok);
        _ok.setEnabled(false);

        _abbrechen = new JButton("Abbrechen");
        _horizontalBox.add(_abbrechen);
    }

    public void setRueckzugebenderPreis(Geld preis)
    {
        if (preis.signum() != -1)
        {
            _rueckzugebenderPreis.setText("Rueckgabe: " + preis);
        }
        else
        {
            setRueckzugebenderPreis(false);
        }
    }

    public void setRueckzugebenderPreis(boolean beFalse)
    {
        if (!beFalse)
        {
            _rueckzugebenderPreis.setText("Betrag nicht ausreichend");
        }
    }

    public JTextField getTextField()
    {
        return _textField;
    }

    public JButton getOKButton()
    {
        return _ok;
    }

    public JButton getAbbrechenButton()
    {
        return _abbrechen;
    }

    public void close()
    {
        _dialog.dispose();
    }

    public void zeigeFenster()
    {
        _dialog.addWindowFocusListener(new WindowAdapter()
        {
            public void windowGainedFocus(WindowEvent e)
            {
                _textField.requestFocusInWindow();
            }
        });
        _dialog.setSize(200, 120);
        _dialog.setLocationRelativeTo(null);
        _dialog.setVisible(true);
    }
}
