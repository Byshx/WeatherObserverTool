package WeatherObserver;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherConfig extends Observer {
	/**
	 * @author Byshx
	 * @since 2016.10.8
	 */
	String citycode = "";
	Object object = null;

	public WeatherConfig(String citycode, Object object) {
		// TODO Auto-generated constructor stub
		this.citycode = citycode;
		this.object = object;
	}

	public void getMessage() throws Exception {
		URL url = new URL("http://www.weather.com.cn/data/sk/" + citycode + ".html");
		String host = url.getHost();
		String path = url.getPath();
		int port = (url.getPort() == -1) ? 80 : url.getPort();
		getStatus("正在连接：" + host + ",端口号:" + port + "\r\n", true);
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(host, port), 3000);
		getStatus("连接成功！\r\n", true);
		OutputStream outputStream1 = socket.getOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream1);
		String request = generateRequest(host, path);
		getStatus("发送请求头：\r\n\r\n", true);
		getStatus(request + "\r\n", false);
		outputStreamWriter.write(request);
		outputStreamWriter.flush();
		getStatus("请求已发送..\r\n\r\n", true);
		InputStream inputStream = socket.getInputStream();
		// socket.setSoTimeout(3000);
		analyseHeader(inputStream);
		analyseContent(inputStream);
		socket.close();
	}

	private void analyseHeader(InputStream inputStream) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		while (true) {
			byte b = (byte) inputStream.read();
			if (b == '\r') {
				b = (byte) inputStream.read();
				if (b == '\n') {
					if (stringBuilder.toString().equals(""))
						break;
					getStatus(stringBuilder.toString() + "\r\n", false);
					stringBuilder.setLength(0);
				}
			} else {
				stringBuilder.append((char) b);
			}
		}
	}

	private void analyseContent(InputStream inputStream) throws Exception {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
		StringBuilder content = new StringBuilder();
		int n = -1;
		while ((n = inputStreamReader.read()) != -1) {
			content.append((char) n + "");
		}
		getJSONInfo(content.toString());
	}

	public String generateRequest(String host, String path) {

		// Accept
		// text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
		// Accept-Encoding
		// gzip, deflate
		// Accept-Language
		// zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
		// Cache-Control
		// max-age=0
		// Connection
		// keep-alive
		// Host
		// www.weather.com.cn
		// Upgrade-Insecure-Requests
		// 1
		// User-Agent
		// Mozilla/5.0 (Windows NT 10.0; WOW64; rv:48.0) Gecko/20100101
		// Firefox/48.0
		StringBuilder stringBuilder = new StringBuilder();
		String Get = "GET " + path + " HTTP/1.1\r\n";
		String Accept = "Accept: text/plain\r\n";// 无格式的正文
		String Accept_Language = "Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3\r\n";
		String Cache_Control = "Cache-Control: max-age=0\r\n";
		String Connection = "Connection: close\r\n";
		String Host = "Host: " + host + "\r\n";
		String Upgrade_Insecure_Requests = "Upgrade-Insecure-Requests: 1\r\n";
		String User_Agent = "User-Agent: A Java Program\r\n\r\n";
		stringBuilder.append(Get).append(Accept).append(Accept_Language).append(Cache_Control).append(Connection)
				.append(Host).append(Upgrade_Insecure_Requests).append(User_Agent);
		return stringBuilder.toString();
	}

	// 使用反射获得方法，增加代码复用性
	private void getStatus(String status, boolean addTime) {
		Method method1 = null;
		try {
			method1 = object.getClass().getDeclaredMethod("JTextgetStatus", String.class);

		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (addTime)
				status = getTime() + status;
			method1.invoke(object, status);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getJSONInfo(String json) {
		Method method2 = null;
		try {
			method2 = object.getClass().getDeclaredMethod("getJSON", String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			method2.invoke(object, json);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getTime() {
		SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
		return s.format(new Date()) + ":";
	}

	@Override
	void UpdateInfo() throws Exception {
		// TODO Auto-generated method stub
		getMessage();
	}

}
