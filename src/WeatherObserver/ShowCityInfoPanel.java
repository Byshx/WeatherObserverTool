package WeatherObserver;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ShowCityInfoPanel extends JPanel {

	JLabel cityname, temperature, wind_D, wind_S, time;
	JFrame jframe = new JFrame("");

	public ShowCityInfoPanel(String[] info, Dimension jframeDimen, int LocationY) {
		// TODO Auto-generated constructor stub

		// 城市名称
		cityname = new JLabel(info[0]);

		// 温度信息
		temperature = new JLabel(info[2]);

		// 风向信息
		wind_D = new JLabel(info[3]);

		// 风速信息

		wind_S = new JLabel(info[4]);

		// 设置面板
		setSize(200, 200);

		// 设置布局

		setLayout(null);
		setBorder(BorderFactory.createEtchedBorder());

		// 添加标签
		add(cityname);
		add(temperature);
		//add(time);
		add(wind_D);
		add(wind_S);
		jframe.setSize(500, 500);
		jframe.add(this,BorderLayout.CENTER);
		jframe.setVisible(true);
	}

	public static void main(String[] args) {
		new ShowCityInfoPanel(new String[] { "荆州", "12", "20", "东北", "1级" }, new Dimension(200, 100), 0);
	}
}
