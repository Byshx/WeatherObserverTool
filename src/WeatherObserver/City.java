package WeatherObserver;

import java.util.Vector;

public class City {

	Vector<Observer> list = new Vector<Observer>();
	WFrame wFrame = null;

	public City(WFrame w) {
		// TODO Auto-generated constructor stub
		wFrame = w;
	}

	public void city(Observer w) {
		list.add(w);
	}

	public void notifyAllCity() {
		for (int i = 0; i < list.size(); i++) {
			try {
				list.get(i).UpdateInfo();
			} catch (Exception e) {
				// TODO: handle exception
				wFrame.JTextgetStatus(e.getMessage() + "\r\n");
			}

		}
	}
	
	//移除所有城市
	public void ClearAllCity() {
		list.clear();
	}
	
	//移除指定城市
	public void ClearCity(int index) {
		list.remove(index);
	}

}
