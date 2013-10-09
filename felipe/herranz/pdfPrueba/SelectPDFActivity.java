package felipe.herranz.pdfPrueba;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectPDFActivity extends Activity 
{
	private ListView listaPDF;
	private List<String> pdfNames;
	private final String pdfPath = "mnt/sdcard/pdfTest";
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_pdf);
		listaPDF = (ListView) findViewById(R.id.listView1);
		new LoadPDFNames().execute();
	}

	private class LoadPDFNames extends AsyncTask<Void,Integer,Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String sdStatus = Environment.getExternalStorageState();
			File pdfFolder = new File(pdfPath);
			if(sdStatus.equals(Environment.MEDIA_MOUNTED))
			{
				if(pdfFolder.exists())
				{
					pdfNames = new ArrayList<String>(Arrays.asList(pdfFolder.list()));
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void voidValue)
		{
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,pdfNames);
			listaPDF.setAdapter(adapter);
			listaPDF.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) 
				{
					// TODO Auto-generated method stub
					intent = new Intent(SelectPDFActivity.this,ScreenPDFActivity.class);
					intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME,pdfPath + "/" + pdfNames.get(arg2));
					startActivity(intent);
				}
				
			});
		}
		
	}

}

