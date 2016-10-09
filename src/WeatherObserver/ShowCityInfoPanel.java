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

		// ��������
		cityname = new JLabel(info[0]);

		// �¶���Ϣ
		temperature = new JLabel(info[2]);

		// ������Ϣ
		wind_D = new JLabel(info[3]);

		// ������Ϣ

		wind_S = new JLabel(info[4]);

		// �������
		setSize(200, 200);

		// ���ò���

		setLayout(null);
		setBorder(BorderFactory.createEtchedBorder());

		// ��ӱ�ǩ
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
		new ShowCityInfoPanel(new String[] { "����", "12", "20", "����", "1��" }, new Dimension(200, 100), 0);
	}
}
