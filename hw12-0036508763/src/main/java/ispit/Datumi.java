package ispit;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Datumi extends JFrame {

    private JList<String> list;
    private JLabel label;

    public Datumi() {
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(10, 10);
        setSize(500, 500);

        openDocument.putValue(Action.NAME, "Open");
        JMenuItem open = new JMenuItem(openDocument);
        JMenu file = new JMenu("File");
        setLayout(new BorderLayout());
        file.add(open);
        JMenuBar mb = new JMenuBar();
        mb.add(file);
        this.add(mb, BorderLayout.NORTH);

        list = new JList<>(new DefaultListModel<>());
        list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = list.getSelectedIndex();
                if (index < 0) return;
                String date = list.getModel().getElementAt(index);
                label.setText("Na datum " + date + " " + day(date));
            }
        });

        label = new JLabel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, label);
        splitPane.setResizeWeight(0.5);
        this.add(splitPane);

    }

    private String day(String date) {
        date = date.trim();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = formatter.parse(date);
            if (d.getDay() == 1) {
                return "bio je ponedjeljak.";
            } else if (d.getDay() == 2) {
                return "bio je utorak.";
            } else if (d.getDay() == 3) {
                return "bila je srijeda.";
            } else if (d.getDay() == 4) {
                return "bio je četvrtak.";
            } else if (d.getDay() == 5) {
                return "bio je petak.";
            } else if (d.getDay() == 6) {
                return "bila je subota.";
            } else {
                return "bila je nedjelja.";
            }
        } catch (ParseException e) {

        }
        return "";
    }

    private final Action openDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Open file");
            if (jfc.showOpenDialog(Datumi.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            Path filePath = jfc.getSelectedFile().toPath();
            if (!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(Datumi.this,
                        "File" + filePath + " couldn't be read.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                java.util.List<String> myList = Files.readAllLines(filePath);
                java.util.List<String> newList = new java.util.ArrayList<>();

                for (String s : myList) {
                    if (s.equals("") || s.startsWith("#")) {
                        continue;
                    }
                    newList.add(s);
                }
                DefaultListModel<String> dfm = new DefaultListModel<>();
                for (String s : newList) {
                    dfm.addElement(s);
                }
                DefaultListModel model = (DefaultListModel)list.getModel();
                ((DefaultListModel) list.getModel()).removeAllElements();
                //listModel.removeAllElements();
                ((DefaultListModel<String>) list.getModel()).addAll(newList);
            } catch (IOException ex) {

            }
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Datumi().setVisible(true);
        });
    }
}
