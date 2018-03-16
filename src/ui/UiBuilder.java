package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONObject;

import app.Main;

public class UiBuilder {
	
	final static String TYPE_ATTR="type";
	final static String TEXT_ATTR="text";

	public static void createApp(String file) throws Exception{
		JSONObject obj=loadJSON(new File(file));
		String title=obj.getString("title");
		JFrame f=new JFrame(title);
		f.setSize(320,480);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		JPanel p=new JPanel();
		
		f.add(p,BorderLayout.NORTH);
		
		addComponent(p, obj);
		
	}
	
	public static void addComponent(Container c,JSONObject obj){
		Iterator<Object> items=(obj.getJSONArray("components").iterator());
		while (items.hasNext()) {
			JSONObject item=(JSONObject)(items.next());
			String type=item.getString(UiBuilder.TYPE_ATTR);
			if (type.equals("row")){
				c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
				JPanel panel=new JPanel();
				c.add(panel);
				addComponent(panel, item);
			}
			if (type.equals("label")){
				String text=item.getString(UiBuilder.TEXT_ATTR);
				c.add(new JLabel(text));
			}
			if (type.equals("button")){
				String text=item.getString(UiBuilder.TEXT_ATTR);
				c.add(new JButton(text));
			}
			if (type.equals("textfield")){
				String text=item.getString(UiBuilder.TEXT_ATTR);
				c.add(new JTextField(text));
			}
				
		}
	}
	
	public static JSONObject loadJSON(File f) throws Exception{
		String sourceData=loadFile(f);
		return new JSONObject(sourceData);
	}

	public static String loadFile(File f) throws Exception {
		StringBuffer result=new StringBuffer();
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		String line = reader.readLine();
		while (line != null) {
		   result.append(line);
		   result.append("\n");
		   line = reader.readLine();
		 }
		reader.close();
		return result.toString();
	}
	

}
