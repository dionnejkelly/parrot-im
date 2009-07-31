package controller.spellcheck;

/*
 *  Credit to: JOrtho Copyright (C) 2005-2008 by i-net software
 *
 */

import javax.swing.JTextArea;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;

public class SpellCheck {
    private JTextArea txt1;

    public SpellCheck() {
        // Build the test frame for the sample
        txt1 = new JTextArea();
        txt1.setColumns(25);
        txt1.setRows(2);
        txt1.setAutoscrolls(true);
        txt1.setLineWrap(true);
        txt1.setWrapStyleWord(true);
        txt1.setToolTipText("Enter text and HTML tags here");

        // Create user dictionary in the current working directory of your
        // application
        SpellChecker.setUserDictionaryProvider(new FileUserDictionary());

        // Load the configuration from the file dictionaries.cnf and
        // use the current locale or the first language as default
        // setIconImage(new
        // ImageIcon(this.getClass().getResource("/images/mainwindow/logo.png")).getImage());
        SpellChecker.registerDictionaries(this.getClass().getResource(
                "/dictionary/dictionary_en.ortho"), null);

        // enable the spell checking on the text component with all features
        SpellChecker.register(txt1);
    }

    public JTextArea getTextArea() {
        return txt1;
    }

}
