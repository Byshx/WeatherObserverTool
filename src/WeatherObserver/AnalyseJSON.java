package WeatherObserver;

import java.util.Iterator;
import net.sf.json.JSONObject;

public class AnalyseJSON {

	private JSONObject jsonObject = null;

	public String[] analyse(String info) {
		// »•µÙ ’Œ≤»ﬂ”‡œÓ
		String[] temp = info.split("\r\n");
		String[] cityInfo = new String[13];
		jsonObject = JSONObject.fromObject(temp[1]);
		Iterator<?> it = jsonObject.keys();
		jsonObject = JSONObject.fromObject(jsonObject.get(it.next()));

		cityInfo[0] = jsonObject.getString("city");
		cityInfo[1] = jsonObject.getString("cityid");
		cityInfo[2] = jsonObject.getString("temp");
		cityInfo[3] = jsonObject.getString("WD");
		cityInfo[4] = jsonObject.getString("WS");
		cityInfo[5] = jsonObject.getString("SD");
		cityInfo[6] = jsonObject.getString("WSE");
		cityInfo[7] = jsonObject.getString("time");
		cityInfo[8] = jsonObject.getString("isRadar");
		cityInfo[9] = jsonObject.getString("Radar");
		cityInfo[10] = jsonObject.getString("njd");
		cityInfo[11] = jsonObject.getString("qy");
		cityInfo[12] = jsonObject.getString("rain");
		return cityInfo;
	}
}
