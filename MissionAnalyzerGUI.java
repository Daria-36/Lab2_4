package gui;

import model.Mission;
import parser.MissionParser;
import parser.ParserFactory;
import report.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

public class MissionAnalyzerGUI extends JFrame {
    private JTextArea reportArea;
    private JLabel statusLabel;
    private JComboBox<String> reportTypeComboBox;
    private ReportContext reportContext;
    
    public MissionAnalyzerGUI() {
        setTitle("Анализатор миссий - Токийский магический колледж (Стабилизированная архитектура)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        reportContext = new ReportContext();
        reportContext.setGenerator(new DetailedReportGenerator());
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Верхняя панель
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton openButton = new JButton("Открыть файл миссии");
        openButton.addActionListener(e -> openMissionFile());
        
        JButton saveButton = new JButton("Сохранить отчёт");
        saveButton.addActionListener(e -> saveReport());
        
        JButton clearButton = new JButton("Очистить");
        clearButton.addActionListener(e -> {
            reportArea.setText("");
            statusLabel.setText("Готов к работе");
        });
        
        reportTypeComboBox = new JComboBox<>(new String[]{"Детализированный", "Краткий", "По рискам"});
        reportTypeComboBox.addActionListener(e -> {
            switch (reportTypeComboBox.getSelectedIndex()) {
                case 0: reportContext.setGenerator(new DetailedReportGenerator()); break;
                case 1: reportContext.setGenerator(new SummaryReportGenerator()); break;
                case 2: reportContext.setGenerator(new RiskReportGenerator()); break;
            }
        });
        
        topPanel.add(openButton);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(new JLabel("Тип отчёта:"));
        topPanel.add(reportTypeComboBox);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(saveButton);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(clearButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Центральная область
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setBackground(new Color(245, 245, 245));
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Отчёт о миссии"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Нижняя панель
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        statusLabel = new JLabel("Готов к работе. Выберите файл миссии.");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        bottomPanel.add(statusLabel, BorderLayout.WEST);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void openMissionFile() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Выберите файл с данными о миссии");
        fileChooser.setFileFilter(new FileNameExtensionFilter(
            "Файлы миссий (JSON, XML, TXT, YAML)", "json", "xml", "txt", "yaml", "yml"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            processFile(file);
        }
    }
    
    private void processFile(File file) {
        try {
            statusLabel.setText("Чтение файла: " + file.getName());
            
            String content = Files.readString(file.toPath());
            MissionParser parser = ParserFactory.getParser(file.getName(), content);
            
            if (parser == null) {
                throw new Exception("Не удалось определить формат файла");
            }
            
            statusLabel.setText("Анализ данных миссии...");
            Mission mission = parser.parse(content);
            
            String report = reportContext.generateReport(mission);
            reportArea.setText(report);
            
            int sorcererCount = mission.getSorcerers() != null ? mission.getSorcerers().size() : 0;
            int techniqueCount = mission.getTechniques() != null ? mission.getTechniques().size() : 0;
            
            statusLabel.setText(String.format(
                "Миссия %s | Результат: %s | Участников: %d | Техник: %d",
                mission.getMissionId(),
                mission.getOutcome().getDisplayName(),
                sorcererCount,
                techniqueCount
            ));
            
        } catch (Exception e) {
            statusLabel.setText("Ошибка: " + e.getMessage());
            reportArea.setText("Ошибка при обработке файла:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void saveReport() {
        if (reportArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Нет данных для сохранения. Сначала загрузите миссию.",
                "Предупреждение", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Сохранить отчёт");
        fileChooser.setSelectedFile(new File("mission_report.txt"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                Files.writeString(file.toPath(), reportArea.getText());
                statusLabel.setText("Отчёт сохранён в: " + file.getName());
                JOptionPane.showMessageDialog(this, 
                    "Отчёт успешно сохранён!", 
                    "Успех", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                statusLabel.setText("Ошибка сохранения: " + e.getMessage());
                JOptionPane.showMessageDialog(this, 
                    "Ошибка при сохранении: " + e.getMessage(),
                    "Ошибка", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}