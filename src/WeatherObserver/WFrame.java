package WeatherObserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("serial")
public class WFrame extends JFrame {

	private JPanel jp, jp_Left, jp_Right;
	private JPanel jp_Right_1, jp_Right_2;
	private JLabel jl1, jl2;
	private JButton jb1, jb2, jb3, jb4;
	private JScrollPane jp1, jp2;
	private JTextArea jta;
	private JTextField jtf;
	private JComboBox<String> jcb;

	// 面板切换
	private JTabbedPane jtp;
	private City citylist = null;
	private CityCode cityCode = null;
	private AnalyseJSON analyseJSON = new AnalyseJSON();

	public WFrame() {
		// TODO Auto-generated constructor stub

		// 初始化城市代码
		try {
			cityCode = new CityCode();
		} catch (IOException io) {
			JOptionPane.showMessageDialog(this, "获取城市代码错误！");
		}

		// 获取计算机屏幕大小信息
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int ScreenWidth = (int) dimension.getWidth();
		int ScreenHeight = (int) dimension.getHeight();

		// 设置JFrame容器信息

		setBounds(ScreenWidth / 4, ScreenHeight / 8, 1000, 800);
		setTitle("中国天气实时监测工具 ---Byshx");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 初始化JComboBox
		jcb = new JComboBox<String>();
		jcb.addItem("城市列表");

		// 设置标签
		jl1 = new JLabel("输入需要观测的城市", JLabel.CENTER);
		jl2 = new JLabel("观测城市:", JLabel.CENTER);

		// 设置JButton按钮
		jb1 = new JButton("确定");
		jb2 = new JButton("刷新信息");
		jb3 = new JButton("清空所有");
		jb4 = new JButton("删除城市");

		// 初始化文本框
		jtf = new JTextField(10);

		// 初始化文本域
		jta = new JTextArea();
		jta.setEditable(false);

		// 初始化面板
		jp_Left = new JPanel();
		jp_Right = new JPanel();
		jp_Right_1 = new JPanel();
		jp_Right_1.setBorder(BorderFactory.createEtchedBorder());
		jp_Right_2 = new JPanel();
		jp_Right_2.setBorder(BorderFactory.createEtchedBorder());
		jp_Right.setPreferredSize(new Dimension(this.getSize().width * 1 / 3, this.getSize().height * 12 / 13));
		jp_Right.setBorder(BorderFactory.createTitledBorder("功能"));

		jp = new JPanel(new GridLayout(10, 1));

		jp1 = new JScrollPane();
		jp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// jp1.setSize(new Dimension(this.getSize().width * 2 / 3,
		// this.getSize().height * 12 / 13));
		jp2 = new JScrollPane(jta);
		jp2.setSize(new Dimension(this.getSize().width * 2 / 3, this.getSize().height * 12 / 13));

		// 设置切换面板
		jtp = new JTabbedPane();
		jtp.setPreferredSize(new Dimension(this.getSize().width * 2 / 3, this.getSize().height * 12 / 13));
		jtp.add(jp1, "城市天气信息");
		jtp.add(jp2, "网络日志");

		// 添加到容器
		GridLayout gridLayout1 = new GridLayout(3, 1, 10, 10);
		jp_Right_1.setLayout(gridLayout1);
		jp_Right_1.add(jl1);
		jp_Right_1.add(jtf);
		jp_Right_1.add(jb1);
		jp_Right_1.setPreferredSize(new Dimension(200, 100));

		GridLayout gridLayout2 = new GridLayout(2, 3, 10, 10);
		jp_Right_2.setLayout(gridLayout2);
		jp_Right_2.add(jl2);
		jp_Right_2.add(jcb);
		jp_Right_2.add(jb2);
		jp_Right_2.add(jb3);
		jp_Right_2.add(jb4);
		jp_Right.add(jp_Right_1);
		jp_Right.add(jp_Right_2);

		jp_Left.add(jtp, BorderLayout.CENTER);
		jp_Left.add(jp_Right, BorderLayout.EAST);
		add(jp_Left, BorderLayout.WEST);

		setVisible(true);
	}

	public void setButtonAction(WFrame wf) {
		citylist = new City(wf);
		jtf.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					DealAction(wf);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DealAction(wf);
			}
		});

		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jp.removeAll();
				citylist.notifyAllCity();
			}
		});

		jb3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jp.removeAll();
				jp.repaint();
				citylist.ClearAllCity();
				jcb.removeAllItems();
				jcb.addItem("城市列表");
			}
		});

		jb4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = jcb.getSelectedIndex();
				if (index != 0) {
					jcb.removeItemAt(index);
					citylist.ClearCity(index - 1);
				}
			}
		});
	}

	private void DealAction(WFrame wf) {
		String get = jtf.getText();
		jtf.setText("");
		String code = cityCode.getCode(get);
		if (code != null) {
			jcb.addItem(get);
			WeatherConfig w = new WeatherConfig(code, wf);
			citylist.city(w);
		} else {
			if (!get.equals(""))
				JOptionPane.showMessageDialog(wf, "输入错误或城市未找到!");
		}
	}

	public void JTextgetStatus(String status) {
		jta.setText(jta.getText() + status);
	}

	public void getJSON(String json) {
		String getBackInfo[] = analyseJSON.analyse(json);
		// 将jp1面板上的组件全部释放
		jp1.getViewport().removeAll();
		// 设置新面板
		JPanel city = new JPanel();
		city.setBorder(BorderFactory.createEtchedBorder());
		// GridLayout gridLayout = new GridLayout(4, 1, 10, 10);
		JLabel cityname, temperature, wind_D, wind_S;
		Color color = Color.BLACK;

		// 城市名称
		cityname = new JLabel("——————" + getBackInfo[0]);
		cityname.setFont(new Font("微软雅黑", 1, 25));
		cityname.setForeground(color);

		// 温度信息
		temperature = new JLabel("     温度:" + getBackInfo[2] + "℃");
		temperature.setFont(new Font("微软雅黑", 0, 15));
		temperature.setForeground(color);

		// 风向信息
		wind_D = new JLabel("    " + getBackInfo[3]);
		wind_D.setFont(new Font("微软雅黑", 0, 15));
		wind_D.setForeground(color);

		// 风速信息

		wind_S = new JLabel("   风速:" + getBackInfo[4]);
		wind_S.setFont(new Font("微软雅黑", 0, 15));
		wind_S.setForeground(color);

		// 协调美观
		JLabel tmp = new JLabel("———————");
		tmp.setFont(new Font("微软雅黑", 1, 25));
		tmp.setForeground(color);

		//

		city.add(cityname);
		city.add(temperature);
		city.add(wind_D);
		city.add(wind_S);
		city.add(tmp);

		// 设置其他信息
		city.setToolTipText("<html><body>城市ID:" + getBackInfo[1] + "<br>" + "相对湿度:" + getBackInfo[5] + "<br>"
				+ "是否有雷达图:" + ((getBackInfo[8].equals("1")) ? "有" : "无") + "<br>" + "雷达图地址:"
				+ "http://www.weather.com.cn/html/radar/" + getBackInfo[9] + "<br>" + "降雨概率:" + getBackInfo[12] + "%<br>"
				+ "数据更新时间:" + getBackInfo[7] + "</body></html>");

		// 设置面板位置
		if (jp.getComponentCount() > 10)
			jp.setLayout(new GridLayout(jp.getComponentCount() + 1, 1));
		jp.add(city);
		jp1.getViewport().add(jp);
	}

	public static void main(String[] args) {
		WFrame w = new WFrame();
		w.setButtonAction(w);
	}
}
