package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicBorders;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JButton startButton;	
	private JButton stopButton; 
	
	private void buttonInit() {
		startButton = new JButton("开始搜索");
		startButton.setFont(new Font("宋体", Font.PLAIN, 14));
		startButton.setLocation( 40, Const.FRAME_HEIGHT - 100);
		startButton.setSize(100, 35);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
		});
		
		stopButton = new JButton("停止");
		stopButton.setEnabled(false);
		stopButton.setFont(new Font("宋体", Font.PLAIN, 14));
		stopButton.setLocation( 180, Const.FRAME_HEIGHT - 100);
		stopButton.setSize(70, 35);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopButton.setEnabled(false);
				startButton.setEnabled(true);
			}
		});
	}
	
	public MainWindow() {
		super();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("File Searcher");
		setBackground(Color.WHITE);
		setLocation(WinUtils.WIDTH / 4, WinUtils.HEIGHT / 4);
		setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		JPanel queryPanel = new JPanel(null);
		queryPanel.setSize(Const.FRAME_WIDTH - 40, Const.FRAME_HEIGHT - 120);
		queryPanel.setBorder(BorderFactory.createTitledBorder("查询条件"));
		queryPanel.setBackground(getContentPane().getBackground());
		queryPanel.setLocation(10, 10);
		add(queryPanel);
		
		JCheckBox checkBox = new JCheckBox("asd");
		
		checkBox.setLocation(50, 50);
		checkBox.setSize(50, 30);
		checkBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println(e.getID());
				System.out.println(e.getStateChange());
			}
		});
		
		add(checkBox);
		
		buttonInit();
		add(startButton);
		add(stopButton);
		
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
}
