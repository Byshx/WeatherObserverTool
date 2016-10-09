package WeatherObserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CityCode {

	private HashMap<String, String> citycode = new HashMap<String, String>();

	public CityCode() throws IOException {
		File file = new File("./src/citycode.code");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String in = "";
		while ((in = bufferedReader.readLine()) != null) {
			if (in.equals(""))
				continue;
			String tmp[] = in.split("=");
			citycode.put(tmp[1], tmp[0]);
		}
		bufferedReader.close();
	}

	public String getCode(String cityname) {
		return citycode.get(cityname);
	}
}
