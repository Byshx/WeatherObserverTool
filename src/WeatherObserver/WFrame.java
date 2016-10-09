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

	// ����л�
	private JTabbedPane jtp;
	private City citylist = null;
	private CityCode cityCode = null;
	private AnalyseJSON analyseJSON = new AnalyseJSON();

	public WFrame() {
		// TODO Auto-generated constructor stub

		// ��ʼ�����д���
		try {
			cityCode = new CityCode();
		} catch (IOException io) {
			JOptionPane.showMessageDialog(this, "��ȡ���д������");
		}

		// ��ȡ�������Ļ��С��Ϣ
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int ScreenWidth = (int) dimension.getWidth();
		int ScreenHeight = (int) dimension.getHeight();

		// ����JFrame������Ϣ

		setBounds(ScreenWidth / 4, ScreenHeight / 8, 1000, 800);
		setTitle("�й�����ʵʱ��⹤�� ---Byshx");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ��ʼ��JComboBox
		jcb = new JComboBox<String>();
		jcb.addItem("�����б�");

		// ���ñ�ǩ
		jl1 = new JLabel("������Ҫ�۲�ĳ���", JLabel.CENTER);
		jl2 = new JLabel("�۲����:", JLabel.CENTER);

		// ����JButton��ť
		jb1 = new JButton("ȷ��");
		jb2 = new JButton("ˢ����Ϣ");
		jb3 = new JButton("�������");
		jb4 = new JButton("ɾ������");

		// ��ʼ���ı���
		jtf = new JTextField(10);

		// ��ʼ���ı���
		jta = new JTextArea();
		jta.setEditable(false);

		// ��ʼ�����
		jp_Left = new JPanel();
		jp_Right = new JPanel();
		jp_Right_1 = new JPanel();
		jp_Right_1.setBorder(BorderFactory.createEtchedBorder());
		jp_Right_2 = new JPanel();
		jp_Right_2.setBorder(BorderFactory.createEtchedBorder());
		jp_Right.setPreferredSize(new Dimension(this.getSize().width * 1 / 3, this.getSize().height * 12 / 13));
		jp_Right.setBorder(BorderFactory.createTitledBorder("����"));

		jp = new JPanel(new GridLayout(10, 1));

		jp1 = new JScrollPane();
		jp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// jp1.setSize(new Dimension(this.getSize().width * 2 / 3,
		// this.getSize().height * 12 / 13));
		jp2 = new JScrollPane(jta);
		jp2.setSize(new Dimension(this.getSize().width * 2 / 3, this.getSize().height * 12 / 13));

		// �����л����
		jtp = new JTabbedPane();
		jtp.setPreferredSize(new Dimension(this.getSize().width * 2 / 3, this.getSize().height * 12 / 13));
		jtp.add(jp1, "����������Ϣ");
		jtp.add(jp2, "������־");

		// ��ӵ�����
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
				jcb.addItem("�����б�");
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
				JOptionPane.showMessageDialog(wf, "�����������δ�ҵ�!");
		}
	}

	public void JTextgetStatus(String status) {
		jta.setText(jta.getText() + status);
	}

	public void getJSON(String json) {
		String getBackInfo[] = analyseJSON.analyse(json);
		// ��jp1����ϵ����ȫ���ͷ�
		jp1.getViewport().removeAll();
		// ���������
		JPanel city = new JPanel();
		city.setBorder(BorderFactory.createEtchedBorder());
		// GridLayout gridLayout = new GridLayout(4, 1, 10, 10);
		JLabel cityname, temperature, wind_D, wind_S;
		Color color = Color.BLACK;

		// ��������
		cityname = new JLabel("������������" + getBackInfo[0]);
		cityname.setFont(new Font("΢���ź�", 1, 25));
		cityname.setForeground(color);

		// �¶���Ϣ
		temperature = new JLabel("     �¶�:" + getBackInfo[2] + "��");
		temperature.setFont(new Font("΢���ź�", 0, 15));
		temperature.setForeground(color);

		// ������Ϣ
		wind_D = new JLabel("    " + getBackInfo[3]);
		wind_D.setFont(new Font("΢���ź�", 0, 15));
		wind_D.setForeground(color);

		// ������Ϣ

		wind_S = new JLabel("   ����:" + getBackInfo[4]);
		wind_S.setFont(new Font("΢���ź�", 0, 15));
		wind_S.setForeground(color);

		// Э������
		JLabel tmp = new JLabel("��������������");
		tmp.setFont(new Font("΢���ź�", 1, 25));
		tmp.setForeground(color);

		//

		city.add(cityname);
		city.add(temperature);
		city.add(wind_D);
		city.add(wind_S);
		city.add(tmp);

		// ����������Ϣ
		city.setToolTipText("<html><body>����ID:" + getBackInfo[1] + "<br>" + "���ʪ��:" + getBackInfo[5] + "<br>"
				+ "�Ƿ����״�ͼ:" + ((getBackInfo[8].equals("1")) ? "��" : "��") + "<br>" + "�״�ͼ��ַ:"
				+ "http://www.weather.com.cn/html/radar/" + getBackInfo[9] + "<br>" + "�������:" + getBackInfo[12] + "%<br>"
				+ "���ݸ���ʱ��:" + getBackInfo[7] + "</body></html>");

		// �������λ��
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
