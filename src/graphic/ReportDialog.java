package graphic;

import logic.Repository;
import utils.BDStateListener;
import utils.Call;
import utils.Callback;
import vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class ReportDialog extends JDialog  {

    private final Repository repository;
    private int imageListSize;
    private JPanel imagePanel;
    private List<JLabel> label;
    private int imageNum = 0;
    private List<JLabel> labelList;

    public ReportDialog( String title) {
        super(new JFrame(), title, true);
        repository = Repository.getInstance();
        imageListSize = repository.getImages().size();
        imagePanel = new JPanel();
        int rows = imageNum / 2;
        imagePanel.setLayout(new GridLayout(rows, 3, 15, 20));

        setSize(700, 350);
        setLayout(new BorderLayout());
        add(imagePanel, BorderLayout.NORTH);
        setImageList();
        setModal(false);



       Repository repository = Repository.getInstance();
       Callback callback = repository.updatedInventoryReport();
       callback.observe(new Call() {
           @Override
           public void onFinishProcess(boolean state) {
               //JOptionPane.showMessageDialog(null,"Jeep was added successfully !");
               //setVisible(false);
           }

           @Override
           public void onStartProcess() {
               //okDetailsButton.setEnabled(false);
           }
       });
       repository.addOnRepChangeListener(new BDStateListener() {
           @Override
           public void onStateChange(List<Vehicle> updatedBD) {
               imagePanel.removeAll();
               setImageList();
               repaint();
               revalidate();
           }
       });

    }


    public void setImageList() {
        Map<Vehicle, ImageIcon> images = repository.getImages();
        labelList = new ArrayList<>();
        System.out.println(images.size());

        for (Map.Entry<Vehicle, ImageIcon> entry : images.entrySet()) {
            JLabel label = new JLabel();
            label.setIcon(entry.getValue());
            label.setToolTipText(entry.getKey().toString());
            labelList.add(label);
            imagePanel.add(label);
        }
    }

}





