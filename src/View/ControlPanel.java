package View;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    public ControlPanel(FontMetrics fnt, Color bg_colour) {
        super();
        super.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        inputVertBeg = new JTextField();
        inputVertEnd = new JTextField();
        outputOptimalPath = new JTextField();
        outputOptimalPathLength = new JTextField();
        findOptimalPath = new JButton();
        deleteGraph = new JButton();

        String sample = "Найти оптимальный путь";
        Dimension minInputSize = new Dimension(fnt.stringWidth(sample), fnt.getFont().getSize() + 2);
        Dimension prefInputSize = new Dimension(minInputSize);
        Dimension maxInputSize = new Dimension((int)(minInputSize.width * 1.5), (minInputSize.height));

        inputVertBeg.setMinimumSize(minInputSize);
        inputVertBeg.setPreferredSize(prefInputSize);
        inputVertBeg.setMaximumSize(maxInputSize);

        inputVertEnd.setMinimumSize(minInputSize);
        inputVertEnd.setPreferredSize(prefInputSize);
        inputVertEnd.setMaximumSize(maxInputSize);

        Dimension minOutputSize = new Dimension((int)(minInputSize.width * 2.5), minInputSize.height);
        Dimension prefOutputSize = new Dimension(minOutputSize);
        Dimension maxOutputSize = new Dimension(Short.MAX_VALUE, (minInputSize.height));

        outputOptimalPath.setMinimumSize(minOutputSize);
        outputOptimalPath.setPreferredSize(prefOutputSize);
        outputOptimalPath.setMinimumSize(maxOutputSize);

        outputOptimalPathLength.setMinimumSize(minOutputSize);
        outputOptimalPathLength.setPreferredSize(prefOutputSize);
        outputOptimalPathLength.setMinimumSize(maxOutputSize);

        inputsGroup = new JPanel();
        outputsGroup = new JPanel();
        buttonsGroup = new JPanel();

        inputsGroup.setLayout(new BoxLayout(inputsGroup, BoxLayout.LINE_AXIS));
        inputsGroup.add(inputVertBeg);
        inputsGroup.add(Box.createHorizontalGlue());
        inputsGroup.add(inputVertEnd);

        outputsGroup.setLayout(new BoxLayout(outputsGroup, BoxLayout.PAGE_AXIS));
        outputsGroup.add(outputOptimalPath);
        outputsGroup.add(outputOptimalPathLength);

        String buttonFindLabel = "Найти оптимальный путь";
        String buttonDeleteLabel = "Очистить граф";
        findOptimalPath.setText(buttonFindLabel);
        deleteGraph.setText(buttonDeleteLabel);
        Dimension minButtonFindSize = new Dimension((int)(minOutputSize.width * 0.5), fnt.getFont().getSize() * 2 + 2);
        Dimension minButtonDeleteSize = new Dimension((int)(minOutputSize.width * 0.4), fnt.getFont().getSize() * 2 + 2);
        Dimension prefButtonFindSize = new Dimension(minButtonFindSize);
        Dimension prefButtonDeleteSize = new Dimension(minButtonDeleteSize);
        Dimension maxButtonFindSize = new Dimension((int)(Short.MAX_VALUE * 0.5), fnt.getFont().getSize() * 2 + 2);
        Dimension maxButtonDeleteSize = new Dimension((int)(Short.MAX_VALUE * 0.4), fnt.getFont().getSize() * 2 + 2);

        findOptimalPath.setMinimumSize(minButtonFindSize);
        findOptimalPath.setPreferredSize(prefButtonFindSize);
        findOptimalPath.setMaximumSize(maxButtonFindSize);

        deleteGraph.setMinimumSize(minButtonDeleteSize);
        deleteGraph.setPreferredSize(prefButtonDeleteSize);
        deleteGraph.setMaximumSize(maxButtonDeleteSize);

        buttonsGroup.setLayout(new BoxLayout(buttonsGroup, BoxLayout.LINE_AXIS));
        buttonsGroup.add(findOptimalPath);
        buttonsGroup.add(Box.createHorizontalGlue());
        buttonsGroup.add(deleteGraph);

        add(inputsGroup);
        add(outputsGroup);
        add(buttonsGroup);

        inputsGroup.setBackground(bg_colour);
        outputsGroup.setBackground(bg_colour);
        buttonsGroup.setBackground(bg_colour);
    }

    private JTextField inputVertBeg;
    private JTextField inputVertEnd;
    private JTextField outputOptimalPath;
    private JTextField outputOptimalPathLength;
    private JButton findOptimalPath;
    private JButton deleteGraph;

    private JPanel inputsGroup;
    private JPanel outputsGroup;
    private JPanel buttonsGroup;
}
