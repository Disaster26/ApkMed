package apkmed;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Lukas
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {

        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;

    private String label;

    private boolean isPushed;
    ArrayList < Blob > blobbs = new ArrayList < Blob > ();
    public ButtonEditor(JCheckBox checkBox, ArrayList < Blob > blobb) {
        super(checkBox);
        this.blobbs = blobb;
        button = new JButton();
        button.setOpaque(true);
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dow.png")));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {

        InputStream input = null;
        FileOutputStream output = null;
        File theFile = null;
        try {
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Zapisz plik");

            int userSelection = fileChooser.showSaveDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                theFile = fileChooser.getSelectedFile();


                output = new FileOutputStream(theFile);
                input = blobbs.get(row).getBinaryStream();
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }

                button.setText("Pobrano!");
            } else {
                button.setText("Niepowodzenie!");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ButtonEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ButtonEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ButtonEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return button;
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}