package com.project.admuc.qrgossip.adaptor;

import com.project.admuc.qrgossip.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MobileArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public MobileArrayAdapter(Context context, String[] values) {
		super(context, R.layout.list_main, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_main, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		System.out.println(s);

		if (s.equals("Profile")) {
			imageView.setImageResource(R.drawable.menu_profile);
		} else if (s.equals("Clipboard")) {
			imageView.setImageResource(R.drawable.menu_clipboard);
		} else if (s.equals("Contacts")) {
			imageView.setImageResource(R.drawable.menu_contacts);
		} else if (s.equals("Messages")) {
			imageView.setImageResource(R.drawable.menu_messages);
		} else if (s.equals("Web Links")) {
			imageView.setImageResource(R.drawable.menu_link);
		} else if (s.equals("Help")) {
			imageView.setImageResource(R.drawable.menu_help);
		} else if (s.equals("About")) {
			imageView.setImageResource(R.drawable.menu_about);
		} else {
			imageView.setImageResource(R.drawable.menu_help);
		}

		return rowView;
	}
}
