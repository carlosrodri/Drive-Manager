package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import constants.ConstantsUI;
import models.entities.User;


public class JSONFileManager {

	public static ArrayList<User> readFile() throws FileNotFoundException, IOException{
		JSONParser parser = new JSONParser();  
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(ConstantsUI.PATH+".json"));
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		JSONArray listJSON = (JSONArray) obj;
		ArrayList<User> list = new ArrayList<>();
		for (Object object : listJSON) {
			JSONObject objCyclist = new JSONObject();

			objCyclist = (JSONObject) object;

			JSONObject o = (JSONObject) objCyclist.get("User");

			list.add(new User(o.get("name").toString(), new File(o.get("file").toString())));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public void writeFile(String path, List<User> userlist) {
		JSONObject obj = null;

		JSONObject topObj = null;

		JSONArray list = new JSONArray();
		if(list != null) {
			for (User user : userlist) {
				obj = new JSONObject();
				topObj = new JSONObject();
				topObj.put("name", user.getName());
				topObj.put("file", user.getDirectory().getPath());

				obj.put("User", topObj);

				list.add(obj);
			}
		}

		try {

			FileWriter file = new FileWriter(path + ".json", false);
			file.write(list.toJSONString());
			file.flush();
			file.close();


		} catch (IOException e) {
		}
	}
}